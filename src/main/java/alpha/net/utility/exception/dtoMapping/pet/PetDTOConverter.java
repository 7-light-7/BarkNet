package alpha.net.utility.exception.dtoMapping.pet;

import alpha.net.pet.Pet;
import alpha.net.pet.petDto.PetListDTO;

public class PetDTOConverter {

    public static PetListDTO convertToDTO(Pet pet) {
        PetListDTO petDTO = new PetListDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setSpecies(pet.getSpecies());
        petDTO.setSubtype(pet.getSubtype());
        petDTO.setAge(pet.getAge());
        petDTO.setWeight(pet.getWeight());
        petDTO.setGender(pet.isGender());

        if (pet.getUser() != null) {
            petDTO.setOwnerName(pet.getUser().getFirstName() + " " + pet.getUser().getLastName());
        } else {
            petDTO.setOwnerName("Unknown");
        }

        return petDTO;
    }
}
