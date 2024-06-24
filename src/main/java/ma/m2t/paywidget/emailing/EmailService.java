package ma.m2t.paywidget.emailing;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostConstruct
    public void init() {
        // Perform any necessary initialization here.
    }

    public void sendDemandeVerificationEmail(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // Email content
        String header = "Cher " + toEmail + ",";

        String verificationMessage = "Nous vous remercions d'avoir soumis votre demande pour devenir un marchand avec nous. " +
                "Nous tenons à vous informer que nous avons bien reçu votre demande et que notre équipe est actuellement en train de la vérifier.<br><br>" +
                "Dans les prochaines 24 heures, vous recevrez un autre email de notre part vous informant de la décision concernant votre demande. " +
                "Nous vous prions de consulter régulièrement votre boîte de réception, y compris votre dossier de spam, au cas où notre email serait dirigé là-bas par erreur.<br>";

        // Construct the HTML message
        String htmlContent = "<!DOCTYPE html>"
                + "<html lang=\"fr\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Confirmation de réception de votre demande</title>"
                + "<style>"
                + "body {"
                + "    background-color: #f4f4f4;"
                + "    font-family: 'Arial', sans-serif;"
                + "}"
                + ".email-container {"
                + "    max-width: 600px;"
                + "    margin: 0 auto;"
                + "    padding: 20px;"
                + "    background-color: #fff;"
                + "    border-radius: 10px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".email-header {"
                + "    text-align: center;"
                + "    padding-bottom: 20px 20px 5px 20px;"
                + "}"
                + ".image-footer {"
                + "    width: 150px;"
                + "    margin: 0 auto;"
                + "    padding: 20px 20px 5px 20px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".email-header img {"
                + "    max-width: 150px;"
                + "    border-radius: 5%;"
                + "}"
                + ".email-content {"
                + "    text-align: center;"
                + "    padding: 0px 20px 0px 20px;"
                + "}"
                + ".email-content h1 {"
                + "    color: #7ab024;"
                + "}"
                + ".email-content p {"
                + "    color: #555;"
                + "    font-size: 16px;"
                + "    line-height: 1.5;"
                + "}"
                + ".email-footer {"
                + "    text-align: center;"
                + "    padding-top: 0px 20px 20px 20px;"
                + "    font-size: 12px;"
                + "    color: #aaa;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"email-container\">"
                + "    <div class=\"email-header\">"
                + "        <img src=\"https://i.ibb.co/gFZGbV3/Logopng.png\" alt=\"PayPik\">"
                + "    </div>"
                + "    <div class=\"email-content\">"
                + "        <p>" + header + "</p>"
                + "        <p>" + verificationMessage + "</p>"
                + "        <p>Cordialement,<br><img class=\"image-footer\"  src=\"https://i.ibb.co/4ZJ5rVW/Pay-Pikpng.png\" alt=\"PayPik\"></p>"
                + "    </div>"
                + "    <div class=\"email-footer\">"
                + "        <p>Si vous avez des questions, veuillez nous contacter à <a href='mailto:paypik.m2t@gmail.com'>paypik.m2t@gmail.com</a>.</p>"
                + "    </div>"
                + "</div>"
                + "</body>"
                + "</html>";

        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Confirmation de réception de votre demande");

        // Set the HTML content
        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }


    /////////////////////////////////////////////////////////

    public void sendValidationAcceptedEmail(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

// Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher " + toEmail + ",\n\n";
        String footer = "\n\nCordialement,\nPayPik\n";

        String acceptanceMessage = "Nous sommes ravis de vous informer que votre demande pour devenir un marchand avec nous a été acceptée !\n\nFélicitations ! Vous êtes désormais officiellement membre de notre réseau de marchands. Nous sommes impatients de travailler avec vous et de vous soutenir dans la croissance de votre activité.\n\nDans les prochains jours, vous recevrez des informations supplémentaires sur les prochaines étapes à suivre pour commencer à vendre sur notre plateforme. Si vous avez des questions ou avez besoin d'assistance, n'hésitez pas à nous contacter à paypik.m2t@gmail.com.\n\nEncore une fois, bienvenue dans notre communauté de marchands. Nous sommes ravis de vous avoir avec nous !";

        String completeMessage = header + acceptanceMessage + footer;

// HTML content with image
        String htmlContentWithImage = completeMessage.replace("\n", "<br>") + "<br><img src='cid:image' style='width:100%; max-width:700px;'>";

        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Confirmation de votre demande acceptée");
// Set both text and HTML content
        mimeMessageHelper.setText(completeMessage, htmlContentWithImage);
// Add the image as an inline attachment
        mimeMessageHelper.addInline("image", imageSource, "image/png");

        mimeMessageHelper.setSubject("Confirmation de votre demande acceptée");

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }


    ////////////////////////////////////////////////


    public void sendValidationRejectedEmail(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

// Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher "+toEmail+",\n\n";
        String footer = "\n\nCordialement,\nPayPik\n";

        String rejectionMessage = "Nous regrettons de vous informer que votre demande pour devenir un marchand avec nous a été rejetée.\n\nNous avons examiné attentivement votre demande, mais malheureusement, nous ne sommes pas en mesure de l'approuver pour le moment. Veuillez noter que cela ne reflète en rien sur vous en tant qu'individu ou sur la qualité de votre entreprise.\n\nNous vous remercions pour l'intérêt que vous avez porté à rejoindre notre réseau de marchands. Pour plus de détails sur la décision, veuillez contacter l'équipe commerciale à l'adresse suivante : paypik.m2t.commercial@gmail.com.\n\nNous vous souhaitons beaucoup de succès dans vos futurs projets.";

        String completeMessage = header + rejectionMessage + footer;

// HTML content with image
        String htmlContentWithImage = completeMessage.replace("\n", "<br>") + "<br><img src='cid:image' style='width:100%; max-width:700px;'>";

        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(completeMessage);

// Set both text and HTML content
        mimeMessageHelper.setText(completeMessage, htmlContentWithImage);
// Add the image as an inline attachment
        mimeMessageHelper.addInline("image", imageSource, "image/png");

        mimeMessageHelper.setSubject("Notification de votre demande rejetée");

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }

    /////////////////////////////////////////// sendPasswordMarchandEmail
    public void sendPasswordMarchandEmail(String toEmail, String username, String password) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher " + username + ",\n\n";
        String footer = "\n\nCordialement,\nPayPik\n";

        String acceptanceMessage = "Nous sommes ravis de vous informer que votre demande pour devenir un marchand avec nous a été acceptée !\n\n"
                + "Félicitations ! Vous êtes désormais officiellement membre de notre réseau de marchands. Nous sommes impatients de travailler avec vous et de vous soutenir dans la croissance de votre activité.\n\n"
                + "Votre nom d'utilisateur est : <b>" + username + "</b>\n"
                + "Votre mot de passe temporaire est : <b>" + password + "</b>\n\n"
                + "Pour des raisons de sécurité, nous vous recommandons fortement de mettre à jour votre mot de passe dans la section 'Profil' dès que possible.\n\n"
                + "Dans les prochains jours, vous recevrez des informations supplémentaires sur les prochaines étapes à suivre pour commencer à vendre sur notre plateforme. "
                + "Si vous avez des questions ou avez besoin d'assistance, n'hésitez pas à nous contacter à paypik.m2t@gmail.com.\n\n"
                + "Encore une fois, bienvenue dans notre communauté de marchands. Nous sommes ravis de vous avoir avec nous !";

        String completeMessage = header + acceptanceMessage + footer;

        // HTML content with image
        String htmlContentWithImage = completeMessage.replace("\n", "<br>") + "<br><img src='cid:image' style='width:100%; max-width:700px;'>";

        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Confirmation de votre demande acceptée");

        // Set both text and HTML content
        mimeMessageHelper.setText(completeMessage, htmlContentWithImage);

        // Add the image as an inline attachment
        mimeMessageHelper.addInline("image", imageSource, "image/png");

        mimeMessageHelper.setSubject("Confirmation de votre demande acceptée");

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }

    ///////////////////////////////////////// sendPasswordAdminEmail

    public void sendPasswordAdminEmail(String toEmail, String username, String password) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher " + username + ",\n\n";
        String footer = "\n\nCordialement,\nL'équipe PayPik\n";

        String internalMessage = "Bienvenue dans l'équipe Admin de PayPik !\n\n"
                + "Nous sommes ravis de vous avoir parmi nous et nous sommes impatients de collaborer avec vous pour atteindre de nouveaux sommets.\n\n"
                + "Voici vos identifiants de connexion :\n"
                + "Nom d'utilisateur : <b>" + username + "</b>\n"
                + "Mot de passe temporaire : <b>" + password + "</b>\n\n"
                + "Pour des raisons de sécurité, veuillez mettre à jour votre mot de passe dans la section 'Profil' dès que possible.\n\n"
                + "Si vous avez des questions ou avez besoin d'assistance, n'hésitez pas à nous contacter.\n\n"
                + "Encore une fois, bienvenue dans l'équipe et nous sommes heureux de vous compter parmi nous !";

        String completeMessage = header + internalMessage + footer;

        // HTML content with image
        String htmlContentWithImage = completeMessage.replace("\n", "<br>") + "<br><img src='cid:image' style='width:100%; max-width:700px;'>";

        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Bienvenue dans l'équipe Admin de PayPik");

        // Set both text and HTML content
        mimeMessageHelper.setText(completeMessage, htmlContentWithImage);

        // Add the image as an inline attachment
        mimeMessageHelper.addInline("image", imageSource, "image/png");

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }

    ///////////////////////////////////////// sendPasswordCommercialEmail

    public void sendPasswordCommercialEmail(String toEmail, String username, String password) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher " + username + ",\n\n";
        String footer = "\n\nCordialement,\nL'équipe PayPik\n";

        String internalMessage = "Bienvenue dans l'équipe commerciale de PayPik !\n\n"
                + "Nous sommes ravis de vous avoir parmi nous et nous sommes impatients de collaborer avec vous pour atteindre de nouveaux sommets.\n\n"
                + "Voici vos identifiants de connexion :\n"
                + "Nom d'utilisateur : <b>" + username + "</b>\n"
                + "Mot de passe temporaire : <b>" + password + "</b>\n\n"
                + "Pour des raisons de sécurité, veuillez mettre à jour votre mot de passe dans la section 'Profil' dès que possible.\n\n"
                + "Si vous avez des questions ou avez besoin d'assistance, n'hésitez pas à nous contacter.\n\n"
                + "Encore une fois, bienvenue dans l'équipe et nous sommes heureux de vous compter parmi nous !";

        String completeMessage = header + internalMessage + footer;

        // HTML content with image
        String htmlContentWithImage = completeMessage.replace("\n", "<br>") + "<br><img src='cid:image' style='width:100%; max-width:700px;'>";

        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Bienvenue dans l'équipe commerciale de PayPik");

        // Set both text and HTML content
        mimeMessageHelper.setText(completeMessage, htmlContentWithImage);

        // Add the image as an inline attachment
        mimeMessageHelper.addInline("image", imageSource, "image/png");

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }

    //////////////////////////////// Send Mail

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String subject, String nom, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("paypik.m2t@gmail.com");
        mailMessage.setSubject("Client: " + subject);
        mailMessage.setText("From: " + nom + "\n\n" + message);

        try {
            emailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
