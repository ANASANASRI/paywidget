package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;

import java.util.List;

public interface MarchandService {

///****************************************************************************************************
//Post
    MarchandDTO saveNewMarchand(MarchandDTO marchandDTO);

    //
    void FirstAssociatePaymentMethodsToMarchand(Long marchandId, List<Long> paymentMethodIds) throws MarchandNotFoundException;

///*****************************************************************************************************
//Get
    List<MarchandDTO> getAllMarchands();
    List<MarchandDTO> getAllMarchandsByMethod(Long methodId);
    MarchandDTO getMarchandById(Long marchandId) throws MarchandNotFoundException;
    List<PaymentMethodDTO> getAllMarchandMethods(Long marchandId);
//    Boolean hasPermission(String hostname, String secretKey);
    Boolean hasPermission(String hostname, String accessKey, String marchandId, String orderId,
                          double amount, String currency, String hmac);

    int findMarchandIdbyMarchandName(String marchandName);

    MarchandDTO findMarchandById(Long id) throws MarchandNotFoundException;



    ///****************************************************************************************************
//Update
    MarchandDTO updateMarchand(MarchandDTO marchandDTO);

///****************************************************************************************************
//Delete
    void deleteMarchand(Long marchandId);


}