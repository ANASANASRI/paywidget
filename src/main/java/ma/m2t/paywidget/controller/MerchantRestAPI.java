package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.repository.MerchantRepository;
import ma.m2t.paywidget.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("merchant")
public class MerchantRestAPI {

    private MerchantService merchantService;
//    private MerchantRepository merchantRepository;

//POST
    @PostMapping("/save")
    public MerchantDTO AddMerchant(@RequestBody MerchantDTO merchantDTO){
        this.merchantService.saveNewMerchant(merchantDTO);
        return merchantDTO;
    }

    @PostMapping("/{merchantId}/associatePaymentMethods")
    public void associatePaymentMethodsWithMerchant(@PathVariable Long merchantId, @RequestBody List<Long> paymentMethodIds) throws MerchantNotFoundException {
        this.merchantService.associatePaymentMethodsToMerchant(merchantId, paymentMethodIds);
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

    @GetMapping("/permission")
    public Boolean testPermission(
            @RequestParam Long merchantID,
            @RequestParam String hostname,
            @RequestParam String secretKey) {

        Boolean hasPermission = merchantService.hasPermission(merchantID, hostname, secretKey);

        System.out.println("Merchant ID: " + merchantID);
        System.out.println("Hostname: " + hostname);
        System.out.println("Secret Key: " + secretKey);
        System.out.println("Has Permission: " + hasPermission);

        return hasPermission;
    }

//DELETE
    @DeleteMapping("/delete/{id}")
    public void DeleteByID(@PathVariable(name = "id") Long id){
        merchantService.deleteMerchant(id);
    }

}
