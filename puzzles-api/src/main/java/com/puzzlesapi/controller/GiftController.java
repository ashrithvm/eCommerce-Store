package com.puzzlesapi.controller;

import com.puzzlesapi.email.GiftEmailService;
import com.puzzlesapi.mapper.OrderMapper;
import com.puzzlesapi.model.Gift;
import com.puzzlesapi.persistence.AccountDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/gifts")
public class GiftController {
    private final GiftEmailService giftEmailService;
    private final AccountDAO accountDAO;
    private final OrderMapper orderMapper;

    public GiftController(GiftEmailService giftEmailService, AccountDAO accountDAO, OrderMapper orderMapper) {
        this.giftEmailService = giftEmailService;
        this.accountDAO = accountDAO;
        this.orderMapper = orderMapper;
    }

    /**
     * Sends a gift email to the recipient.
     * @param gift The gift to send
     * @return A response entity with a boolean indicating if the email was sent successfully.
     * A 500 status code is returned if an error occurs.
     */
    @PostMapping("")
    public ResponseEntity<Boolean> sendGiftEmail(@RequestBody Gift gift) throws IOException{
        Logger.getLogger(GiftController.class.getName()).info("POST /gifts, sending gift email to " + gift.getRecipientEmail());
        try {
            giftEmailService.sendGiftEmail(gift.getRecipientEmail(), gift);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(false);
        }
    }
}
