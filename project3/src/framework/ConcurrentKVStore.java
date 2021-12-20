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

//public class ConcurrentKVStore {
//	Hashtable<Object, LinkedList<Object>> buffer = new Hashtable<Object, LinkedList<Object>>();
//	
//	
//	
//	public void Deposit(Object key, Object toAdd) {
//			//mutex.lock();
//			if (!buffer.contains(key)) {
//				buffer.put(key, new LinkedList<Object>());
//			}
//		
//			buffer.get(key).add(toAdd);
//			//mutex.unlock();
//	}
//	
//	public Object Fetch(Object key) {
//		if (!buffer.contains(key)) {
//			buffer.put(key, new LinkedList<Object>());
//		}
//		//mutex.lock();
//		if (buffer.get(key).size() == 0) {
//			return null;
//		}
//		Object ret = buffer.get(key).remove();
//		//mutex.unlock();
//		return ret;
//	}	
//}
