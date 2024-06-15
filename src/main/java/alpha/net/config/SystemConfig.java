package alpha.net.config;

import alpha.net.appuser.*;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class SystemConfig {

    @Autowired
    private AppUserService userService;

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