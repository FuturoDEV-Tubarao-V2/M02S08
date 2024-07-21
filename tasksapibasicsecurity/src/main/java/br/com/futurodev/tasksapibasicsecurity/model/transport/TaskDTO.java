package br.com.futurodev.tasksapibasicsecurity.model.transport;

import br.com.futurodev.tasksapibasicsecurity.model.Task;
import br.com.futurodev.tasksapibasicsecurity.model.enums.TaskStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record TaskDTO(Long id,
                      String title,
                      String description,
                      TaskStatusEnum status,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
                      LocalDateTime createdAt,
                      @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
                      LocalDateTime finishedAt,
                      PersonDTO owner,
                      Set<PersonDTO> assignees) {

    public TaskDTO(Task task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getFinishedAt(),
                new PersonDTO(task.getOwner()),
                task.getAssignees().stream().map(PersonDTO::new).collect(Collectors.toSet())
        );
    }
}
