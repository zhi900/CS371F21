package framework;

import java.util.ArrayList;
import java.util.Hashtable;

import utils.BoundedBuffer;

public class PartitionTable<T> {
	//TODO: your codde here
	//Notes:
	// (1) each partition works like a bounded buffer between
	// mappers and a reducer. (you can assume size = 10 or 50)
	// (2) if reducer_i wants to fetch a KV pair it can
	// only fetches from partition_i, but mapper_i can drop messages
	// into different partitions.
	Hashtable<Object, BoundedBuffer<T>> buffers = new Hashtable<Object, BoundedBuffer<T>>();
    public PartitionTable() {
    	
    }
    
    public T fetchNext(Object key) {
    	if(!buffers.containsKey(key)) {
    		buffers.put(key, new BoundedBuffer<T>());
    	}
    	return buffers.get(key).fetch();
    }
    public void add(Object key, T value) {
    	if(!buffers.containsKey(key)) {
    		buffers.put(key, new BoundedBuffer<T>());
    	}
    	buffers.get(key).deposit(value);
    }

}

