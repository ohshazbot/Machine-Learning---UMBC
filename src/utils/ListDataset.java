package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ListDataset implements Dataset {
	private List<Data> intern;
	
	public ListDataset()
	{
		intern = new ArrayList<Data>();
	}
	@Override
	public void addData(Data data) throws IOException {
		intern.add(data);
	}

	@Override
	public Data getData(int index) throws IOException {
		return intern.get(index);
	}

	@Override
	public int size() {
		return intern.size();
	}

	@Override
	public void remove(int index) throws IOException {
		if (index < size())
			intern.remove(index);
	}

	@Override
	public Iterator<Data> iter() throws IOException {
		return intern.iterator();
	}

	@Override
	public void close() throws IOException {
		return;
	}

	@Override
	public void closeWrite() throws IOException {
		return;
	}

	@Override
	public void closeRead() throws IOException {
		return;
	}

}
