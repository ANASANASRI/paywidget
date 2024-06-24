package ma.m2t.paywidget.service.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.model.Token;
import ma.m2t.paywidget.repository.TokenRepository;
import org.json.JSONObject;
import ma.m2t.paywidget.model.Transaction;
import ma.m2t.paywidget.service.TokenService;
import ma.m2t.paywidget.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_URL = "https://oper-token-api-preprod.m2t.ma/api/v2/token";
    private static final String EXTCHANGERATE_API_KEY = "4044ed9d37053959ab585fba";
    private static final String EXTCHANGERATE_API_URL = "https://v6.exchangerate-api.com/v6/" + EXTCHANGERATE_API_KEY + "/latest/";

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TransactionService transactionService;

///****************************************************************************************************
//Post/////////////////////


    ////// Generate Token
    @Override
    public String generateToken(
            String serviceId,
            String organismId,
            String expirationDate,
            String requestDate,
            String checkSum,
            String tokenStatus,
            String orderId,
            String orderAmount,
            String customerName,
            String customerMail,
            String currency,
            String marchandId
    ) throws Exception {

//---//        //String amountInMADString = this.convertToMAD(Double.parseDouble(orderAmount), currency.toUpperCase());

        // Create request payload
        Map<String, String> payload = new HashMap<>();
        payload.put("orderId", orderId);
        payload.put("serviceId", serviceId);
        payload.put("organismId", organismId);
//---//        payload.put("orderAmount", amountInMADString);
        payload.put("orderAmount", orderAmount);
        payload.put("requestDate", requestDate);
        payload.put("customerName", customerName);
        payload.put("customerMail", customerMail);
        payload.put("expirationDate", expirationDate);
        payload.put("checkSum", checkSum);
        payload.put("tokenStatus", tokenStatus);

        // Log the request payload
        System.out.println("Payload: " + payload);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        // Send request and parse response
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();


                //saveToken
//                Token token = new Token(
//                        responseBody != null ? (String) responseBody.get("token") : null,
//                        customerName,
//                        customerMail
//                );
//                saveToken(token);

                // saveTokenTransaction
//---//                TransactionDTO transactionDTO = saveTokenTransaction(orderId, amountInMADString, customerName, customerMail, marchandId);
                TransactionDTO transactionDTO = saveTokenTransaction(orderId, orderAmount, customerName, customerMail, marchandId);

                return responseBody != null ? (String) responseBody.get("token") : null;
            } else {
                throw new RuntimeException("Failed to generate token: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // Log the detailed error message
            System.err.println("Error Response Body: " + e.getResponseBodyAsString() );
            throw new RuntimeException("Failed to generate token: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }


    @Override
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public void callback() {

    }

    @Override
    public String CallbackTokenReceived() {
        return null;
    }

    @Override
    public String CallbackTokenPay() {
        return null;
    }

///****************************************************************************************************
//Get////////////////////////

///****************************************************************************************************
//Update/////////////////////

///****************************************************************************************************
//Delete/////////////////////

///****************************************************************************************************
//Called/////////////////////

////////////////////////////
public static String convertToMAD(double amount, String currency) throws Exception {
    String urlStr = EXTCHANGERATE_API_URL + currency.toUpperCase();
    URL url = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    int responseCode = conn.getResponseCode();
    if (responseCode != 200) {
        throw new RuntimeException("Failed to get response from API. Response code: " + responseCode);
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();

    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    in.close();

    // Parse JSON response
    String jsonResponse = response.toString();
    int startIndex = jsonResponse.indexOf("\"MAD\":");
    int endIndex = jsonResponse.indexOf(",", startIndex);
    String exchangeRateStr = jsonResponse.substring(startIndex + 6, endIndex);

    // Convert exchange rate to double
    double exchangeRateToMAD = Double.parseDouble(exchangeRateStr);

    // Calculate equivalent amount in MAD
    double equivalentAmount = amount * exchangeRateToMAD;

    // Return the result as a String
    return String.valueOf(equivalentAmount);
}

////////////////////////////

    public TransactionDTO saveTokenTransaction(
                                               String orderId,
                                               String amountInMADString,
                                               String customerName,
                                               String customerMail,
                                               String marchandId
                                               ) {
    ////
            //Obtenez la date actuelle
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Formattez la date actuelle dans le format requis
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    ////
            String timestamp = currentDateTime.format(formatter);
            String status = "pending";
            Long paymentMethodId = 1L;
            String hmac = "def456abc";
            String currency = "MAD" ;
    ///

            TransactionDTO transactionDTO = new TransactionDTO();

            transactionDTO.setTimestamp(timestamp);
            transactionDTO.setStatus(status);
            transactionDTO.setPaymentMethodId(paymentMethodId);
            transactionDTO.setHmac(hmac);
            transactionDTO.setCurrency(currency);
            transactionDTO.setOrderId(orderId);
            transactionDTO.setAmount(Double.parseDouble(amountInMADString));
            transactionDTO.setOrderId(orderId);
            transactionDTO.setClientEmail(customerMail);
            transactionDTO.setClientName(customerName);
            transactionDTO.setMarchandId(Long.valueOf(marchandId));

            transactionService.saveTransaction(transactionDTO);

            return transactionDTO;
    }

////////////////////////////
    public static String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

//////////////////////////


//////////////////////////


}
