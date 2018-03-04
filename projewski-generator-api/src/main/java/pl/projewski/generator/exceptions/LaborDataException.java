package pl.projewski.generator.exceptions;


/*
 * Klasa błędów deskrypcji. Stanowi rozszerzenie klasy BaseException.
 */
public class LaborDataException
extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2459816270691076496L;
	public static int UNNOWN_ERROR = 0;
	public static int NOT_IMPLEMENTED_ERROR = 1;
	public static int NUMBER_STORE_ERROR = 2;
	public static int WRONG_VALUES_ERROR = 3;
	public static int WRONG_TYPE_ERROR = 4;

	public LaborDataException(int id, String s)
	{
		super(id, s);
	}
	
	public LaborDataException(int id)
	{
		super(id);
	}

	public LaborDataException(String s)
	{
		super( s );
	}
	
	public LaborDataException(Throwable t)
	{
		super(t);
	}
	
	public int getBaseCode() {
		return 400;
	}
}
