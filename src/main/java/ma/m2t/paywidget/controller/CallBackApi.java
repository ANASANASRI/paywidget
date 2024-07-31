package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.service.TokenService;
import ma.m2t.paywidget.service.serviceImpl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/paypik/callback")
public class CallBackApi {

    @Autowired
    private TokenService tokenService;

    // Token X Payed  (Update the payment status to complete )
    @GetMapping("/payed-token-complete")
    public String isPayedToken(@RequestParam String token,
                               @RequestParam String orderAmount,
                               @RequestParam String customerMail,
                               @RequestParam String customerName,
                               @RequestParam String currency,
                               @RequestParam String marchandId){
        return "completed";
    }

    @GetMapping("/payed-token-cancelled")
    public String isNotPayedToken(){
        return "cancelled";
    }

}
