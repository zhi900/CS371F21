import java.util.Hashtable;

import static org.hamcrest.CoreMatchers.not;

public class VirtMemory extends Memory {

    static Hashtable<Integer, Byte> ht1 = new Hashtable<>();
    static int count = 0;

   
    public VirtMemory() {
        super(new PhyMemory(64*1024));


    }


    public void write(int addr, byte value) {
        addr = addr%64;
        ht1.put(addr, value);

        count++;

        if (count >= 32) {
            getPhyMemory().store(addr,0);
            getPhyMemory().load(addr,0);

        }





    }


    public byte read(int addr) {
        addr = addr%64;

        return ht1.get(addr);

    }


    protected void sync_to_disk() {

    }
}

