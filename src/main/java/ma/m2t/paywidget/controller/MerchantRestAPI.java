package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.repository.MerchantRepository;
import ma.m2t.paywidget.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("merchant")
public class MerchantRestAPI {

    private MerchantService merchantService;
//    private MerchantRepository merchantRepository;

    @PostMapping("/save")
    public MerchantDTO AddMerchant(@RequestBody MerchantDTO merchantDTO){
        this.merchantService.saveNewMerchant(merchantDTO);
        return merchantDTO;
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

}

// https://moroccomart.ma

// f5423d3682d5c219629d6c7e2be7be0a8496f8b8a4a68566c8d37a8f3c59524d