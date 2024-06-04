package ma.m2t.paywidget.repository;

import ma.m2t.paywidget.enums.ERole;
import ma.m2t.paywidget.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u ORDER BY u.id")
    List<User> findAllUsersSortedById();
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_ADMIN'")
    List<User> findAdmin();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_MODERATOR'")
    List<User> findMod();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
    List<User> findUser();

    @Query("SELECT r.name FROM User u JOIN u.roles r WHERE u.username=?1")
    ERole findRole(String username);

    @Query("SELECT u.roles FROM User u WHERE u.username = ?1")
    List<ERole> findRolesByUsername(String username);

    void deleteUsersById(Long id);
}
