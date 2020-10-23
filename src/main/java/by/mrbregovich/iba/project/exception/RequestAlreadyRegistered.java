package by.mrbregovich.iba.project.exception;

public class RequestAlreadyRegistered extends Exception {

    public RequestAlreadyRegistered(String message) {
        super(message);
    }

    public RequestAlreadyRegistered(String message, Throwable cause) {
        super(message, cause);
    }
}
