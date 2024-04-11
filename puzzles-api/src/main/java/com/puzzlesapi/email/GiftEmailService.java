package com.puzzlesapi.email;

import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Gift;
import com.puzzlesapi.model.Puzzle;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class GiftEmailService {
    private final String SENDER_EMAIL = "bestpuzzles.store@gmail.com";
    private final EmailService emailService;
    private final String HEADER_FORMAT = "Puzzle Gift From %s";
    private final String MESSAGE_FORMAT =
       """
       Hello! Best Puzzles store is happy to send you a gift from your friend %s.
       The gift is an order with id %d. Puzzles included in the order are: %s.
       Your friend is very eager for you to read their message:
       `%s`
       We hope you enjoy your gift!
       """;

    private final String HTML_FORMAT = """
        <html>
        <body>
            <h2>Hello!</h2>
            <p>Best Puzzles store is happy to send you a gift from your friend %s.</p>
            <p>Order will get shipped soon to you address.</p>
            %s
            <h2>Message from %s:</h2>
            <p><i>%s</i></p>
            <p>We hope you enjoy your gift!</p>
        </body>
        </html>
        """;
    public GiftEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    private String prepareImageHTML(Gift gift) {
        StringBuilder imageHTML = new StringBuilder();
        for(CustomPuzzleDTO puzzle : gift.getOrder().getCustomPuzzles()){
            imageHTML.append(String.format("<img src=\"%s\" alt=\"%s\" width=\"100\" height=\"100\">", puzzle.getImageURL(), puzzle.getName()));
            imageHTML.append(String.format("<h2>%s(Custom)</h2>", puzzle.getName()));
            imageHTML.append("<p>Designed by you<p><br>");
        }
        for (PuzzleItemDTO puzzleItem : gift.getOrder().getItems()) {
            Puzzle puzzle = puzzleItem.getPuzzle();
            imageHTML.append(String.format("<img src=\"%s\" alt=\"%s\" width=\"100\" height=\"100\">", puzzle.getImageURL(), puzzle.getName()));
            imageHTML.append(String.format("<h2>%s</h2>", puzzle.getName()));
            imageHTML.append(String.format("<p>Description: %s</p><br>", puzzle.getDescription()));
        }
        return imageHTML.toString();
    }

    public void sendGiftEmail(String recipientEmail, Gift gift) throws MessagingException {
        Account buyer = gift.getOrder().getBuyer();
        String senderName = buyer.getUsername();
        String message = gift.getMessage();

        String header = String.format(HEADER_FORMAT, senderName);
        String emailMessage = String.format(HTML_FORMAT, senderName, prepareImageHTML(gift), buyer.getUsername(),  message);
        emailService.sendHtmlEmail(SENDER_EMAIL, recipientEmail, header, emailMessage);
    }

}
