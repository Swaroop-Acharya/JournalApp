package net.engineeringdigest.journalApp.exception;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message){
        super(message);
    }
}
