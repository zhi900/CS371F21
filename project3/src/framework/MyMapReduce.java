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
	
	ConcurrentKVStore store;
    PartitionTable<Object> partTable;
    MapperReducerClientAPI mapperReducerObj;
    int num_reducers;
	
	//What is in a running instance of MapReduce?
	public void MREmit(Object key, Object value)
	{
		//long reducerBatch = mapperReducerObj.Partitioner(key, num_reducers);
		partTable.add(key, value);
	}

	public Object MRGetNext(Object key, int partition_number) {
		//TODO: your code here. Delete UnsupportedOperationException after your implementation is done.
		return partTable.fetchNext(key);
	}
	
	
	
	@Override
	protected void MRRunHelper(String inputFileName,
		    		  MapperReducerClientAPI mapperReducerObj,
		    		  int num_mappers, 
		    		  int num_reducers)
	{
		//TODO: your code here. Delete UnsupportedOperationException after your implementation is done.
		store = new ConcurrentKVStore();
		this.num_reducers = num_reducers;
		partTable = new PartitionTable<Object>(50);
		this.mapperReducerObj = mapperReducerObj;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < num_mappers; i++) {
			Thread mapThread = new MapThread(i, inputFileName);
			threads.add(mapThread);
			mapThread.start();
		}
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		threads.clear();
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
