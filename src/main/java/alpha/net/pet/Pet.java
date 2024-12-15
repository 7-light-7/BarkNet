package alpha.net.pet;

import alpha.net.appuser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a pet entity with various attributes such as species, subtype, name, age, weight, and gender.
 * Each pet is associated with a user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {

    /**
     * Unique identifier for the pet.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The species of the pet. Cannot be null or empty.
     */
    @NonNull
    @NotEmpty(message ="species cannot be empty ")
    private String species;

    /**
     * The subtype of the pet. Cannot be null or empty.
     */
    @NonNull
    @NotEmpty(message ="subtype cannot be empty")
    private String subtype;

    /**
     * The name of the pet.
     */
    private String name;

    /**
     * The age of the pet in years.
     */
    private int age;

    /**
     * The weight of the pet in kilograms.
     */
    private float weight;

    /**
     * The gender of the pet. True indicates female, false indicates male.
     */
    private boolean gender;

    /**
     * The user associated with the pet.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AppUser user;

    /**
     * The ID of the user associated with the pet.
     */
    @Column(name = "user_id")
    private Long userId;
}
