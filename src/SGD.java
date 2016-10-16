import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

/**
* Generic Java Class for Stochastic Gradient Descent Algorithm
*
* @author  Coenen Pieter-Jan
* @version 1.1
* @since   2016-10-16
*/

public class SGD<I, M, O> {
	
	public SGD(Phi<I, M, O> phi, O[] possibleValues){
		this.setPhi(phi);
		this.setPossibleValues(possibleValues);
	}
	
	public HashMap<M, Float> run(ArrayList<TrainingExample<I, O>> examples, int T, float eta){
		HashMap<M, Float> w = this.getW();
		for(int i = 0; i < T; i++){
			// random shuffle
			long seed = System.nanoTime();
			Collections.shuffle(examples, new Random(seed));
			for(TrainingExample<I, O> example : examples){
				Entry<Tuple<I, O>, Float> y_tilde = this.getMaxScore(w, example.getX(), example.getY());
				updateW(eta, w, example, y_tilde);	
			}
		}
		return w;
	}
	
	private void updateW(float eta, HashMap<M, Float> w, TrainingExample<I, O> example,
			Entry<Tuple<I, O>, Float> y_tilde) {
		HashMap<M,Float> phi_y_tilde = phi.getScore(example.getX(), y_tilde.getKey().gety());
		HashMap<M,Float> phi_y = phi.getScore(example.getX(), example.getY());
		for(Entry<M, Float> entry : phi_y_tilde.entrySet()){
			float actualValue = 0;
			if(phi_y.containsKey(entry.getKey())){
				actualValue = phi_y.get(entry.getKey());
			}
			float newValue = eta * (actualValue - entry.getValue());
			w.put(entry.getKey(), w.get(entry.getKey()) + newValue);
		}	
		for(Entry<M, Float> entry : phi_y.entrySet()){
			float predictedValue = 0;
			if(phi_y_tilde.containsKey(entry.getKey())){
				predictedValue = phi_y_tilde.get(entry.getKey());
			}
			float newValue = eta * (entry.getValue() - predictedValue);
			w.put(entry.getKey(), w.get(entry.getKey()) + newValue);
		}
	}
	
	public O predict(I data){
		HashMap<M, Float> w = this.getW();
		Entry<Tuple<I, O>, Float> y_tilde = this.getMaxScore(w, data);
		return y_tilde.getKey().gety();
		
	}
	
	private float totalScore(HashMap<M, Float> w, O y_accent, I x, O y){
		if(y == null){
			return score(w, y_accent, x);
		} else {
			return score(w, y_accent, x) + cost(y, y_accent);
		}
		
	}
	
	private float score(HashMap<M, Float> w, O Y_accent, I X){
		Phi<I, M, O> phi = this.getPhi();
		float score = 0;
		HashMap<M, Float> phi_value = phi.getScore(X, Y_accent);
		for(Entry<M, Float> entry : phi_value.entrySet()){
			this.prepareW(w, entry.getKey());
			score += w.get(entry.getKey()) * entry.getValue();
		}
		return score;
				
	}
	
	private void prepareW(HashMap<M, Float> w, M key){
		if(!w.containsKey(key)){
			w.put(key, (float) 0);
		}
	}
	
	private float cost(O y, O y_accent){
		if(y.equals(y_accent)){
			return 0;
		}
		return 1;
	}
	
	private HashMap<Tuple<I, O>, Float> getAllScores(HashMap<M, Float> w, I x, O y){
		HashMap<Tuple<I, O>, Float> result = new HashMap<>();
		for(O y_accent : getPossibleValues()){
			Tuple<I, O> tuple = new Tuple<I, O>(x, y_accent);
			result.put(tuple, totalScore(w, y_accent, x, y));
		}
		return result;
	}
	
	private Entry<Tuple<I, O>, Float> getMaxScore(HashMap<M, Float> w, I x, O y){
		HashMap<Tuple<I, O>, Float> allScores =  getAllScores(w, x, y);
		float max = Float.NEGATIVE_INFINITY;
		Entry<Tuple<I, O>, Float> biggest = null;
		for(Entry<Tuple<I, O>, Float> entry : allScores.entrySet()){
			if(entry.getValue() > max){
				max = entry.getValue();
				biggest = entry;
			}
		}
		return biggest;
	}
	
	private Entry<Tuple<I, O>, Float> getMaxScore(HashMap<M, Float> w, I x){
		return getMaxScore(w, x, null);
	}
	
	private O[] getPossibleValues() {
		return possibleValues;
	}
	private void setPossibleValues(O[] possibleValues) {
		this.possibleValues = possibleValues;
	}
	
	private O[] possibleValues;
	
	private HashMap<M, Float> getW() {
		return this.w;
	}

	private HashMap<M, Float> w = new HashMap<>();
	
	private Phi<I, M, O> getPhi() {
		return phi;
	}
	private void setPhi(Phi<I, M, O> phi) {
		this.phi = phi;
	}
	
	private Phi<I, M, O> phi;
}
