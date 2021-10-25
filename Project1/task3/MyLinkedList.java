import java.util.Iterator;

// Probably a block class with at least these fields and methods.
public class MyLinkedList implements Iterable { //generic types are not required, you can just do MyLinkedList for blocks but Iterable is mandatory.
    //in addition to other regular list member functions such as insert and delete: (split and consolidate blocks must be implemented at the level of linked list)
    public class Node {

        public int offset;                                  //changed data to offset
        public int size;
        public Node next;


        public Node(int offset, int size, Node next) {      //added Node next as this will add next
            this.offset = offset;                           //changed data to offset
            this.size = size;                               
            this.next = next;                               
        }
    }
       
    public Node head = null;                                //added head node decloration
    
    public MyLinkedList(Node head) {
        this.head = head;
    }
    
    //add the sort here or have the list sorted on insertion
    public void insert(int offset, int size){
        if(head == null){
            head = new Node(offset, size);
        } else {
            Node newNode = new Node(offset, size);
            newNode.setNext(head);
            head = newNode;
        }
    }
    public String toString() {
        String result = "";
        Node current = head;
        while(current.getNext() != null){
            result += current.offset + "-" + current.size;
            if(current.next != null){
                result += ", ";
            }
            current = current.next;
        }
        result += current.getData();
        return "Contents of the List: " + result;
    }

    public int size()
    {
        Node temp = head;
        int count = 0;
        while (temp != null)
        {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public void setnextHead(){
        head = head.next;
    }
    public void splitMayDelete() {

    }
    public void insertMayCompact() {
        
    }


    void deleteNode(int position)                           //modified deleteNode method
    {
        // If linked list is empty
        if (head == null)
            return;

        //memory offset can't be 0
        if (offset <= 0) {
            System.err.println("Invalid memory offset: " + offset);
            return;
        }
        
        //Store head node
        Node temp = head;
        
        if (offset == 1) {                                  //changes head
            head = temp.next;
            return;
        }

       while (temp != null) {                               //will go throught the list, find temp node where the temp.next.offset==offset 
           if (temp.next.offset == offset) {                //then chage the parent node to the next next node
               temp.next = temp.next.next;
           }
        temp.next = next;  // Unlink the deleted node from list
        }
        System.out.println("Invalid deleteNode did not find node with offset: " + offset);
    }

    //return total amount in this list, sum of all size
    public int size(){
    }
    
    @Override
    public Iterator iterator() {
        return null;
    }

}
