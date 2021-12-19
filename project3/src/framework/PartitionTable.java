package framework;

import java.util.ArrayList;

import utils.BoundedBuffer;

public class PartitionTable<T> {
	//TODO: your codde here
	//Notes:
	// (1) each partition works like a bounded buffer between
	// mappers and a reducer. (you can assume size = 10 or 50)
	// (2) if reducer_i wants to fetch a KV pair it can
	// only fetches from partition_i, but mapper_i can drop messages
	// into different partitions.
	ArrayList<BoundedBuffer<T>> buffers = new ArrayList<BoundedBuffer<T>>();
    public PartitionTable(int numReducers) {
    	for (int i = 0; i < numReducers; i++) {
    		buffers.add(new BoundedBuffer<T>());	
    	}
    	
    }
    
    public T fetchNext(long partition, Object key) {
    	return buffers.get((int)partition).Fetch(key);
    }
    public void add(long partition, Object key, T value) {
    	buffers.get((int)partition).Deposit(key, value);
    }

}

