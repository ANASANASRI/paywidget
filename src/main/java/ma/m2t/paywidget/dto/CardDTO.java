package ma.m2t.paywidget.dto;

import lombok.Data;

@Data

public class CardDTO {
    private Long cardId;

    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
