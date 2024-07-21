package br.com.futurodev.tasksapibasicsecurity.model.transport;


import br.com.futurodev.tasksapibasicsecurity.model.enums.TaskStatusEnum;

import java.time.LocalDateTime;

public record UpdateTaskForm(String title,
                             String description,
                             TaskStatusEnum status,
                             LocalDateTime finishedAt) {
}
