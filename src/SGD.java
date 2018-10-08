import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
* Generic Java Class for Stochastic Gradient Descent Algorithm
* 
* Needs as input a class that implements the interface Phi.java
* 
* To train the algorithm you need to input a list of Training Examples
*
* @author  Coenen Pieter-Jan
* @version 1.1
* @since   2016-10-16
*/

/**
 * 
 * @param <I> The type of input, probably String
 * @param <M> The type in which the algorithm needs to save the values for w
 * @param <O> The type of the output, probably String
 */
public class SGD<I, M, O> {
	
	/**
	 * 
	 * @param 	helper 	
	 * 			A class that implements the the interface Phi.java, needed to calculate the score
	 * @param 	possibleValues
	 * 			An array which contains all possible values for the output
	 */
	public SGD(SGD_helper<I, M, O> helper){
		this.setSGDHelper(helper);
	}
	
	/**
	 * 
	 * @param 	examples
	 * 			A list of TraingExamples which contain the data to train the algorithm
	 * @param 	T
	 * 			An integer value that says how many times the algorithm needs to iterate
	 * @param 	eta
	 * 			The value eta
	 * @return	The function returns the variable W that contains a HashMap with the values	
	 * 				for the trained data which can be used for further training or prediction
	 */
	public HashMap<M, Float> run(List<TrainingExample<I, O>> examples, int T, float eta){
		HashMap<M, Float> w = this.getW();
		for(int i = 0; i < T; i++){
			// random shuffle
			long seed = System.nanoTime();
			Collections.shuffle(examples, new Random(seed));
			for(TrainingExample<I, O> example : examples){
				Entry<Tuple<I, O>, Float> y_tilde = this.getMaxScore(w, example.getInputData(), example.getCorrectOutput());
				updateW(eta, w, example, y_tilde);	
			}
		}
		return w;
	}
	
	private void updateW(float eta, HashMap<M, Float> w, TrainingExample<I, O> example,
			Entry<Tuple<I, O>, Float> y_tilde) {
		HashMap<M,Float> phi_y_tilde = helper.Phi(example.getInputData(), y_tilde.getKey().gety());
		HashMap<M,Float> phi_y = helper.Phi(example.getInputData(), example.getCorrectOutput());
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
	
	/**
	 * 
	 * @param 	data
	 * 			The data for which the answer needs to be predicted.
	 * @return	It returns one of the output values that fits best for this input
	 */
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
		SGD_helper<I, M, O> helper = this.getSGDHelper();
		float score = 0;
		HashMap<M, Float> phi_value = helper.Phi(X, Y_accent);
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
		for(O y_accent : getPossibleValues(x)){
			Tuple<I, O> tuple = new Tuple<I, O>(x, y_accent);
			result.put(tuple, totalScore(w, y_accent, x, y));
		}
		return result;
	}
	
	private Entry<Tuple<I, O>, Float> getMaxScore(HashMap<M, Float> w, I x, O y){
		HashMap<Tuple<I, O>, Float> allScores =  getAllScores(w, x, y);
		float max = Float.NEGATIVE_INFINITY;
		List<Entry<Tuple<I, O>, Float>> biggest = new ArrayList<>();
		for(Entry<Tuple<I, O>, Float> entry : allScores.entrySet()){
			if(Math.abs(entry.getValue() - max) < 0.0001){
				biggest.add(entry);
			} else if(entry.getValue() > max){
				max = entry.getValue();
				biggest = new ArrayList<>();
				biggest.add(entry);
			}
		}
		long seed = System.nanoTime();
		Collections.shuffle(biggest, new Random(seed));		
		return biggest.get(0);
	}
	
	private Entry<Tuple<I, O>, Float> getMaxScore(HashMap<M, Float> w, I x){
		return getMaxScore(w, x, null);
	}
	
	private List<O> getPossibleValues(I x) {
		SGD_helper<I, M, O> helper = this.getSGDHelper();
		return helper.getPossibleOutputs(x);
	}
	
	private HashMap<M, Float> getW() {
		return this.w;
	}

	private HashMap<M, Float> w = new HashMap<>();
	
	private SGD_helper<I, M, O> getSGDHelper() {
		return helper;
	}
	private void setSGDHelper(SGD_helper<I, M, O> helper) {
		this.helper = helper;
	}
	
	private SGD_helper<I, M, O> helper;
}
