import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// I would like a simulation class to do most of the work.
public class MyMemoryAllocation extends MyLinkedList {
    String algorithm; //best fit, first fit or next fit
    MyLinkedList freeList = new MyLinkedList();
    MyLinkedList usedList = new MyLinkedList();
    int memSize = 0;
    String algo;
    Block block;
    public MyMemoryAllocation(int mem_size, String algorithm) {
        memSize = mem_size;
        algo = algorithm;


        
        if(freeList.size()!=0) System.out.println(freeList.head.getData());
        if(usedList.size()!=0)System.out.println(usedList.head.getData());
        System.out.println(freeList.size());

        
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
        int data = freeList.head.data;

            for(int i =0;i<size;i++){


                freeList.setnextHead();

            }
        usedList.insert(freeList.head.data, size);



        return data;
    }

    public void free(int address) {

    }


    public int size() {
        for (int i =memSize-1;i>0;i--) {
            freeList.insert(i,i);

        }
        return freeList.size();
    }


    public int max_size() {
        return memSize - 1;
    }

}
class Block  {
    int offset;
    int size;



    public Block(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    public String toString() // highly recommended
    {
        return null;

    }

    public boolean is_adjacent(Block other) {

        return false;
    }
}

// Sort-by in Java: (needs a class)
// You may need it to sort your list or you can maintain a sorted list upon insertion


