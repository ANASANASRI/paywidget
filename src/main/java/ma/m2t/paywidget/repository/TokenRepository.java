package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
