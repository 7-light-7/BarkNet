package alpha.net.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Account account1 = Account.builder().name("Test Account 1").build();
        Account account2 = Account.builder().name("Test Account 2").build();
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<Account> accounts = accountService.findAll();
        assertThat(accounts).hasSize(2);
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Account account = Account.builder().name("Test Account").build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountService.findById(1L);
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getName()).isEqualTo("Test Account");
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveAccount() {
        Account account = Account.builder().name("Test Account").build();
        when(accountRepository.save(account)).thenReturn(account);

        Account savedAccount = accountService.save(account);
        assertThat(savedAccount).isNotNull();
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(accountRepository).deleteById(1L);
        accountService.deleteById(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }
}
