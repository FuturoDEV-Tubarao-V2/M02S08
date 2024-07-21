package br.com.futurodev.tasksapibasicsecurity.exception;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(Long id) {
        super(String.format("Task for identifier %d was not found or you do not have permission to view it", id));
    }
}
