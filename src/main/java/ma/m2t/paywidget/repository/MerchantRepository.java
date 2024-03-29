package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
}
