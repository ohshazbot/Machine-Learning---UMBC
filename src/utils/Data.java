package utils;

import java.util.ArrayList;
import java.util.List;

public class Data {
    List<Double> elements;
    String classification;
   
    public Data (String line)
    {
    	String[] pieces = line.split("[,;]");
    	classification = pieces[pieces.length-1];
    	elements = new ArrayList<Double>();
    	for (int i = 0; i < pieces.length-1; i++)
    	{
    		elements.add(Double.parseDouble(pieces[i]));
    	}
    }
    
    public int size()
    {
    	return elements.size();
    }
    
    public Data (String classification, double ...elements)
    {
        this.classification = classification;
        this.elements = new ArrayList<Double>();
        for (int i = 0; i < elements.length; i++)
        	this.elements.add(elements[i]);
    }
    
    @Override
    public boolean equals(Object other)
    {
    	if (other instanceof Data)
    		return elements.equals(((Data)other).elements) && classification.equals(((Data)other).classification);
    	return false;
    }

    public double get (int i)
    {
        return elements.get(i);
    }

    public String getClassification()
    {
        return classification;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Double s : elements)
        {
            sb.append(s);
            sb.append(',');
        }
        sb.append(classification);
        return sb.toString();
    }

	public static Data newData(String line) {
		Data toRet = new Data(line);
		if (toRet.size() > 0 && !toRet.getClassification().equalsIgnoreCase(""))
			return toRet;
		return null;
	}
}