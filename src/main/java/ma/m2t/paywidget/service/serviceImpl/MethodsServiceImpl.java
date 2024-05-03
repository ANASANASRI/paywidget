package ma.m2t.paywidget.service.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.repository.MarchandRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.repository.TransactionRepository;
import ma.m2t.paywidget.service.MethodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MethodsServiceImpl implements MethodsService{

    private TransactionRepository transactionRepository;
    private MarchandRepository marchandRepository;
    private PaymentMethodRepository paymentMethodReposirory;
    private PayMapperImpl dtoMapper;
    
    @Override
    public List<PaymentMethodDTO> listPaymentMethod() {
        List<PaymentMethod> paymentMethods = paymentMethodReposirory.findAll();
        List<PaymentMethodDTO> paymentMethodDTOS = paymentMethods.stream()
                .map(paymentMethod -> dtoMapper.fromPaymentMethod(paymentMethod))
                .collect(Collectors.toList());
    /*
    List<MerchantDTO> merchantDTOS = new ArrayList<>();
    for (Merchant merchant : merchants) {
        MerchantDTO merchantDTO = dtoMapper.fromMerchant(merchant);
        merchantDTOS.add(merchantDTO);
    }
    */
        return paymentMethodDTOS;
    }

    @Override
    public PaymentMethodDTO updatePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        return null;
    }

    @Override
    public void deletePaymentMethod(PaymentMethodDTO paymentMethodDTO) {

    }

    @Override
    public PaymentMethodDTO getPaymentMethodById(Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodReposirory.findById(paymentMethodId).orElse(null);
        if(paymentMethod == null) {
            // Gérer le cas où aucun mode de paiement avec cet identifiant n'est trouvé
            return null; // Ou vous pouvez lancer une exception appropriée
        }
        return dtoMapper.fromPaymentMethod(paymentMethod);
    }

    @Override
    public PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        if (paymentMethodDTO == null) {
            log.error("PaymentMethodDTO is null. Cannot save null object.");
            throw new IllegalArgumentException("PaymentMethodDTO cannot be null");
        }
        log.info("Saving new Payment Method");
        PaymentMethod paymentMethod = dtoMapper.fromPaymentMethodDTO(paymentMethodDTO);
        // Utilisation de Optional pour éviter les NullPointerException
        Optional<PaymentMethod> savedPaymentMethod = Optional.ofNullable(paymentMethodReposirory.save(paymentMethod));
        return savedPaymentMethod.map(dtoMapper::fromPaymentMethod)
                .orElseThrow(() -> new RuntimeException("Error while saving payment method"));
    }

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethodsByMarchand(Long marchandId) {
        return null;
    }

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethodsByMethod(Long methodId) {
        return null;
    }

    @Override
    public PaymentMethodDTO UpdatePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        return null;
    }

    @Override
    public void deletePaymentMethod(Long paymentMethodId) {

    }
///****************************************************************************************************


//Post/////////////////////

///****************************************************************************************************
//Get/////////////////////

///****************************************************************************************************
//Update/////////////////////

///****************************************************************************************************
//Delete/////////////////////
}
//INSERT INTO payment_method (method_name, method_description, method_icon_url, type)
//VALUES
//        ('token', 'Paiement sécurisé avec token bancaire', 'https://uxwing.com/wp-content/themes/uxwing/download/education-school/t-alphabet-round-icon.png','TOKEN'),
//        ('carte bancaire', 'Paiement par carte bancaire Visa', 'https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/master-card-icon.png', 'CARD'),
//         ('Amanty', 'Paiement via le service Amanty', 'https://uxwing.com/wp-content/themes/uxwing/download/crime-security-military-law/safe-icon.png', 'AMANTY'),
//         ('Direct Pay', 'Paiement sécurisé directement en ligne', 'https://uxwing.com/wp-content/themes/uxwing/download/e-commerce-currency-shopping/credit-card-color-icon.png', 'PAYDIRECT');
