package ie.cct.farmCA2019412;

public class Animal {
	
	// declaring attributes of animal, private to be accessed only inside the class
	private String type;
	private Float weight;
	
	
	//Default constructor
	public Animal() {
		
	}
	
	// Constructor passing only weight as parameter 	
	public Animal(String type, Float weight) {
		super();
		this.type = type;
		this.weight = weight;
	}
	
	public Animal(String type) {
		super();
		this.type = type;
	}
	
	//Providing getter and setter to acceded the variable outside the class
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Float getWeight() {
		return weight;
	}
	
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	
}

