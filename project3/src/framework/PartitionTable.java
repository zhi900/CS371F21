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
	int capacityPerBuffer;
	
    public PartitionTable(int capacityPerBuffer) {
    	this.capacityPerBuffer = capacityPerBuffer;
    }
    
    public T fetchNext(Object key) {
    	if(!buffers.containsKey(key)) {
    		buffers.put(key, new BoundedBuffer<T>(capacityPerBuffer));
    	}
    	if (buffers.get(key).isEmpty()) {
    		return null;
    	}
    	return buffers.get(key).fetch();
    }
    public void add(Object key, T value) {
    	if(!buffers.containsKey(key)) {
    		buffers.put(key, new BoundedBuffer<T>(capacityPerBuffer));
    	}
    	buffers.get(key).deposit(value);
    }
    public ArrayList<ArrayList<Object>> GetKeysInBatches(int numReducers, MapperReducerClientAPI api) {
    	ArrayList<ArrayList<Object>> ret = new ArrayList<ArrayList<Object>>();
    	for (int i = 0; i < numReducers; i++) {
    		ret.add(new ArrayList<Object>());
    	}
    	for (Object key : buffers.keySet()) {
    		int partition = (int)api.Partitioner(key, numReducers);
    		ret.get(partition).add(key);
    	}
    	return ret;
    }
}
