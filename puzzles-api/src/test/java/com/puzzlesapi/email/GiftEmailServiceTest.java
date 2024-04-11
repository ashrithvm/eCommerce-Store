package com.puzzlesapi.email;

import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.dto.OrderDTO;
import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Gift;
import com.puzzlesapi.model.Order;
import com.puzzlesapi.model.Puzzle;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class GiftEmailServiceTest {

    @Autowired
    private GiftEmailService giftEmailService;



    @Test
    void testSendGiftEmail() throws MessagingException {
        OrderDTO orderDTO = new OrderDTO(1, new Account(), List.of(new PuzzleItemDTO()), List.of(new CustomPuzzleDTO()), 10);
        giftEmailService.sendGiftEmail( "b@a", new Gift(1, orderDTO, "a@b", ""));
        assert true;
    }
}
