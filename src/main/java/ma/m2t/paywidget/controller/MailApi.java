package ma.m2t.paywidget.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import ma.m2t.paywidget.emailing.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("mail")
public class MailApi {
    private EmailService emailService;

    //POST
    @PostMapping("/verification")
    public String sentVerificationMail(String toEmail) throws MessagingException {
        this.emailService.sendDemandeVerificationEmail(toEmail);
        return "Success verification";
    }

    @PostMapping("/accepted")
    public String sentAcceptedMail(String toEmail) throws MessagingException {
        this.emailService.sendValidationAcceptedEmail(toEmail);
        return "Success accepted";
    }

    @PostMapping("/rejected")
    public String sentRejectedMail(String toEmail) throws MessagingException {
        this.emailService.sendValidationRejectedEmail(toEmail);
        return "Success Rejected";
    }

    // email send password
    @PostMapping("/commercial/password")
    public String sendPasswordCommercialMail(String toEmail, String username, String password) throws MessagingException {
        this.emailService.sendPasswordCommercialEmail(toEmail, username, password);
        return "Password Email Sent Successfully";
    }

    @PostMapping("/marchand/password")
    public String sentpasswordMarchandMail(String toEmail, String username, String password) throws MessagingException {
        this.emailService.sendPasswordMarchandEmail(toEmail, username, password);
        return "Password Email Sent Successfully";
    }


    //Send Mail
    @PostMapping("/send-email")
    public void sendEmail(
            @RequestParam("subject") String subject,
            @RequestParam("nom") String nom,
            @RequestParam("message") String message) {
        emailService.sendEmail(subject, nom, message);
    }

}
