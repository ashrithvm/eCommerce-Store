package com.puzzlesapi.mapper;

import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.dto.OrderDTO;
import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Order;
import com.puzzlesapi.model.PuzzleItem;
import com.puzzlesapi.persistence.AccountDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps an Order object to an OrderDTO object and vice versa.
 */
@Component
public class OrderMapper {
    private final AccountDAO accountDAO;
    private final PuzzleItemMapper puzzleItemMapper;
    private final CustomPuzzleMapper customPuzzleMapper;

    public OrderMapper(AccountDAO accountDAO, PuzzleItemMapper puzzleItemMapper, CustomPuzzleMapper customPuzzleMapper){
        this.accountDAO = accountDAO;
        this.puzzleItemMapper = puzzleItemMapper;
        this.customPuzzleMapper = customPuzzleMapper;
    }

    /**
     * Maps an Order object to an OrderDTO object.
     * @param order The Order object to be mapped.
     * @return The OrderDTO object.
     */
    public OrderDTO toDTO(Order order) throws IOException {
        Account buyer = accountDAO.get(order.getBuyerId());
        List<PuzzleItemDTO> items = order.getItems().stream()
                .map(puzzleItem -> {
                    try {
                        return puzzleItemMapper.toDTO(puzzleItem);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        List<CustomPuzzleDTO> customPuzzles = order.getCustomPuzzles().stream()
                .map(customPuzzle -> {
                    try {
                        return customPuzzleMapper.toDTO(customPuzzle);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        return new OrderDTO(order.getId(), buyer, items, customPuzzles, order.getTotal());
    }

    /**
     * Maps an OrderDTO object to an Order object.
     * @param orderDTO The OrderDTO object to be mapped.
     * @return The Order object.
     */
    public Order toModel(OrderDTO orderDTO) {
        List<PuzzleItem> items = orderDTO.getItems().stream()
                .map(puzzleItemMapper::toModel).toList();

        List<CustomPuzzle> customPuzzles = orderDTO.getCustomPuzzles().stream()
                .map(customPuzzleMapper::toModel).toList();

        return new Order(orderDTO.getId(), orderDTO.getBuyer().getId(), items, customPuzzles, orderDTO.getTotal());
    }

}
