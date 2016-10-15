
public class Tuple {
	
	public Tuple(String x, String y){
		this.setx(x);
		this.sety(y);
	}	
	
	public boolean equals(Tuple tuple2){
		return tuple2.getx().equals(this.getx()) && tuple2.gety().equals(this.gety());
	}
	
	public String getx() {
		return x;
	}
	private void setx(String x) {
		this.x = x;
	}
	
	private String x;
	
	public String gety() {
		return y;
	}
	private void sety(String y) {
		this.y = y;
	}

	private String y;
	
	
}
