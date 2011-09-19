package homework1;

import java.io.File;

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
		Dataset ds = Dataset.foldFile(f, folds);
		
	}

	private static Dataset foldFile(File f, int folds) {
		boolean fileSplit = f.length() > Runtime.getRuntime().maxMemory()*.75;
		// TODO Auto-generated method stub
		return null;
	}

}
