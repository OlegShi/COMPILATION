package AST;

public class SemanticException extends Exception {
    
    public int line;
    public String error;
    
    public SemanticException(String message,int line) {
        super(message);
        this.error = message;
        this.line = line;
    }

}