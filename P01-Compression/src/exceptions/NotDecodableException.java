package exceptions;

public class NotDecodableException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotDecodableException()
	{
		
	}
	
	public NotDecodableException(String message)
	{
		super(message);
	}
	
}
