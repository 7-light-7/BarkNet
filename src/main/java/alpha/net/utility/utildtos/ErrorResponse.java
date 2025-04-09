package alpha.net.utility.utildtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public class ErrorResponse extends ProblemDetail {

    private LocalDateTime timestamp;
    

    public ErrorResponse(String detail) {
        super();
        this.setDetail(detail);
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse forStatusAndDetail(HttpStatus status, String detail) {
        ErrorResponse errorResponse = new ErrorResponse(detail);
        errorResponse.setStatus(status);
        errorResponse.setDetail(detail);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }




    
    
}
