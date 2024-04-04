package ma.m2t.paywidget.dto;

import lombok.Data;

@Data

public class TokenDTO {
    private Long tokenId;

    private String tokenResponse;
    private String tokenName;
    private String tokenEmail;

}
