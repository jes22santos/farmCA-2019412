package ie.cct.farmCA2019412;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1858260750867037200L;
	
	public BadRequestException(String msg) {
		super(msg);
	}

}