package br.com.futurodev.tasksapirest.exception;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(Long id) {
        super(String.format("Task for id %d is not found", id));
    }
}
