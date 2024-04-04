package ma.m2t.paywidget.service.serviceImpl;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.model.Merchant;
import ma.m2t.paywidget.model.MerchantMethods;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.repository.MerchantMethodsRepository;
import ma.m2t.paywidget.repository.MerchantRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.service.MerchantMethodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class MerchantMethodsServiceImpl implements MerchantMethodsService {

    private MerchantRepository merchantRepository;
    private MerchantMethodsRepository merchantMethodsRepository;
    private PaymentMethodRepository paymentMethodRepository;
///****************************************************************************************************
//Post/////////////////////

///****************************************************************************************************
//Get/////////////////////

    @Override
    public List<Map<String, Object>> getMerchantPaymentMethods(Long merchantId) throws MerchantNotFoundException {
        if (!merchantRepository.existsById(merchantId)) {
            throw new MerchantNotFoundException("Merchant not found with ID: " + merchantId);
        }

        List<MerchantMethods> existingAssociations = merchantMethodsRepository.findByMerchantMerchantId(merchantId);
        List<Long> existingPaymentMethodIds = new ArrayList<>();
        List<Map<String, Object>> paymentMethods = new ArrayList<>();

        for (MerchantMethods association : existingAssociations) {
            existingPaymentMethodIds.add(association.getPaymentMethod().getPaymentMethodId());
        }

        for (Long paymentMethodId : existingPaymentMethodIds) {
            Optional<PaymentMethod> optionalPaymentMethod = paymentMethodRepository.findById(paymentMethodId);
            optionalPaymentMethod.ifPresent(paymentMethod -> {
                Map<String, Object> methodMap = new HashMap<>();
                methodMap.put("paymentMethodId", paymentMethod.getPaymentMethodId());
                methodMap.put("methodName", paymentMethod.getMethodName());
                methodMap.put("methodDescription", paymentMethod.getMethodDescription());
                methodMap.put("methodIconUrl", paymentMethod.getMethodIconUrl());
                paymentMethods.add(methodMap);
            });
        }
        return paymentMethods;
    }

///****************************************************************************************************
//Update/////////////////////
    public void selectPaymentMethodInMerchant(Long merchantId, Long paymentMethodId) throws MerchantNotFoundException {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant Not found"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

        MerchantMethods existingAssociation = merchantMethodsRepository.findByMerchantMerchantIdAndPaymentMethodPaymentMethodId(merchantId, paymentMethodId);

        if (existingAssociation == null) {
            // If association doesn't exist, create a new one and set isSelected to true
            MerchantMethods newAssociation = new MerchantMethods();
            newAssociation.setMerchant(merchant);
            newAssociation.setPaymentMethod(paymentMethod);
            newAssociation.setSelected(true);
            merchantMethodsRepository.save(newAssociation);
        } else {
            // If association exists, toggle isSelected value
            existingAssociation.setSelected(!existingAssociation.isSelected());
            merchantMethodsRepository.save(existingAssociation);
        }

        //
        System.out.println("Payment method " + paymentMethod.getMethodName() + " for merchant " + merchant.getMerchantName() + " is now " + (existingAssociation != null && existingAssociation.isSelected() ? "selected" : "deselected"));
    }
////////

///****************************************************************************************************
//Delete/////////////////////

}
