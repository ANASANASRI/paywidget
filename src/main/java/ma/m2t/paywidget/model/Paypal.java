package ma.m2t.paywidget.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PAYPAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paypal extends PaymentMethod {
    private Long paypalId;
    private String emailAddress;
    private String password;
    private Double balance;

}
