package ma.m2t.paywidget.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PaypalController {
//
//    @Autowired
//    private PaypalService paypalService;
//
//    @PostMapping("/create-payment")
//    public Map<String, Object> createPayment(@RequestBody Map<String, Object> paymentInfo) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            String cancelUrl = "http://localhost:4200/cancel"; // Update with your actual cancel URL
//            String successUrl = "http://localhost:4200/success"; // Update with your actual success URL
//            Payment payment = paypalService.createPayment(
//                    Double.parseDouble(paymentInfo.get("amount").toString()),
//                    paymentInfo.get("currency").toString(),
//                    "paypal",
//                    "sale",
//                    paymentInfo.get("description").toString(),
//                    cancelUrl,
//                    successUrl);
//            for (Links links : payment.getLinks()) {
//                if (links.getRel().equals("approval_url")) {
//                    response.put("id", payment.getId());
//                    response.put("approval_url", links.getHref());
//                }
//            }
//        } catch (PayPalRESTException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    @PostMapping("/execute-payment")
//    public Map<String, Object> executePayment(@RequestBody Map<String, String> paymentData) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            Payment payment = paypalService.executePayment(paymentData.get("paymentId"), paymentData.get("payerId"));
//            if (payment.getState().equals("approved")) {
//                response.put("status", "success");
//                response.put("details", payment);
//            }
//        } catch (PayPalRESTException e) {
//            e.printStackTrace();
//            response.put("status", "failure");
//        }
//        return response;
//    }
}


//package ma.m2t.paywidget.controller;
//
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import ma.m2t.paywidget.service.serviceImpl.PaypalService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.view.RedirectView;
//
//@RestController
//@RequestMapping("/api/paypal")
//public class PaypalController {
//
//    @Autowired
//    private PaypalService paypalService;
//
//    @PostMapping("/create-payment")
//    public RedirectView createPayment(@RequestParam("amount") Double amount, @RequestParam("currency") String currency, @RequestParam("description") String description) {
//        try {
//            String cancelUrl = "http://localhost:4200/cancel"; // Update with your actual cancel URL
//            String successUrl = "http://localhost:4200/success"; // Update with your actual success URL
//            Payment payment = paypalService.createPayment(amount, currency, "paypal", "sale", description, cancelUrl, successUrl);
//            for (Links links : payment.getLinks()) {
//                if (links.getRel().equals("approval_url")) {
//                    return new RedirectView(links.getHref());
//                }
//            }
//        } catch (PayPalRESTException e) {
//            e.printStackTrace();
//        }
//        return new RedirectView("/error");
//    }
//
//    @PostMapping("/execute-payment")
//    public String executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//        try {
//            Payment payment = paypalService.executePayment(paymentId, payerId);
//            if (payment.getState().equals("approved")) {
//                return "Payment successful";
//            }
//        } catch (PayPalRESTException e) {
//            e.printStackTrace();
//        }
//        return "Payment failed";
//    }
//}
