package alpha.net.pet.petDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO for filtering pets.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetFilterBodyDTO {

    private String species;
    private String subtype;
    private String name;
    private Integer age;
    private Float weight;
    private Boolean gender;
    private String ownerName;
}

