package br.com.futurodev.tasksapibasicsecurity.controller;

import br.com.futurodev.tasksapibasicsecurity.exception.PersonNotFoundException;
import br.com.futurodev.tasksapibasicsecurity.exception.TaskNotFoundException;
import br.com.futurodev.tasksapibasicsecurity.model.transport.CreateTaskForm;
import br.com.futurodev.tasksapibasicsecurity.model.transport.TaskDTO;
import br.com.futurodev.tasksapibasicsecurity.model.transport.UpdateTaskForm;
import br.com.futurodev.tasksapibasicsecurity.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<Page<TaskDTO>> listPaginatedTasks(@PageableDefault(size = 10, sort = {"title"}) Pageable pageable,
                                                            @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        Page<TaskDTO> response = this.taskService.listPaginatedTasks(pageable, userInSession);
        return response.hasContent() ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getSingleTask(@PathVariable("id") Long id,
                                                 @AuthenticationPrincipal UserDetails userInSession) throws TaskNotFoundException, PersonNotFoundException {
        TaskDTO response = this.taskService.getSingleTask(id, userInSession);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskForm form,
                                              UriComponentsBuilder uriComponentsBuilder,
                                              @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        TaskDTO response = this.taskService.createTask(form, userInSession);

        URI uri = uriComponentsBuilder
                .path("/tasks/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id,
                                              @RequestBody @Valid UpdateTaskForm form,
                                              @AuthenticationPrincipal UserDetails userInSession) throws TaskNotFoundException, PersonNotFoundException {
        TaskDTO response = this.taskService.updateTask(id, form, userInSession);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable("id") Long id,
                                              @AuthenticationPrincipal UserDetails userInSession) throws TaskNotFoundException, PersonNotFoundException {
        this.taskService.deleteTask(id, userInSession);
        return ResponseEntity.noContent().build();
    }
}
