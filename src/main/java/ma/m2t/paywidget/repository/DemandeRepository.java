package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Long> {
    List<Demande> findAllByDemandeIsVerifiedFalse();
}
