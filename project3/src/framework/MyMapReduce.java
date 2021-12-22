package framework;
import java.util.ArrayList;
import java.util.logging.Level;
public class MyMapReduce extends MapReduce {
	//TODO: your code here. Define all attributes
	
	class MapThread extends Thread
	{
		int threadNumber;
		String inputFileName;
		public MapThread(int threadNumber, String inputFileName) {
			this.threadNumber = threadNumber;
			this.inputFileName = inputFileName;
		}
	    @Override
	    public void run()
	    {
	    	String suffix = threadNumber < 10 ? ".0" + threadNumber : "." + threadNumber;
			String subFileName = inputFileName + suffix;
			mapperReducerObj.Map(subFileName);
	    }
	}
	
	class ReduceToStoreThread extends Thread
	{
		int partitionIndex;
		public boolean turnOff;
		public ReduceToStoreThread(int partitionIndex) {
			this.partitionIndex = partitionIndex;
		}
	    @Override
	    public void run()
	    {
	    	boolean toReduce = true;
	    	while(!turnOff || toReduce) {
	    		toReduce = partTable.processToStore(partitionIndex);
	    		//System.out.println("Turn off " + turnOff + " index " + toReduce);
	    	}
	    }
	}
	
	class ReduceThread extends Thread
	{
		ArrayList<Object> keys;
		int partitionNumber;
		public ReduceThread(int partitionNumber, ArrayList<Object> keys) {
			this.partitionNumber = partitionNumber;
			this.keys = keys;
		}
	    @Override
	    public void run()
	    {
	    	for(Object key : keys) {
				mapperReducerObj.Reduce(key, partitionNumber);
			}
	    }
	}
	
    PartitionTable<Object> partTable;
    MapperReducerClientAPI mapperReducerObj;
    int num_reducers;
	
	//What is in a running instance of MapReduce?
	public void MREmit(Object key, Object value)
	{
		//System.out.println("Emit: " + key + " " + value);
		partTable.add(key, value);
	}

	public Object MRGetNext(Object key, int partition_number) {
		//System.out.println("Fetch: " + key + " " + partition_number);
		return partTable.fetchNext(key);
	}
	
	
	@Override
	protected void MRRunHelper(String inputFileName,
		    		  MapperReducerClientAPI mapperReducerObj,
		    		  int num_mappers, 
		    		  int num_reducers)
	{
		//TODO: your code here. Delete UnsupportedOperationException after your implementation is done.
		//store = new ConcurrentKVStore();
		this.num_reducers = num_reducers;
		// This will make bounded buffer infinity size which is okay because we make one BoundedBuffer per key.
		partTable = new PartitionTable<Object>(num_reducers, mapperReducerObj);
		this.mapperReducerObj = mapperReducerObj;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<ReduceToStoreThread> processThreads = new ArrayList<ReduceToStoreThread>();
		for (int i = 0; i < num_mappers; i++) {
			Thread mapThread = new MapThread(i, inputFileName);
			threads.add(mapThread);
			mapThread.start();
		}
		
		for (int i = 0; i < num_reducers; i++) {
			ReduceToStoreThread reduceThread = new ReduceToStoreThread(i);
			processThreads.add(reduceThread);
			reduceThread.start();
		}
		
		System.out.println("Waiting on maps");
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(ReduceToStoreThread t : processThreads) {
			t.turnOff = true;
		}
		System.out.println("Waitng for process threads");
		for(ReduceToStoreThread t : processThreads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		threads.clear();
		processThreads.clear();
		
		ArrayList<ArrayList<Object>> keysInBatches = partTable.GetKeysInBatches(num_reducers, mapperReducerObj);
		for (int i = 0; i < num_mappers; i++) {
			Thread reduceThread = new ReduceThread(i, keysInBatches.get(i));
			threads.add(reduceThread);
			reduceThread.start();
		}
		
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
