package alpha.net.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import alpha.net.appuser.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @Transactional
    @Rollback
    public void testFindByChat() {
        // Setup
        AppUser user1 = AppUser.builder()
                .username("user1")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();
        user1 = appUserService.save(user1);

        AppUser user2 = AppUser.builder()
                .username("user2")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();
        user2 = appUserService.save(user2);

        // Save and manage chat entity
        Chat chat = Chat.builder()
                .users(List.of(user1, user2))
                .build();
        chat = chatRepository.save(chat);

        // Save messages
        ChatMessage message1 = ChatMessage.builder()
                .chat(chat)
                .sender(user1)
                .content("Hello")
                .build();
        message1 = messageRepository.save(message1);

        ChatMessage message2 = ChatMessage.builder()
                .chat(chat)
                .sender(user2)
                .content("Hi")
                .build();
        message2 = messageRepository.save(message2);

        // Execution
        List<ChatMessage> messages = messageRepository.findByChat(chat);

        // Assertion
        assertThat(messages).containsExactlyInAnyOrder(message1, message2);
    }
}
