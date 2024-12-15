package alpha.net.pet.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alpha.net.pet.Pet;
import alpha.net.pet.petDto.PetFilterBodyDTO;
import alpha.net.pet.petDto.PetListDTO;
import alpha.net.pet.service.PetService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The PetController class is a REST controller that handles HTTP requests
 * related to pet operations such as saving, updating, and retrieving pets.
 * It uses the PetService to perform business logic and interacts with the
 * database through service methods.
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
     * Handles HTTP POST requests to save or update a pet.
     * 
     * @param newPet The pet to save or update, provided in the request body.
     * @return A ResponseEntity containing a map with the saved or updated pet
     *         and a success flag, or an error message and status if an exception occurs.
     */
    @PostMapping("/v1/save-or-update")
    public ResponseEntity<Map<String, Object>> saveOrUpdate(@RequestBody Pet newPet) {
        log.info("Received request to save or update pet: {}", newPet);
        Map<String, Object> response = new HashMap<>();
        try {
            // Attempt to save or update the pet
            Pet savedPet = petService.saveOrUpdatePet(newPet);
            response.put("pet", savedPet);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            // Handle case where pet ID is not found
            log.error("Id was detected in request body so update was attempted. Pet with Id: {} not found, cannot proceed", newPet.getId());
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
            problemDetail.setProperty("description", "Failed to update pet with Id: " + newPet.getId());
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            // Handle data integrity violations
            log.error("Data integrity violation has occurred when trying to save or update pet: {}", newPet);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
            problemDetail.setProperty("description", "Data integrity violation has occurred when attempting to save or update pet: " + newPet);
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e) {
            // Handle persistence errors
            log.error("Persistence error has occurred when trying to save or update pet: {}", newPet);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setProperty("description", "Persistence error occurred when trying to save or update pet: " + newPet);
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle unexpected errors
            log.error("Unexpected error has occurred when trying to update pet: {}", newPet);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setProperty("description", "Unexpected error occurred when trying to update pet: " + newPet);
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles HTTP GET requests to retrieve all pets based on provided filters.
     * 
     * @param petFilterBodyDTO The filters to apply when retrieving pets, provided in the request body.
     * @return A ResponseEntity containing a map with the list of pets and a success flag,
     *         or an error message and status if an exception occurs.
     */
    @GetMapping("/v1/get-all-pets")
    public ResponseEntity<Map<String, Object>> getAllPets(@RequestBody PetFilterBodyDTO petFilterBodyDTO) {
        log.info("Received request to get all pets with filters: {}", petFilterBodyDTO);
        Map<String, Object> response = new HashMap<>();
        try {
            // Attempt to retrieve all pets with the given filters
            List<PetListDTO> pets = petService.findAllPets(petFilterBodyDTO);
            response.put("pets", pets);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            // Handle case where no pets are found
            log.error("No pets found with the given filters");
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
            problemDetail.setProperty("description", "No pets found with the given filters");
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (PersistenceException e) {
            // Handle persistence errors
            log.error("Persistence error has occurred when trying to get all pets");
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setProperty("description", "Persistence error occurred when trying to get all pets");
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle unexpected errors
            log.error("Unexpected error has occurred when trying to get all pets");
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setProperty("description", "Unexpected error occurred when trying to get all pets");
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}