package alpha.net.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import alpha.net.appuser.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private AppUser sender;

    private String content;

    private LocalDateTime timestamp;

    public ChatMessage(Chat chat, AppUser sender, String content) {
        this.chat = chat;
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}