package it.mikedmc.ggtodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserDto {
    @NotBlank(message = "Lo username non può essere vuoto")
    @NotEmpty(message = "Lo username non può essere vuoto")
    @NotNull(message = "Username non trovato")
    private String username;

    @NotBlank(message = "La password non può essere vuota")
    @NotEmpty(message = "La password non può essere vuota")
    @NotNull(message = "Password non trovata")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
