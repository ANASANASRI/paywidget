package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Marchand;
import ma.m2t.paywidget.model.MarchandMethods;
import ma.m2t.paywidget.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarchandMethodsRepository extends JpaRepository<MarchandMethods,Long> {
    List<MarchandMethods> findByMarchandMarchandId(Long marchandId);
    MarchandMethods findByMarchandMarchandIdAndPaymentMethodPaymentMethodId(Long marchandId, Long paymentMethodId);
    List<MarchandMethods> findByPaymentMethodAndMarchand(PaymentMethod paymentMethod, Marchand marchand);
    List<MarchandMethods> findByPaymentMethod(PaymentMethod paymentMethod);}
