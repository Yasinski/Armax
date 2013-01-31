package third.facade;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class HumanExistsException extends Exception{
	private HumanExistsException humanExistsException;

	public HumanExistsException() {
	}

	public HumanExistsException(String message) {
		super(message);
	}

	public HumanExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public HumanExistsException(Throwable cause) {
		super(cause);
	}
}
