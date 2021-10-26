import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// I would like a simulation class to do most of the work.
public class MyMemoryAllocation extends MemoryAllocation  {
    String algorithm; //best fit, first fit or next fit
    MyLinkedList freeList = new MyLinkedList();
    MyLinkedList usedList = new MyLinkedList();
    int memSize = 0;
    String algo;
    
    
    public MyMemoryAllocation(int mem_size, String algorithm) {
    	super(mem_size, algorithm);
    	memSize = mem_size;
        this.algorithm = algorithm;
        freeList.insert(1, memSize);
        System.out.println("Size: " + this.size());
    }

    public static void main(String[] args)
    {
        MyMemoryAllocation mal = new MyMemoryAllocation(14, "ff");
    }

    // Strongly recommend you start with printing out the pieces.
    public void print() {
        if(freeList.size()!=0)  System.out.println(freeList.toString());
        if(usedList.size()!=0)  System.out.println(usedList.toString());
    }

    public int alloc(int size) {
    	MyLinkedList.Node freeNode = null;
        if (algorithm.equals("FF")) {
            MyLinkedList.Node potentialFreeNode = freeList.head;
            while (potentialFreeNode != null) {
                if (potentialFreeNode.size >= size) {
                    freeNode = potentialFreeNode;
                    break;
                }
            }
        } else {
            System.err.println("Other algorithms not implemented.");
            return 0;
        }
        
        if (freeNode == null) {
            System.err.println("Free node big enough not found for size " + size);
            return 0;
        }
        
        usedList.insert(freeNode.offset, size);
        freeList.splitMayDelete(freeNode, size);

        return 0;
    }

    public void free(int address) {
        // call deleteNode(address) on usedList
    	MyLinkedList.Node deleted = usedList.deleteNode(address);
        
        // then call insertMayCompact on freelist
        freeList.insertMayCompact(deleted.offset, deleted.size);
    }


    public int size() {
        return freeList.size();
    }


    public int max_size() {
        return memSize - 1;
    }

}


// Sort-by in Java: (needs a class)
// You may need it to sort your list or you can maintain a sorted list upon insertion
