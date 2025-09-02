package br.com.fiap.epictaskz.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Data
@Table(name = "epicuser")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String avatarUrl;

    public User(OAuth2User principal) {
        this.name = principal.getAttributes().get("name").toString();
        this.email = principal.getAttributes().get("email").toString();
        this.avatarUrl = principal.getAttributes().get("avatar_url").toString();
    }

    public User() {

    }
}
