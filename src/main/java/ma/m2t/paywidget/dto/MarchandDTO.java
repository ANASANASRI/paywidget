package ma.m2t.paywidget.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ma.m2t.paywidget.enums.AneeActivite;
import ma.m2t.paywidget.enums.Formejuridique;
import ma.m2t.paywidget.enums.Status;

@Data
public class MarchandDTO {
    private Long marchandId;

    // Marchand info
    private String marchandName;
    private String marchandDescription;
    private String marchandPhone;
    private String marchandHost;
    private String marchandEmail;
    private String marchandLogoUrl;
    private Status marchandStatus;

    // Marchand info private
    private String marchandTypeActivite;
    private String marchandRcIf;
    private String marchandSiegeAddresse;
    private String marchandDgName;
    private Formejuridique marchandFormejuridique;
    private AneeActivite marchandAnneeActivite;


    // Callback
    private String callback;

    // Service ID
    private String serviceId;

    // Access Key
    private String accessKey;
    // Secret key (Hashed)
    private String secretKey;
}
