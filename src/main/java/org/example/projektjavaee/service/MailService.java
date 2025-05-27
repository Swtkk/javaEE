package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.OrderItem;

import java.util.Properties;


import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;
import jakarta.ejb.Stateless;
import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.OrderItem;

import java.util.List;

@Stateless
public class MailService {

    private static final String TOKEN = "b70ece933eeeb5d198bfef26d6138283";
    private static final String FROM_EMAIL = "hello@demomailtrap.co";
    private static final String FROM_NAME = "Sklep JavaEE";

    public void sendOrderConfirmation(String toEmail, Order order) {
        MailtrapConfig config = new MailtrapConfig.Builder()
                .token(TOKEN)
                .build();

        MailtrapClient client = MailtrapClientFactory.createMailtrapClient(config);

        StringBuilder text = new StringBuilder("Dziękujemy za złożenie zamówienia!\n\n");
        for (OrderItem item : order.getItems()) {
            text.append("- ").append(item.getProduct().getName())
                    .append(", ilość: ").append(item.getQuantity()).append("\n");
        }

        MailtrapMail mail = MailtrapMail.builder()
                .from(new Address(FROM_EMAIL, FROM_NAME))
                .to(List.of(new Address(toEmail)))
                .subject("Potwierdzenie zamówienia")
                .text(text.toString())
                .category("Zamówienia")
                .build();

        try {
            System.out.println("Wysyłanie e-maila...");
            System.out.println(client.send(mail));
        } catch (Exception e) {
            System.err.println("Błąd przy wysyłaniu e-maila przez Mailtrap: " + e.getMessage());
        }
    }
}


