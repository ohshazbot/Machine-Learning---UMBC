package utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import org.junit.Test;

public class DatasetTest {

	@Test
	public void testList() throws IOException, OperationNotSupportedException {
		Dataset ds = new ListDataset();
		test(ds);
	}

	@Test
	public void testFile() throws IOException, OperationNotSupportedException
	{
		File testFile = new File("~/tmp");
		Dataset ds = new FileDataset(testFile,1);
		test(ds);
	}

	public void test(Dataset ds) throws IOException, OperationNotSupportedException
	{
		Data d1, d2, d3, d4;
		d1 = new Data("1.0,2.0,3.0,fish");
		d2 = new Data("1.1,2.0,3.0,caramel");
		d3 = new Data("1.1,2.2,3.0,pants");
		d4 = new Data("1.1,2.2,3.3,tiramasu");

		ds.addData(d1);
		assertEquals(ds.size(), 1);

		ds.addData(d2);
		ds.addData(d3);

		assertEquals(ds.size(), 3);
		assertEquals(ds.getData(0), (d1));
		ds.remove(1);
		assertEquals(ds.size(), 2);

		ds.addData(d4);
		assertEquals(ds.size(),3);
		assertEquals(ds.getData(2), d4);
		ds.remove(0);
		ds.remove(0);
		ds.remove(0);
		assertEquals(ds.size(), 0);
		ds.remove(0);
	}

}
