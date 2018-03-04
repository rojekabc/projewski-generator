package pl.projewski.generator.exceptions;

/*
 *	Klasa wyjątków pochdzących z generatora. Stanowi rozszerzenie klasy
 *	BaseException. Definiuje własne kody błędów.
 */

public class ViewDataException
	extends BaseException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -215336258764201424L;
	/*
	 * Dostępne numery błędów dla ViewDataException
	 */
	public static int UNKNOWN_ERROR = 0; // Nieznany
	public static int WRONG_PARAMETERS = 1; // Błędne dane, pod kštem walidacji do użycia
	
	/*
	 * Zgłoszenie błędu o wyznaczonym numerze indeksowym
	 */
	public ViewDataException(int errorIndex)
	{
		super(errorIndex);
	}

	/*
	 * Zgłoszenie błędu o wybranym numerze i z określonym argumentem
	 */
	public ViewDataException(int errorIndex, String arg)
	{
		super(errorIndex, arg);
	}

	/*
	 * Zgłoszenie błędu o wybranym nuemrze i z określonymi argumentami
	 */
	public ViewDataException(int errorIndex, String [] args)
	{
		super(errorIndex, args);
	}

	public ViewDataException(String errorDescription)
	{
		super(errorDescription);
	}
	
	public ViewDataException(Throwable t)
	{
		super(t);
	}

	// zwracanie opisu błędu
	public String toString()
	{
		return "ViewDataException: " + super.toString();
	}

	public String getMessage()
	{
		return toString();
	}

	public int getBaseCode() {
		return 600;
	}

};
