package br.com.futurodev.tasksapirest.model.transport;

import br.com.futurodev.tasksapirest.model.enums.TaskStatusEnum;

import java.time.LocalDateTime;

public record UpdateTaskForm(String title,
                             String description,
                             TaskStatusEnum status,
                             LocalDateTime finishedAt) {
}
