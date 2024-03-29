package ma.m2t.paywidget.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    // Order * * * * * *
    private String orderId;
    private double amount;
    private String currency;
    private String status;
    private long timestamp;

    // Client * * *
    private String clientId;
    private String clientName;
    private String clientEmail;

    // Hmac
    private String hmac;

    // Notif
    private String notif;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @ManyToOne
    private Merchant merchant;
}
