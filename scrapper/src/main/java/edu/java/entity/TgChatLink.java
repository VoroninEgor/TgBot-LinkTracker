package edu.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tgchat_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TgChatLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tgchats_id")
    private TgChat chat;

    @ManyToOne
    @JoinColumn(name = "links_id")
    private Link link;

    public void setChat(TgChat chat) {
        this.chat = chat;
        chat.getTgChatLinks().add(this);
    }

    public void setLink(Link link) {
        this.link = link;
        link.getTgChatLinks().add(this);
    }
}
