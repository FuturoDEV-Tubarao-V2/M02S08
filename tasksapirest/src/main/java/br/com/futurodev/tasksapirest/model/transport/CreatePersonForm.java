package br.com.futurodev.tasksapirest.model.transport;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonForm(@NotBlank String name,
                               @NotBlank String email) {
}
