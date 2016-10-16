import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OddEven_SGDHelper implements SGD_helper<String, ArrayList<String>, String> {

	@Override
	public HashMap<ArrayList<String>, Float> Phi(String x, String y) {
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

	@Override
	public List<String> getPossibleOutputs(String x) {
		ArrayList<String> list = new ArrayList<>();
		list.add("Even");
		list.add("Oneven");
		return list;
	}
	
	
	
	

	

}
