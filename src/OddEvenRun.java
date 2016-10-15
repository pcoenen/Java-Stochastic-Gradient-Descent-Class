import java.util.ArrayList;
import java.util.HashMap;

public class OddEvenRun {

	public static void main(String[] args) {
		Phi<String, ArrayList<String>, String> phi = new OddEven();
		String[] possibilitys = { "Even", "Oneven"};
		SGD<String, ArrayList<String>, String> sgd = new SGD<>(phi, possibilitys);
		ArrayList<TrainingExample<String,String>> list = new ArrayList<>();
		list.add(new TrainingExample<String,String>("five", "Oneven"));
		list.add(new TrainingExample<String,String>("thirty one", "Oneven"));
		list.add(new TrainingExample<String,String>("forty nine", "Oneven"));
		list.add(new TrainingExample<String,String>("fifty two", "Even"));
		list.add(new TrainingExample<String,String>("eighty three", "Oneven"));
		list.add(new TrainingExample<String,String>("eighty four", "Even"));
		list.add(new TrainingExample<String,String>("eighty six", "Even"));
		HashMap<ArrayList<String>, Float> result = sgd.run(list, 10, (float) 0.1);
		//System.out.println(result.toString());
		System.out.println(sgd.predict("forty three"));
	}

}
