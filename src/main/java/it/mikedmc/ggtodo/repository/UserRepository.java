package it.mikedmc.ggtodo.repository;

import it.mikedmc.ggtodo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
