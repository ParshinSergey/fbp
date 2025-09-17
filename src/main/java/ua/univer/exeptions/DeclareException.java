package ua.univer.exeptions;

public class DeclareException extends RuntimeException{

    public DeclareException(String message) {
        super(message);
    }

    public DeclareException(String message, Throwable cause) {
        super(message, cause);
    }


}
