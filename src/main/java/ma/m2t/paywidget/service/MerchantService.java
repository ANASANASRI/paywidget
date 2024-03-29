package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MerchantNotFoundException;
import ma.m2t.paywidget.model.Merchant;

import java.util.List;

public interface MerchantService {

    //Post
    MerchantDTO saveNewMerchant(MerchantDTO merchantDTO);

    //Get
    List<MerchantDTO> getAllMerchantsByMethod(Long methodId);
    MerchantDTO getMerchantById(Long merchantId) throws MerchantNotFoundException;
    List<PaymentMethodDTO> getAllMerchantMethods(Long merchantId);
    Boolean hasPermission(Long merchantId, String hostname, String secretKey);

    //Update
    MerchantDTO UpdateMerchant(MerchantDTO merchantDTO);

    //Delete
    void deleteMerchant(Long merchantId);
}
