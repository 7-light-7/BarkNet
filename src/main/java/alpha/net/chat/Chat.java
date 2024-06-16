package alpha.net.chat;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import alpha.net.appuser.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "chat_users",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private List<AppUser> users = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    public Chat(List<AppUser> users, List<ChatMessage> messages) {
        this.users = users;
        this.messages = messages;
    }
}
