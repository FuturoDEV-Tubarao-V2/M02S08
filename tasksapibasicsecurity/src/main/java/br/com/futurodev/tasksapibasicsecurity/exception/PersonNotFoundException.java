package br.com.futurodev.tasksapibasicsecurity.exception;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(Long id) {
        super(String.format("Person with identifier %d was not found or you do not have permission to see them", id));
    }

    public PersonNotFoundException(String email) {
        super(String.format("Person with identifier %s was not found or you do not have permission to see them", email));
    }
}
