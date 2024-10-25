package it.mikedmc.ggtodo.service;

import it.mikedmc.ggtodo.model.User;
import it.mikedmc.ggtodo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user) {
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }
}
