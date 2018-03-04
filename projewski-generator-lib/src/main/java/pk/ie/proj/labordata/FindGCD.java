/*
 * Implementacja poszukiwania największego wspólnego dzielnika.
 */
package pk.ie.proj.labordata;
import java.io.FileNotFoundException;
import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

public class FindGCD
	extends LaborDataInterface
{
	private final static String GCD = "gcd()";

	public void initParameterInterface()
	{
		parameters.put(GCD, null);
	}

	// TODO: Dla całkowitych poszukać lepszego algorytmu
	//
	// TODO:
	// Dodatkowo dla wartości przecinkowych trzeba ustalic
	// dokladnosc rozpatrywania zadania, gdyż właściwie odejmując
	// dwie wartości przecinkowe w systemie dwójkowym można nigdy nie otrzymać
	// tego samego. Zastanawiam się nad wstępnym przekształceniem liczby
	// rzeczywistej do liczby całkowitej z określoną dokładnością. Np. Jeśli
	// dokładność wynosi 0.001, to liczba zmiennoprzecinkowa po wymnożeniu
	// przez 1000 i pobraniu części przed przecinkiem mogłaby już być dalej
	// odpowiednio traktowana. Zjawiska, które by tu przeszkadzały:
	// - bezpośrednie mnożenie mogłoby zatracić postać liczby rzeczywistej
	// - liczba rzeczywista może mieć dużą wartość przed przecinkiem przed
	//	przystąpieniem do operacji.
	//
	// Dla całkowitych nie stosuję typowego algorytmu Euklidesa, lecz
	// zmodyfikowanego następująco:
	// najpierw jest sprawdzane, czy liczba_wieksza%liczba_mniejsza
	// jeśli tak to liczba_mniejsza jest gcd
	// jesli nie to nie następuje pojedyncze odejmowanie liczby_mniejsza
	// od liczba_wieksza jak w Euklidesie, gdyż np. jeśli liczba_mniejsza to 1,
	// a liczba_wieksza to 0x7FFF to następowałoby dość długie odejmowanie.
	// Wykonuję tu operację
	// liczba_wieksza -= (liczba_wieksza/liczba_mniejsza)*liczba_mniejsza,
	// gdzie dzielenie daje ilość liczb_mniejsza w liczb_wieksza i jest liczbą
	// całkowitą
	int gcdInt(int a, int b)
	{
		if ( (a == 0) || (b == 0) )
			return 0; // TODO: Exception
		while ( a != b )
		{
			if ( a > b )
			{
				if ( a%b == 0 )
					return b;
				a -= (a/b)*b;
			}
			else
			{
				if ( b%a == 0 )
					return a;
				b -= (b/a)*a;
			}
		}
		return a;
	}
	long gcdLong(long a, long b)
	{
		if ( (a == 0) || (b == 0) )
			return 0; // TODO: Exception
		while ( a != b )
		{
			if ( a > b )
			{
				if ( a%b == 0 )
					return b;
				a -= (a/b)*b;
			}
			else
			{
				if ( b%a == 0 )
					return a;
				b -= (b/a)*a;
			}
		}
		return a;
	}
	float gcdFloat(float a, float b)
	{
		if ( (a == 0) || (b == 0) )
			return 0; // TODO: Exception
		// UWAGA ! Algorytm b. wolny np. gdy zapodamy a=0x7FFF, b=1
		// dlatego do przebudowania na np. odejmij tyle co (a/b)*b,
		// jesli modulo != 0
		while ( Float.compare (a, b) != 0 )
		{
			if ( a > b )
				a -= b;
			else
				b -= a;
		}
		return a;
	}
	double gcdDouble(double a, double b)
	{
		if ( (a == 0) || (b == 0) )
			return 0; // TODO: Exception
		// UWAGA ! Algorytm b. wolny np. gdy zapodamy a=0x7FFF, b=1
		// dlatego do przebudowania na np. odejmij tyle co (a/b)*b,
		// jesli modulo != 0
		
		while ( Double.compare(a, b) != 0 )
		{
			if ( a > b )
				a -= b;
			else
				b -= a;
		}
		return a;
	}

	public Class<?> [] getAllowedClass(String param)
	{
		return new Class[0];
	}

	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException {
		Object out = parameters.get(GCD);
		
		if ( out == null )
			return false;
		if (data == null)
			return false; // TODO: NULL Ecxeption
		
		NumberWriter writer = null;
		
		try
		{
			writer = data.getNumberWriter();
			
			if (out instanceof Integer)
			{
				writer.write( ((Integer)out).intValue() );
				data.setStoreClass(ClassEnumerator.INTEGER);
			}
			else if (out instanceof Long)
			{
				writer.write( ((Long)out).longValue() );
				data.setStoreClass(ClassEnumerator.LONG);
			}
			else if (out instanceof Float)
			{
				writer.write( ((Float)out).floatValue() );
				data.setStoreClass(ClassEnumerator.FLOAT);
			}
			else if (out instanceof Double)
			{
				writer.write( ((Double)out).doubleValue() );
				data.setStoreClass(ClassEnumerator.DOUBLE);
			}
			else
				return false; // TODO: Wrong Output Data
			
			data.setSize(1);
		} catch (FileNotFoundException e) {
			throw new LaborDataException(e);
		} catch (IOException e) {
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(writer);
		}

		parameters.put(GCD, null);
		return true;
	}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {
		
		NumberReader is = null;
		
		try {
			ClassEnumerator cl = data.getStoreClass();
			if (cl == null)
				return; // TODO: Throw info about null data
			
			is = data.getNumberReader();

			if ( cl == ClassEnumerator.INTEGER )
			{
				int gcd = 0;
				if ( is.hasNext() )
					gcd = is.readInt();
				while ( is.hasNext() )
					gcd = gcdInt( gcd, is.readInt() );

				parameters.put(GCD, Integer.valueOf( gcd ));
			}
			else if ( cl == ClassEnumerator.LONG )
			{
				long gcd = 0l;
				if ( is.hasNext() )
					gcd = is.readLong();
				while ( is.hasNext() )
					gcd = gcdLong( gcd, is.readLong() );

				parameters.put(GCD, Long.valueOf( gcd ));
			}
			else if ( cl == ClassEnumerator.FLOAT )
			{
				throw new LaborDataException(
					LaborDataException.NOT_IMPLEMENTED_ERROR);
			}
			else if ( cl == ClassEnumerator.DOUBLE )
			{
				throw new LaborDataException(
					LaborDataException.NOT_IMPLEMENTED_ERROR);
			}
			else
				return; // TODO: Exception Unknown Input Data Type
//			return new NumberStoreOne(outData);

		} catch (ClassCastException e) {
			throw new LaborDataException(
						LaborDataException.WRONG_TYPE_ERROR
					);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);			
		} finally {
			Mysys.closeQuiet(is);
		}
	}
}
