import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyMemoryAllocation extends MyLinkedList {
    String algorithm;
    MyLinkedList freeList = new MyLinkedList();
    MyLinkedList usedList = new MyLinkedList();
    int memSize = 0;
    String algo;
    int lastNode = 0;
    public MyMemoryAllocation(int mem_size, String algorithm) {
        memSize = mem_size;
        algo = algorithm;

        initiate();

        

        
    }

    public void initiate(){
        for (int i =memSize-1;i>0;i--) {
            freeList.insert(i);

        }
        freeList.sortList();
        lastNode = freeList.get(memSize-2);
    }



   




   

    public int alloc(int size) {

            int data = freeList.head.data;
            usedList.insert(freeList.head.data);
            usedList.sortList();
            for (int i = 0; i < size; i++) {


                freeList.setnextHead();
            }
        if(freeList.size() == 0){
            data = 0;
        }

        return data;




    }

    public void free(int address) {

        try {
            int index = usedList.indexOf(address);
            int lastNode = usedList.get(index+1);

            for (int i =address;i<lastNode;i++){
                freeList.insert(address++);
            }
            usedList.deleteNode(index);
            freeList.sortList();
        }
        catch(NullPointerException e) {
            int index = usedList.size()-1;


            for (int i =address;i<=lastNode;i++){
                freeList.insert(address++);
            }
            usedList.deleteNode(index);
            freeList.sortList();
        }


    }


    public int size() {


        return freeList.size();
    }


    public int max_size() {
        int count = 1;
        int maxCount = 0;
        for(int i =0;i<freeList.size()-1;i++){
            if(freeList.get(i)+1 == freeList.get(i+1)){
                count++;
                if (count>maxCount) {
                    maxCount = count;
                }
            }
            else{
                count = 1;
            }
        }
        return maxCount;
    }

}
