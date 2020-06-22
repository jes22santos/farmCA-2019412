package ie.cct.farmCA2019412;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ie.cct.farmCA2019412*")
public class FarmCa2019412Application {

	public static void main(String[] args) {
		SpringApplication.run(FarmCa2019412Application.class, args);
	}

}
