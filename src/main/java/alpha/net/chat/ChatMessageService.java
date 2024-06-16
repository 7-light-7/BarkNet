package alpha.net.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alpha.net.appuser.AppUser;

import java.time.LocalDateTime;
import java.util.List;@Service
public class ChatMessageService {

    private final ChatMessageRepository messageRepository;

    public ChatMessageService(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatMessage sendMessage(Chat chat, AppUser sender, String content) {
        ChatMessage message = ChatMessage.builder()
                .chat(chat)
                .sender(sender)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();
        return messageRepository.save(message);
    }

    public List<ChatMessage> getMessagesByChat(Chat chat) {
        return messageRepository.findByChat(chat);
    }
}