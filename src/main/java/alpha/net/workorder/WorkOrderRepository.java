package alpha.net.workorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByAccountId(Long accountId);
}
