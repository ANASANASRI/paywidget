package ma.m2t.paywidget.service.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ExtchangeServiceImpl {

    private static final String EXTCHANGERATE_API_KEY = "4044ed9d37053959ab585fba";
    private static final String EXTCHANGERATE_API_URL = "https://v6.exchangerate-api.com/v6/" + EXTCHANGERATE_API_KEY + "/latest/";

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

}
