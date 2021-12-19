package utils;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer <T> {
//Please note the biggest difference between this BoundBuffer
//and the one we demoed in class is <T>
//implement member functions: deposit() and fetch()
	Hashtable<Object, LinkedList<T>> buffer = new Hashtable<Object, LinkedList<T>>();
	
	private Lock mutex = new ReentrantLock(true);
	
	public void Deposit(Object key, T toAdd) {
			mutex.lock();
			if (!buffer.contains(toAdd)) {
				buffer.put(key, new LinkedList<T>());
			}
				buffer.get(key).add(toAdd);
			mutex.unlock();
	}
	
	public T Fetch(Object key) {
		mutex.lock();
		T ret = buffer.get(key).pop();
		mutex.unlock();
		return ret;
	}
}
