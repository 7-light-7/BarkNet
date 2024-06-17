package alpha.net.appuser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service 
public class AppUserService {

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    private final AppUserRepository appuserRepository;

    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appuserRepository, PasswordEncoder passwordEncoder) {
        this.appuserRepository = appuserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser save(AppUser user) {
        logger.info("Saving user with username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser = appuserRepository.save(user);
        logger.info("User saved successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public AppUser findByUsername(String username) {
        logger.info("Finding user by username: {}", username);
        AppUser user = appuserRepository.findByUsername(username);
        if (user == null) {
            logger.warn("User not found with username: {}", username);
        }
        return user;
    }

    public Optional<AppUser> findByUsernameIgnoreCase(String username) {
        logger.info("Finding user by username (ignore case): {}", username);
        Optional<AppUser> user = appuserRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            logger.warn("User not found with username (ignore case): {}", username);
        }
        return user;
    }

    public List<AppUser> findAllUsers() {
        logger.info("Retrieving all users");
        List<AppUser> users = appuserRepository.findAll();
        logger.info("Found {} users", users.size());
        return users;
    }

    public List<AppUser> findByRolesContaining(String role) {
        logger.info("Finding users with role: {}", role);
        List<AppUser> users = appuserRepository.findByRolesContaining(role);
        logger.info("Found {} users with role: {}", users.size(), role);
        return users;
    }

    public List<AppUser> findByUsernameContainingIgnoreCase(String username) {
        logger.info("Finding users by username containing (ignore case): {}", username);
        List<AppUser> users = appuserRepository.findByUsernameContainingIgnoreCase(username);
        logger.info("Found {} users with username containing: {}", users.size(), username);
        return users;
    }

    public List<AppUser> findByIdIn(List<Long> ids) {
        logger.info("Finding users by IDs: {}", ids);
        List<AppUser> users =appuserRepository.findByIdIn(ids);
        logger.info("Found {} users with specified IDs", users.size());
        return users;
    }

    public AppUser createUser(String username, String password, Set<String> roles) {
        logger.info("Creating user with username: {}", username);
        if (appuserRepository.findByUsername(username) != null) {
            logger.warn("Username already exists: {}", username);
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser newUser = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();
        AppUser createdUser = appuserRepository.save(newUser);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return createdUser;
    }

    public void deleteUserByUsername(String username) {
        logger.info("Deleting user by username: {}", username);
        if ("admin".equals(username)) {
            logger.warn("Attempt to delete admin user: {}", username);
            throw new IllegalArgumentException("Cannot delete admin user");
        }

        AppUser user = appuserRepository.findByUsername(username);
        if (user != null) {
            // Delete the user
            appuserRepository.delete(user);
            logger.info("User deleted successfully with username: {}", username);
        } else {
            logger.warn("User not found with username: {}", username);
            throw new IllegalArgumentException("User not found");
        }
    }
}