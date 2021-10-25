import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// I would like a simulation class to do most of the work.
public class MyMemoryAllocation extends MemoryAllocation {    //changing extends MyLinkedList to MemoryAllocation
    String algorithm; //best fit, first fit or next fit
    MyLinkedList freeList = new MyLinkedList();
    MyLinkedList usedList = new MyLinkedList();
    int memSize = 0;
    String algo;
    Block block;
    public MyMemoryAllocation(int mem_size, String algorithm) {       
        memSize = mem_size;
        this.algorithm = algorithm;                            //changed algo to algorithm.
        freeList.insert(1, memSize);                           //added freelist insert from 1 to desired range
        System.out.println("Size: " + this.size());            //added printout to show size of inserted
    }
    public static void main(String[] args)                     //moved main up
    {
        MyMemoryAllocation mal = new MyMemoryAllocation(14, "ff");
    }
    
    // Strongly recommend you start with printing out the pieces.
    public void print() {                                      //Removed unnecessary print out
        if(freeList.size()!=0)  System.out.println(freeList.toString());
        if(usedList.size()!=0)  System.out.println(usedList.toString());
    }

    public int alloc(int size) {
        int data = freeList.head.data;                         //not sure what is happening here
                                                               //Are we trying to use best fit or first fit?
            for (int i = memSize-1;i>0;i--) {
            freeList.insert(i,i);

        }
      
        usedList.insert(freeList.head.offset, size);           //changed data to offset as no data is passed but offset is

        return offset;
    }                                                          //changed data to offset

    public void free(int address) {
        //need to add a call deleteNode(address) on myLinkedList

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


