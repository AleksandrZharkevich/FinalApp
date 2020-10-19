package by.mrbregovich.iba.project.exception;

public class CompanyNotFoundException extends Exception {

    public CompanyNotFoundException(String message) {
        super(message);
    }

    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
