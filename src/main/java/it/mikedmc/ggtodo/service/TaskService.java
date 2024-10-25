package it.mikedmc.ggtodo.service;

import it.mikedmc.ggtodo.model.Task;
import it.mikedmc.ggtodo.model.User;
import it.mikedmc.ggtodo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findTasksByUser (User user) { return taskRepository.findByUser(user); }

    public Task findByIdAndUser(Long id, User user) {
        return taskRepository.findByIdAndUser(id, user);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public void deleteByIdAndUser(long id, User currentUser) {
        taskRepository.deleteByIdAndUser(id,currentUser);
    }
}
