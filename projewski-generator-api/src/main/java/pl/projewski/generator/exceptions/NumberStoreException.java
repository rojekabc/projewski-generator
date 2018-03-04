package pl.projewski.generator.exceptions;


/*
 * Klasa błędów deskrypcji. Stanowi rozszerzenie klasy BaseException.
 */
public class NumberStoreException
extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6390640542370653244L;
	public static final int UNNOWN_ERROR = 0;
	public static final int INCORRECT_TYPE_ERROR = 1;
	public static final int IMPOSSIBLE_TYPE_ERROR = 2;
	public static final int CANNOT_CHOOSE_ERROR = 3;
	public NumberStoreException(int id, String s)
	{
		super(id, s);
	}
	
	public NumberStoreException(int id)
	{
		super(id);
	}

	public NumberStoreException(String s)
	{
		super( s );
	}
	
	public NumberStoreException(Throwable t)
	{
		super(t);
	}

	public int getBaseCode() {
		return 300;
	}
}
