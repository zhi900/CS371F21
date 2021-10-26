import java.util.Iterator;

// Probably a block class with at least these fields and methods.
public class MyLinkedList implements Iterable { //generic types are not required, you can just do MyLinkedList for blocks but Iterable is mandatory.
    //in addition to other regular list member functions such as insert and delete: (split and consolidate blocks must be implemented at the level of linked list)
    public class Node {

        public int offset;
        public int size;
        public Node next;

        public Node(int offset, int size, Node next) {
            this.offset = offset;
            this.size = size;
            this.next = next;
        }
    }

    public Node head = null;

    public MyLinkedList() {
    }
    public MyLinkedList(Node head) {
        this.head = head;
    }

    public void insert(int offset, int size){
        Node temp = this.head;
        Node newNode = new Node(offset, size, null);

        // first check if should add at head.
        if (temp == null || temp.offset > offset) {
            this.head = newNode;
            newNode.next = temp;
            return;
        }

        while (temp != null) {
            if(temp.next.offset > offset) {
                newNode.next = temp.next;
                temp.next = newNode;
                return;
            }
            temp = temp.next;
        }
    }

    public String toString() {
        String result = "";
        Node current = head;
        while(current.next != null) {
            result += current.offset + "-" + current.size;
            if(current.next != null){
                result += ", ";
            }
            current = current.next;
        }
        return "Contents of the List: " + result;
    }

    public void splitMayDelete(Node toSplit, int size) {
        Node deleted = this.deleteNode(toSplit.offset);
        deleted.size -= size;
        deleted.offset += size;
        if (deleted.size > 0) {
            this.insert(deleted.offset, deleted.size);
        }
    }

    public void insertMayCompact(int offset, int size) {
        Node temp = head;
        int offsetCausingCompact = offset + size;
        while (temp != null) {
            if (temp.offset == offsetCausingCompact) {
                temp.offset -= size;
                assert (temp.offset == offset);
                return;
            }
            temp = temp.next;
        }
        this.insert(offset, size);
    }

    public Node deleteNode(int offset)
    {
        // If linked list is empty
        if (head == null)
            return null;

        // memory offset can't be 0
        if (offset <=0 ) {
            System.err.println("Invalid memory offset: "+offset);
            return null;
        }

        Node temp = head;

        if (temp.offset == offset)
        {
            head = temp.next;   // Change head
            return temp;
        }

        while(temp!=null) {
            if (temp.next.offset == offset) {
                Node deletedNode = temp.next;
                temp.next = temp.next.next;
                return deletedNode;
            }
            temp = temp.next;
        }
        System.err.println("Invalid deleteNode did not find node with offset: "+offset);
		return temp;
    }


    // returns total amount in this list, sum of all sizes
    public int size(){
        int total = 0;
        Node temp = this.head;
        while(temp != null) {
            total += temp.size;
            temp = temp.next;
        }
        return total;
    }


    @Override
    public Iterator iterator() {
        return null;
    }

}
