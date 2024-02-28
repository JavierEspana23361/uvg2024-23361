public class Main {
    public static void main(String[] args) throws Exception {
        //ListADT<String> list = new ListADT<>();
        //infixReadFactory factory = new infixReadFactory();
        //factory.getCalculator().doOperation(list);

        infixRead read = new infixRead();
        ListADT<String> list = new ListADT<>();
        read.doOperation(list);
    }
}
