package vm;

import java.util.LinkedList;

import storage.PhyMemory;

public class Policy {
	LinkedList<MyPageTable.PageTableEntry> usedMemory = new LinkedList();
	int maxSize;
	PhyMemory pmem;
	Policy(int maxSize, PhyMemory pmem) {
		this.maxSize = maxSize;
		this.pmem = pmem;
	}
	
	void setPfn(MyPageTable.PageTableEntry entry) {
		if (usedMemory.size() < maxSize) {
			entry.pfn = usedMemory.size();
			System.out.println("Loading to open space in memory: " + entry.vpn);
			pmem.load(entry.vpn, MyPageTable.pfnToAddress(entry.pfn));
			usedMemory.add(entry);
		}
		else {
			MyPageTable.PageTableEntry removed = usedMemory.removeFirst();
			if (removed.dirty) {
				pmem.store(removed.vpn, MyPageTable.pfnToAddress(removed.pfn));
				removed.dirty = false;
			}
			entry.pfn = removed.pfn;
			removed.pfn = -1;
			System.out.println("Loading to after clearing memory: " + entry.vpn);
			pmem.load(entry.vpn, MyPageTable.pfnToAddress(entry.pfn));
		}
	}
	
	void sync_to_disk() {
		for (MyPageTable.PageTableEntry entry : usedMemory) {
			if (entry.dirty) {
				pmem.store(entry.vpn, MyPageTable.pfnToAddress(entry.pfn));
			}
		}
	}
}
