package framework;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utils.BoundedBuffer;

// All reducers also have concurrent access to a kv store which maps
	// a key to all the value associated to the key, for instace
	// if reducer_i has fetched ("foo", 1) ("bar",1) ("foo",1)
	// ("foo",1) and ("bar",1) from partition_i,
	// then after injecting them, reducer_i should have updated the KV
        // store to contain ("foo", {1,1,1}) and ("bar", {1,1}). You can use a
	// concurrent hashmap/tree to implement the concurrent KV store


// Partition table is essentially ConcurrentKVStore combined with BoundedBuffer
// So a separate ConccurentKVStore is not necessary for this implementation.
// Our trick is make a BoundedBuffer per key. So it locks per key which should work for dictionary!

public class ConcurrentKVStore<T> {
	Hashtable<Object, LinkedList<T>> buffers = new Hashtable<Object, LinkedList<T>>();
	Lock[] locksPerReducer;
	MapperReducerClientAPI api;
	
	public ConcurrentKVStore(int reducerCount, MapperReducerClientAPI api) {
		locksPerReducer = new Lock[reducerCount];
		for (int i = 0; i < reducerCount; i++) {
			locksPerReducer[i] = new ReentrantLock(false);
		}
		this.api = api;
	}
	
	public T pop(Object key) {
		int reducer = (int)api.Partitioner(key, locksPerReducer.length);
		//System.out.println("Pop " + key + " r: " + reducer);
		locksPerReducer[reducer].lock();
		
		if (!buffers.containsKey(key)) {
			buffers.put(key, new LinkedList<T>());
			System.out.println("Key not found");
			return null;
		}
		if (buffers.get(key).size() == 0) {
			return null;
		}
		T ret = buffers.get(key).remove();
		
		locksPerReducer[reducer].unlock();
		return ret;
	}
	
	public void store(Object key, T value) {
		int reducer = (int)api.Partitioner(key, locksPerReducer.length);
		//System.out.println("Storing " + key + " " + value + " r: " + reducer + "len " + locksPerReducer.length);
		locksPerReducer[reducer].lock();
		if (!buffers.containsKey(key)) {
			buffers.put(key, new LinkedList<T>());
			//System.out.println("Key not found " + key + " " + reducer);
		}
		buffers.get(key).add(value);
		//System.out.println("Stored" + buffers.get(key).getLast());
		locksPerReducer[reducer].unlock();
	}
}
