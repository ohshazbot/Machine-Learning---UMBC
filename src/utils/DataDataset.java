package utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

public class DataDataset implements Dataset {
	List<Dataset> intern;
	
	
	public DataDataset(List<Dataset> d)
	{
		intern = d;
	}
	
	@Override
	public void addData(Data data) throws IOException, OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Data getData(int index) throws IOException {
		for (int i = 0; i < intern.size(); i++)
		{
			if (intern.get(i).size() > index)
				return intern.get(i).getData(index);
			else
				index-=intern.get(i).size();
		}
		return null;
	}

	@Override
	public int size() {
		int tot = 0;
		for (int i = 0; i < intern.size(); i++)
		{
			tot+= intern.get(i).size();
		}
		return tot;
	}

	@Override
	public void remove(int index) throws IOException, OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Iterator<Data> iter() throws IOException {
		return new DataDataIterator(intern);
	}

	@Override
	public void close() throws IOException {
		for (int i = 0; i < intern.size(); i++)
			intern.get(i).close();
	}

	@Override
	public void closeWrite() throws IOException {
		for (int i = 0; i < intern.size(); i++)
			intern.get(i).closeWrite();
	}

	@Override
	public void closeRead() throws IOException {
		for (int i = 0; i < intern.size(); i++)
			intern.get(i).closeRead();
	}

}
