public class LispInterpreter {
    public int add(int n1, int n2){

        return n1 + n2;
    }

    public int substract(int n1, int n2){
        return n1 - n2;

    }
    public int multiplication(int n1, int n2){
        return n1 * n2;

    }
    public int division(int n1, int n2){
        return n1 / n2;

    }
    public int QUOTE(int n1){
        return n1;
    }
    public int DEFUN(int n1, int n2){
        return n1;
    }
    public int SETQ(int n1, int n2){
        return n1;
    }
    public int ATOM(int n1){
        return n1;
    }
    public int LIST(int n1){
        return n1;
    }
    public int EQUAL(int n1, int n2){
        return n1;
    }
    public int LESS(int n1, int n2){
        return n1;
    }
    public int GREATER(int n1, int n2){
        return n1;
    }
    public int COND(int n1, int n2){
        return n1;
    }

    
    private boolean isOperand(String element) {
        try {
            Integer.parseInt(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") || 
        element.equals("QUOTE") || element.equals("DEFUN") || element.equals("SETQ") || element.equals("ATOM") || element.equals("LIST")
        || element.equals("EQUAL") || element.equals("<") || element.equals(">") || element.equals("COND");
    }
}
     
