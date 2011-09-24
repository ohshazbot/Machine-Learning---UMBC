package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataTest {

	@Test
	public void test() {
		String source = "1.0,2.2,3.4,4.6,fish";
		Data d1 = new Data(source);
		Data d2 = new Data(d1.toString());
		assertEquals(source, d1.toString());
		assertEquals(d1,d2);
		assertEquals(source, d2.toString());
	}

}
