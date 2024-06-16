package alpha.net.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import alpha.net.appuser.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private ChatMessageService messageService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private AppUserService appUserService;

    @Test
    @Transactional
    public void testSendMessage() {
        // Setup
        AppUser user1 = AppUser.builder()
                .username("user1")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();
        appUserService.save(user1);

        AppUser user2 = AppUser.builder()
                .username("user2")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();
        appUserService.save(user2);

        Chat chat = chatService.createChat(List.of(user1, user2));

        // Execution
        ChatMessage message = messageService.sendMessage(chat, user1, "Hello");

        // Assertion
        assertThat(message.getId()).isNotNull();
        assertThat(message.getChat()).isEqualTo(chat);
        assertThat(message.getSender()).isEqualTo(user1);
        assertThat(message.getContent()).isEqualTo("Hello");
    }

    @Test
    @Transactional
    public void testGetMessagesByChat() {
        // Setup
        AppUser user1 = AppUser.builder()
                .username("user1")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();
        appUserService.save(user1);

        AppUser user2 = AppUser.builder()
                .username("user2")
                .password("password")
                .roles(Set.of("ROLE_USER"))
                .build();
        appUserService.save(user2);
        
        Chat chat = chatService.createChat(List.of(user1, user2));

        messageService.sendMessage(chat, user1, "Hello");
        messageService.sendMessage(chat, user2, "Hi");

        // Execution
        List<ChatMessage> messages = messageService.getMessagesByChat(chat);

        // Assertion
        assertThat(messages).hasSize(2);
        assertThat(messages).extracting(ChatMessage::getContent).containsExactlyInAnyOrder("Hello", "Hi");
    }
}
