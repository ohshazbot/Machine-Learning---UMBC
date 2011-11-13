package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;


public class FileDataset implements Dataset {
	private File sync;
	private BufferedReader reader = null;
	private FileWriter writer = null;
	int size;
	int curPos = -1;

	public FileDataset(File f, int i) throws IOException {
		sync = File.createTempFile(f.getName(), Integer.toString(i));
		sync.deleteOnExit();
		size = 0;
	}
	
	public FileDataset(File f) throws IOException {
		sync = f;
		size = 0;
		reader = new BufferedReader(new FileReader(sync));
		while (reader.readLine() != null)
			size++;
	}

	@Override
	public void addData(Data data) throws IOException {
		if (writer == null)
			writer = new FileWriter(sync);
		writer.append(data.toString()+"\n");
		size++;
	}

	@Override
	public Data getData(int index) throws IOException {
		if (index >= size)
			return null;
		if (index <= curPos)
			closeRead();
		writer.close();
		if (reader == null)
			try {
				reader = new BufferedReader(new FileReader(sync));
			} catch (FileNotFoundException e) {
				throw new IOException(e);
			}

		int cnt = 0;
		String line = null;
		for (cnt = 0; cnt <= index; cnt++)
			line = reader.readLine();
		
		if (line == null)
			return null;
		curPos = index;
		return new Data(line);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void remove(int index) throws IOException {
		File replacement = File.createTempFile(sync.getName(), ".tmp");
		replacement.deleteOnExit();
		if (index >= size)
			return;
		if (reader == null)
			try {
				reader = new BufferedReader(new FileReader(sync));
			} catch (FileNotFoundException e) {
				throw new IOException(e);
			}
		closeWrite();
		writer = new FileWriter(replacement);

		int cnt = 0;
		for (cnt = 0; cnt < size; cnt++)
			if (cnt != index)
				writer.append(reader.readLine()+"\n");
		
		closeRead();
		sync.delete();
		replacement.renameTo(sync);
		size--;
	}

	@Override
	public Iterator<Data> iter() throws IOException {
		return new DataIterator(FileUtils.lineIterator(sync));
	}

	@Override
	public void close() throws IOException {
		closeRead();
		closeWrite();
	}

	@Override
	public void closeWrite() throws IOException {
		if (writer != null)
		{
			writer.close();
			writer = null;
		}
	}

	@Override
	public void closeRead() throws IOException {
		curPos = -1;
		if (reader != null)
		{
			reader.close();
			reader = null;
		}
	}
}
