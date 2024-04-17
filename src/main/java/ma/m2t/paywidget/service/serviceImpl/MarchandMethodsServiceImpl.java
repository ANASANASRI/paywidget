package ma.m2t.paywidget.service.serviceImpl;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.model.Marchand;
import ma.m2t.paywidget.model.MarchandMethods;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.repository.MarchandMethodsRepository;
import ma.m2t.paywidget.repository.MarchandRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.service.MarchandMethodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class MarchandMethodsServiceImpl implements MarchandMethodsService {

    private MarchandRepository marchandRepository;
    private MarchandMethodsRepository marchandMethodsRepository;
    private PaymentMethodRepository paymentMethodRepository;

///****************************************************************************************************
//Post/////////////////////

///****************************************************************************************************
//Get/////////////////////

    @Override
    public List<Map<String, Object>> getMarchandPaymentMethods(Long marchandId) throws MarchandNotFoundException {
        if (!marchandRepository.existsById(marchandId)) {
            throw new MarchandNotFoundException("Marchand not found with ID: " + marchandId);
        }

        List<MarchandMethods> existingAssociations = marchandMethodsRepository.findByMarchandMarchandId(marchandId);
        List<Long> existingPaymentMethodIds = new ArrayList<>();
        List<Map<String, Object>> paymentMethods = new ArrayList<>();

        for (MarchandMethods association : existingAssociations) {
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
    public void selectPaymentMethodInMarchand(Long marchandId, Long paymentMethodId) throws MarchandNotFoundException {
        Marchand marchand = marchandRepository.findById(marchandId)
                .orElseThrow(() -> new MarchandNotFoundException("Marchand Not found"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

        MarchandMethods existingAssociation = marchandMethodsRepository.findByMarchandMarchandIdAndPaymentMethodPaymentMethodId(marchandId, paymentMethodId);

        if (existingAssociation == null) {
            // If association doesn't exist, create a new one and set isSelected to true
            MarchandMethods newAssociation = new MarchandMethods();
            newAssociation.setMarchand(marchand);
            newAssociation.setPaymentMethod(paymentMethod);
            newAssociation.setSelected(true);
            marchandMethodsRepository.save(newAssociation);
        } else {
            // If association exists, toggle isSelected value
            existingAssociation.setSelected(!existingAssociation.isSelected());
            marchandMethodsRepository.save(existingAssociation);
        }

        //
        System.out.println("Payment method " + paymentMethod.getMethodName() + " for marchand " + marchand.getMarchandName() + " is now " + (existingAssociation != null && existingAssociation.isSelected() ? "selected" : "deselected"));
    }
////////

///****************************************************************************************************
//Delete/////////////////////

}
