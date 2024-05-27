package ma.m2t.paywidget.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.enums.Status;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.Marchand;
import ma.m2t.paywidget.model.MarchandMethods;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.repository.MarchandMethodsRepository;
import ma.m2t.paywidget.repository.MarchandRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.service.MarchandService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class MarchandServiceImpl implements MarchandService {

    private MarchandRepository marchandRepository;
    private MarchandMethodsRepository marchandMethodsRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private PayMapperImpl dtoMapper;

///****************************************************************************************************
///POST/////////////////////
    // Save marchand and generate secret key
    public MarchandDTO saveNewMarchand(MarchandDTO marchandDTO) {

        Marchand marchand = dtoMapper.fromMarchandDTO(marchandDTO);

        String secretKey = generateSecretKey();
        marchand.setSecretKey(secretKey);

        Marchand savedMarchand = marchandRepository.save(marchand);

        return dtoMapper.fromMarchand(savedMarchand);
    }

    // Generate a secret key
    public String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16);
    }

    //Hash
    public static String hashSecretKey(String secretKey) {
        return BCrypt.hashpw(secretKey, BCrypt.gensalt());
    }
    public static boolean verifySecretKey(String secretKey, String hashedKey) {
        return BCrypt.checkpw(secretKey, hashedKey);
    }
    // Send secret key SMS

//**END**//

//
public void FirstAssociatePaymentMethodsToMarchand(Long marchandId, List<Long> paymentMethodIds) throws MarchandNotFoundException {
    Marchand marchand = marchandRepository.findById(marchandId)
            .orElseThrow(() -> new MarchandNotFoundException("Marchand Not found"));

    // Retrieve the existing associations for this marchand
    List<MarchandMethods> existingAssociations = marchandMethodsRepository.findByMarchandMarchandId(marchandId);
    Set<Long> existingPaymentMethodIds = new HashSet<>();
    for (MarchandMethods association : existingAssociations) {
        existingPaymentMethodIds.add(association.getPaymentMethod().getPaymentMethodId());
    }

    // Iterate over the chosen payment method IDs
    for (Long paymentMethodId : paymentMethodIds) {
        if (!existingPaymentMethodIds.contains(paymentMethodId)) {
            // Retrieve the payment method from the database
            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                    .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

            // Create a new MarchandMethods entity
            MarchandMethods marchandMethods = new MarchandMethods();
            marchandMethods.setMarchand(marchand);
            marchandMethods.setPaymentMethod(paymentMethod);
            marchandMethods.setSelected(true);

            marchandMethodsRepository.save(marchandMethods);
        } else {
            // Update existing association if it exists
            for (MarchandMethods existingAssociation : existingAssociations) {
                if (existingAssociation.getPaymentMethod().getPaymentMethodId().equals(paymentMethodId)) {
                    existingAssociation.setSelected(true); // Update isSelected to true
                    marchandMethodsRepository.save(existingAssociation);
                    break;
                }
            }
        }
    }

    // Print out the names of associated payment methods
    System.out.print("Marchand " + marchand.getMarchandName() + " has the following payment methods: ");
    List<PaymentMethod> paymentMethods = paymentMethodRepository.findAllById(paymentMethodIds);
    for (PaymentMethod p : paymentMethods) {
        System.out.print(p.getMethodName() + ", ");
    }
    System.out.println();
}
//**END**//


///****************************************************************************************************
///GET/////////////////////
//
    @Override
    public List<MarchandDTO> getAllMarchandsByMethod(Long methodId) {
        return null;
    }
//**END**//

//
    @Override
    public List<MarchandDTO> getAllMarchands() {
        List<Marchand> marchands = marchandRepository.findAll(Sort.by(Sort.Direction.ASC, "marchandId"));
        List<MarchandDTO> marchandsDTO = new ArrayList<>();
        for (Marchand m : marchands) {
            marchandsDTO.add(dtoMapper.fromMarchand(m));
        }
        return marchandsDTO;
    }

//**END**//

//
    @Override
    public MarchandDTO getMarchandById(Long marchandId) throws MarchandNotFoundException {
        Marchand marchand = marchandRepository.findById(marchandId)
                .orElseThrow(() -> new MarchandNotFoundException("Marchand Not found"));
        return dtoMapper.fromMarchand(marchand);
    }
//**END**//

//
    @Override
    public List<PaymentMethodDTO> getAllMarchandMethods(Long marchandId) {
        return null;
    }
//**END**//


