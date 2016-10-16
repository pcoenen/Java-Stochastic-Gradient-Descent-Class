import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OddEvenRun {

	public static void main(String[] args) {
		SGD_helper<String, ArrayList<String>, String> helper = new OddEven_SGDHelper();
		SGD<String, ArrayList<String>, String> sgd = new SGD<>(helper);
		ArrayList<TrainingExample<String,String>> list = new ArrayList<>();
		list.add(new TrainingExample<String,String>("five", "Oneven"));
		list.add(new TrainingExample<String,String>("thirty one", "Oneven"));
		list.add(new TrainingExample<String,String>("forty nine", "Oneven"));
		list.add(new TrainingExample<String,String>("fifty two", "Even"));
		list.add(new TrainingExample<String,String>("eighty three", "Oneven"));
		list.add(new TrainingExample<String,String>("eighty four", "Even"));
		list.add(new TrainingExample<String,String>("eighty six", "Even"));
		HashMap<ArrayList<String>, Float> w = sgd.run(list, 10, (float) 0.1);
		//System.out.println(w.toString());
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		list.clear();
		while(reader.hasNextLine()){
			String value = reader.nextLine();
			String result = sgd.predict(value);
			System.out.println(result);
			System.out.println("Is this correct ? (yes/no)");
			String answer = reader.nextLine();
			if(answer.equals("yes")){
				list.add(new TrainingExample<String, String>(value, result));
			} else if(answer.equals("no")){
				if(result == "Oneven"){
					result = "Even";
				} else if(result == "Even"){
					result = "Oneven";
				}
				list.add(new TrainingExample<String, String>(value, result));
			}
			if(list.size() >= 5){
				w = sgd.run(list, 10, (float) 0.1);
				list.clear();
				System.out.println(w.toString());
			}
			System.out.println("Enter a number: ");
		}
		reader.close();
	}

}
