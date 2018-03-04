package pl.projewski.generator.interfaces;

import pk.ie.proj.exceptions.DescriptionException;

/** 
	* Interfejs ten ma na celu zarządzanie opisami. Nie interesuje go ich sposób
	* organizacji. Jedyny wymóg jaki sobie stawia to udostępnienie określonego
	* opisu po podaniu identyfikatora. Umożliwia zaimplementowanie w klasie
	* wariantu wybierania języka deskrypcji oraz podawania dodatkowych
	* argumentów (informacji) podczas tworzenia informacji zwrotnej.
	* Klasy implementujące ów interfejs mogą również implementować funkcje, które
	* ustawiwają deskrypcje dla określonych numerów identyfikatorów.
	* Są także dodane metody interfejsu z możliwościa selekcjonowania poprzez
	* dowolny obiekt Javy (klasy Object) i równocześnie zwracające ogólny obiekt
	* (klasy Object).
  */
public interface DescriptionInterface
{
	/** 
	* Metoda zwracająca opis wyselekcjonowany poprzez podany numer identyfikatora.
	* @param id Identyfikator określający, który z opisów ma być zwrócony.
	* @return Zwracany jest łańcuch znaków stanowiący wybrany opis.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public String getDescription(int id)
		throws DescriptionException;
	/** 
	* Metoda zwracająca opis wyselekcjonowany poprzez podany numer identyfikatora.
	* Dodatkowo jest podawany łańcuch znaków, który może stanowić pomocniczą
	* wiadomość przy tworzeniu informacji zwrotnej. Informacja ta może być
	* dowolnie obrobiona przez funkcję (może zawierać informację, jakieś
	*	ustawienie itp.), lecz może być pominięta.
	* @param id Identyfikator określający, który z opisów ma być zwrócony.
	* @param arg Łańcuch znaków zawierający dodatkową informację do opisu.
	* @return Zwracany jest łańcuch znaków stanowiący wybrany opis.
	* Jeżeli funkcja jest niezaimplementowana zostanie zwrócony null.
	* Jeżeli nastąpiły kłopoty podczas generowania informacji zwrotnej
	* będzie porzucony wyjątek.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public String getDescription(int id, String arg)
		throws DescriptionException;
	/** 
	* Metoda zwracająca opis wyselekcjonowany poprzez podany numer identyfikatora.
	* Dodatkowo jest podawana tablica łańcuchów znaków, która może stanowić
	* pomocniczą wiadomość przy tworzeniu informacji zwrotnej. Informacja ta
	* może być dowolnie obrobiona przez funkcję (może zawierać informację,
	* jakieś ustawienie itp.), lecz może być pominięta.
	* @param id Identyfikator określający, który z opisów ma być zwrócony.
	* @param arg Tablica łańcuchów zawierających dodatkową informację do opisu.
	* @return Zwracany jest łańcuch znaków stanowiący wybrany opis.
	* Jeżeli funkcja jest niezaimplementowana zostanie zwrócony null.
	* Jeżeli nastąpiły kłopoty podczas generowania informacji zwrotnej
	* będzie porzucony wyjątek.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public String getDescription(int id, String [] args)
		throws DescriptionException;
	/** 
	* Metoda zwracająca obiekt opisu, wyselekcjonowany poprzez podany obiekt,
	* który identyfikuje go spośród inncyh.
	* @param idobj Obiekt określający, który z opisów ma być zwrócony.
	* @return Zwracany jest obiekt, który zawiera dowolnie przedstawioną
	* informację opisującą.
	* Jeżeli funkcja jest niezaimplementowana zostanie zwrócony null.
	* Jeżeli nastąpiły kłopoty podczas generowania informacji zwrotnej
	* będzie porzucony wyjątek.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public Object getDescription(Object idobj)
		throws DescriptionException;
	/** 
	* Metoda pozwalająca na określenie opisu dla wskazanego identyfikatora.
	* @param id Identyfikator określający, który z opisów ma być ustawiony.
	* @param desc Opis jaki ma zostać przypisany dla wybranego identyfikatora.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public void setDescription(int id, String desc)
		throws DescriptionException;
	/** 
	* Metoda pozwalająca na określenie opisu dla wskazanego obiektu.
	* @param idobj Obiekt określający, który z opisów ma być ustawiony.
	* @param descobj Obiekt opisowy jaki ma zostać przypisany.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public void setDescription(Object idobj, Object descobj)
		throws DescriptionException;
	/** 
	* Metoda pozwalająca na dokonanie wyboru, jaki język ma być używany dla
	* generowanych opisów.
	* @param lang Łańcuch znaków określający selekcjonowany język opisu.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public void setLanguage(String lang)
		throws DescriptionException;
	
	/**
	 * Metoda wykonujšca selekcję ładowanego zestawu opisów
	 */
	public void setDescriptionSet(String setName)
		throws DescriptionException;
	/** 
	* Metoda ustawiająca plik, z którego będą odczytywane dane.
	* @param filename Nazwa pliku źródłowego.
	* @exception DescriptionException
	* @see DescriptionException
  */
	public void setInputFile(String filename)
		throws DescriptionException;
}
