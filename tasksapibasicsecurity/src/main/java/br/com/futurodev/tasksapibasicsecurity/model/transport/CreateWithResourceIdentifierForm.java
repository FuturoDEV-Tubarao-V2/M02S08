package br.com.futurodev.tasksapibasicsecurity.model.transport;

import jakarta.validation.constraints.NotNull;

public record CreateWithResourceIdentifierForm(@NotNull Long id) {
}
