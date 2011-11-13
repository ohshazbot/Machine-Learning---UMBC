package utils;

import java.util.Iterator;

import org.apache.commons.io.LineIterator;


public class DataIterator implements Iterator<Data> {
	Iterator<String> internal;
	public DataIterator(LineIterator lineIterator) {
		internal = lineIterator;
	}

	@Override
	public boolean hasNext() {
		return internal.hasNext();
	}

	@Override
	public Data next() {
		return new Data(internal.next());
	}

	@Override
	public void remove() {
		internal.remove();
	}

}
