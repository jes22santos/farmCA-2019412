package ie.cct.farmCA2019412;

public class ReturnResults {

	private String message;
	private Float value;
	
	
	public ReturnResults(String message, Float value) {
		super();
		this.message = message;
		this.value = value;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	
	
	

	
	
}
