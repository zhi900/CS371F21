import java.util.Comparator;
import java.util.Iterator;

// I would like a simulation class to do most of the work.
public class MyMemoryAllocation extends MemoryAllocation {
    String algorithm; //best fit, first fit or next fit
    MyLinkedList free_list;
    MyLinkedList used_list;

    public MyMemoryAllocation(int mem_size, String algorithm) {
        super(mem_size, algorithm);
    }

    // Strongly recommend you start with printing out the pieces.
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
// Probably a block class with at least these fields and methods.
class Block {
    int offset;
    int size;

    public String toString() // highly recommended
    {
        
    }

    public boolean is_adjacent(Block other) {
        
    }
}
// Probably a block class with at least these fields and methods.
class MyLinkedList implements Iterable { //generic types are not required, you can just do MyLinkedList for blocks but Iterable is mandatory.
    //in addition to other regular list member functions such as insert and delete: (split and consolidate blocks must be implemented at the level of linked list)
    public void splitMayDelete() {

    }
    public void insertMayCompact() {

    }
    public String toString() {

    } //highly recommended

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
