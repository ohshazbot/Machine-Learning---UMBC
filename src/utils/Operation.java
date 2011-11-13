package utils;

import java.io.IOException;
import java.util.List;

public interface Operation {
	
	public void validateArgs(String[] args) throws IOException;
	public Result runOp(List<Dataset> training, Dataset validation) throws IOException, Exception;
}
