package alpha.net.pet.service;

import java.util.List;

import alpha.net.pet.Pet;
import alpha.net.pet.petDto.PetFilterBodyDTO;
import alpha.net.pet.petDto.PetListDTO;
import jakarta.transaction.Transactional;

public interface PetService {

    /**
     * saves or updates a pet in the BarkNet system
     * @param pet the pet entity to be persisted
     * @return the saved pet entity
     * @throws Exception the exception class caught by the lambda exception handler
     */
    @Transactional
    Pet saveOrUpdatePet(Pet pet) throws Exception;

    /**
     * Fetches a specific pet entity by their ID
     * @param petId the ID of the pet to be fetched
     * @return the pet entity with the matching ID
     * @throws Exception the exception class caught by the lambda exception handler
     */
    Pet getPetById(long petId) throws Exception;


    /**
     * Fetches all pets from BarkNet
     * @return List of all pets in the system
     * @throws Exception the exception class caught by the lambda exception handler
     */
    List<PetListDTO> findAllPets(PetFilterBodyDTO petFilterBodyDTO) throws Exception;


    /**
     * Deletes a pet entity from BarkNet by their ID
     * @param petId the ID of the pet to be deleted
     * @throws Exception the exception class caught by the lambda exception handler
     */
    @Transactional
    void deletePetById(long petId) throws Exception;
}
