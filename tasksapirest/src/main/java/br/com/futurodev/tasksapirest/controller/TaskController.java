package br.com.futurodev.tasksapirest.controller;

import br.com.futurodev.tasksapirest.exception.PersonNotFoundException;
import br.com.futurodev.tasksapirest.exception.TaskNotFoundException;
import br.com.futurodev.tasksapirest.model.transport.CreateTaskForm;
import br.com.futurodev.tasksapirest.model.transport.TaskDTO;
import br.com.futurodev.tasksapirest.model.transport.UpdateTaskForm;
import br.com.futurodev.tasksapirest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> listPaginatedTasks(@PageableDefault(size = 10, sort = {"title"}) Pageable pageable) throws TaskNotFoundException {
        Page<TaskDTO> response = this.taskService.listPaginatedTasks(pageable);
        return response.hasContent() ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getSingleTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        TaskDTO response = this.taskService.getSingleTask(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskForm form,
                                              UriComponentsBuilder uriComponentsBuilder) throws PersonNotFoundException {
        TaskDTO response = this.taskService.createTask(form);

        URI uri = uriComponentsBuilder
                .path("/tasks/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id,
                                              @RequestBody @Valid UpdateTaskForm form) throws TaskNotFoundException {
        TaskDTO response = this.taskService.updateTask(id, form);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
