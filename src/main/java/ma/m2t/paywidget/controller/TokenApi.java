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
@RequestMapping("token")
public class TokenApi {

    @Autowired
    private TokenService tokenService;

    // Generate the token
    @GetMapping("/generate-token")
    public String generateToken(
                @RequestParam String orderId,
                @RequestParam String orderAmount,
                @RequestParam String customerMail,
                @RequestParam String customerName,
                @RequestParam String currency,
                @RequestParam String marchandId
            ) throws Exception {

        // Get current date and calculate expiration date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String requestDate = dateFormat.format(now);
        //System.out.println(requestDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        String expirationDate = dateFormat.format(calendar.getTime());
        //System.out.println(expirationDate);


        //
        String organismId = "3001" ;
        String serviceId = "3001" ;
        // Oper Secret Key
        String secretKey = "BYu5@SP++hG278FC6KGvbn";

        // Calculate checksum
        String checkSumData = requestDate + organismId + serviceId + orderId + secretKey;
        String checkSum = TokenServiceImpl.calculateMD5(checkSumData);
        //System.out.println(checkSum);

        // Generate token
        return tokenService.generateToken(
                 serviceId,
                 organismId,
                 expirationDate,
                 requestDate,
                 checkSum,
                 "ACTIVATE" ,
                 orderId,
                 orderAmount,
                 customerName,
                 customerMail,
                 currency,
                 marchandId
        );
    }

}
