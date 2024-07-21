package br.com.futurodev.tasksapibasicsecurity.service;

import br.com.futurodev.tasksapibasicsecurity.exception.PersonNotFoundException;
import br.com.futurodev.tasksapibasicsecurity.exception.TaskNotFoundException;
import br.com.futurodev.tasksapibasicsecurity.model.Person;
import br.com.futurodev.tasksapibasicsecurity.model.Task;
import br.com.futurodev.tasksapibasicsecurity.model.transport.*;
import br.com.futurodev.tasksapibasicsecurity.repository.PersonRepository;
import br.com.futurodev.tasksapibasicsecurity.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;

    public TaskService(TaskRepository taskRepository, PersonRepository personRepository, PersonService personService) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.personService = personService;
    }


    public TaskDTO getSingleTask(Long id, UserDetails userInSession) throws TaskNotFoundException, PersonNotFoundException {
        PersonDTO userInSessionAsDTO = this.personService.getSinglePerson(userInSession.getUsername());
        return this.taskRepository.findByIdAndDeletedFalseAndOwnerId(id, userInSessionAsDTO.id())
                .map(TaskDTO::new)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Page<TaskDTO> listPaginatedTasks(Pageable pageable, UserDetails userInSession) throws PersonNotFoundException {
        PersonDTO userInSessionAsDTO = this.personService.getSinglePerson(userInSession.getUsername());
        Long id = userInSessionAsDTO.id();
        return this.taskRepository.findDistinctByDeletedFalseAndOwnerIdOrAssigneesId(pageable, id, id).map(TaskDTO::new);
    }

    @Transactional
    public TaskDTO createTask(CreateTaskForm form, UserDetails userInSession) throws PersonNotFoundException {
        Person owner = this.personService.getSinglePersonAsEntity(userInSession.getUsername());
        List<Long> assigneesIdentifiers = form.assignees()
                .stream()
                .map(CreateWithResourceIdentifierForm::id)
                .toList();

        Set<Person> assignees = new HashSet<>(this.personRepository.findAllByIdInAndDeletedFalse(assigneesIdentifiers));

        Task persistedTask = this.taskRepository.save(new Task(form, owner, assignees));
        return new TaskDTO(persistedTask);
    }

    @Transactional
    public TaskDTO updateTask(Long id, UpdateTaskForm form, UserDetails userInSession) throws TaskNotFoundException, PersonNotFoundException {
        PersonDTO userInSessionAsDTO = this.personService.getSinglePerson(userInSession.getUsername());
        Task taskForUpdate = this.taskRepository.findByIdAndDeletedFalseAndOwnerId(id, userInSessionAsDTO.id())
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskForUpdate.updateAvailableAttributes(form);
        return new TaskDTO(taskForUpdate);
    }

    @Transactional
    public void deleteTask(Long id, UserDetails userInSession) throws TaskNotFoundException, PersonNotFoundException {
        PersonDTO userInSessionAsDTO = this.personService.getSinglePerson(userInSession.getUsername());
        Task taskForDelete = this.taskRepository.findByIdAndDeletedFalseAndOwnerId(id, userInSessionAsDTO.id())
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskForDelete.markAsDeleted();
    }
}
