package it.mikedmc.ggtodo.controller;


import it.mikedmc.ggtodo.dto.TaskDto;
import it.mikedmc.ggtodo.model.Task;
import it.mikedmc.ggtodo.model.User;
import it.mikedmc.ggtodo.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    @Autowired
    private TaskService taskService;

    @PostMapping() // POST per creare una nuova task
    public ResponseEntity<?> newTask(@Valid @RequestBody TaskDto taskDto, HttpSession session, BindingResult result) {
        // recupero l'utente loggato.
        User currentUser = (User) session.getAttribute("user");

        // controllo se l'utente è loggato, sennò ritorno subito un errore.
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login richiesto");
        }

        // Controllo che i campi siano validi.
        if (result.hasErrors()) {
            return getErrorResponseEntity(result);
        }

        // se l'utente è loggato e i dati sono validi, creo una Task.
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setChecked(false);
        task.setUser(currentUser);

        // salvo la task e la restituisco.
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping() // GET per ottenere le task dell'utente
    public ResponseEntity<?> tasks(HttpSession session) {
        // recupero l'utente loggato.
        User currentUser = (User) session.getAttribute("user");

        // Controllo se l'utente è loggato
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login richiesto");
        }

        // Restituisce le task dell'utente.
        return ResponseEntity.ok(taskService.findTasksByUser(currentUser));
    }

    @GetMapping("/{id}") // GET /{id} per ottenere una task specifica
    public ResponseEntity<?> taskById(@PathVariable("id") long id, HttpSession session) {
        // recupero l'utente loggato.
        User currentUser = (User) session.getAttribute("user");

        // controllo se l'utente è loggato, sennò ritorno subito un errore.
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login richiesto");
        }

        // Restituisco la task che trova se la trova.
        return ResponseEntity.ok(taskService.findByIdAndUser(id, currentUser));
    }

    @PutMapping("/{id}") // PUT /{id} per aggiornare una task esistente.
    public ResponseEntity<?> updateTaskById(@PathVariable("id") long id, @RequestBody TaskDto taskDto, HttpSession session, BindingResult result) {

        // recupero l'utente loggato.
        User currentUser = (User) session.getAttribute("user");

        // Controllo se l'utente è loggato
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login richiesto");
        }
        Task task = taskService.findByIdAndUser(id, currentUser);
        task.setDescription(taskDto.getDescription());
        task.setTitle(taskDto.getTitle());

        // salvo la task e la restituisco.
        return ResponseEntity.ok(taskService.save(task));
    }

    @PatchMapping("/{id}") // PATCH /{id}/check per segnare una task come completata o viceversa
    public ResponseEntity<?> checkTask(@PathVariable("id") long id, HttpSession session) {
        // recupero l'utente loggato.
        User currentUser = (User) session.getAttribute("user");

        // Controllo se l'utente è loggato
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login richiesto");
        }

        // Recupero la task dal database
        Task task = taskService.findByIdAndUser(id, currentUser);
        if (task == null) {
            return ResponseEntity.notFound().build(); // Task non trovata
        }

        task.setChecked(!task.isChecked());

        // salva la task e restituiscila
        return ResponseEntity.ok(taskService.save(task));
    }

    @DeleteMapping("/{id}") // DELETE /{id} per eleminare una task
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") long id, HttpSession session) {
        // recupero l'utente loggato.
        User currentUser = (User) session.getAttribute("user");

        // Recupero la task dal database
        Task task = taskService.findByIdAndUser(id, currentUser);
        if (task == null) {
            return ResponseEntity.notFound().build(); // Task non trovata
        }

        // elimino la task.
        taskService.delete(task);
        // restituisco un messaggio di successo.
        return ResponseEntity.ok("Eliminato con successo.");
    }


    /***
     * Metodo riutilizzabile per controllare se i campi siano validi, e restituisce il messaggio d'errore se presente.
     * @param result BindingResult in cui controllare gli errori.
     * @return ResponseEntity con il messaggio d'errore.
     */
    private ResponseEntity<String> getErrorResponseEntity(BindingResult result) {
        String errorMessage = result.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
