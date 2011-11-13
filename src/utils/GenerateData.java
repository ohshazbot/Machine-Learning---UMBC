package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateData {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File fparent = new File("data/");
		Random r = new Random();
		for (int i = 0; i < 4; i++)
		{
			File f = new File(fparent, Integer.toString(i));
			FileWriter writer = new FileWriter(f);
			for (int j = 0; j < 100; j++)
			{
				int value = r.nextInt(1000);
				String s;
				if (value < 333)
					s = "low";
				else if (value < 666)
					s = "mid";
				else
					s = "high";
				writer.append(Integer.toString(value)+","+s+"\n");
			}
			writer.close();
		}
	}

}
