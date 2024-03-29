package ma.m2t.paywidget.dto;

import lombok.Data;

@Data

public class PaymentMethodDTO {
    private Long paymentMethodId;

    private String methodName;
    private String methodDescription;
    private String methodIconUrl;

    // indicate if the merchant has selected this method
    private boolean isSelected;
}
