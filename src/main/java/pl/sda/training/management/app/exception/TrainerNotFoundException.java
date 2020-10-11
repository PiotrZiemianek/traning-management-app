package pl.sda.training.management.app.exception;

public class TrainerNotFoundException extends RuntimeException{
    public TrainerNotFoundException(String message) {
        super(message);
    }
}
