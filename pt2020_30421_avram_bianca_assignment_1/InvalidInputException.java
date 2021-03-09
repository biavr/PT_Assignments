public class InvalidInputException extends Exception {
    public InvalidInputException(){
        super("Wrong input format!\n Please make sure you introduced the polynomials in the format exemplified in the instructions.");
    }
}
