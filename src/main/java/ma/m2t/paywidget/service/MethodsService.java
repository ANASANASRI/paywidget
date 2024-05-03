package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.PaymentMethodDTO;

import java.util.List;

public interface MethodsService {

    //CRUD METHODE
    List<PaymentMethodDTO> listPaymentMethod();

    PaymentMethodDTO updatePaymentMethod(PaymentMethodDTO paymentMethodDTO);
    void deletePaymentMethod(PaymentMethodDTO paymentMethodDTO);
    
    PaymentMethodDTO getPaymentMethodById(Long paymentMethodId) ;

///****************************************************************************************************
//Post
    PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO);

///****************************************************************************************************
//Get
    List<PaymentMethodDTO> getAllPaymentMethodsByMarchand(Long marchandId);
    List<PaymentMethodDTO> getAllPaymentMethodsByMethod(Long methodId);
    //PaymentMethodDTO getPaymentMethodById(Long paymentMethodId);
///****************************************************************************************************
//Update
    PaymentMethodDTO UpdatePaymentMethod(PaymentMethodDTO paymentMethodDTO);
///****************************************************************************************************
//Delete
    void deletePaymentMethod(Long paymentMethodId);
}
