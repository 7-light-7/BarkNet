package alpha.net.pet.service;

import java.util.List;

import alpha.net.pet.Pet;
import alpha.net.pet.petDto.PetFilterBodyDTO;
import alpha.net.pet.petDto.PetListDTO;
import jakarta.transaction.Transactional;

public interface PetService {

    /**
     * Saves or updates a pet in the BarkNet system.
     * If the pet has an ID, it attempts to update the existing pet.
     * If the pet does not have an ID, it saves a new pet.
     * 
     * @param pet the pet entity to be persisted
     * @return the saved or updated pet entity
     * @throws EntityNotFoundException if the pet to be updated does not exist
     * @throws DataIntegrityViolationException if there is a data integrity violation
     * @throws PersistenceException if a database error occurs
     * @throws Exception for any unexpected errors
     */
    @Transactional
    Pet saveOrUpdatePet(Pet pet) throws Exception;

    /**
     * Fetches a specific pet entity by their ID.
     * 
     * @param petId the ID of the pet to be fetched
     * @return the pet entity with the matching ID
     * @throws EntityNotFoundException if no pet with the given ID exists
     * @throws PersistenceException if a database error occurs
     * @throws Exception for any unexpected errors
     */
    Pet getPetById(long petId) throws Exception;

    /**
     * Fetches all pets from BarkNet based on the provided filter criteria.
     * 
     * @param petFilterBodyDTO the filter criteria for fetching pets
     * @return List of all pets matching the filter criteria
     * @throws EntityNotFoundException if no pets match the given filters
     * @throws PersistenceException if a database error occurs
     * @throws Exception for any unexpected errors
     */
    List<PetListDTO> findAllPets(PetFilterBodyDTO petFilterBodyDTO) throws Exception;

    /**
     * Deletes a pet entity from BarkNet by their ID.
     * 
     * @param petId the ID of the pet to be deleted
     * @throws EntityNotFoundException if no pet with the given ID exists
     * @throws DataIntegrityViolationException if there is a data integrity violation during deletion
     * @throws PersistenceException if a database error occurs
     * @throws Exception for any unexpected errors
     */
    @Transactional
    void deletePetById(long petId) throws Exception;
}
