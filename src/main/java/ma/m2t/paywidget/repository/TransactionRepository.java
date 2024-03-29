package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
