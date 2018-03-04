package pl.projewski.generator.exceptions;

/*
 * Klasa błędów deskrypcji. Stanowi rozszerzenie klasy BaseException.
 */
public class DescriptionException
extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -170435668414245087L;
	public static int UNKNOWN_ERROR = 0;
	public static int FILE_ERROR = 1;
	public static int FILE_NOT_INITIALIZED_ERROR = 2;
	public static int NOT_FOUND_ERROR = 3;
	public static int NOT_IMPLEMENTED_ERROR = 4;
	public DescriptionException(int id, String s)
	{
		super(id, s);
	}
	
	public DescriptionException(int id)
	{
		super(id);
	}

	public DescriptionException(String s)
	{
		super( s );
	}

	public int getBaseCode() {
		return 100;
	}
}
