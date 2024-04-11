package com.puzzlesapi.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class EmailConfigurationTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testMailSender() {
        assert mailSender instanceof JavaMailSenderImpl;
        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) mailSender;
        assertNotNull(mailSenderImpl);
        assertEquals("smtp.gmail.com", mailSenderImpl.getHost());
        assertEquals(587, mailSenderImpl.getPort());
    }

}
