package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Marchand;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByMarchand(Optional<Marchand> marchand);

    List<Transaction> findAllByPaymentMethod(Optional<PaymentMethod> paymentMethod);
}
