package br.com.fiap.epictaskz.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(OAuth2User principal) {
        var user = userRepository.findByEmail(principal.getAttributes().get("email").toString());
        if (user.isEmpty()){
            return userRepository.save(new User(principal));
        }
        return user.get();
    }
}
