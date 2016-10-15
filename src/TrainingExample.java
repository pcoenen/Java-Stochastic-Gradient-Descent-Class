

public class TrainingExample<I, O> {
	public TrainingExample(I X, O Y){
		setX(X);
		setY(Y);
	}
	public I getX() {
		return X;
	}
	private void setX(I x) {
		X = x;
	}
	private I X;
	
	public O getY() {
		return Y;
	}
	private void setY(O y) {
		Y = y;
	}
	
	private O Y;

}
