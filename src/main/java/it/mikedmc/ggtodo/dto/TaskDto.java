package it.mikedmc.ggtodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TaskDto {

    @NotNull(message = "Titolo non valido")
    @NotBlank(message = "Il titolo non può essere vuoto")
    @NotEmpty(message = "Il titolo non può essere vuoto")
    private String title;

    @NotNull(message = "Descrizione non valida")
    @NotBlank(message = "La descrizione non può essere vuota")
    @NotEmpty(message = "La descrizione non può essere vuota")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
