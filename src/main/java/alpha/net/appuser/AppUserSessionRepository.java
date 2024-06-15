package alpha.net.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class AppUserSessionRepository {

    private static final Logger logger = LoggerFactory.getLogger(AppUserSessionRepository.class);

    static final String KEY = "USER_SESSION";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUserSession(AppUserSession userSession) {
        logger.info("Saving user session for userId: {}", userSession.getUserId());
        redisTemplate.opsForHash().put(KEY, userSession.getSessionId(), userSession);
        redisTemplate.expire(KEY, 30, TimeUnit.MINUTES); // Corrected method call
        logger.info("User session saved successfully for sessionId: {}", userSession.getSessionId());
    }

    public AppUserSession getUserSession(String sessionId) {
        return (AppUserSession) redisTemplate.opsForHash().get(KEY, sessionId);
    }

    public void deleteUserSession(String sessionId) {
        redisTemplate.opsForHash().delete(KEY, sessionId);
    }

    public List<AppUserSession> getAllUserSessions() {
        return redisTemplate.opsForHash().values(KEY).stream()
                .map(obj -> (AppUserSession) obj) 
                .collect(Collectors.toList());
    }
}
