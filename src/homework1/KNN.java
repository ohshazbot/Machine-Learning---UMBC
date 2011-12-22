package homework1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.Data;
import utils.DataDataIterator;
import utils.Dataset;
import utils.Operation;
import utils.Result;

public class KNN implements Operation {
	private enum Mode {ITER};
	private enum Distance {EUCLID};
	private enum Classification {AVERAGE, WEIGHTED};

	Distance dist = Distance.EUCLID;
	Mode mode = Mode.ITER;
	Classification classType = Classification.AVERAGE;
	int k= 4;

	@Override
	public Result runOp(List<Dataset> training, Dataset validation) throws Exception {
		if (mode.equals(Mode.ITER))
			return iter(training, validation);
		else throw new UnsupportedOperationException();
	}

	private Result iter(List<Dataset> training, Dataset validation) throws IOException {
		Iterator<Data> iter = validation.iter();
		Result toRet = new Result();
		List<Data> dataList = new LinkedList<Data>();
		List<Double> distList = new LinkedList<Double>();
		while (iter.hasNext())
		{
			dataList.clear();
			distList.clear();
			double maxDist = 0;
			Data d = iter.next();
			Iterator<Data> dataIter = new DataDataIterator(training);
			while (dataIter.hasNext())
			{
				Data train = dataIter.next();
				double distance = computeDistance(d, train);
				if (dataList.size() < k)
				{
					dataList.add(train);
					distList.add(distance);
					if (distance > maxDist)
						maxDist = distance;
				}
				else
				{
					if (distance < maxDist)
					{
						double newMax = 0;
						for (int j = 0; j < dataList.size(); j++)
						{
							if (distList.get(j).doubleValue() == maxDist)
							{
								distList.remove(j);
								dataList.remove(j);
								j--;
							}
							else if (distList.get(j) > newMax)
								newMax = distList.get(j);
						}
						maxDist = Math.max(newMax, distance);
						dataList.add(train);
						distList.add(distance);
					}
				}
			}

			String classification = determineClassification(dataList, distList);
			boolean valid = classification.equals(d.getClassification());
			toRet.addToResult(valid);
		}

		return toRet;
	}

	private String determineClassification(List<Data> dataList, List<Double> distList) {
		if (classType.equals(Classification.AVERAGE))
		{
			Map<String, Integer> counts = new HashMap<String, Integer>();
			int max = 0;
			String maxClass = null;
			for (Data dat : dataList)
			{
				Integer count = counts.get(dat.getClassification());
				if (count == null)
				{
					count = new Integer(1);
				} else
					count = count+1;
				if (count > max)
				{
					max = count;
					maxClass = dat.getClassification();
				}
				counts.put(dat.getClassification(), count);
			}
			return maxClass;
		} else if (classType.equals(Classification.WEIGHTED))
		{
			Map<String, Double> counts = new HashMap<String, Double>();
			double max = 0;
			String maxClass = null;
			for (int i = 0; i < dataList.size(); i++)
			{
				Data dat = dataList.get(i);
				Double count = counts.get(dat.getClassification());
				if (count == null)
				{
					count = 1/distList.get(i);
				} else
					count = count+1/distList.get(i);
				if (count > max)
				{
					max = count;
					maxClass = dat.getClassification();
				}
				counts.put(dat.getClassification(), count);
			}
			return maxClass;

		} else
			throw new UnsupportedOperationException();
	}

	private double computeDistance(Data d, Data train) {
		if (dist.equals(Distance.EUCLID))
		{
			double tot = 0;
			for (int i = 0; i < d.size(); i++)
				tot+=Math.pow(d.get(i) - train.get(i), 2);
			tot = Math.sqrt(tot);
			return tot;
		} else
			throw new UnsupportedOperationException();
	}

	@Override
	public void validateArgs(String[] args) throws IOException {
	    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		if (args.length > 0)
			k = Integer.parseInt(args[0]);
		else
		{
			System.out.println("K size?");
			k = Integer.parseInt(stdin.readLine());
		}
		
		String m;
		if (args.length > 1)
		{
			m = args[1];
		} else
		{
			System.out.println("Mode? [ITER]");
			m = stdin.readLine();
		}
		if (m.equalsIgnoreCase("iterate"))
			mode = Mode.ITER;
		else
			mode = Mode.ITER;

		String d;
		if (args.length > 2)
		{
			d = args[2];
		} else
		{
			System.out.println("Distance? [EUCLID]");
			d = stdin.readLine();
		}
		if (d.equalsIgnoreCase("euclid"))
			dist = Distance.EUCLID;
		else
			dist = Distance.EUCLID;

		
		String n;
		if (args.length > 3)
		{
			n = args[3];
		} else
		{
			System.out.println("Neighbor average? [AVERAGE]");
			n = stdin.readLine();
		}
		if (n.equalsIgnoreCase("weighted"))
			classType = Classification.WEIGHTED;
		else
			classType = Classification.AVERAGE;
	}

}
