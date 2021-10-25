import java.util.Iterator;


public class MyLinkedList implements Iterable {

    public class Node {

        public int data;
        public int size;
        public Node next;


        public Node(int data) {
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

    public void insert(int data){
        if(head == null){
            head = new Node(data);
        } else {
            Node newNode = new Node(data);
            newNode.setNext(head);
            head = newNode;
        }
    }

    public void sortList()
    {


        Node current = head, index = null;

        int temp;

        if (head == null) {
            return;
        }
        else {
            while (current != null) {

                index = current.next;

                while (index != null) {



                    if (current.data > index.data) {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }

                    index = index.next;
                }
                current = current.next;
            }
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
    public int get(int i) {

        Node u = head;
        for(int j = 0; j < i; j++){
            u = u.next;
        }
        return u.data;


    }
    public int indexOf(int data) {

        int index = 0;
        Node current = head;


        while(current != null) {

            if(current.data == data) {
                return index;
            }

            current = current.next;
            index++;
        }
        return index;
    }


    void deleteNode(int position)
    {

        if (head == null)
            return;


        Node temp = head;


        if (position == 0)
        {
            head = temp.next;
            return;
        }


        for (int i=0; temp!=null && i<position-1; i++)
            temp = temp.next;


        if (temp == null || temp.next == null)
            return;


        Node next = temp.next.next;

        temp.next = next;
    }





    @Override
    public Iterator iterator() {
        return null;
    }

}
class Block  {
    int offset;
    int size;



    public Block(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    public String toString()
    {
        return null;

    }

    public boolean is_adjacent(Block other) {

        return false;
    }
}
