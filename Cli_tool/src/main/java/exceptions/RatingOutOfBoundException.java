package exceptions;

public class RatingOutOfBoundException extends Exception {

    public RatingOutOfBoundException() {
        super("Rating out of bound!! Should be in the range [0, 5].");
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}
