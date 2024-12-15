package alpha.net.pet;

import alpha.net.appuser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message ="species cannot be empty ")
    private String species;

    @NonNull
    @NotEmpty(message ="subtype cannot be empty")
    private String subtype;

    private String name;
    private int age;
    private float weight;

    /**
     * True = female
     * false = male
     */
    private boolean gender;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AppUser user;

    @Column(name = "user_id")
    private Long userId;
}
