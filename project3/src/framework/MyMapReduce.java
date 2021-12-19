package framework;
import java.util.logging.Level;
public class MyMapReduce extends MapReduce {
	//TODO: your code here. Define all attributes
	
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
	}
}