///****************************************************************************************************
///UPDATE/////////////////////
//
    @Override
    public MarchandDTO updateMarchand(MarchandDTO marchandDTO) {
        Marchand marchandToUpdate = dtoMapper.fromMarchandDTO(marchandDTO);

        Marchand existingMarchand = marchandRepository.findById(marchandDTO.getMarchandId())
                .orElseThrow(() -> new EntityNotFoundException("Marchand not found"));

        // Update the existing marchand with the values from the DTO
        existingMarchand.setMarchandName(marchandToUpdate.getMarchandName());
        existingMarchand.setMarchandDescription(marchandToUpdate.getMarchandDescription());
        existingMarchand.setMarchandPhone(marchandToUpdate.getMarchandPhone());
        existingMarchand.setMarchandHost(marchandToUpdate.getMarchandHost());
        existingMarchand.setMarchandEmail(marchandToUpdate.getMarchandEmail());
        existingMarchand.setMarchandLogoUrl(marchandToUpdate.getMarchandLogoUrl());
        existingMarchand.setMarchandStatus(marchandToUpdate.getMarchandStatus());

        // Marchand info private
        existingMarchand.setMarchandTypeActivite(marchandToUpdate.getMarchandTypeActivite());
        existingMarchand.setMarchandRcIf(marchandToUpdate.getMarchandRcIf());
        existingMarchand.setMarchandSiegeAddresse(marchandToUpdate.getMarchandSiegeAddresse());
        existingMarchand.setMarchandDgName(marchandToUpdate.getMarchandDgName());
        existingMarchand.setMarchandFormejuridique(marchandToUpdate.getMarchandFormejuridique());
        existingMarchand.setMarchandAnneeActivite(marchandToUpdate.getMarchandAnneeActivite());


        // Save the updated marchand
        Marchand updatedMarchand = marchandRepository.save(existingMarchand);

        // Mapping from entity to DTO and returning
        return dtoMapper.fromMarchand(updatedMarchand);
    }

//**END**//

//

//**END**//


///****************************************************************************************************
///DELETE/////////////////////
//
    @Override
    public void deleteMarchand(Long marchandId) {
        marchandRepository.deleteById(marchandId);
    }
//**END**//




///****************************************************************************************************
///CHECK PERMISSION/////////////////////
//
public Boolean hasPermission(String hostname, String accessKey, String marchandId, String orderId,
                             double amount, String currency, String hmac){
    List<Marchand> marchands = marchandRepository.findAll();

    for (Marchand marchand : marchands) {
        //verify the dehash secret key if equals to secretKey provided
        if ( marchand.getMarchandId().equals(Long.parseLong(marchandId)) &&  marchand.getMarchandHost().equals(hostname) && marchand.getAccessKey().equals(accessKey) && marchand.getMarchandStatus().equals(Status.Active)) {
            String generatedHmac = generateHmac(marchandId, orderId, amount, currency, marchand.getSecretKey());
            if (hmac.equals(generatedHmac)) {
                System.out.println("HMAC Permission granted."+generatedHmac+"......"+ marchand.getSecretKey());
                return true;
            } else {
                System.out.println("......"+marchandId+"......");

                System.out.println("HMAC verification failed."+generatedHmac+"......"+ marchand.getSecretKey());
                return false;
            }
        }
    }

    System.out.println("Marchand not found with provided hostname and secret key.");
    return false;
}



    private String generateHmac(String marchandId, String orderId, double amount, String currency, String secretKey) {
        String data = marchandId + ':' + orderId + ':' + amount + ':' + currency;
        return hmacDigest(data, secretKey);
    }

    private String hmacDigest(String data, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private String hmacDigest(String data, String key, String algorithm) {
//        try {
//            Mac mac = Mac.getInstance(algorithm);
//            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algorithm);
//            mac.init(secretKey);
//            byte[] hmacBytes = mac.doFinal(data.getBytes());
//            return Base64.getEncoder().encodeToString(hmacBytes);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//            return null;
//        }
//    }

//**END**//

}



//// Method to generate a secret key
//public static String generateSecretKey(int length) {
//    // Generate a random secret key
//    SecureRandom random = new SecureRandom();
//    byte[] keyBytes = new byte[length];
//    random.nextBytes(keyBytes);
//    String secretKey = bytesToHex(keyBytes);
//    return secretKey;
//}
//
// Method to hash the secret key using BCrypt

//
//// Helper method to convert bytes to hexadecimal string
//private static String bytesToHex(byte[] bytes) {
//    StringBuilder result = new StringBuilder();
//    for (byte b : bytes) {
//        result.append(String.format("%02x", b));
//    }
//    return result.toString();
//}