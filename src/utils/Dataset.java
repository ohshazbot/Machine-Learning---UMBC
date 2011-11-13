package utils;

import java.io.IOException;
import java.util.Iterator;

import javax.naming.OperationNotSupportedException;

public interface Dataset
{
    public void addData(Data data) throws IOException, OperationNotSupportedException;
    public Data getData(int index) throws IOException;
    public int size();
    public void remove(int index) throws IOException, OperationNotSupportedException;
    public Iterator<Data> iter() throws IOException;
    public void close() throws IOException;
    public void closeWrite() throws IOException;
    public void closeRead() throws IOException;
}