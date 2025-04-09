package alpha.net.utility.utildtos.apiutildtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Generic class to standardize the response of the API.
 * 
 * @param <T> The type of the data to be returned.
 * 
 * @author Dillon Gaughan
 * @version 1.0
 * @since 2025-04-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
    private boolean successStatus;
    private String detail;
    private T data;

    public static <T> SuccessResponse<T> of(T data) {
        return SuccessResponse.<T>builder()
            .successStatus(true)
            .detail(null) // defaults to null if no extra message is needed
            .data(data)
            .build();
    }
    
    
}
