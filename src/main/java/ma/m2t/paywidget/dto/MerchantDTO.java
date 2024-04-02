package ma.m2t.paywidget.dto;

import lombok.Data;

@Data
public class MerchantDTO {
    private Long merchantId;

    // Merchant info
    private String merchantName;
    private String merchantDescription;
    private String merchantHost;
    private String merchantLogoUrl;

    // Callback
    private String callback;

    // Service ID
    private String serviceId;

    // Access Key
    private String accessKey;
    // Secret key (Hashed)
    private String secretKey;
}
