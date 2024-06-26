package ma.m2t.paywidget.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.m2t.paywidget.enums.AneeActivite;
import ma.m2t.paywidget.enums.Formejuridique;
import ma.m2t.paywidget.enums.Status;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marchand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marchandId;

    // Marchand info
    private String marchandName;
    private String marchandDescription;
    private String marchandPhone;
    private String marchandHost;
    private String marchandEmail;
    private String marchandLogoUrl;
    @Enumerated(EnumType.STRING)
    private Status marchandStatus;

    // Marchand info private
    private String marchandTypeActivite;
    private String marchandRcIf;
    private String marchandSiegeAddresse;
    private String marchandDgName;
    @Enumerated(EnumType.STRING)
    private Formejuridique marchandFormejuridique;
    @Enumerated(EnumType.STRING)
    private AneeActivite marchandAnneeActivite;

//    //Password
//    private String marchandPassword;

    // Callback
    private String callback;

    // Service ID
    private String serviceId;

    // Access Key
    private String accessKey;
    // Secret key (Hashed)
    private String secretKey;

    //
    @OneToMany(mappedBy = "marchand")
    private List<Transaction> transactions;

    @ManyToMany
    @JoinTable(name = "marchand_paymentMethod",
            joinColumns = @JoinColumn(name = "marchandId"),
            inverseJoinColumns = @JoinColumn(name = "paymentMethodId"))
    private List<PaymentMethod> paymentMethods;

}
