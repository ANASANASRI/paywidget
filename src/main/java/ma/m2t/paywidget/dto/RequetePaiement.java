package ma.m2t.paywidget.dto;

import lombok.Data;

@Data
public class RequetePaiement {
    private String montant;
    private String numeroCarte;
    private String dateExpirationCarte;
    private String cvcCarte;
    private String email;
    private String nomFacturation;
    private String societeFacturation;
    private String adresseFacturation;
    private String villeFacturation;
    private String etatFacturation;
    private String codePostalFacturation;
    private String paysFacturation;
    private String telephone;
    private String urlRetour;

    // Getters et setters
}
