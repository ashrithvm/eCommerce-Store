package com.puzzlesapi.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendSimpleMessage() {
        emailService.sendSimpleMessage("a@b", "b@a", "Test", "Test message");
        assert true;
    }

    @Test
    void testSendHtmlEmail() {
        try {
            emailService.sendHtmlEmail("a@b", "b@a", "Test", "<h1>Test message</h1>");
            assert true;
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
}
