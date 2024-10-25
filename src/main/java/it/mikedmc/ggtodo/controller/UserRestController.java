package it.mikedmc.ggtodo.controller;

import it.mikedmc.ggtodo.dto.UserDto;
import it.mikedmc.ggtodo.dto.builder.UserDtoBuilder;
import it.mikedmc.ggtodo.model.User;
import it.mikedmc.ggtodo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return getErrorResponseEntity(result);
        }

        String username = userDto.getUsername();
        if (userService.findByUsername(username) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username già utilizzato.");
        }

        User user = UserDtoBuilder.fromDtoToEntity(userDto);
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto, HttpSession session, BindingResult result) {

        if (result.hasErrors()) {
            return getErrorResponseEntity(result);
        }

        User user = userService.findByUsername(userDto.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide.");
        }

        session.setAttribute("user", user);
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("user");
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(currentUser);
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
