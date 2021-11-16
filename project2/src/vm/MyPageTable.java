package vm;

import java.util.LinkedList;

public class MyPageTable {
	public static class PageTableEntry {
		public int vpn;
		public int pfn;
		public int writesCount = 0;
		public boolean dirty = false;
	}
	
	private static int INITIAL_SIZE = 1024;
	private LinkedList<PageTableEntry>[] buckets = new LinkedList[INITIAL_SIZE];
	public MyPageTable() {
		
	}

	
	public static int hash(int address) {
		return address;
	}

	

	boolean contains(int address) {
		int entryIndex = convertToIndex(address);
		for (PageTableEntry entry : getBucket(entryIndex)) {
            if (entry.vpn == address) {
            	return true;
            }
        }
		return false;
	}
	
	void remove(int address) {
		int entryIndex = convertToIndex(address);
		getBucket(entryIndex).removeIf(e -> (e.vpn == address));
	}
	
	PageTableEntry put(int address) {
		int entryIndex = convertToIndex(address);
		PageTableEntry entry = new PageTableEntry();
		entry.vpn = getVpn(address);
		entry.pfn = 0;
		entry.dirty = false;
		getBucket(entryIndex).add(entry);
		return entry;
	}
	
	PageTableEntry get(int address) throws NoPFNException {
		int entryIndex = convertToIndex(address);
		int vpn = getVpn(address);
		for (PageTableEntry entry : getBucket(entryIndex)) {
            if (entry.vpn == vpn) {
            	return entry;
            }
        }
		throw new NoPFNException();
	}
	
	LinkedList<PageTableEntry> getBucket(int index) { 
		if (buckets[index] == null) {
			buckets[index] = new LinkedList();
		}
		return buckets[index];
	}
	
	public static int getVpn(int address) {
		return address >>> 6;
	}
	
	public static int pfnToAddress(int pfn) {
		return pfn << 6;
	}
	
	int convertToIndex(int address) {
		int truncated = getVpn(address);
		return hash(truncated) % INITIAL_SIZE;
	}
}
