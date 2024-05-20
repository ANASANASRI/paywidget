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

}
