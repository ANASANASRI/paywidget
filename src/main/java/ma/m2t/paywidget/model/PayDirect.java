package ma.m2t.paywidget.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PAYDIRECT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayDirect extends PaymentMethod {

    private Long payDirectId;
}
