package br.com.futurodev.tasksapirest.model;


import br.com.futurodev.tasksapirest.model.transport.CreatePersonForm;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "assignees")
    private Set<Task> assignedTasks = new HashSet<>();

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    private LocalDateTime deletedAt;

    public Person() {

    }

    public Person(CreatePersonForm form) {
        this.name = form.name();
        this.email = form.email();
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
