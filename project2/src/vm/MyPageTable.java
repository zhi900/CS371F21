package vm;

import java.util.LinkedList;
import java.util.Objects;

import static java.util.Objects.hash;

public class MyPageTable {
    private static int INITIAL_SIZE = 64*1024;
    private PageTableEntry[] table;
    private int count =0;
    private int diskCount = 0;

    public MyPageTable(){
        table = new PageTableEntry[INITIAL_SIZE];
    }


    private static class PageTableEntry {
        int vpn;
        int pfn;
        boolean dirty;
        PageTableEntry next;
    }

    public void put(int vpn, int pfn) {

        int bucket = hash(vpn);
        PageTableEntry list = table[bucket];

        while (list != null) {

            if (list.vpn  == vpn)
                break;
            list = list.next;
        }

        if (list != null) {

            list.pfn = pfn;
        }
        else {


            PageTableEntry newNode = new PageTableEntry();
            newNode.vpn = vpn;
            newNode.pfn = pfn;
            newNode.next = table[bucket];
            table[bucket] = newNode;
            count++;
        }
    }

    public int getV(int vpn) {

        int bucket = hash(vpn);
        PageTableEntry list = table[bucket];
        while (list != null) {

            if (list.vpn == vpn)
                return list.vpn;
            list = list.next;
        }

        return 0;
    }
    public int getP(int vpn) {

        int bucket = hash(vpn);
        PageTableEntry list = table[bucket];
        while (list != null) {

            if (list.vpn == vpn)
                return list.pfn;
            list = list.next;
        }

        return 0;
    }
    public void remove(int vpn) {

        int bucket = hash(vpn);
        if (table[bucket] == null) {

            return;
        }
        if (table[bucket].vpn == vpn) {


            table[bucket] = table[bucket].next;
            count--;
            return;
        }

        PageTableEntry prev = table[bucket];

        PageTableEntry curr = prev.next;

        while (curr != null && !(curr.vpn == vpn)) {
            curr = curr.next;
            prev = curr;
        }

        if (curr != null) {
            prev.next = curr.next;
            count--;
        }
    }
    public boolean containsKey(int vpn) {

        int bucket = hash(vpn);
        PageTableEntry list = table[bucket];
        while (list != null) {

            if (list.vpn == vpn)
                return true;
            list = list.next;
        }

        return false;
    }
    public int size() {

        return count;
    }


    private int hash(int vpn) {

        return (Math.abs(vpn)) % table.length;
    }



}

