package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.service.MarchandMethodsService;
import ma.m2t.paywidget.service.MethodsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/method")
public class PaymentMethodApi {
    private MarchandMethodsService marchandMethodsService;
    private MethodsService paymentMethodService;
    @PutMapping("/{merchantId}/payment-method/{paymentMethodId}")
    public void selectPaymentMethodInMerchant(@PathVariable Long merchantId, @PathVariable Long paymentMethodId) throws  MarchandNotFoundException {
        marchandMethodsService.selectPaymentMethodInMarchand(merchantId,paymentMethodId);
    }
    //liste paimentMethode
    @GetMapping("/findAll")
    public List<PaymentMethodDTO> listPaimentMethod() {
        return paymentMethodService.listPaymentMethod();
    }
    /*utiliser pour front Merchant (details) Transaction*/
    @GetMapping("/findById/{paymentMethodId}")
    public PaymentMethodDTO getPymentMethodeById(@PathVariable Long paymentMethodId) {
        return paymentMethodService.getPaymentMethodById(paymentMethodId);
    }
}
