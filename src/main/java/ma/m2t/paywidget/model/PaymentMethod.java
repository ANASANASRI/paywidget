package ma.m2t.paywidget.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING) // Specify discriminator column type
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentMethodId;

    private String methodName;
    private String methodDescription;
    private String methodIconUrl;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Transaction> transactions;

    @ManyToMany(mappedBy = "paymentMethods")
    private List<Merchant> merchants;

}
