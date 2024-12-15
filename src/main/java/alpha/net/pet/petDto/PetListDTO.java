package alpha.net.pet.petDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetListDTO {
    private Long id;
    private String name;
    private String species;
    private String subtype;
    private int age;
    private float weight;
    private boolean gender;
    private String ownerName;
}


