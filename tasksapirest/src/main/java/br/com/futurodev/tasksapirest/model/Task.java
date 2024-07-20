package br.com.futurodev.tasksapirest.model;

import br.com.futurodev.tasksapirest.model.enums.TaskStatusEnum;
import br.com.futurodev.tasksapirest.model.transport.CreateTaskForm;
import br.com.futurodev.tasksapirest.model.transport.UpdateTaskForm;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person owner;

    @ManyToMany
    @JoinTable(
            name = "tasks_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> assignees = new HashSet<>();

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    private LocalDateTime deletedAt;

    public Task() {

    }

    public Task(CreateTaskForm form, Person owner, Set<Person> assignees) {
        this.title = form.title();
        this.description = form.description();
        this.status = form.status();
        this.createdAt = LocalDateTime.now();
        this.owner = owner;
        this.assignees = assignees != null ? assignees : new HashSet<>();
    }

    public void updateAvailableAttributes(UpdateTaskForm form) {
        this.title = form.title() != null ? form.title() : this.title;
        this.description = form.description() != null ? form.description() : this.description;
        this.status = form.status() != null ? form.status() : this.status;
        this.finishedAt = form.finishedAt() != null ? form.finishedAt() : this.finishedAt;
    }

    public void markAsDeleted() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public Person getOwner() {
        return owner;
    }

    public Set<Person> getAssignees() {
        return assignees;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
