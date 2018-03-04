package pl.projewski.generator.exceptions;

import pl.projewski.generator.interfaces.BaseExceptionInterface;
import pl.projewski.generator.tools.Mysys;

/**
 *	Klasa reprezentująca podstawową obsługę błędów dla systemu.
 *	Stanowi rozszerzenie klasy Exception.
 *	Wszystkie klasy obsługi błędów rozszerzają tą klasę, dodając własne metody.
 *	Klasa dziedzicząca powinna posługiwać się własną przestrzenią kodów
 *	błędów, które będzie zwracać, choć nie jest to wymogiem, ale dobrym
 *	zachowaniem, które pozwoli na rozdzielenie poszczególnych błędów.
 *	Klasa wykorzystuje DescriptionInterface do ustalania tekstów komunikatów
 *	błędów. Standardowo jest to DescriptionFile o prefixie err i sufixie dat.
 *	Podczas pisania własnej klasy używającej interfejsu DescriptionInterface
 *	należy pamiętać, iż porzuca on wyjątek DescriptionException, który jest
 *	pochodną klasy BaseException. Należy więc unikać niekorzystnych odwołań,
 *	które mogą spowodować nieskńczone zagłębianie sie w konstruktorach. Pomocny
 *	jest tutaj konstruktor BaseException(String), który nie pobiera tekstu
 *	komunikatu z DescriptionInterface, a porzuca go w oryginalnej formie.
 *	@author Piotr Rojewski
 */
public abstract class BaseException
extends Exception
implements BaseExceptionInterface
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3734380712631652275L;
	/**
	 * komunikat mówiący o szczególnym błedzie wewnętrznym - do usunięcia(testy)
	 */
	private static String internal = "IE ! "; // do usunięcia
	/**
	 * nienazwany błąd
	 */
	public static int UNNAME_ERROR = -1;
	/**
	 * opis błędu wynikowy 
	 */
	private String descr = "";
	/**
	 * kod błędu 
	 */
	private int errCode = 0;
	
	/**
	 * Metoda, która buduje komunikat błędu.
	 * Komunikat błędu może zawierać w sobie argumenty, które można wypełniać stosownumi
	 * zmiennymi uszczegółowiającymi jego treść.
	 * @param errorIndex numer indeksowy komunikatu
	 * @param args argumenty uszczegółowiacjące treść komunikatu
	 */
	private void build( int errorIndex, String [] args )
	{
		errCode = errorIndex;
		descr = Mysys.getDescription(this.getClass().getSimpleName(), "" + errorIndex, "exception", args);
/*		try {
			errCode = this.getBaseCode() + errorIndex;
			if ( errorFile == null )
				errorFile = new DescriptionFile( Mysys.getErrCodeFile() );
			descr = errorFile.getDescription( errCode, args );
		} catch ( DescriptionException e ) {
			descr = e.toString();
		} catch ( Exception e ) {
			descr = e.toString();
		}*/
	}
	
	/**
	 * Konstruktor budujący komunikat błędu o określonym numerze indeksowym.
	 * Konstruktor do tworzenia komunikatu wykorzystuje @see DescriptionInterface.
	 * @param index numer indeksowy komunikatu błędu
	 */
	public BaseException(int index)
	{
		build( index, new String[0] );
	}
	
	/**
	 * Konstruktor budujący komunikat błędu o określonym numerze indeksowym
	 * z zastosowaniem argumentów uszczegółowiających komunikat
	 * Konstruktor do tworzenia komunikatu wykorzystuje @see DescriptionInterface.
	 * @param index numer indeksowy komunikatu
	 * @param args argumenty uszczegółowiające
	 */
	public BaseException(int index, String [] args)
	{
		build( index, args );
	}

	/**
	 * Konstruktor komunikatu błędu o jednym numerze indeksowym oraz jednym argumencie
	 * uszczegółowiającym komunikat.
	 * Konstruktor do tworzenia komunikatu wykorzystuje @see DescriptionInterface.
	 * @param index numer indeksowy komunikatu
	 * @param arg Argument uszczegółowiający komunikat
	 */
	public BaseException(int index, String arg)
	{
		String [] args = new String[1];
		args[0] = arg;
		build( index, args );
	}

	/**
	 * Konstruktor komunikatu błędu o opisie, który zostaje przekazany jako argument.
	 * Konstruktor ten nie wykorzystuje do tworzenia komunikatu DescriptionInterface,
	 * więc służy do generowania błędów specjalnych, które mówią o problemach
	 * z formułowałeniem komunikatu błędu przy pomocy dostępnego systemu.
	 * @param errorString
	 */
	public BaseException(String errorString)
	{
		errCode = -1;
		descr = internal + errorString;
	}
	
	public BaseException(Throwable t)
	{
		super(t);
	}
	
	/**
	 * Funkcja zwraca utowrzony komunikat błędu.
	 * @return komunikat błędu 
	 */
	public String toString()
	{
		return descr;
	}

	/**
	 * Funkcja podaje wartość kodu komunikatu błędu, na podstawie którego został
	 * wygenerowany
	 * @return kod wygenerowanego komunikatu błędu
	 */
	public int getCode()
	{
		return errCode;
	}

}
