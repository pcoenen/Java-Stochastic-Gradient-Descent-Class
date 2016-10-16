import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OddEvenRun {

	public static void main(String[] args) {
		Phi<String, ArrayList<String>, String> phi = new PhiOddEven();
		String[] possibilitys = { "Even", "Oneven"};
		SGD<String, ArrayList<String>, String> sgd = new SGD<>(phi, possibilitys);
		ArrayList<TrainingExample<String,String>> list = new ArrayList<>();
		list.add(new TrainingExample<String,String>("five", "Oneven"));
		list.add(new TrainingExample<String,String>("thirty one", "Oneven"));
		HashMap<ArrayList<String>, Float> result = sgd.run(list, 10, (float) 0.1);
		System.out.println(result.toString());
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		while(reader.hasNextLine()){
			String value = reader.nextLine();
			System.out.println(sgd.predict(value));
			System.out.println("Enter a number: ");
		}
	}

}
