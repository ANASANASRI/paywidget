package ma.m2t.paywidget.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private Long adminId;
    private String adminName;
    private String adminCin;
    private String adminPassword;
    private String adminEmail;
}
