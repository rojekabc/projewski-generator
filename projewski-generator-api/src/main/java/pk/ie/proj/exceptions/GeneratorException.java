package pk.ie.proj.exceptions;


public class GeneratorException
	extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9100069763925843001L;
	/*
	 * Dostępne numery błędów dla GeneratorException
	 */
	public static final int UNKNOWN_ERROR = 0; // Nieznany
	public static final int WRONG_PARAMETER_INSTANCE_ERROR = 1;
	public static final int NULL_PARAMETER_ERROR = 2;
	public static final int PARAMETER_CONVERT_ERROR = 3;
	public static final int FILE_ALREADY_EXISTS = 4;
	public static final int FILE_WRITE_ERROR = 5;
	public static final int NOT_ALLOWED_USE_ERROR = 6;
	public static final int FILE_NOT_EXISTS = 7;
	
	/*
	 * Zgłoszenie błędu o wyznaczonym numerze indeksowym
	 */
	public GeneratorException(int errorIndex)
	{
		super(errorIndex);
	}

	/*
	 * Zgłoszenie błędu o wybranym numerze i z określonym argumentem
	 */
	public GeneratorException(int errorIndex, String arg)
	{
		super(errorIndex, arg);
	}

	/*
	 * Zgłoszenie błędu o wybranym nuemrze i z określonymi argumentami
	 */
	public GeneratorException(int errorIndex, String [] args)
	{
		super(errorIndex, args);
	}

	public GeneratorException(String errorDescription)
	{
		super(errorDescription);
	}
	
	public GeneratorException(Throwable t)
	{
		super(t);
	}

	// zwracanie opisu błędu
	public String toString()
	{
		return "GeneratorException: " + super.toString();
	}

	public String getMessage()
	{
		return toString();
	}

};
