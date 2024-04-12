import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;



public class HeapUsingIterativeBinaryTreeTest {

    @Test
    public void testInsert() {
        HeapUsingIterativeBinaryTree<Integer, ArrayList<String>> heap = new HeapUsingIterativeBinaryTree<Integer, ArrayList<String>>(new ComparadorNumeros<>());
        ArrayList<ArrayList<String>> text = new ArrayList<ArrayList<String>>();
        ArrayList<String> line = new ArrayList<String>();
        line.add("ls");
        line.add("diego20");
        line.add("-20");
        line.add("100");

        heap.Insert(Integer.parseInt(line.get(3)), line);

        line = new ArrayList<String>();

        line.add("linux");
        line.add("pedro89");
        line.add("-15");
        line.add("105");

        heap.Insert(Integer.parseInt(line.get(3)), line);
        
        assertEquals(2, heap.count());
    }

    @Test
    public void testRemove() {
        HeapUsingIterativeBinaryTree<Integer, ArrayList<String>> heap = new HeapUsingIterativeBinaryTree<Integer, ArrayList<String>>(new ComparadorNumeros<>());
        ArrayList<ArrayList<String>> text = new ArrayList<ArrayList<String>>();
        ArrayList<String> line = new ArrayList<String>();
        line.add("ls");
        line.add("diego20");
        line.add("-20");
        line.add("100");

        heap.Insert(Integer.parseInt(line.get(3)), line);

        line = new ArrayList<String>();

        line.add("linux");
        line.add("pedro89");
        line.add("-15");
        line.add("105");

        heap.Insert(Integer.parseInt(line.get(3)), line);

        heap.remove();
        
        assertEquals(1, heap.count());
    }
}
