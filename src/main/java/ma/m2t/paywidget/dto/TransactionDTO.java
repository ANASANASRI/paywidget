package ma.m2t.paywidget.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionDTO {
    private Long transactionId;

    // Order * * * * * *
    private String orderId;
    private double amount;
    private String currency;
    private String status;
    private Date timestamp;

    // Client * * *
    private String clientId;
    private String clientName;
    private String clientEmail;

    // Hmac
    private String hmac;

    // Notif
    private String notif;
}
