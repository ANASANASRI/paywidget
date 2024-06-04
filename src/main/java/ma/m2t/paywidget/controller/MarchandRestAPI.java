package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.service.MarchandMethodsService;
import ma.m2t.paywidget.service.MarchandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("marchand")
public class MarchandRestAPI {

    private MarchandService marchandService;
    private MarchandMethodsService marchandMethodsService;
//    private MarchandRepository marchandRepository;

//POST
    @PostMapping("/save")
    public MarchandDTO AddMarchand(@RequestBody MarchandDTO marchandDTO) throws Exception {
        this.marchandService.saveNewMarchand(marchandDTO);
        return marchandDTO;
    }

    @PostMapping("/{marchandId}/associatePaymentMethods")
    public void associatePaymentMethodsWithMarchand(@PathVariable Long marchandId, @RequestBody List<Long> paymentMethodIds) throws MarchandNotFoundException {
        this.marchandService.FirstAssociatePaymentMethodsToMarchand(marchandId, paymentMethodIds);
    }

//GET
    @GetMapping("/all")
    public List<MarchandDTO> GetAll(){
        return marchandService.getAllMarchands();
    }

    @GetMapping("/{id}")
    public MarchandDTO FindByID(@PathVariable(name = "id") Long id) throws MarchandNotFoundException {
        return marchandService.getMarchandById(id);
    }

    @GetMapping("/methods/{marchandId}")
    public List<Map<String, Object>> getMarchandPaymentMethods(@PathVariable Long marchandId) throws MarchandNotFoundException {
        return marchandMethodsService.getMarchandPaymentMethods(marchandId);
    }

    @GetMapping("/permission")
    public Boolean testPermission(
            @RequestParam String hostname,
            @RequestParam String accessKey,
            @RequestParam String marchandId,
            @RequestParam String orderId,
            @RequestParam double amount,
            @RequestParam String currency,
            @RequestParam String hmac) throws Exception {

        Boolean hasPermission = marchandService.hasPermission(hostname, accessKey, marchandId, orderId, amount, currency, hmac);

        System.out.println("Hostname: " + hostname);
        System.out.println("Secret Key: " + accessKey);
        System.out.println("Marchand ID: " + marchandId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Currency: " + currency);
        System.out.println("HMAC: " + hmac);
        System.out.println("Has Permission: " + hasPermission);

        return hasPermission;
    }

    /*usin for authenticated by marchand */
    @GetMapping("/marchandName/{marchandName}")
    public int findMarchandIdbyMarchandName(@PathVariable String marchandName) {
        return marchandService.findMarchandIdbyMarchandName(marchandName);
    }

    @GetMapping("/findById/{id}")
    public MarchandDTO getMarchandById(@PathVariable(name = "id") Long marchandId) throws MarchandNotFoundException {
        return marchandService.findMarchandById(marchandId);
    }
    
//UPDATE
    @PutMapping("/{marchandId}/payment-method/{paymentMethodId}")
    public void selectPaymentMethodInMarchand(@PathVariable Long marchandId, @PathVariable Long paymentMethodId) throws MarchandNotFoundException {
        marchandMethodsService.selectPaymentMethodInMarchand(marchandId, paymentMethodId);
    }

    @PutMapping("/update")
    public MarchandDTO updateCustomer(@RequestBody MarchandDTO marchandDTO){
        return marchandService.updateMarchand(marchandDTO);
    }

//DELETE
    @DeleteMapping("/delete/{id}")
    public void DeleteByID(@PathVariable(name = "id") Long id){
        marchandService.deleteMarchand(id);
    }

}
