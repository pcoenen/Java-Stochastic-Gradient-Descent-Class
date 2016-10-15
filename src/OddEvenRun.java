import java.util.ArrayList;
import java.util.HashMap;

public class OddEvenRun {

	public static void main(String[] args) {
		Phi phi = new OddEven();
		String[] possibilitys = { "Even", "Oneven"};
		SGD sgd = new SGD(phi, possibilitys);
		ArrayList<TrainingExample> list = new ArrayList<>();
		list.add(new TrainingExample("five", "Oneven"));
		list.add(new TrainingExample("thirty one", "Oneven"));
		list.add(new TrainingExample("forty nine", "Oneven"));
		list.add(new TrainingExample("fifty two", "Even"));
		list.add(new TrainingExample("eighty three", "Oneven"));
		list.add(new TrainingExample("eighty four", "Even"));
		list.add(new TrainingExample("eighty six", "Even"));
		HashMap<ArrayList<String>, Float> result = sgd.run(list, 10, (float) 0.1);
		//System.out.println(result.toString());
		System.out.println(sgd.predict("forty three"));
	}

}
