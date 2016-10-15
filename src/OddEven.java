import java.util.ArrayList;
import java.util.HashMap;

public class OddEven implements Phi {

	@Override
	public HashMap<ArrayList<String>, Float> getScore(String x, String y) {
		HashMap<ArrayList<String>, Float> map = new HashMap<>();
		ArrayList<String> lastElement = new ArrayList<>();
		lastElement.add(y);
		lastElement.add(x.split(" ")[x.split(" ").length -1]);
		map.put(lastElement, (float) 1);
		ArrayList<String> stringY = new ArrayList<>();
		stringY.add(y);
		map.put(stringY, (float) 1);
		return map;
	}
	
	
	
	

	

}
