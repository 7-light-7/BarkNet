package alpha.net.config;

import alpha.net.appuser.*;
import alpha.net.pet.Pet;
import alpha.net.pet.service.PetService;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@Profile("!test")
public class SystemConfig {

    @Autowired
    private AppUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PetService petService; // Service to handle Pet entities

    private AppUser createUser(String username, String firstName, String lastName, String email, Integer cellPhone, String password, Set<String> roles) {
        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .cellPhone(cellPhone)
                .password(passwordEncoder.encode(password)) 
                .firstName(firstName)
                .lastName(lastName)
                .roles(roles)
                .build();
        return userService.save(user);
    }

    
    private Pet createPet(String name, AppUser owner) throws Exception {
        Pet pet = Pet.builder()
                .name(name)
                .species("Dog")
                .subtype("Beagle")
                .age(1)
                .weight(10.0f)
                .gender(true)
                .user(owner)
                .userId(owner.getId())
                .build();
        return petService.saveOrUpdatePet(pet);
    }

    @PostConstruct
    public void init() throws Exception {
        createPetOwnersWithPets();
    }

    public void createPetOwnersWithPets() throws Exception {
        for (int i = 1; i <= 10; i++) {
            String username = "petOwner" + i;
            String firstName = "FirstName" + i;
            String lastName = "LastName" + i;
            String email = "petOwner" + i + "@example.com";
            Integer cellPhone = 1000000000 + i;
            AppUser owner = createUser(username, firstName, lastName, email, cellPhone, "password", Set.of("ROLE_PET_OWNER"));
            
            for (int j = 1; j <= 3; j++) { // Create 3 pets for each owner
                try {
                    String petName = "Pet" + j + "_Owner" + i;
                    createPet(petName, owner);
                } catch (Exception e) {
                    log.error("Error on startup generation. Failed when creating pet for owner " + i + " and pet " + j, e);
                    throw e;
                }
            }
        }
    }

    @Bean
    @Qualifier("defaultUser1")
    public AppUser getDefaultUser1() {
        AppUser user = AppUser.builder()
                .username("default1")
                .password("password") 
                .roles(Set.of("ROLE_USER"))
                .build();
        return userService.save(user);
    }

    @Bean
    @Qualifier("defaultUser2")
    public AppUser getDefaultUser2() {
        AppUser user = AppUser.builder()
                .username("default2")
                .password("password")  
                .roles(Set.of("ROLE_USER"))
                .build();
        return userService.save(user);
    }

    @Bean
    @Qualifier("admin")
    public AppUser getAdmin() {
        AppUser admin = AppUser.builder()
                .username("admin")
                .password("admin")
                .roles(Set.of("ROLE_ADMIN"))
                .build();
        return userService.save(admin);
    }
}