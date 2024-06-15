package alpha.net.workorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    public List<WorkOrder> findAll() {
        return workOrderRepository.findAll();
    }

    public Optional<WorkOrder> findById(Long id) {
        return workOrderRepository.findById(id);
    }

    public List<WorkOrder> findByAccountId(Long accountId) {
        return workOrderRepository.findByAccountId(accountId);
    }

    public WorkOrder save(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    public void deleteById(Long id) {
        workOrderRepository.deleteById(id);
    }
}
