package com.puzzlesapi.email;

import com.puzzlesapi.model.Puzzle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AdminEmailServiceTest {

    @Autowired
    private AdminEmailService adminEmailService;

    @Test
    void testSendAdminEmail() {
        adminEmailService.sendLowStockEmail(new ArrayList<>(List.of(new Puzzle())));
        assert true;
    }
}
