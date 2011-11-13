package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class saveFolds implements Operation {
	File path;
	int last = 0;
	
	@Override
	public void validateArgs(String[] args) throws IOException {
	    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	    if (args.length > 0)
	    	path = new File(args[0]);
	    else
	    {
			System.out.println("outDir?");
			path = new File(stdin.readLine());
	    }
	    if (!path.exists())
	    	path.mkdir();
	}

	@Override
	public Result runOp(List<Dataset> training, Dataset validation)
			throws IOException, Exception {
		File newFile = new File(path,Integer.toString(last));
		newFile.createNewFile();
		FileDataset fd = new FileDataset(newFile);
		Iterator<Data> iter = validation.iter();
		while (iter.hasNext())
		{
			fd.addData(iter.next());
		}
		fd.close();
		last++;
		return new Result();
	}

}
