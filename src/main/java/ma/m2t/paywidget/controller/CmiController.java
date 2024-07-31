package ma.m2t.paywidget.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.RequetePaiement;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("cmi")
public class CmiController {

    private final String urlApiPaiement = "https://api.passerellepaiement.com/process";
    private final String cleApi = "votre-cle-api";

    //////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/processPayment")
    public void processPayment(
            @RequestParam("amount") String amount,
            @RequestParam("storekey") String storekey,
            @RequestParam("clientid") String clientid,
            @RequestParam("oid") String oid,
//            @RequestParam("shopurl") String shopurl,
//            @RequestParam("okUrl") String okUrl,
//            @RequestParam("failUrl") String failUrl,
            @RequestParam("email") String email,
            @RequestParam("billToName") String billToName,
            @RequestParam("billToCompany") String billToCompany,
            @RequestParam("billToStreet12") String billToStreet12,
            @RequestParam("billToCity") String billToCity,
            @RequestParam("billToStateProv") String billToStateProv,
            @RequestParam("billToPostalCode") String billToPostalCode,
//            @RequestParam("billToCountry") String billToCountry,
            @RequestParam("tel") String tel,
            @RequestParam("callbackUrl") String callbackUrl,
            HttpServletResponse response) throws IOException {

        // Build the CMI client URL with necessary parameters
        String url = "##shopurl" + "/example"; // Example base URL
        String redirectUrl = url + "?storekey=" + storekey +
                "&clientid=" + clientid +
                "&oid=" + oid +
                "&shopurl=" + "##shopurl" +
                "&okUrl=" + "##okUrl" +
                "&failUrl=" + "##failUrl" +
                "&email=" + email +
                "&BillToName=" + billToName +
                "&BillToCompany=" + billToCompany +
                "&BillToStreet12=" + billToStreet12 +
                "&BillToCity=" + billToCity +
                "&BillToStateProv=" + billToStateProv +
                "&BillToPostalCode=" + billToPostalCode +
                "&BillToCountry=" + 504 +
                "&tel=" + tel +
                "&amount=" + amount +
                "&CallbackURL=" + callbackUrl;

        response.sendRedirect(redirectUrl);
    }

//////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/effectuerPaiement")
    public ResponseEntity<String> effectuerPaiement(
            @RequestParam("montant") String montant,
            @RequestParam("numeroCarte") String numeroCarte,
            @RequestParam("dateExpirationCarte") String dateExpirationCarte,
            @RequestParam("cvcCarte") String cvcCarte,
            @RequestParam("email") String email,
//            @RequestParam("nomFacturation") String nomFacturation,
//            @RequestParam("societeFacturation") String societeFacturation,
//            @RequestParam("adresseFacturation") String adresseFacturation,
//            @RequestParam("villeFacturation") String villeFacturation,
//            @RequestParam("etatFacturation") String etatFacturation,
            @RequestParam("codePostalFacturation") String codePostalFacturation,
            @RequestParam("paysFacturation") String paysFacturation,
            @RequestParam("telephone") String telephone,
            @RequestParam("urlRetour") String urlRetour) {

        // Créer le corps de la requête
        RequetePaiement requetePaiement = new RequetePaiement();
        requetePaiement.setMontant(montant);
        requetePaiement.setNumeroCarte(numeroCarte);
        requetePaiement.setDateExpirationCarte(dateExpirationCarte);
        requetePaiement.setCvcCarte(cvcCarte);
        requetePaiement.setEmail(email);
        requetePaiement.setNomFacturation("nomFacturation");
        requetePaiement.setSocieteFacturation("societeFacturation");
        requetePaiement.setAdresseFacturation("adresseFacturation");
        requetePaiement.setVilleFacturation("villeFacturation");
        requetePaiement.setEtatFacturation("etatFacturation");
        requetePaiement.setCodePostalFacturation(codePostalFacturation);
        requetePaiement.setPaysFacturation(paysFacturation);
        requetePaiement.setTelephone(telephone);
        requetePaiement.setUrlRetour(urlRetour);

        // Définir les en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + cleApi);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequetePaiement> entityRequete = new HttpEntity<>(requetePaiement, headers);

        // Créer RestTemplate pour effectuer la requête HTTP POST
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = restTemplate.exchange(urlApiPaiement, HttpMethod.POST, entityRequete, String.class);

        return reponse;
    }
}