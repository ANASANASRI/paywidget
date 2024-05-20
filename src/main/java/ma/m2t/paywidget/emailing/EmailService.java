package ma.m2t.paywidget.emailing;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

// Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher "+toEmail+",\n\n";
        String footer = "\n\nCordialement,\nPayPik";

        String verificationMessage = "Nous vous remercions d'avoir soumis votre demande pour devenir un marchand avec nous. Nous tenons à vous informer que nous avons bien reçu votre demande et que notre équipe est actuellement en train de la vérifier.\n\nNous vous prions de bien vouloir patienter pendant que nous examinons attentivement votre demande. Nous vous assurons que nous mettons tout en œuvre pour traiter votre demande dans les plus brefs délais.\n\nDans les prochaines 24 heures, vous recevrez un autre email de notre part vous informant de la décision concernant votre demande. Nous vous prions de consulter régulièrement votre boîte de réception, y compris votre dossier de spam, au cas où notre email serait dirigé là-bas par erreur.\n\nNous vous remercions de votre intérêt à rejoindre notre réseau de marchands. Si vous avez des questions ou des préoccupations en attendant, n'hésitez pas à nous contacter à paypik.m2t@gmail.com .";

        String completeMessage = header + verificationMessage + footer;

// HTML content with image
        String htmlContentWithImage = completeMessage.replace("\n", "<br>") + "<br><img src='cid:image' style='width:100%; max-width:700px;'>";


        mimeMessageHelper.setFrom("paypik.m2t@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(completeMessage);

// Set both text and HTML content
        mimeMessageHelper.setText(completeMessage, htmlContentWithImage);
// Add the image as an inline attachment
        mimeMessageHelper.addInline("image", imageSource, "image/png");

        mimeMessageHelper.setSubject("Confirmation de réception de votre demande");


//        try {
//            Resource resource = new ClassPathResource("static/images/emailaccepted.jpg");
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }
        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }

    public void sendValidationAcceptedEmail(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

// Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher " + toEmail + ",\n\n";
        String footer = "\n\nCordialement,\nPayPik";

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


//        try {
//            Resource resource = new ClassPathResource("static/images/emailaccepted.jpg");
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }

    public void sendValidationRejectedEmail(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

// Load the image file
        String imagePath = "static/images/footer.png";
        Resource imageSource = new ClassPathResource(imagePath);

        String header = "Cher "+toEmail+",\n\n";
        String footer = "\n\nCordialement,\nPayPik";

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

//        try {
//            Resource resource = new ClassPathResource("static/images/emailaccepted.jpg");
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }
        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to " + toEmail);
    }
}
