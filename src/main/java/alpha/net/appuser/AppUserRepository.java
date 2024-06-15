package alpha.net.appuser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
    AppUser findByUsername(String username);

    @SuppressWarnings("null")
    List<AppUser> findAll();

    List<AppUser> findByRolesContaining(String role);

    List<AppUser> findByUsernameContainingIgnoreCase(String username);

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    List<AppUser> findByIdIn(List<Long> ids);
}