package br.com.futurodev.tasksapibasicsecurity.controller;

import br.com.futurodev.tasksapibasicsecurity.exception.PersonNotFoundException;
import br.com.futurodev.tasksapibasicsecurity.model.transport.CreatePersonForm;
import br.com.futurodev.tasksapibasicsecurity.model.transport.PersonDTO;
import br.com.futurodev.tasksapibasicsecurity.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/current")
    public ResponseEntity<PersonDTO> getUserInSession(@AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        PersonDTO response = this.personService.getSinglePerson(userInSession.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody @Valid CreatePersonForm form,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        PersonDTO response = this.personService.createPerson(form);

        URI uri = uriComponentsBuilder
                .path("/persons/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
