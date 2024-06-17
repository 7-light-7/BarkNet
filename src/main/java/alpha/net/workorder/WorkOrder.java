package alpha.net.workorder;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import alpha.net.account.*;
    
@Entity
@Table(name = "work_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Work order description is required")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkOrder workOrder = (WorkOrder) o;

        return id != null ? id.equals(workOrder.id) : workOrder.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}