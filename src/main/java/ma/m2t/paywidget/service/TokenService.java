package ma.m2t.paywidget.service;

import ma.m2t.paywidget.model.Token;

public interface TokenService {
///****************************************************************************************************
//Post
    Token saveNewToken(Token token);
    void callback();

///****************************************************************************************************
//Get
    String getToken(String requestDate,String organismId,String orderId,String orderAmount,String customerMail ,String customerPhone);

    String CallbackTokenReceived();

    String CallbackTokenPay();

///****************************************************************************************************
//Update

///****************************************************************************************************
//Delete
}
