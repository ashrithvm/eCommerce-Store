package com.puzzlesapi.controller;

import com.puzzlesapi.dto.OrderDTO;
import com.puzzlesapi.mapper.OrderMapper;
import com.puzzlesapi.model.Order;
import com.puzzlesapi.persistence.OrderDAO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Handles the REST API requests for the Order resource,
 * Utilizes OrderDTO and OrderMapper to handle the data transfer. While only ids are stored for foreign keys, the client retrieves
 * the full information about the buyer and the puzzle items and that is handled by the OrderDTO and OrderMapper.
 */
@RestController
@RequestMapping("orders")
public class OrderController {
    private OrderDAO orderDao;
    private OrderMapper orderMapper;
    private Logger LOG = Logger.getLogger(OrderController.class.getName());
    public OrderController(OrderDAO orderDao, OrderMapper orderMapper){
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    /**
     * Retrieves all orders.
     * @return A list of all orders or a 500 status code if an error occurs
     */
    @RequestMapping("")
    public ResponseEntity<List<OrderDTO>> getAll() throws IOException {
        List<Order> orders;
        try{
            orders = orderDao.getAll();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }

        List<OrderDTO> orderDTOs = orders.stream().map(order -> {
            try {
                return orderMapper.toDTO(order);
            } catch (IOException e) {
                return null;
            }
        }).collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    /**
     * Retrieves all orders made by a buyer.
     * @param buyerId The id of the buyer
     * @return A list of orders made by the buyer or a 500 status code if an error occurs
     */
    @GetMapping("/{buyerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByBuyer(@PathVariable int buyerId) {
        List<Order> orders= orderDao.getOrdersByBuyer(buyerId);

        List<OrderDTO> orderDTOs = orders.stream().map(order -> {
            try {
                return orderMapper.toDTO(order);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).toList();
        return ResponseEntity.ok(orderDTOs);
    }

    /**
     * Creates a new order by taking the orderDTO and converting it to an order object.
     * @param orderDTO The order to create
     * @return The created order or a 500 status code if an error occurs
     */
    @PostMapping("")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) throws IOException {
        LOG.info("POST /orders, orderDTO: " + orderDTO);
        Order order = orderMapper.toModel(orderDTO);
        try{
            if(orderDao.create(order) != null)
                return ResponseEntity.ok(orderDTO);

            return ResponseEntity.status(500).build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        } catch (IllegalArgumentException e) {
            //add the error to the http header
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorMessage", e.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

}
