package br.com.futurodev.tasksapirest.service;

import br.com.futurodev.tasksapirest.exception.PersonNotFoundException;
import br.com.futurodev.tasksapirest.exception.TaskNotFoundException;
import br.com.futurodev.tasksapirest.model.Person;
import br.com.futurodev.tasksapirest.model.Task;
import br.com.futurodev.tasksapirest.model.transport.CreateTaskForm;
import br.com.futurodev.tasksapirest.model.transport.CreateWithResourceIdentifierForm;
import br.com.futurodev.tasksapirest.model.transport.TaskDTO;
import br.com.futurodev.tasksapirest.model.transport.UpdateTaskForm;
import br.com.futurodev.tasksapirest.repository.PersonRepository;
import br.com.futurodev.tasksapirest.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;

    public TaskService(TaskRepository taskRepository, PersonRepository personRepository) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
    }

    public TaskDTO getSingleTask(Long id) throws TaskNotFoundException {
        return this.taskRepository.findById(id)
                .map(TaskDTO::new)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Page<TaskDTO> listPaginatedTasks(Pageable pageable) {
        return this.taskRepository.findByDeletedFalse(pageable).map(TaskDTO::new);
    }

    @Transactional
    public TaskDTO createTask(CreateTaskForm form) throws PersonNotFoundException {
        Person owner = this.personRepository.findByIdAndDeletedFalse(form.owner().id())
                .orElseThrow(() -> new PersonNotFoundException(form.owner().id()));

        List<Long> assigneesIdentifiers = form.assignees()
                .stream()
                .map(CreateWithResourceIdentifierForm::id)
                .toList();

        Set<Person> assignees = new HashSet<>(this.personRepository.findAllByIdInAndDeletedFalse(assigneesIdentifiers));

        Task persistedTask = this.taskRepository.save(new Task(form, owner, assignees));
        return new TaskDTO(persistedTask);
    }

    @Transactional
    public TaskDTO updateTask(Long id, UpdateTaskForm form) throws TaskNotFoundException {
        Task taskForUpdate = this.taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskForUpdate.updateAvailableAttributes(form);
        return new TaskDTO(taskForUpdate);
    }

    @Transactional
    public void deleteTask(Long id) throws TaskNotFoundException {
        Task taskForDelete = this.taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskForDelete.markAsDeleted();
    }
}
