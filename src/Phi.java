import java.util.HashMap;

public interface Phi<I, M, O> {
	public HashMap<M, Float> getScore(I x, O y);
}
