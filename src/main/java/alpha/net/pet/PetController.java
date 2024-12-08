package alpha.net.pet;


import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/save-or-update")
    public ResponseEntity<Map<String,Object>> saveOrUpdate(@RequestBody Pet newPet) {
        log.info("Received request to save or update pet: {}", newPet);
        Map<String, Object> response = new HashMap<>();
        try {
            Pet savedPet = petService.saveOrUpdatePet(newPet);
            response.put("pet", savedPet);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch  (EntityNotFoundException e){
            log.error("Id was detected in request body so update was attempted. Pet with Id: {} not found, cannot proceed", newPet.getId());
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
            problemDetail.setProperty("description", "Failed to update pet with Id: " + newPet.getId());
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e ) {
            log.error("Data integrity violation has occurred when trying to save or update pet: {}", newPet);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
            problemDetail.setProperty("description", "Data integrity violation has occurred when attempting to save or update pet: " + newPet);
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (PersistenceException e ){
            log.error("Persistence error has occurred when trying to save or update pet: {}", newPet);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setProperty("description", "Persistence error occurred when trying to save or update pet: " + newPet);
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error has occurred when trying to update pet: {}", newPet);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            problemDetail.setProperty("description", "Unexpected error occurred when trying to update pet: " + newPet);
            response.put("problemDetail", problemDetail);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
