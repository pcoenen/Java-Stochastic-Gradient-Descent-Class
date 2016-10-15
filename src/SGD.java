import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class SGD {
	
	public SGD(Phi phi, String[] possibleValues){
		this.setPhi(phi);
		this.setPossibleValues(possibleValues);
	}
	public HashMap<ArrayList<String>, Float> run(ArrayList<TrainingExample> examples, int T, float eta){
		HashMap<ArrayList<String>, Float> w = this.getW();
		for(int i = 0; i < T; i++){
			// random shuffle
			long seed = System.nanoTime();
			Collections.shuffle(examples, new Random(seed));
			for(TrainingExample example : examples){
				Entry<Tuple, Float> y_tilde = this.getMaxScore(w, example.getX(), example.getY());
				HashMap<ArrayList<String>,Float> phi_y_tilde = phi.getScore(example.getX(), y_tilde.getKey().gety());
				HashMap<ArrayList<String>,Float> phi_y = phi.getScore(example.getX(), example.getY());
				for(Entry<ArrayList<String>, Float> entry : phi_y_tilde.entrySet()){
					float actualValue = 0;
					if(phi_y.containsKey(entry.getKey())){
						actualValue = phi_y.get(entry.getKey());
					}
					float newValue = eta * (actualValue - entry.getValue());
					w.put(entry.getKey(), w.get(entry.getKey()) + newValue);
				}	
				for(Entry<ArrayList<String>, Float> entry : phi_y.entrySet()){
					float predictedValue = 0;
					if(phi_y_tilde.containsKey(entry.getKey())){
						predictedValue = phi_y_tilde.get(entry.getKey());
					}
					float newValue = eta * (entry.getValue() - predictedValue);
					w.put(entry.getKey(), w.get(entry.getKey()) + newValue);
				}	
			}
		}
		return w;
	}
	
	public String predict(String data){
		HashMap<ArrayList<String>, Float> w = this.getW();
		Entry<Tuple, Float> y_tilde = this.getMaxScore(w, data);
		return y_tilde.getKey().gety();
		
	}
	
	private float scoreWithCost(HashMap<ArrayList<String>, Float> w, String y_accent, String x, String y){
		return score(w, y_accent, x) + cost(y, y_accent);
	}
	
	private float score(HashMap<ArrayList<String>, Float> w, String Y_accent, String X){
		Phi phi = this.getPhi();
		float score = 0;
		HashMap<ArrayList<String>, Float> phi_value = phi.getScore(X, Y_accent);
		for(Entry<ArrayList<String>, Float> entry : phi_value.entrySet()){
			this.prepareW(w, entry.getKey());
			score += w.get(entry.getKey()) * entry.getValue();
		}
		return score;
				
	}
	
	private void prepareW(HashMap<ArrayList<String>, Float> w, ArrayList<String> key){
		if(!w.containsKey(key)){
			w.put(key, (float) 0);
		}
	}
	
	private float cost(String y, String y_accent){
		if(y.equals(y_accent)){
			return 0;
		}
		return 1;
	}
	
	private HashMap<Tuple, Float> getAllScores(HashMap<ArrayList<String>, Float> w, String x, String y){
		HashMap<Tuple, Float> result = new HashMap<>();
		for(String y_accent : getAllPossibleY()){
			Tuple tuple = new Tuple(x, y_accent);
			result.put(tuple, scoreWithCost(w, y_accent, x, y));
		}
		return result;
	}
	
	private HashMap<Tuple, Float> getAllScores(HashMap<ArrayList<String>, Float> w, String x){
		HashMap<Tuple, Float> result = new HashMap<>();
		for(String y_accent : getAllPossibleY()){
			Tuple tuple = new Tuple(x, y_accent);
			result.put(tuple, score(w, y_accent, x));
		}
		return result;
	}
	
	private Entry<Tuple, Float> getMaxScore(HashMap<ArrayList<String>, Float> w, String x, String y){
		HashMap<Tuple, Float> allScores =  getAllScores(w, x, y);
		float max = Float.NEGATIVE_INFINITY;
		Entry<Tuple, Float> biggest = null;
		for(Entry<Tuple, Float> entry : allScores.entrySet()){
			if(entry.getValue() > max){
				max = entry.getValue();
				biggest = entry;
			}
		}
		return biggest;
	}
	
	private Entry<Tuple, Float> getMaxScore(HashMap<ArrayList<String>, Float> w, String x){
		HashMap<Tuple, Float> allScores =  getAllScores(w, x);
		float max = Float.NEGATIVE_INFINITY;
		Entry<Tuple, Float> biggest = null;
		for(Entry<Tuple, Float> entry : allScores.entrySet()){
			if(entry.getValue() > max){
				max = entry.getValue();
				biggest = entry;
			}
		}
		return biggest;
	}
	
	private String[] getAllPossibleY(){
		return this.possibleValues;		
	}
	
	private String[] getPossibleValues() {
		return possibleValues;
	}
	private void setPossibleValues(String[] possibleValues) {
		this.possibleValues = possibleValues;
	}
	
	private String[] possibleValues;
	
	private HashMap<ArrayList<String>, Float> getW() {
		return this.w;
	}

	private HashMap<ArrayList<String>, Float> w = new HashMap<>();
	
	private Phi getPhi() {
		return phi;
	}
	private void setPhi(Phi phi) {
		this.phi = phi;
	}
	
	private Phi phi;
}
