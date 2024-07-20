package br.com.futurodev.tasksapirest.controller;

import br.com.futurodev.tasksapirest.exception.PersonNotFoundException;
import br.com.futurodev.tasksapirest.model.transport.CreatePersonForm;
import br.com.futurodev.tasksapirest.model.transport.PersonDTO;
import br.com.futurodev.tasksapirest.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getSinglePerson(@PathVariable("id") Long id) throws PersonNotFoundException {
        PersonDTO response = this.personService.getSinglePerson(id);
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
