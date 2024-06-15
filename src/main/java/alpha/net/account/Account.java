package alpha.net.account;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

import alpha.net.workorder.*;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Account name is required")
    private String name;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WorkOrder> workOrders;
}
