package ma.m2t.paywidget.service;

import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.model.PaymentMethod;

import java.util.List;
import java.util.Map;

public interface MerchantMethodsService {
///****************************************************************************************************
//Post/////////////////////

///****************************************************************************************************
//Get/////////////////////
    List<Map<String, Object>> getMerchantPaymentMethods(Long merchantId) throws MerchantNotFoundException;


///****************************************************************************************************
//Update/////////////////////
    void selectPaymentMethodInMerchant(Long merchantId, Long paymentMethodId) throws MerchantNotFoundException;

///****************************************************************************************************
//Delete/////////////////////
}
