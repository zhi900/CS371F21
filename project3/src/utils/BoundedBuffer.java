package utils;
import java.util.ArrayList;
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
	//Hashtable<Object, LinkedList<T>> buffer = new Hashtable<Object, LinkedList<T>>();
	ArrayList<T> buffer = new ArrayList<T>();
	private Lock mutex = new ReentrantLock(true);
	
	public void deposit(T toAdd) {
			mutex.lock();
			buffer.add(toAdd);
			mutex.unlock();
	}
	
	public T fetch() {
		mutex.lock();
		T ret = buffer.get(0);
		buffer.remove(0);
		mutex.unlock();
		return ret;
	}
}
