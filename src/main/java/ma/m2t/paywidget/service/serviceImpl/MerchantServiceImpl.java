package ma.m2t.paywidget.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.Merchant;
import ma.m2t.paywidget.model.MerchantMethods;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.repository.MerchantMethodsRepository;
import ma.m2t.paywidget.repository.MerchantRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.service.MerchantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.apache.commons.codec.binary.Hex;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class MerchantServiceImpl implements MerchantService {

    private MerchantRepository merchantRepository;
    private MerchantMethodsRepository merchantMethodsRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private PayMapperImpl dtoMapper;

///****************************************************************************************************
///POST/////////////////////
    // Save merchant and generate secret key
    public MerchantDTO saveNewMerchant(MerchantDTO merchantDTO) {

        Merchant merchant = dtoMapper.fromMerchantDTO(merchantDTO);

        String secretKey = generateSecretKey();
        merchant.setSecretKey(secretKey);

        Merchant savedMerchant =merchantRepository.save(merchant);

        return dtoMapper.fromMerchant(savedMerchant);
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
public void FirstAssociatePaymentMethodsToMerchant(Long merchantId, List<Long> paymentMethodIds) throws MerchantNotFoundException {
    Merchant merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new MerchantNotFoundException("Merchant Not found"));

    // Retrieve the existing associations for this merchant
    List<MerchantMethods> existingAssociations = merchantMethodsRepository.findByMerchantMerchantId(merchantId);
    Set<Long> existingPaymentMethodIds = new HashSet<>();
    for (MerchantMethods association : existingAssociations) {
        existingPaymentMethodIds.add(association.getPaymentMethod().getPaymentMethodId());
    }

    // Iterate over the chosen payment method IDs
    for (Long paymentMethodId : paymentMethodIds) {
        if (!existingPaymentMethodIds.contains(paymentMethodId)) {
            // Retrieve the payment method from the database
            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                    .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

            // Create a new MerchantMethods entity
            MerchantMethods merchantMethods = new MerchantMethods();
            merchantMethods.setMerchant(merchant);
            merchantMethods.setPaymentMethod(paymentMethod);
            merchantMethods.setSelected(true);

            merchantMethodsRepository.save(merchantMethods);
        } else {
            // Update existing association if it exists
            for (MerchantMethods existingAssociation : existingAssociations) {
                if (existingAssociation.getPaymentMethod().getPaymentMethodId().equals(paymentMethodId)) {
                    existingAssociation.setSelected(true); // Update isSelected to true
                    merchantMethodsRepository.save(existingAssociation);
                    break;
                }
            }
        }
    }

    // Print out the names of associated payment methods
    System.out.print("Merchant " + merchant.getMerchantName() + " has the following payment methods: ");
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
    public List<MerchantDTO> getAllMerchantsByMethod(Long methodId) {
        return null;
    }
//**END**//

//
    @Override
    public List<MerchantDTO> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantDTO> merchantsDTO=new ArrayList<>();
        for (Merchant m:merchants){
            merchantsDTO.add(dtoMapper.fromMerchant(m));
        }
        return merchantsDTO;
    }
//**END**//

//
    @Override
    public MerchantDTO getMerchantById(Long merchantId) throws MerchantNotFoundException{
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant Not found"));
        return dtoMapper.fromMerchant(merchant);
    }
//**END**//

//
    @Override
    public List<PaymentMethodDTO> getAllMerchantMethods(Long merchantId) {
        return null;
    }
//**END**//


///****************************************************************************************************
///UPDATE/////////////////////
//
    @Override
    public MerchantDTO updateMerchant(MerchantDTO merchantDTO) {
        Merchant merchantToUpdate = dtoMapper.fromMerchantDTO(merchantDTO);

        Merchant existingMerchant = merchantRepository.findById(merchantDTO.getMerchantId())
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));

        // Update the existing merchant with the values from the DTO
        existingMerchant.setMerchantName(merchantToUpdate.getMerchantName());
        // existingMerchant.setEmail(merchantToUpdate.getEmail());
        //
        //
        //
        //
        //

        // Save the updated merchant
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);

        // Mapping from entity to DTO and returning
        return dtoMapper.fromMerchant(updatedMerchant);
    }

//**END**//

//

//**END**//


///****************************************************************************************************
///DELETE/////////////////////
//
    @Override
    public void deleteMerchant(Long merchantId) {
        merchantRepository.deleteById(merchantId);
    }
//**END**//




///****************************************************************************************************
///CHECK PERMISSION/////////////////////
//
public Boolean hasPermission(String hostname, String accessKey, String merchantId, String orderId,
                             double amount, String currency, String hmac){
    List<Merchant> merchants = merchantRepository.findAll();

    for (Merchant merchant : merchants) {
        //verify the dehash secret key if equals to secretKey provided
        if (merchant.getMerchantHost().equals(hostname) && merchant.getAccessKey().equals(accessKey)) {
            String generatedHmac = generateHmac(merchantId, orderId, amount, currency, merchant.getSecretKey());
            if (hmac.equals(generatedHmac)) {
                System.out.println("HMAC Permission granted."+generatedHmac+"......"+merchant.getSecretKey());
                return true;
            } else {
                System.out.println("HMAC verification failed."+generatedHmac+"......"+merchant.getSecretKey());
                return false;
            }
        }
    }

    System.out.println("Merchant not found with provided hostname and secret key.");
    return false;
}



    private String generateHmac(String merchantId, String orderId, double amount, String currency, String secretKey) {
        String data = merchantId + ':' + orderId + ':' + amount + ':' + currency;
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