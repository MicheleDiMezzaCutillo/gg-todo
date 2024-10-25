package it.mikedmc.ggtodo.repository;

import it.mikedmc.ggtodo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
