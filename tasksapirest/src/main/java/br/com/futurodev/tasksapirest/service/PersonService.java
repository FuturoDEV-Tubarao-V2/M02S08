package br.com.futurodev.tasksapirest.service;

import br.com.futurodev.tasksapirest.exception.PersonNotFoundException;
import br.com.futurodev.tasksapirest.model.Person;
import br.com.futurodev.tasksapirest.model.transport.CreatePersonForm;
import br.com.futurodev.tasksapirest.model.transport.PersonDTO;
import br.com.futurodev.tasksapirest.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO getSinglePerson(Long id) throws PersonNotFoundException {
        return this.personRepository.findById(id)
                .map(PersonDTO::new)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Transactional
    public PersonDTO createPerson(CreatePersonForm form) {
        Person persistedPerson = this.personRepository.save(new Person(form));
        return new PersonDTO(persistedPerson);
    }
}
