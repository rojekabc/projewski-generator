package tests.test8;

/**
 * Implementacje tego interfejsu mają na celu podawanie kolejnych przypadków
 * wartości początkowych generatora do testowania.
 * @author projewski
 *
 */
public interface TestInterface {
	/**
	 * zainicjowanie implementacji do pracy
	 */
	public void init();
	/**
	 * Metoda zwraca parametry, jaki mają zostać ustawione dla generatora
	 * Są to kolejno M, A, C
	 * 
	 * @return kolejna tablica parametrów generatora, lub wartość null, jeżeli
	 * więcej już nie ma
	 */
	public int [] getNextToTest();
}
