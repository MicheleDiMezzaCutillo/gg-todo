package it.mikedmc.ggtodo.controller;

import it.mikedmc.ggtodo.dto.UserDto;
import it.mikedmc.ggtodo.dto.builder.UserDtoBuilder;
import it.mikedmc.ggtodo.model.User;
import it.mikedmc.ggtodo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()) != null) {
            return ResponseEntity.badRequest().build();  // L'utente è già presente.
        }
        User user = UserDtoBuilder.fromDtoToEntity(userDto);
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto, HttpSession session) {

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

}
