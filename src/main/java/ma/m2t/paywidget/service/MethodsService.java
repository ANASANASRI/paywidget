package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.PaymentMethodDTO;

import java.util.List;

public interface MethodsService {

    //Post
    PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO);

    //Get
    List<PaymentMethodDTO> getAllPaymentMethodsByMerchant(Long merchantId);
    List<PaymentMethodDTO> getAllPaymentMethodsByMethod(Long methodId);
    PaymentMethodDTO getPaymentMethodById(Long paymentMethodId);


    //Update
    PaymentMethodDTO UpdatePaymentMethod(PaymentMethodDTO paymentMethodDTO);

    //Delete
    void deletePaymentMethod(Long paymentMethodId);
}
