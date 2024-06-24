package ma.m2t.paywidget.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CARD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card extends PaymentMethod {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
