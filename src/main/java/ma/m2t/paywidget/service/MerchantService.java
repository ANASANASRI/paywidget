package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.model.Merchant;

import java.util.List;

public interface MerchantService {

///****************************************************************************************************
//Post
    MerchantDTO saveNewMerchant(MerchantDTO merchantDTO);

    //
    void FirstAssociatePaymentMethodsToMerchant(Long merchantId, List<Long> paymentMethodIds) throws MerchantNotFoundException;

///*****************************************************************************************************
//Get
    List<MerchantDTO> getAllMerchants();
    List<MerchantDTO> getAllMerchantsByMethod(Long methodId);
    MerchantDTO getMerchantById(Long merchantId) throws MerchantNotFoundException;
    List<PaymentMethodDTO> getAllMerchantMethods(Long merchantId);
//    Boolean hasPermission(String hostname, String secretKey);
    Boolean hasPermission(String hostname, String accessKey, String merchantId, String orderId,
                          double amount, String currency, String hmac);

///****************************************************************************************************
//Update
    MerchantDTO updateMerchant(MerchantDTO merchantDTO);

///****************************************************************************************************
//Delete
    void deleteMerchant(Long merchantId);

}