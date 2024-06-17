package alpha.net.workorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @GetMapping
    public List<WorkOrder> getAllWorkOrders() {
        return workOrderService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<WorkOrder> getWorkOrderById(@PathVariable Long id) {
        return workOrderService.findById(id);
    }

    @GetMapping("/account/{accountId}")
    public List<WorkOrder> getWorkOrdersByAccountId(@PathVariable("accountId") Long accountId) {
        return workOrderService.findByAccountId(accountId);
    }

    @PostMapping
    public WorkOrder createWorkOrder(@RequestBody WorkOrder workOrder) {
        return workOrderService.save(workOrder);
    }

    @PutMapping("/{id}")
    public WorkOrder updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        workOrder.setId(id);
        return workOrderService.save(workOrder);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkOrder(@PathVariable Long id) {
        workOrderService.deleteById(id);
    }
}
