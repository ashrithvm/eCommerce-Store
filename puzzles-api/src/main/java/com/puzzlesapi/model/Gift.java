package com.puzzlesapi.model;

import com.puzzlesapi.dto.OrderDTO;

/**
 * Represents a gift. A gift has an order, recipient email, and a message.
 */
public class Gift extends ModelTemplate {
    private final OrderDTO order;
    private final String recipientEmail;
    private final String message;

    public Gift(int id, OrderDTO order, String recipientEmail, String message) {
        super(id);
        this.order = order;
        this.recipientEmail = recipientEmail;
        this.message = message;
    }
    public Gift(int id) {
        this(id, new OrderDTO(), "", "");
    }

    public Gift() {
        this(0);
    }

    public OrderDTO getOrder() {
        return order;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getMessage() {
        return message;
    }
}
