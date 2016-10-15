
public class Tuple<I, O> {
	
	public Tuple(I x, O y){
		this.setx(x);
		this.sety(y);
	}	
	
	public boolean equals(Tuple<I, O> tuple2){
		return tuple2.getx().equals(this.getx()) && tuple2.gety().equals(this.gety());
	}
	
	public I getx() {
		return x;
	}
	private void setx(I x) {
		this.x = x;
	}
	
	private I x;
	
	public O gety() {
		return y;
	}
	private void sety(O y) {
		this.y = y;
	}

	private O y;
	
	
}
