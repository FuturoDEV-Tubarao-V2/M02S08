package br.com.futurodev.tasksapirest.model.transport;

import br.com.futurodev.tasksapirest.model.Person;

public record PersonDTO(Long id,
                        String name,
                        String email) {

    public PersonDTO(Person person) {
        this(person.getId(), person.getName(), person.getEmail());
    }
}
