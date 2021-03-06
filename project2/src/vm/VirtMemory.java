package vm;

import java.util.LinkedList;

import com.sun.security.auth.UnixNumericUserPrincipal;
import storage.PhyMemory;

public class VirtMemory extends Memory{
    private int size;
    private int pages;
    private final int FRAME_SIZE = 64;
    private MyPageTable pageTable;
    private Policy policy;
    
    public VirtMemory() {
        super(new PhyMemory());
        size = 64 * 1024;
        pages = size / FRAME_SIZE;
        pageTable = new MyPageTable();
        policy = new Policy(ram.num_frames(), ram);
       
    }

   


    public VirtMemory(PhyMemory ram) {
        super(ram);
        size = 64 * 1024;
        pages = size / FRAME_SIZE;
        pageTable = new MyPageTable();
    }


    private boolean isValidAddress(int addr) {
        try {
            if (addr >= size || addr < 0) {
                throw new InvalidAddressException();
            }
        } catch (InvalidAddressException e) {
            System.err.println("Invalid address "+ addr);
            return false;
        }
        return true;
    }

    @Override
    public void write(int addr, byte value) {
        if (!isValidAddress(addr)) {
            return;
        }

        MyPageTable.PageTableEntry entry = loadEntry(addr);
        ram.write(getFullPhyicalAddress(entry.pfn, addr), value);
        entry.dirty = true;
        entry.writesCount += 1;
        if (entry.writesCount % 32 == 0) {

            sync_to_disk();
            entry.dirty = false;

        }
    }

    private MyPageTable.PageTableEntry loadEntry(int addr) {
        MyPageTable.PageTableEntry entry;
        // Get the physical page table entry..
        try {
            entry = pageTable.get(addr);
            if (entry.pfn == -1) {
                policy.setPfn(entry);

            }
        } catch (NoPFNException e) {
            entry = pageTable.put(addr);
            policy.setPfn(entry);

        }
        return entry;
    }

    @Override
    public byte read(int addr) {
        if (!isValidAddress(addr)) {
            return 0;
        }
        // Get the physical page table entry...
        MyPageTable.PageTableEntry entry = loadEntry(addr);

        sync_to_disk();
        return ram.read(getFullPhyicalAddress(entry.pfn, addr));
    }

    @Override
    protected void sync_to_disk() {
        
        policy.sync_to_disk();
    }

    public static int getFullPhyicalAddress(int pfn, int logicalAddress) {
        int offset = logicalAddress%64;

        return pfn*64+offset;
    }
}
