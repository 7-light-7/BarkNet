package alpha.net.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import alpha.net.appuser.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @Transactional
    @Rollback
    public void testFindByChat() {
        // Setup
        AppUser user1 = appUserRepository.save(new AppUser(null,"user1", "password", null));
        AppUser user2 = appUserRepository.save(new AppUser(null, "user2", "password",null));
        Chat chat = chatRepository.save(new Chat(List.of(user1, user2), List.of()));

        ChatMessage message1 = messageRepository.save(new ChatMessage(chat, user1, "Hello"));
        ChatMessage message2 = messageRepository.save(new ChatMessage(chat, user2, "Hi"));

        // Execution
        List<ChatMessage> messages = messageRepository.findByChat(chat);

        // Assertion
        assertThat(messages).containsExactlyInAnyOrder(message1, message2);
    }
}
