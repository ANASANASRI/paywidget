package ma.m2t.paywidget.controller;

import ma.m2t.paywidget.service.serviceImpl.ExtchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("extchange")
public class ExtchangeRateApi {

    @Autowired
    private ExtchangeServiceImpl extchangeService;

    @GetMapping("/convert")
    public String convertCurrency(@RequestParam double amount, @RequestParam String currency) {
        try {
            // Convert currency to MAD
            return extchangeService.convertToMAD(amount, currency);
        } catch (Exception e) {
            return "Error converting currency: " + e.getMessage();
        }
    }

}
