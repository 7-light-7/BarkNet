package alpha.net.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import alpha.net.utility.apiutils.ResponseUtils;
import alpha.net.utility.utildtos.apiutildtos.SuccessResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest Controller for Account Entity
 * 
 * TODO: Update the handling after service and repo updates are complete
 * 
 * @author Dillon Gaughan
 * @version 1.0
 * @since 2025-04-08
 */
@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        log.info("Received request to get all accounts");
        try {
            return ResponseEntity.ok(
                SuccessResponse.of(
                    accountService.findAll()));
        } catch (Exception e) {
            log.error("Error getting all accounts", e);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        log.info("Received request to get account by id: {}", id);
        try {
            return ResponseEntity.ok(
                SuccessResponse.of(
                    accountService.findById(id)));
        } catch (Exception e) {
            log.error("Error getting account by id: {}", id, e);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        log.info("Received request to create account: {}", account);
        try {
            return ResponseEntity.ok(
                SuccessResponse.of(
                    accountService.save(account)));
        } catch (Exception e) {
            log.error("Error creating account: {}", account, e);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        log.info("Received request to update account: {}", account);
        try {
            return ResponseEntity.ok(
                SuccessResponse.of(
                    accountService.save(account)));
        } catch (Exception e) {
            log.error("Error updating account: {}", account, e);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());  
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        log.info("Received request to delete account by id: {}", id);
        try {
            accountService.deleteById(id);
            return ResponseEntity.ok(
                SuccessResponse.of(
                    "Account deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting account: {}", id, e);
            return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
