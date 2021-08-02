package br.bluetasksandre.bluetaskbackend.domain.task;

public class DuplicatedTaskException extends Exception {

    public DuplicatedTaskException(String message) {
        super(message);
    }
}
