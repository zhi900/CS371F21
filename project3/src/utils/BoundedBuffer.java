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
	LinkedList<T> buffer = new LinkedList<T>();
	private Lock mutex = new ReentrantLock(false);
	int capacity = 0;
	int fetchCounter = 0;
	
	Condition isFull = mutex.newCondition();
	Condition isEmpty = mutex.newCondition();
	
	public BoundedBuffer(int capacity) {
		this.capacity = capacity;
	}
	
	public void deposit(T toAdd) {
			mutex.lock();
			// Since partition table makes a bounded buffer per key
			// this can have a "no capacity" mode if capacity is 0
			// since concurrektKV will need LinkedList at least this big per key anyway should be okay
			while (capacity > 0 && buffer.size() >= capacity) {
				System.out.println("Full capacity " + capacity);
				try {
					isFull.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			buffer.add(toAdd);
			isEmpty.signal();
			mutex.unlock();
	}
	
	public boolean isEmpty() {
		return buffer.size() == 0;
	}
	
	public T fetch() {
		mutex.lock();
		while(buffer.size() == 0) {
			try {
				isEmpty.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T ret = buffer.remove();
		isFull.signal();
		mutex.unlock();
		return ret;
	}
}
