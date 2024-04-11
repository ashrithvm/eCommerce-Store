package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.email.AdminEmailService;
import com.puzzlesapi.model.Order;
import com.puzzlesapi.model.Puzzle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * A DAO for orders that uses a file to read and write orders.
 * The file is a JSON file that contains an array of orders.
 * The orders are stored in a TreeMap with the order id as the key in decreasing order.
 */
@Component
public class OrderFileDAO extends BasicFileDAO<Order> implements OrderDAO{
    private static final double TAX_RATE = 0.07;
    private final PuzzleDAO puzzleDAO;
    private final CartDAO cartDAO;

    @Autowired
    private AdminEmailService adminEmailService;
    private final TreeMap<Integer, Order> orders = getObjects();

    public OrderFileDAO(@Value("${orders.file}") String filename, PuzzleDAO puzzleDAO, CartDAO cartDAO,  ObjectMapper mapper) throws IOException {
        super(filename, Order[].class, new TreeMap<>(), mapper);
        this.puzzleDAO = puzzleDAO;
        this.cartDAO = cartDAO;
    }

    /**
     * Calculates the total of the order by summing the prices of all the puzzle items in the order
     * @param order The order to calculate the total for
     * @return The total of the order
     */
    private double getTotal(Order order) {
        double sum = order.getItems().stream()
                .mapToDouble(puzzleItem -> {
                    try {
                        return puzzleDAO.get(puzzleItem.getId()).getPrice() * puzzleItem.getQuantity();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
        double customSum = order.getCustomPuzzles().stream()
                .mapToDouble(customPuzzle -> customPuzzle.getPrice() * customPuzzle.getQuantity())
                .sum();
        return getPriceWithTax(sum + customSum);
    }

    private double round(double value, int places){
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    /**
     * Calculates the price of a puzzle item with tax
     * @param price The price of the puzzle item
     * @return The price of the puzzle item with tax rounded to 2 decimal places
     */
    private double getPriceWithTax(double price) {
        return round(price * (1 + TAX_RATE), 2);
    }

    /**
     * Checks if the order is valid.
     * An order is valid if the buyer id is greater than 1 and all the puzzle items in the order exist.
     * @param order The order to check
     * @return True if the order is valid, false otherwise
     */
    private boolean isOrderValid(Order order){
        if(order.getBuyerId() <=1)
            return false;

        return order.getItems().stream().allMatch(puzzleItem -> {
            try {
                return puzzleDAO.get(puzzleItem.getId()) != null;
            } catch (IOException e) {
                throw new RuntimeException("Error reading from puzzles file.");
            }
        });
    }

    /**
     * Updates the stock quantity of the puzzles in the order
     * @param order The order that was made
     */
    private void updateStockQuantity(Order order){
        List<Puzzle> lowStockPuzzles = new LinkedList<>();
        order.getItems().forEach(puzzleItem -> {
            try {
                int purchasedPuzzleId = puzzleItem.getId();
                int purchasedQuantity = puzzleItem.getQuantity();
                Puzzle puzzle = puzzleDAO.get(purchasedPuzzleId);
                puzzle.setQuantity(puzzle.getQuantity() - purchasedQuantity);
                if(puzzle.getQuantity() < 5)
                    lowStockPuzzles.add(puzzle);
                puzzleDAO.update(puzzle);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if(!lowStockPuzzles.isEmpty()) {
            try {
                adminEmailService.sendLowStockEmail(lowStockPuzzles);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a new order in the file and ensures that the total passed in the model is correct.
     * Also updates the stock quantity to account for the puzzles that were purchased.
     * The cart of the buyer is cleared after the order is created.
     * @param model The model object to be created and saved. The id and total fields are ignored. Id is generated by the DAO and
     * total is calculated.
     * @return The saved order object with the new id
     */
    @Override
    public Order create(Order model) throws IOException {
        if(!isOrderValid(model))
            throw new IllegalArgumentException("Order contains invalid puzzle items.");

        model.setTotal(getTotal(model));
        updateStockQuantity(model);
        if(super.create(model) != null){
            cartDAO.clearCart(model.getBuyerId());
            return model;
        }
        return null;
    }

    @Override
    public Order createCopyWithId(Order model, int id) {
        return new Order(id, model.getBuyerId(), model.getItems(), model.getCustomPuzzles(), model.getTotal());
    }

    @Override
    public List<Order> getOrdersByBuyer(int buyerId) {
        List<Order> userOrders = new LinkedList<>();
        synchronized (orders) {
            for(int i = orders.lastKey(); i > 0; i--){
                Order order = orders.get(i);
                if(order.getBuyerId() == buyerId)
                    userOrders.add(order);
            }
        }
        return userOrders;
    }

}