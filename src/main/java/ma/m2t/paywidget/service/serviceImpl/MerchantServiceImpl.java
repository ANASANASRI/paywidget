package ma.m2t.paywidget.service.serviceImpl;

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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class MerchantServiceImpl implements MerchantService {

    private MerchantRepository merchantRepository;
    private MerchantMethodsRepository merchantMethodsRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private PayMapperImpl dtoMapper;


///POST//////////////////////////////////////////
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
    public void associatePaymentMethodsToMerchant(Long merchantId, List<Long> paymentMethodIds) throws MerchantNotFoundException {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant Not found"));

        // Retrieve the payment method entities
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAllById(paymentMethodIds);
        // Iterate over the chosen payment method IDs
        for (Long paymentMethodId : paymentMethodIds) {
            // Retrieve the payment method from the database
            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                    .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

            // Create a new MerchantMethods entity
            MerchantMethods merchantMethods = new MerchantMethods();
            merchantMethods.setMerchant(merchant);
            merchantMethods.setPaymentMethod(paymentMethod);
            merchantMethods.setSelected(true);

            // Save the MerchantMethods entity
            merchantMethodsRepository.save(merchantMethods);

        // Print out the names of associated payment methods
        System.out.print("Merchant " + merchant.getMerchantName() + " has the following payment methods: ");
        for (PaymentMethod p : paymentMethods) {
            System.out.print(p.getMethodName() + ", ");
        }
        System.out.println();
        //
    }
    }
//**END**//


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



///UPDATE/////////////////////
//
    @Override
    public MerchantDTO UpdateMerchant(MerchantDTO merchantDTO) {
        return null;
    }
//**END**//



///DELETE/////////////////////
//
    @Override
    public void deleteMerchant(Long merchantId) {
        merchantRepository.deleteById(merchantId);
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