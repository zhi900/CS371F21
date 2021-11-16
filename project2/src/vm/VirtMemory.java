package vm;

import java.util.LinkedList;

import storage.PhyMemory;

public class VirtMemory extends Memory{
	int size;
	int pages;
	private static final int FRAME_SIZE = 64;
	MyPageTable pageTable;
	Policy policy;
	
	public VirtMemory() {
		super(new PhyMemory());
		size = 64 * 1024;
		pages = size / FRAME_SIZE;
		pageTable = new MyPageTable();
		policy = new Policy(ram.num_frames(), ram);
		// TODO Auto-generated constructor stub
	}

	
	public VirtMemory(PhyMemory ram) {
		super(ram);
		size = 64 * 1024;
		pages = size / FRAME_SIZE;
		pageTable = new MyPageTable();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	boolean isValidAddress(int addr) {
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
		if (entry.writesCount >= 32) {
			ram.store(entry.vpn, MyPageTable.pfnToAddress(entry.pfn));
			entry.dirty = false;
			entry.writesCount = 0;
		}
	}

	MyPageTable.PageTableEntry loadEntry(int addr) {
		MyPageTable.PageTableEntry entry;
		// Get the physical page table entry...
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
		return ram.read(getFullPhyicalAddress(entry.pfn, addr));
	}

	@Override
	protected void sync_to_disk() {
		// TODO Auto-generated method stub
		policy.sync_to_disk();
	}

	public static int getFullPhyicalAddress(int pfn, int logicalAddress) {
		return (pfn << 6) + ((logicalAddress << 26) >> 26);
	}
}
