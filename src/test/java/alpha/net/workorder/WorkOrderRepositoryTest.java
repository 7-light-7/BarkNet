package alpha.net.workorder;

import alpha.net.account.Account;
import alpha.net.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WorkOrderRepositoryTest {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveWorkOrder() {
        Account account = Account.builder().name("Test Account").build();
        Account savedAccount = accountRepository.save(account);

        WorkOrder workOrder = WorkOrder.builder()
                .description("Test Work Order")
                .account(savedAccount)
                .build();
        WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);

        assertThat(savedWorkOrder).isNotNull();
        assertThat(savedWorkOrder.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Account account = Account.builder().name("Test Account").build();
        Account savedAccount = accountRepository.save(account);

        WorkOrder workOrder = WorkOrder.builder()
                .description("Test Work Order")
                .account(savedAccount)
                .build();
        WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);

        Optional<WorkOrder> foundWorkOrder = workOrderRepository.findById(savedWorkOrder.getId());
        assertThat(foundWorkOrder).isPresent();
        assertThat(foundWorkOrder.get().getDescription()).isEqualTo("Test Work Order");
    }
}
