package alpha.net.workorder;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

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
    private Account account;
}
    