package utils;

public class Result {
	public double total=0;
	public double success=0;
	public double failure=0;

	public double successRate() 
	{
		return success*1.0/total;
	}

	public double failureRate()
	{
		return failure*1.0/total;
	}

	public static Result add(Result r1, Result r2)
	{
		Result toRet = new Result();
		toRet.total=r1.total+r2.total;
		toRet.success=r1.success+r2.success;
		toRet.failure=r1.failure+r2.failure;
		return toRet;
	}

	public static Result avg(Result ...r)
	{
		Result toRet = new Result();
		for (int i = 0; i < r.length; i++)
		{
			toRet.total+=r[i].total;
			toRet.success+=r[i].success;
			toRet.failure+=r[i].failure;
		}
		toRet.total = toRet.total/r.length;
		toRet.success = toRet.success/r.length;
		toRet.failure = toRet.failure/r.length;
		return toRet;
	}

	public void addToResult(boolean valid) {
		total++;
		if (valid)
			success++;
		else
			failure++;
	}

	public String toString()
	{
		return Double.toString(successRate()) + "% Success rate based on " + success + " good and " + failure + " bad total of " + total;
	}
}
