package alpha.net.chat;

import org.springframework.stereotype.Service;
import java.util.List;

import alpha.net.appuser.*;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(List<AppUser> users) {
        Chat chat = Chat.builder().users(users).build();
        return chatRepository.save(chat);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id).orElse(null);
    }
}