package br.com.futurodev.tasksapibasicsecurity.service;

import br.com.futurodev.tasksapibasicsecurity.exception.PersonNotFoundException;
import br.com.futurodev.tasksapibasicsecurity.model.Person;
import br.com.futurodev.tasksapibasicsecurity.model.transport.CreatePersonForm;
import br.com.futurodev.tasksapibasicsecurity.model.transport.PersonDTO;
import br.com.futurodev.tasksapibasicsecurity.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.personRepository.findByEmailAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public PersonDTO getSinglePerson(String email) throws PersonNotFoundException {
        return this.personRepository.findByEmailAndDeletedFalse(email)
                .map(PersonDTO::new)
                .orElseThrow(() -> new PersonNotFoundException(email));
    }

    public Person getSinglePersonAsEntity(String email) throws PersonNotFoundException {
        return this.personRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new PersonNotFoundException(email));
    }

    @Transactional
    public PersonDTO createPerson(CreatePersonForm form) {
        String encodedPassword = this.passwordEncoder.encode(form.password());
        Person persistedPerson = this.personRepository.save(new Person(form, encodedPassword));
        return new PersonDTO(persistedPerson);
    }
}
