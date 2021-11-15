package vm;

import org.hamcrest.MatcherAssert;
import storage.PhyMemory;
import vm.Memory;



import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;

public class VirtMemory extends Memory {

         private static MyPageTable table = new MyPageTable();
        private int memSize = 64*1024;
        private int count = 0;

    public VirtMemory() {
        super(new PhyMemory(64*1024));


    }




    public void write(int addr, byte value) {

        try {

            if(addr>memSize) {
                throw new InvalidAddressException();
            }
            table.put(addr, value);
            if(addr>63){
                count = addr/32;
                sync_to_disk();
            }


        } catch (InvalidAddressException e) {
            e.printStackTrace();
        }

    }


    public byte read(int addr) {
        return (byte) table.getP(addr);

    }


    protected void sync_to_disk() {
            getPhyMemory().store(count,0);
            getPhyMemory().load(count,0);
    }
    }

