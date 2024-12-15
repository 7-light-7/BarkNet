package alpha.net.pet.service;

import alpha.net.pet.Pet;
import alpha.net.pet.petDto.PetFilterBodyDTO;
import alpha.net.pet.petDto.PetListDTO;
import alpha.net.pet.repo.PetRepository;
import alpha.net.utility.exception.ExceptionHandlerUtils;
import alpha.net.utility.exception.dtoMapping.pet.PetDTOConverter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    @Override
    public Pet saveOrUpdatePet(Pet pet) throws Exception {
        String logMessage = "Saving or updating pet";
        return ExceptionHandlerUtils.handleTransactionExceptions(
                () -> {
                    if (pet.getId() != null && pet.getId() > 0) {
                        Optional<Pet> existingPetOpt = petRepository.findById(pet.getId());
                        if (existingPetOpt.isPresent()) {
                            log.info("Pet {} already exists, proceeding with update", pet.getId());
                            Pet existingPet = updateExistingPet(existingPetOpt.get(), pet);
                            return petRepository.save(existingPet);
                        } else {
                            throw new EntityNotFoundException("Pet with id " + pet.getId() + " does not exist");
                        }
                    } else {
                        log.info("No existing pet detected, saving new pet as: {}", pet);
                        return petRepository.save(pet);
                    }
                },
                () -> new DataIntegrityViolationException("Data integrity violation detected for pet: " + pet),
                () -> new PersistenceException("Database error occurred when saving or updating pet: " + pet),
                () -> new Exception("Unexpected error occurred when saving or updating pet: " + pet),
                logMessage,
                pet
        );
    }

    @Override
    public Pet getPetById(long petId) throws Exception {
        String logMessage = "Getting pet with id " + petId;
        return ExceptionHandlerUtils.handleTransactionExceptions(
                () -> petRepository.findById(petId)
                        .orElseThrow(() -> new EntityNotFoundException("Pet with id " + petId + " does not exist")),
                () -> new PersistenceException("Database error occurred when getting pet by id: " + petId),
                () -> new Exception("Unexpected error occurred when getting pet by id: " + petId),
                logMessage
        );
    }



    @Transactional
    @Override
    public void deletePetById(long petId) throws Exception {
        String logMessage = "Deleting pet with Id " + petId;
        ExceptionHandlerUtils.handleTransactionExceptions(
                () -> {
                    Pet pet = petRepository.findById(petId)
                            .orElseThrow(() -> new EntityNotFoundException("Pet not found: " + petId));
                    petRepository.delete(pet);
                    return null;
                },
                () -> new DataIntegrityViolationException("Data integrity violation during deletion of pet with Id: " + petId),
                () -> new PersistenceException("Database persistence error occurred when deleting pet with Id: " + petId),
                () -> new Exception("Unexpected error occurred when deleting pet with Id: " + petId),
                logMessage
        );
    }


    /**
     * Sets the pet fields in the update case for save or update
     * @param existingPet the existing pet in BarkNet
     * @param newPet the new details to be persisted
     * @return the new pet entity with the merged details
     */
    private Pet updateExistingPet(Pet existingPet, Pet newPet) {
        existingPet.setName(newPet.getName());
        existingPet.setSpecies(newPet.getSpecies());
        existingPet.setSubtype(newPet.getSubtype());
        existingPet.setWeight(newPet.getWeight());
        existingPet.setAge(newPet.getAge());
        existingPet.setGender(newPet.isGender());
        existingPet.setUser(newPet.getUser());
        return existingPet;
    }

    @Override
    public List<PetListDTO> findAllPets(PetFilterBodyDTO petFilterBodyDTO) throws Exception {
        String logMessage = "Fetching all pets with filters: " +
                            "species=" + petFilterBodyDTO.getSpecies() + ", " +
                            "subtype=" + petFilterBodyDTO.getSubtype() + ", " +
                            "name=" + petFilterBodyDTO.getName() + ", " +
                            "age=" + petFilterBodyDTO.getAge() + ", " +
                            "weight=" + petFilterBodyDTO.getWeight() + ", " +
                            "gender=" + petFilterBodyDTO.getGender() + ", " +
                            "ownerName=" + petFilterBodyDTO.getOwnerName();
        log.info(logMessage);

        return ExceptionHandlerUtils.handleTransactionExceptions(
            () -> {
                List<Pet> pets = petRepository.findByAllPetsByFilter(
                    petFilterBodyDTO.getSpecies(),
                    petFilterBodyDTO.getSubtype(),
                    petFilterBodyDTO.getName(),
                    petFilterBodyDTO.getAge(),
                    petFilterBodyDTO.getWeight(),
                    petFilterBodyDTO.getGender(),
                    petFilterBodyDTO.getOwnerName()
                ).orElseThrow(() -> new EntityNotFoundException("No pets found with the given filters"));
                
                return pets.stream()
                    .map(PetDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
            },
            () -> new PersistenceException("Database persistence error occurred when fetching all pets"),
            () -> new Exception("Unexpected error occurred when fetching all pets"),
            logMessage
        );  
    }



}
