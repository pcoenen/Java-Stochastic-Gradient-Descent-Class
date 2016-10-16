import java.util.HashMap;

public interface Phi<I, M, O> {
	/**
	 * 
	 * @param 	x
	 * 			The input
	 * @param 	y
	 * 			The (possible) output for this value
	 * @return	The score of this input/output combination
	 */
	public HashMap<M, Float> getScore(I x, O y);
}
