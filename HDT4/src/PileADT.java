import java.util.ArrayList;

public class PileADT<E> implements IPileADT<E> {

    postfixCalc ptcalc = new postfixCalc();
    infixRead inread = new infixRead();

    ArrayList<E> elements;

    public PileADT(){
        elements = new ArrayList<E>();
    }

    @Override
    public int count() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    
    @Override
    public void push(E value) {
        
            elements.add(value);
        
    }

    @Override
    public E pop() {
        E tempValue = null;
        if (elements.size() > 0){
            tempValue = elements.remove(elements.size() - 1);
        }
        
        return tempValue;
    }

    @Override
    public E peek() {
        E tempValue = null;
        if (elements.size() > 0){
            tempValue = elements.get(elements.size() - 1);
        }
        return tempValue;
    }


}
