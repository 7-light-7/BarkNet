package alpha.net.pet.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alpha.net.pet.Pet;
import alpha.net.pet.petDto.PetFilterBodyDTO;
import alpha.net.pet.service.PetService;
import alpha.net.utility.apiutils.ResponseUtils;
import alpha.net.utility.utildtos.apiutildtos.SuccessResponse;

/**
 * This class is using the wildcard return type to allow me to return a SuccessResponse
 * or a ProblemDetail. The catch all Exception will prevent any unexpected errors from
 * being returned directly to the client. I belive it is safe to use wildcards here. 
 * 
 * @author Dillon Gaughan
 * @version 1.0
 * @since 2025-04-09
 */
@Slf4j
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    /**
     * Constructs a new PetController with the specified PetService.
     *
     * @param petService The service used to perform operations on pets.
     */
    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * Save or update a pet.
     * 
     * @param newPet The pet to save or update, provided in the request body.
     * @return A ResponseEntity containing a map with the saved or updated pet
     *         and a success flag, or an error message and status if an exception occurs.
     */
    @PostMapping("/v1/save-or-update")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Pet newPet) {
        log.info("Received request to save or update pet: {}", newPet);

        try {

            // Attempt to save or update the pet
            return ResponseEntity.ok(
                SuccessResponse.of(
                    petService.saveOrUpdatePet(newPet)));

        } catch (EntityNotFoundException e) {
            // Handle case where pet ID is not found
            log.error("Id was detected in request body so update was attempted. Pet with Id: {} not found, cannot proceed",
                 newPet.getId());

            return ResponseUtils.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());

        } catch (DataIntegrityViolationException e) {
            // Handle data integrity violations
            log.error("Data integrity violation has occurred when trying to save or update pet: {}", newPet);
            return ResponseUtils.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (PersistenceException e) {
            // Handle persistence errors
            log.error("Persistence error has occurred when trying to save or update pet: {}", newPet);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            log.error("Unexpected error has occurred when trying to update pet: {}", newPet);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    /**
     *  Retrieve all pets based on provided filters.
     * 
     * @param petFilterBodyDTO The filters to apply when retrieving pets, provided in the request body.
     * @return A ResponseEntity containing a map with the list of pets and a success flag,
     *         or an error message and status if an exception occurs.
     */
    @GetMapping("/v1/get-all-pets")
    public ResponseEntity<?> getAllPets(@RequestBody PetFilterBodyDTO petFilterBodyDTO) {
        log.info("Received request to get all pets with filters: {}", petFilterBodyDTO);
        try {
            // Attempt to retrieve all pets with the given filters
            return ResponseEntity.ok(
                SuccessResponse.of(
                    petService.findAllPets(petFilterBodyDTO)));
            
        } catch (EntityNotFoundException e) {
            // Handle case where no pets are found
            log.error("No pets found with the given filters");
            return ResponseUtils.buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (PersistenceException e) {
            // Handle persistence errors
            log.error("Persistence error has occurred when trying to get all pets");
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            log.error("Unexpected error has occurred when trying to get all pets");
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}