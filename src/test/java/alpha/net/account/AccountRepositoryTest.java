package alpha.net.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAccount() {
        Account account = Account.builder().name("Test Account").build();
        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Account account = Account.builder().name("Test Account").build();
        Account savedAccount = accountRepository.save(account);

        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getName()).isEqualTo("Test Account");
    }
}
