package pk.ie.proj.exceptions;


public class ParameterException
	extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4099032758240150573L;
	/*
	 * Dostępne numery błędów dla ParameterException
	 */
	public static final int UNKNOWN_ERROR = 0; // Nieznany
	public static final int CANNOT_CREATE_ERROR = 1;
	public static final int PARAMETER_FILE_STRUCTURE_ERROR = 2;
	public static final int PARAMETER_FILE_ERROR = 3;
	public static final int PARAMETER_CLASS_ERROR = 4;
	
	/*
	 * Zgłoszenie błędu o wyznaczonym numerze indeksowym
	 */
	public ParameterException(int errorIndex)
	{
		super(errorIndex);
	}

	/*
	 * Zgłoszenie błędu o wybranym numerze i z określonym argumentem
	 */
	public ParameterException(int errorIndex, String arg)
	{
		super(errorIndex, arg);
	}

	/*
	 * Zgłoszenie błędu o wybranym nuemrze i z określonymi argumentami
	 */
	public ParameterException(int errorIndex, String [] args)
	{
		super(errorIndex, args);
	}

	public ParameterException(String errorDescription)
	{
		super(errorDescription);
	}

	// zwracanie opisu błędu
	public String toString()
	{
		return "ParameterException: " + super.toString();
	}

	public String getMessage()
	{
		return toString();
	}

	public int getBaseCode() {
		return 500;
	}

};
