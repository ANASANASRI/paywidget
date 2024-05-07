package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.enums.ERole;
import ma.m2t.paywidget.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
