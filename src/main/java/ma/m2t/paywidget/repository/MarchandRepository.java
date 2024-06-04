package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Marchand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarchandRepository extends JpaRepository<Marchand,Long> {
    Optional<Marchand> findByMarchandHost(String marchandHost);
    /*using for Authenticated */
    Optional<Marchand> findByMarchandName(String marchandName);
}
