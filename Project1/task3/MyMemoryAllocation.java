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
    	memSize = mem_size -1;
        this.algorithm = algorithm;
        freeList.insert(1, memSize);
        System.out.println("Size: " + this.size());
    }

    public static void main(String[] args)
    {
       MyMemoryAllocation mal = new MyMemoryAllocation(14, "FF");
	}

    // Strongly recommend you start with printing out the pieces.
    public void print() {
        if(freeList.size()!=0)  System.out.println("free: " + freeList.toString());
        if(usedList.size()!=0)  System.out.println("used: " + usedList.toString());
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
                potentialFreeNode = potentialFreeNode.next;
            }
        } else {
            System.err.println("Other algorithms not implemented.");import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// I would like a simulation class to do most of the work.
public class MyMemoryAllocation extends MemoryAllocation  {
    String algorithm; //best fit, first fit or next fit
    MyLinkedList freeList = new MyLinkedList();
    MyLinkedList usedList = new MyLinkedList();
    int memSize = 0;
    String algo;
    int index = 0;
    int addr= 0;
    public MyMemoryAllocation(int mem_size, String algorithm) {
        super(mem_size, algorithm);
        memSize = mem_size -1;
        this.algorithm = algorithm;
        freeList.insert(1, memSize);
        System.out.println("Size: " + this.size());





        System.out.println(index);
        System.out.println(freeList.listSize());

        print();
    }

    public static void main(String[] args)
    {
        MyMemoryAllocation mal = new MyMemoryAllocation(14, "NF");
    }

    // Strongly recommend you start with printing out the pieces.
    public void print() {
        if(freeList.size()!=0)  System.out.println("free: " + freeList.toString());
        if(usedList.size()!=0)  System.out.println("used: " + usedList.toString());
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
                potentialFreeNode = potentialFreeNode.next;
            }
        }
        else if(algorithm.equals("BF")) {
            MyLinkedList.Node potentialFreeNode = freeList.head;
            while (potentialFreeNode != null) {
                if (potentialFreeNode.size >= size && (freeNode == null || potentialFreeNode.size < freeNode.size )) {
                    freeNode = potentialFreeNode;
                }
                potentialFreeNode = potentialFreeNode.next;
            }
        }
        else if(algorithm.equals("NF")) {


            MyLinkedList.Node potentialFreeNode = freeList.get(index);

            while (potentialFreeNode != null ) {

                if (potentialFreeNode.size >= size && potentialFreeNode.offset >= freeList.lastAlloced) {
                    freeNode = potentialFreeNode;
                    freeList.lastAlloced = potentialFreeNode.offset;

                    index++;

                    break;
                }
                else{
                    index++;
                }

                potentialFreeNode = potentialFreeNode.next;



            }
            if(index >= freeList.listSize()){
                index = 0;
            }

            if (freeNode == null) {
                potentialFreeNode = freeList.get(index);
                while (potentialFreeNode != null) {
                    if (potentialFreeNode.size >= size) {
                        freeNode = potentialFreeNode;
                        freeList.lastAlloced = potentialFreeNode.offset;
                        index++;
                        break;
                    }
                    potentialFreeNode = potentialFreeNode.next;
                }
            }

        }
        else {
            System.err.println("Other algorithms not implemented.");
            return 0;
        }

        if (freeNode == null) {
            System.err.println("Free node big enough not found for size " + size);
            return 0;
        }

        usedList.insert(freeNode.offset, size);
        int returnvalue = freeNode.offset;
        freeList.splitMayDelete(freeNode, size);

        return returnvalue;
    }

    public void free(int address) {
        addr = address;
        // call deleteNode(address) on usedList
        MyLinkedList.Node deleted = usedList.deleteNode(address);

        if (deleted == null) {
            System.err.println("Address wasn't freeable");
            return;
        }
        if(address == freeList.lastAlloced){
            freeList.lastAlloced = freeList.head.offset;
        }


        // then call insertMayCompact on freelist
        freeList.insertMayCompact(deleted.offset, deleted.size);
    }

    public int size() {
        return freeList.size();
    }


    public int max_size() {
        MyLinkedList.Node temp = freeList.head;
        int biggestSize = 0;
        while(temp!=null) {
            if (temp.size > biggestSize) {
                biggestSize = temp.size;
            }
            temp = temp.next;
        }
        return biggestSize;
    }

}


            return 0;
        }
        
        if (freeNode == null) {
            System.err.println("Free node big enough not found for size " + size);
            return 0;
        }
        
        usedList.insert(freeNode.offset, size);
        int returnvalue = freeNode.offset;
        freeList.splitMayDelete(freeNode, size);

        return returnvalue;
    }

    public void free(int address) {
        // call deleteNode(address) on usedList
        MyLinkedList.Node deleted = usedList.deleteNode(address);

        if (deleted == null) {
            System.err.println("Address wasn't freeable");
            return;
        }
        // then call insertMayCompact on freelist
        freeList.insertMayCompact(deleted.offset, deleted.size);
    }

    public int size() {
        return freeList.size();
    }


    public int max_size() {
        MyLinkedList.Node temp = freeList.head;
        int biggestSize = 0;
        while(temp!=null) {
            if (temp.size > biggestSize) {
                biggestSize = temp.size;
            }
            temp = temp.next;
        }
        return biggestSize;
    }

}


// Sort-by in Java: (needs a class)
// You may need it to sort your list or you can maintain a sorted list upon insertion
