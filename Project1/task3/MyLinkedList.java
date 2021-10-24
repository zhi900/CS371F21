import java.util.Iterator;

// Probably a block class with at least these fields and methods.
public class MyLinkedList implements Iterable { //generic types are not required, you can just do MyLinkedList for blocks but Iterable is mandatory.
    //in addition to other regular list member functions such as insert and delete: (split and consolidate blocks must be implemented at the level of linked list)
    public class Node {

        public int data;
        public int size;
        public Node next;


        public Node(int data, int size) {
            this.data = data;
            this.size = size;
            next = null;
        }

        public int getData() {
            return data;
        }
        public void setNext(Node n) {
            next = n;
        }
        public Node getNext() {
            return next;
        }

    }

    public Node head;

    public void insert(int data, int size){
        if(head == null){
            head = new Node(data, size);
        } else {
            Node newNode = new Node(data, size);
            newNode.setNext(head);
            head = newNode;
        }
    }
    public String toString() {
        String result = "";
        Node current = head;
        while(current.getNext() != null){
            result += current.getData();
            if(current.getNext() != null){
                result += ", ";
            }
            current = current.getNext();
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


    void deleteNode(int position)
    {
        // If linked list is empty
        if (head == null)
            return;

        // Store head node
        Node temp = head;

        // If head needs to be removed
        if (position == 0)
        {
            head = temp.next;   // Change head
            return;
        }

        // Find previous node of the node to be deleted
        for (int i=0; temp!=null && i<position-1; i++)
            temp = temp.next;

        // If position is more than number of nodes
        if (temp == null || temp.next == null)
            return;

        // Node temp->next is the node to be deleted
        // Store pointer to the next of node to be deleted
        Node next = temp.next.next;

        temp.next = next;  // Unlink the deleted node from list
    }





    @Override
    public Iterator iterator() {
        return null;
    }

}
