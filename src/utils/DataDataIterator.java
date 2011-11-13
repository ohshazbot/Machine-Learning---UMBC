package utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DataDataIterator implements Iterator<Data> {
	List<Dataset> intern;
	int place = 0;
	Iterator<Data> curr;
	
	public DataDataIterator(List<Dataset> intern) throws IOException {
		this.intern = intern;
		curr = this.intern.get(0).iter();
	}

	@Override
	public boolean hasNext() {
		if (curr.hasNext())
			return true;
		place++;
		if (place >= intern.size())
			return false;
		try {
			curr = intern.get(place).iter();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public Data next() {
		if (!hasNext())
			return null;
		else return curr.next();
	}

	@Override
	public void remove() {
		if (!hasNext())
			return;
		curr.remove();
	}

}
