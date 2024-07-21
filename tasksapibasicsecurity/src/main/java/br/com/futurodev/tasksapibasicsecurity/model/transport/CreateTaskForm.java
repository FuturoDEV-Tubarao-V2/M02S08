package br.com.futurodev.tasksapibasicsecurity.model.transport;

import br.com.futurodev.tasksapibasicsecurity.model.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateTaskForm(@NotBlank String title,
                             @NotBlank String description,
                             @NotNull TaskStatusEnum status,
                             Set<CreateWithResourceIdentifierForm> assignees) {
}
