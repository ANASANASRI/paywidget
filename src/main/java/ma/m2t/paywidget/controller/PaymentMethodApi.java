package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.service.MarchandMethodsService;
import ma.m2t.paywidget.service.MethodsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/method")
public class PaymentMethodApi {
    private MarchandMethodsService marchandMethodsService;
    private MethodsService paymentMethodService;
    @PutMapping("/{marchandId}/payment-method/{paymentMethodId}")
    public void selectPaymentMethodInMarchand(@PathVariable Long marchandId, @PathVariable Long paymentMethodId) throws  MarchandNotFoundException {
        marchandMethodsService.selectPaymentMethodInMarchand(marchandId,paymentMethodId);
    }
    //liste paimentMethode
    @GetMapping("/findAll")
    public List<PaymentMethodDTO> listPaimentMethod() {
        return paymentMethodService.listPaymentMethod();
    }
    /*utiliser pour front Marchand (details) Transaction*/
    @GetMapping("/findById/{paymentMethodId}")
    public PaymentMethodDTO getPymentMethodeById(@PathVariable Long paymentMethodId) {
        return paymentMethodService.getPaymentMethodById(paymentMethodId);
    }

    @GetMapping("/status/{marchandId}/{paymentMethodId}")
    public boolean findStatusMarchandPaymentByMarchandIdAndPaymentMethodId(@PathVariable Long marchandId, @PathVariable Long paymentMethodId) {
        return marchandMethodsService.findStatusMarchandPaymentByMarchandIdAndPaymentMethodId(marchandId, paymentMethodId);
    }

}
