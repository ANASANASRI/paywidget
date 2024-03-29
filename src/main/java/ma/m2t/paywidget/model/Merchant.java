package ma.m2t.paywidget.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantId;

    // Merchant info
    private String merchantName;
    private String merchantDescription;
    private String merchantSMS;
    private String merchantHost;
    private String merchantLogoUrl;

//    //Password
//    private String merchantPassword;

    // Callback
    private String callback;

    // Service ID
    private String serviceId;

    // Secret key (Hashed)
    private String secretKey;


    //
    @OneToMany(mappedBy = "merchant")
    private List<Transaction> transactions;

    @ManyToMany
    @JoinTable(name = "merchant_paymentMethod",
            joinColumns = @JoinColumn(name = "merchantId"),
            inverseJoinColumns = @JoinColumn(name = "paymentMethodId"))
    private List<PaymentMethod> paymentMethods;

}
