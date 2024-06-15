package alpha.net.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        accountRepository.deleteAll();
    }

    @Test
    @Rollback
    public void testSaveAccount() {
        Account account = Account.builder()
            .id(5L)
            .name("Test Account")
            .build();
        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();
    }

    @Test
    @Rollback
    public void testFindById() {
        Account account = Account.builder()
            .id(6L)
            .name("Test Account")
            .build();
        Account savedAccount = accountRepository.save(account);

        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getName()).isEqualTo("Test Account");
    }
}
