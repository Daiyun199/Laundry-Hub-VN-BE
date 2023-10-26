package start.exception.exceptions;

public class ExpiredToken  extends RuntimeException{
    public ExpiredToken(String message) {
        super(message);
    }
}
