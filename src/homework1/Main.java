package homework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.naming.OperationNotSupportedException;

import utils.Data;
import utils.Dataset;
import utils.FileDataset;
import utils.ListDataset;
import utils.Operation;
import utils.Result;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

	    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		File f;
		if (args.length <= 0)
		{
			System.out.println("Filename");
			f = new File(stdin.readLine());
		}
		else
			f = new File(args[0]);
		if (!f.exists())
			throw new IOException("Input file " + f.getCanonicalPath()+ " does not exist");
		Operation op;
		if (args.length > 1)
			op = (Operation) Class.forName(args[1]).newInstance();
		else
		{
			System.out.println("Operation");
			op = (Operation) Class.forName(stdin.readLine()).newInstance();
		}
//		Operation op = new KNN();		
		String[] newArgs = new String[Math.max(args.length-3, 0)];
		for (int i = 0; i < newArgs.length; i++)
			newArgs[i] = args[i+2];

		op.validateArgs(newArgs);

		List<Dataset> ds;
		if (f.isFile())
		{
			int folds;
			if (args.length > 2)
				folds = Integer.parseInt(args[2]);
			else
			{
				System.out.println("Number of folds");
				folds = Integer.parseInt(stdin.readLine());
			}
			ds = foldFile(f, folds);
		}
		else if (f.isDirectory())
			ds = readFiles(f);
		else
			throw new Exception("What am I if not a file, nor a folder");
		List<Result> results = new LinkedList<Result>(); 
		for (int i = 0; i < ds.size(); i++)
		{
			List<Dataset> training = new LinkedList<Dataset>();
			Dataset validation = null;
			for (int j = 0; j < ds.size(); j++)
				if (j == i)
					validation = ds.get(i);
				else
					training.add(ds.get(j));
			results.add(op.runOp(training, validation));	
		}
		for (Result r : results)
			System.out.println(r);
	}

	private static List<Dataset> readFiles(File f) throws IOException {
		List<Dataset> toRet = new LinkedList<Dataset>();
		for (File sf : f.listFiles())
			toRet.add(new FileDataset(sf));
		return toRet;
	}

	public static List<Dataset> foldFile(File f, int folds) throws IOException, OperationNotSupportedException {
		boolean fileSplit = f.length() > Runtime.getRuntime().maxMemory() * .75;
		List<Dataset> datasets = new ArrayList<Dataset>();
		for (int i = 0; i < folds; i++)
			if (fileSplit)
				datasets.add(new FileDataset(f, i));
			else
				datasets.add(new ListDataset());

		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			Data nData = Data.newData(line);
			if (nData != null)
				addData(datasets, nData);
		}
		return datasets;
	}

	private static void addData(List<Dataset> datasets, Data data) throws IOException, OperationNotSupportedException {
		int biggest = 0;
		for (Dataset ds : datasets)
			if (biggest < ds.size())
				biggest = ds.size();

				int tot = 0;
				List<Integer> breaks = new LinkedList<Integer>();
				for (Dataset ds : datasets)
				{
					int prob = biggest-ds.size()+1;
					tot+=prob;
					breaks.add(tot);
				}

				Random r = new Random();
				int pick = r.nextInt(tot);
				for (int i = 0; i < breaks.size(); i++)
					if (pick < breaks.get(i))
					{
						datasets.get(i).addData(data);
						break;
					}
	}
}
