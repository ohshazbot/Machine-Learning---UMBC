package homework2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GenerateRandomCrap {
	enum Size {
		SMALL, BIG;
	}

	enum Conds {
		PERFECT, IRRELEVANT, ATTR_NOISE, LABEL_NOISE;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println(Boolean.toString(true));
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Folder");
		File f = new File(stdin.readLine());
		if (!f.isDirectory())
		{
			System.out.println("Needs to be an existant folder");
			return;
		}

		for (Size s : Size.values())
		{
			for (Conds c: Conds.values())
			{
				createFile(new File(f, s.name()+"_"+c.name()+".arff"), s, c);
			}
		}

	}

	private static void createFile(File file, Size s, Conds c) throws IOException {
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.append(makeHeader(s, c));
		int count;
		if (s.equals(Size.SMALL))
			count = 60;
		else
			count = 2000;
		boolean[] a = new boolean[5];
		boolean result;
		Random r = new Random();
		for (int i = 0; i < count; i++)
		{
			for (int b = 0; b < 5; b++)
			{
				a[b] = r.nextBoolean();
			}
			if (c.equals(Conds.IRRELEVANT))
				result = irrelevantClassify(a);
			else
				result = classify(a);

			switch (c)
			{
			case ATTR_NOISE:
				for (int b = 0; b < 5; b++)
					if (r.nextInt(100) < 8)
						a[b] = !a[b];
			case LABEL_NOISE:
				if (r.nextInt(100) < 8)
					result = !result;
			}
			for (int b = 0; b < 5; b++)
			{
//			fw.append((a[b]? "1":"0"));
			fw.append(Boolean.toString(a[b]));
			fw.append(",");
			}
			fw.append(Boolean.toString(result));
//			fw.append((result? "1":"0"));
			fw.append("\n");
		}
		fw.close();
	}

	private static boolean irrelevantClassify(boolean[] a) {
		return a[0] ^ ((a[1] || !a[4]) || !(a[3] && (a[4] ^ a[1])));
	}

	private static boolean classify(boolean[] a) {
		return a[0] ^ ((a[1] && (a[2] || a[4])) || !(a[3] && (a[4] ^ a[1])));
	}

	public static String makeHeader(Size size, Conds condition)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("% Random crap database- size ");
		sb.append(size.name());
		sb.append(" condition ");
		sb.append(condition.name());
		sb.append("\n@RELATION\tbinaryCrap\n\n@ATTRIBUTE\tA1\t{true,false}\n@ATTRIBUTE\tA2\t{true,false}\n@ATTRIBUTE\tA3\t{true,false}\n@ATTRIBUTE\tA4\t{true,false}\n@ATTRIBUTE\tA5\t{true,false}\n@ATTRIBUTE\tclass\t{true,false}\n\n@DATA\n");
				return sb.toString();

	}
}
