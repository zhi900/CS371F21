package framework;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utils.BoundedBuffer;

public class PartitionTable<T> {
	public class KeyValue<T> {
		public Object key;
		public T value;
		public KeyValue(Object key, T value) {
			this.key = key;
			this.value = value;
		}
	}
	
	
	//TODO: your codde here
	//Notes:
	// (1) each partition works like a bounded buffer between
	// mappers and a reducer. (you can assume size = 10 or 50)
	// (2) if reducer_i wants to fetch a KV pair it can
	// only fetches from partition_i, but mapper_i can drop messages
	// into different partitions.
	//Hashtable<Object, BoundedBuffer<T>> buffers = new Hashtable<Object, BoundedBuffer<T>>();
	BoundedBuffer<KeyValue<T>>[] buffers;
	ConcurrentKVStore<T> store;
	MapperReducerClientAPI api;
	int reducerCount;
	//private Lock mutex = new ReentrantLock(false);
	
	
	
    public PartitionTable(int reducerCount, MapperReducerClientAPI api) {
    	this.api = api;
    	this.reducerCount = reducerCount;
    	buffers = new BoundedBuffer[reducerCount];
    	for (int i = 0; i< reducerCount; i++) {
    		buffers[i] = new BoundedBuffer<KeyValue<T>>(50);
    	}
    	store = new ConcurrentKVStore(reducerCount, api);
    }
    
    public boolean processToStore(int reducerIndex) {
    	if (buffers[reducerIndex].isEmpty()) {
    		return false;
    	}
    	KeyValue<T> result = buffers[reducerIndex].fetch();
    	store.store(result.key, result.value);
    	return true;
    }
    
    public T fetchNext(Object key) {
    	return store.pop(key);
    }
    
    public void add(Object key, T value) {
    	int reducerIndex = (int)api.Partitioner(key, reducerCount);
    	buffers[reducerIndex].deposit(new KeyValue<T>(key, value));
    }
    
    public ArrayList<ArrayList<Object>> GetKeysInBatches(int numReducers, MapperReducerClientAPI api) {
    	ArrayList<ArrayList<Object>> ret = new ArrayList<ArrayList<Object>>();
    	for (int i = 0; i < numReducers; i++) {
    		ret.add(new ArrayList<Object>());
    	}
    	for (Object key : store.buffers.keySet()) {
    		int partition = (int)api.Partitioner(key, numReducers);
    		ret.get(partition).add(key);
    	}
    	return ret;
    }
}
