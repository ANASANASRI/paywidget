package ma.m2t.paywidget.dto;

import lombok.Data;

@Data
public class MarchandDTO {
    private Long marchandId;

    // Marchand info
    private String marchandName;
    private String marchandDescription;
    private String marchandHost;
    private String marchandLogoUrl;

    // Callback
    private String callback;

    // Service ID
    private String serviceId;

    // Access Key
    private String accessKey;
    // Secret key (Hashed)
    private String secretKey;
}
