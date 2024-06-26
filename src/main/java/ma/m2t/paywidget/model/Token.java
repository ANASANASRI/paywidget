package ma.m2t.paywidget.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TOKEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "paymentMethodId")
public class Token extends PaymentMethod {

    private String tokenResponse;
    private String tokenName;
    private String tokenEmail;
}
