package ie.cct.farmCA2019412;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FarmCAController {
	
	
	private List<Animal> animals;
	private Float numCow = 0f;
	private Float numPig = 0f;
	private Float numChi = 0f;
	
	public FarmCAController() {
		
		animals = new ArrayList<Animal>();
	}
	
	// creating Post for url "localhost:8080/add-animal"
	@PostMapping("add-animal")
	public SuccessResponse addAnimal(@RequestBody Animal animal) {
		
		if(!(animal.getType().contentEquals("Cow")) && !(animal.getType().contentEquals("Pig"))&&!(animal.getType().contentEquals("Chicken"))){
			throw new BadRequestException("This animal type is not valid for this Farm");
		} else {
			animals.add(animal);
			return new SuccessResponse("A " + animal.getType() + " added.");
		}
	}
//----------------------------------------------------------------------------//
	//Method to bring the average from other method depends on the parameter
	
	//GET http://localhost:8080/average-weight?animal=Pig
	//GET http://localhost:8080/average-weight?animal=Cow
	//GET http://localhost:8080/average-weight?animal=Chicken
	
	@GetMapping("average-weight")
	public ReturnResults averageWeight(@RequestParam(required = true) String animal) {
		
		ReturnResults result = null;
		
		if (animals.isEmpty()) {
			throw new NoContentException("Farm is empty for now.");
			//throw new NotFoundException("Farm is empty for now.");
		}	
		else if(animal.equals("Pig")){
		
		result= new ReturnResults ("Pig average weight = " , averagePig());
		}
		else if(animal.equals("Cow")){
			
			result= new ReturnResults ("Cow average weight = " , averageCow());
		}
		else if(animal.equals("Chicken")){
			
			result= new ReturnResults ("Chicken average weight = " , averageChicken());
		}
		else if (!(animal.equals("Chicken")) && !(animal.equals("Pig")) && !(animal.equals("Cow"))) {
			throw new BadRequestException("Animal type invalid.");
		}
		
		return result;
	}
//----------------------------------------------------------------------------------//
	//Method to check how many animal can be sold, one endpoint for each animal
	// GET http://localhost:8080/animals-tobesold?animal=Chicken
	// GET http://localhost:8080/animals-tobesold?animal=Cow
	// GET http://localhost:8080/animals-tobesold?animal=Pig
	
	@GetMapping("animals-tobesold")
	public ReturnResults animalsToBeSold(@RequestParam(required = true) String animal) {
		
		ReturnResults result = null;
		
		// calling the method to get the number of animal good to be sold
		numCow = numOfCow();
		numPig = numOfPig();
		numChi = numOfChi();
		
		if (animals.isEmpty()) {
			throw new NoContentException("Farm is empty for now.");
			//throw new NotFoundException("Farm is empty for now.");
		}
		else if ((animal.equals("Cow")) && (numCow==0)) {
			throw new NotFoundException("No Cows found to be sold");
		}
		else if((animal.equals("Cow")) && !(numCow==0)) {	
		
			result = new ReturnResults ("The number of Cows to be sold is ", numCow) ;
		}
		else if((animal.equals("Pig")) && (numPig==0)) {	
			throw new NotFoundException("No Pig found to be sold");
		}
		else if((animal.equals("Pig")) && !(numPig==0)){	
			
			result = new ReturnResults ("The number of Pigs to be sold is ", numPig) ;
		}
		else if((animal.equals("Chicken")) && (numChi==0)) {	
			throw new NotFoundException("No Chicken found to be sold");
		}
		else if((animal.equals("Chicken")) && !(numChi==0)) {	
			
			result = new ReturnResults ("The number of Chicken to be sold is ", numChi) ;
		}
		else if (!(animal.equals("Chicken")) && !(animal.equals("Pig")) && !(animal.equals("Cow"))) {
			throw new BadRequestException("Animal type invalid.");
		}
		
		return result;
	}
		
	
//-------------------------------------------------------------------------//	
	// Method to get the Current Value with or without parameters
	//GET http://localhost:8080/currentValue?cow=400&chicken=100&pig=300
	//GET http://localhost:8080/currentValue
	
	@GetMapping("currentValue")
	public ReturnResults currentValue(Float cow, Float chicken, Float pig) {
		
		Float value = 0.0f;
		
		//default animal value, in case the user not pass all parameters
		Float valueCow=500f;
		Float valueChicken=5f;
		Float valuePig=250f;
		
		// calling the method to get the number of animal good to be sold
		numCow = numOfCow();
		numPig = numOfPig();
		numChi = numOfChi();
		
		if (animals.isEmpty()) {
			throw new NoContentException("Farm is empty for now.");
			//throw new NotFoundException("Farm is empty for now.");
		}
		else if((cow==null) && (chicken==null) && (pig==null)) {
			value = ((numCow*valueCow) + (numPig*valuePig) + (numChi*valueChicken));
		}
		else if(!(cow==null) && !(chicken==null) && !(pig==null)) {
			value = ((numCow*cow) + (numPig*pig) + (numChi*chicken));
		}
		else {
			throw new BadRequestException("You need to set 3 values, or nothing");
		}
		
		
		return new ReturnResults ("Current Value is: ", value);
	}

//------------------------------------------------------------------------------//
	/*Methods to check how many animals can be sold, and 
	 *return the number of animal. one method per animal type 
	 */
	public Float numOfCow() {
		
		Float numCow=0f;
		for (Animal ani: this.animals) {
			
			if ((ani.getType().contentEquals("Cow")) && (ani.getWeight() >= 300)) {
				numCow ++;
			}	
		}	
		return numCow;
	}
//-----------------------------------------------------------------------------//	
	
	public Float numOfPig() {
		
		Float numPig=0f;
		for (Animal ani: this.animals) {
			
			if ((ani.getType().contentEquals("Pig")) && (ani.getWeight() >= 100)) {
				numPig ++;
			}	
		}	
		return numPig;
	}
//-----------------------------------------------------------------------------//	
	
	public Float numOfChi() {
		
		Float numChi=0f;
		for (Animal ani: this.animals) {
			
			if ((ani.getType().contentEquals("Chicken")) && (ani.getWeight() >= 0.5)) {
				numChi ++;
			}	
		}	
		return numChi;
	}
//-----------------------------------------------------------------------------//
	/*Methods to calculate the average weight of each animal, and 
	 *return the average, one method per animal type 
	 */
	
	public Float averageCow() {
		
		int totalCow=0;
		Float weightCow=0f;
		
		for (Animal ani: animals) {
			if (ani.getType().contentEquals("Cow")) {
				weightCow += ani.getWeight();
				totalCow++;
			}
		}
		weightCow = weightCow/totalCow;
		return weightCow;
	}
	
	public Float averagePig() {
		
		int totalPig=0;
		Float weightPig=0f;
		
		for (Animal ani: animals) {
			if (ani.getType().contentEquals("Pig")) {
				weightPig += ani.getWeight();
				totalPig++;
			}
		}
		weightPig = weightPig/totalPig;
		return weightPig;
	}
	
	public Float averageChicken() {
		
		int totalChi=0;
		Float weightChi=0f;
		
		for (Animal ani: animals) {
			if (ani.getType().contentEquals("Chicken")) {
				weightChi += ani.getWeight();
				totalChi++;
			}
		}
		weightChi = weightChi/totalChi;
		return weightChi;
	}
}

	