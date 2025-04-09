package alpha.net.utility.apiutils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import alpha.net.utility.utildtos.ErrorResponse;

/**
 * Utility class for building error responses.
 * 
 * @author Dillon Gaughan
 * @version 1.0
 * @since 2025-04-08
 */
public class ResponseUtils {
    

    /**
     * Wrap an error response in a ResponseEntity.
     * 
     * @param status The status code to be returned.
     * @param detail The detail message to be returned.
     * @return A ResponseEntity containing the error response.
     */
    public static ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String detail) {
        return ResponseEntity
         .status(status)
          .body(ErrorResponse.forStatusAndDetail(status, detail));
    }
}
