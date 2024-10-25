package it.mikedmc.ggtodo.repository;

import it.mikedmc.ggtodo.model.Task;
import it.mikedmc.ggtodo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);

    Task findByIdAndUser(Long id, User user);

    void deleteByIdAndUser(long id, User currentUser);
}
