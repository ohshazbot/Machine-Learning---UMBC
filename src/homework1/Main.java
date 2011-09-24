package homework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utils.Data;
import utils.Dataset;
import utils.FileDataset;
import utils.ListDataset;

public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File f = new File(args[0]);
		if (!f.exists())
			throw new Exception("Input file does not exist");
		int folds = Integer.parseInt(args[1]);
		List<Dataset> ds = foldFile(f, folds);

	}

	public static List<Dataset> foldFile(File f, int folds) throws IOException {
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
			addData(datasets, new Data(line));
		}

		// TODO Auto-generated method stub
		return null;
	}

	private static void addData(List<Dataset> datasets, Data data) throws IOException {
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
