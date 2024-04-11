package com.puzzlesapi.email;

import com.puzzlesapi.model.Puzzle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminEmailService {
    private final String SENDER_EMAIL = "bestpuzzles.store@gmail.com";
    private final String RECIPIENT_EMAIL = "klejdis36@gmail.com";
    private final EmailService emailService;
    private final String HEADER_FORMAT = "Low Stock Alert";
    private final String HTML_FORMAT = """
        <html>
        <body>
            <h2>These items may need restocking soon.</h2>
            %s
        </body>
        </html>
        """;

    public AdminEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    private String prepareProductsHTML(List<Puzzle> products){
        StringBuilder productsHTML = new StringBuilder();
        for(Puzzle product : products){
            productsHTML.append(String.format("<h2>%s</h2>", product.getName()));
            productsHTML.append(String.format("<img src=\"%s\" alt=\"%s\" width=\"100\" height=\"100\">", product.getImageURL(), product.getName()));
            productsHTML.append(String.format("<p>Stock: %d</p>", product.getQuantity()));
        }
        return productsHTML.toString();
    }

    public void sendLowStockEmail(List<Puzzle> products) {
        String html = String.format(HTML_FORMAT, prepareProductsHTML(products));
        try {
            emailService.sendHtmlEmail(SENDER_EMAIL, RECIPIENT_EMAIL, HEADER_FORMAT, html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
