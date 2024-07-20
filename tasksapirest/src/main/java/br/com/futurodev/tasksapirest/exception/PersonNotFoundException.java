package br.com.futurodev.tasksapirest.exception;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(Long id) {
        super(String.format("Person for id %d is not found", id));
    }
}
