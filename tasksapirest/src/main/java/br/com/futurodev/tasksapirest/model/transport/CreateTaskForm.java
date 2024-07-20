package br.com.futurodev.tasksapirest.model.transport;

import br.com.futurodev.tasksapirest.model.enums.TaskStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateTaskForm(@NotBlank String title,
                             @NotBlank String description,
                             @NotNull TaskStatusEnum status,
                             @NotNull @Valid CreateWithResourceIdentifierForm owner,
                             Set<CreateWithResourceIdentifierForm> assignees) {
}
