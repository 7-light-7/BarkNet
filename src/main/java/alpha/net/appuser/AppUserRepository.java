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

    /**
     * Finds all users that have the specified role
     * 
     * @param role The role to search for
     * @return List of users that have the specified role
     */
    List<AppUser> findByRolesContaining(String role);

    /**
     * Finds all users that have the specified username
     * 
     * @param username The username to search for
     * @return List of users that have the specified username
     */
    List<AppUser> findByUsernameContainingIgnoreCase(String username);

    /**
     * Finds a user by their username, ignoring case
     * 
     * @param username The username to search for
     * @return The user that has the specified username
     */
    Optional<AppUser> findByUsernameIgnoreCase(String username);

    /**
     * Finds all users that have the specified IDs
     * 
     * @param ids The IDs to search for
     * @return List of users that have the specified IDs
     */
    List<AppUser> findByIdIn(List<Long> ids);
}
