package ma.m2t.paywidget.service.serviceImpl;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.Merchant;
import ma.m2t.paywidget.repository.MerchantRepository;
import ma.m2t.paywidget.service.MerchantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class MerchantServiceImpl implements MerchantService {

    private MerchantRepository merchantRepository;
    private PayMapperImpl dtoMapper;

// Save a new merchant and generate secret key
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
    // Send secret key SM

//**END**//


//
    @Override
    public List<MerchantDTO> getAllMerchantsByMethod(Long methodId) {
        return null;
    }
//**END**//


//
    @Override
    public MerchantDTO getMerchantById(Long merchantId) throws MerchantNotFoundException{
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFoundException("Customer Not found"));
        return dtoMapper.fromMerchant(merchant);
    }
//**END**//


//
    @Override
    public List<PaymentMethodDTO> getAllMerchantMethods(Long merchantId) {
        return null;
    }
//**END**//


//
    @Override
    public MerchantDTO UpdateMerchant(MerchantDTO merchantDTO) {
        return null;
    }
//**END**//


//
    @Override
    public void deleteMerchant(Long merchantId) {

    }
//**END**//


//  Test if he has Permission
    @Override
    public Boolean hasPermission(Long merchantID, String hostname, String secretKey) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantID);

        if (optionalMerchant.isPresent()) {
            Merchant merchant = optionalMerchant.get();

            if (!merchant.getMerchantHost().equals(hostname)) {
                System.out.println("Hostname does not match.");
                return false;
            }

            if (!merchant.getSecretKey().equals(secretKey)) {
                System.out.println("Secret key does not match.");
                return false;
            }

            System.out.println("Permission granted.");
            return true;
        }

        System.out.println("Merchant not found.");
        return false;
    }
//**END**//


}


//// Example usage
//public static void main(String[] args) {
//    // Create a sample merchant
//    Merchant merchant = new Merchant();
//    merchant.setMerchantHost("https://example.com");
//    merchant.setSecretKey("randomsecretkey");
//
//    // Example hostname and secret key for testing
//    String hostname = "https://example.com";
//    String secretKey = "randomsecretkey";
//
//    // Create an instance of MerchantService
//    MerchantService merchantService = new MerchantService();
//
//    // Test if the merchant has permission
//    boolean hasPermission = merchantService.hasPermission(merchant, hostname, secretKey);
//    if (hasPermission) {
//        System.out.println("Merchant has permission.");
//    } else {
//        System.out.println("Merchant does not have permission.");
//    }
//
//    // Generate a new secret key and send it to the merchant via SMS
//    String newSecretKey = merchantService.generateSecretKey();
//    merchantService.sendSecretKeyViaSMS(merchant, newSecretKey);
//}