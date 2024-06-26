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

    private String timestamp;

    // Client * * *
    private String clientId;
    private String clientName;
    private String clientEmail;

    // Hmac
    private String hmac;

    // Notif
    private String notif;

    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    private Marchand marchand;

}

//    @Column(name = "payment_method_id")
//    private Long paymentMethodId;
//
//    @Column(name = "marchand_id")
//    private Long marchandId;