package br.com.futurodev.tasksapirest.model.transport;

import jakarta.validation.constraints.NotNull;

public record CreateWithResourceIdentifierForm(@NotNull Long id) {
}
