import java.util.Comparator;
import java.util.Iterator;

public class MyMemoryAllocation extends MemoryAllocation {
    String algorithm; //best fit, first fit or next fit
    MyLinkedList free_list;
    MyLinkedList used_list;

    public MyMemoryAllocation(int mem_size, String algorithm) {
        super(mem_size, algorithm);
    }

    
    public void print() {

    }

    public int alloc(int size) {
        
    }

    public void free(int address) {

    }

    @Override
    public int size() {
        
    }

    @Override
    public int max_size() {
        
    }

}

class Block {
    int offset;
    int size;

    public String toString() 
    {
        
    }

    public boolean is_adjacent(Block other) {
        
    }
}
class MyLinkedList implements Iterable { 
    
    public void splitMayDelete() {

    }
    public void insertMayCompact() {

    }
    public String toString() {

    } /

    @Override
    public Iterator iterator() {
        
    }
}
// Sort-by in Java: (needs a class)
// You may need it to sort your list or you can maintain a sorted list upon insertion
class ByOffset implements Comparator<Block> {
    @Override int compare(Block lhs, Block rhs) {
        return Integer.compare(lhs.offset, rhs.offset);
    }
}
