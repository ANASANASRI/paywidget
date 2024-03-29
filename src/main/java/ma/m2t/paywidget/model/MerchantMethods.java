package ma.m2t.paywidget.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchant_paymentMethod")
public class MerchantMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // indicate if the merchant has selected this method
    private boolean isSelected;

    @ManyToOne
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "paymentMethodId")
    private PaymentMethod paymentMethod;

}
