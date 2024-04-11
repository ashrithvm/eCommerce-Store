package com.puzzlesapi.persistence;

import com.puzzlesapi.model.Order;

import java.util.List;

public interface OrderDAO extends BasicDAO<Order>{

    /**
     * Get all the orders made by a user.
     * @param buyerId The id of the buyer
     * @return A list of orders made by the buyer in reverse order, meaning latest order appears first.
     */
    List<Order> getOrdersByBuyer(int buyerId);

}
