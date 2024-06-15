package alpha.net.appuser;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserSession implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String sessionId;
    private Long userId;
    private String username;
    private String createdAt;
    private boolean active;
}