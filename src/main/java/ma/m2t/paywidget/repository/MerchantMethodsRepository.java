package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.MerchantMethods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantMethodsRepository extends JpaRepository<MerchantMethods,Long> {
    List<MerchantMethods> findByMerchantMerchantId(Long merchantId);
    MerchantMethods findByMerchantMerchantIdAndPaymentMethodPaymentMethodId(Long merchantId, Long paymentMethodId);
}
