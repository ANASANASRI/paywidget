package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.service.MerchantMethodsService;
import ma.m2t.paywidget.service.MerchantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("merchant")
public class MerchantRestAPI {

    private MerchantService merchantService;
    private MerchantMethodsService merchantMethodsService;
//    private MerchantRepository merchantRepository;

//POST
    @PostMapping("/save")
    public MerchantDTO AddMerchant(@RequestBody MerchantDTO merchantDTO) throws Exception {
        this.merchantService.saveNewMerchant(merchantDTO);
        return merchantDTO;
    }

    @PostMapping("/{merchantId}/associatePaymentMethods")
    public void associatePaymentMethodsWithMerchant(@PathVariable Long merchantId, @RequestBody List<Long> paymentMethodIds) throws MerchantNotFoundException {
        this.merchantService.FirstAssociatePaymentMethodsToMerchant(merchantId, paymentMethodIds);
    }

//GET
    @GetMapping("/all")
    public List<MerchantDTO> GetAll(){
        return merchantService.getAllMerchants();
    }

    @GetMapping("/{id}")
    public MerchantDTO FindByID(@PathVariable(name = "id") Long id) throws MerchantNotFoundException {
        return merchantService.getMerchantById(id);
    }

    @GetMapping("/methods/{merchantId}")
    public List<Map<String, Object>> getMerchantPaymentMethods(@PathVariable Long merchantId) throws MerchantNotFoundException {
        return merchantMethodsService.getMerchantPaymentMethods(merchantId);
    }

    @GetMapping("/permission")
    public Boolean testPermission(
            @RequestParam String hostname,
            @RequestParam String accessKey,
            @RequestParam String merchantId,
            @RequestParam String orderId,
            @RequestParam double amount,
            @RequestParam String currency,
            @RequestParam String hmac) throws Exception {

        Boolean hasPermission = merchantService.hasPermission(hostname, accessKey, merchantId, orderId, amount, currency, hmac);

        System.out.println("Hostname: " + hostname);
        System.out.println("Secret Key: " + accessKey);
        System.out.println("Merchant ID: " + merchantId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Currency: " + currency);
        System.out.println("HMAC: " + hmac);
        System.out.println("Has Permission: " + hasPermission);

        return hasPermission;
    }

//UPDATE
    @PutMapping("/{merchantId}/payment-method/{paymentMethodId}")
    public void selectPaymentMethodInMerchant(@PathVariable Long merchantId, @PathVariable Long paymentMethodId) throws MerchantNotFoundException {
        merchantMethodsService.selectPaymentMethodInMerchant(merchantId, paymentMethodId);
    }

//DELETE
    @DeleteMapping("/delete/{id}")
    public void DeleteByID(@PathVariable(name = "id") Long id){
        merchantService.deleteMerchant(id);
    }

}
