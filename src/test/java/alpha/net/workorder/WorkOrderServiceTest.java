package alpha.net.workorder;

import alpha.net.account.Account;
import alpha.net.account.AccountRepository;
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

public class WorkOrderServiceTest {

    @InjectMocks
    private WorkOrderService workOrderService;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Account account = Account.builder().name("Test Account").build();
        WorkOrder workOrder1 = WorkOrder.builder().description("Test Work Order 1").account(account).build();
        WorkOrder workOrder2 = WorkOrder.builder().description("Test Work Order 2").account(account).build();
        when(workOrderRepository.findAll()).thenReturn(Arrays.asList(workOrder1, workOrder2));

        List<WorkOrder> workOrders = workOrderService.findAll();
        assertThat(workOrders).hasSize(2);
        verify(workOrderRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Account account = Account.builder().name("Test Account").build();
        WorkOrder workOrder = WorkOrder.builder().description("Test Work Order").account(account).build();
        when(workOrderRepository.findById(1L)).thenReturn(Optional.of(workOrder));

        Optional<WorkOrder> foundWorkOrder = workOrderService.findById(1L);
        assertThat(foundWorkOrder).isPresent();
        assertThat(foundWorkOrder.get().getDescription()).isEqualTo("Test Work Order");
        verify(workOrderRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveWorkOrder() {
        Account account = Account.builder().name("Test Account").build();
        WorkOrder workOrder = WorkOrder.builder().description("Test Work Order").account(account).build();
        when(workOrderRepository.save(workOrder)).thenReturn(workOrder);

        WorkOrder savedWorkOrder = workOrderService.save(workOrder);
        assertThat(savedWorkOrder).isNotNull();
        verify(workOrderRepository, times(1)).save(workOrder);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(workOrderRepository).deleteById(1L);
        workOrderService.deleteById(1L);
        verify(workOrderRepository, times(1)).deleteById(1L);
    }
}
