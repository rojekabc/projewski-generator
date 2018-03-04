/*
 * Interfejs Generatora, pozwalający na jego wykorzystywanie w programach
 * z ustawionym dowolnym generatorem. Równocześnie pomocny do wykonywania
 * testów i obliczeń na danych wynikowych generatora.
 */
/*
 * Init:
 * X0 = SystemTime
 * Generation:
 * X[i] = X[i-1]
 */
package pl.projewski.generator.generator;

import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberWriter;

public class GeneratorSystemTime
	extends GeneratorInterface
{

	public final static String XN = "zm. losowa";
	
	public void initParameterInterface()
	{
		parameters.put(XN, Integer.valueOf(0));
	}

	/* zainicjowanie generatora wedle określonych uprzednio paramertrów */
	public void init() 
		throws GeneratorException
	{
		long r = System.currentTimeMillis();
		parameters.put(XN, Long.valueOf( r ));
	}
	/* przeinicjowanie generatora po zmianie parametrów */
	public void reinit()
		throws GeneratorException
	{
	}

	public Class<?> [] getAllowedClass(String param)
		throws ParameterException
	{
		if ( param.equals(XN) )
		{
			return new Class[] {
					Integer.class,
					Long.class
			};
		}
		return new Class[0];
	}

	/*
	 * Funkcje uzyskiwania wartości od generatora.
	 * Typy zależne mogą być od samego generatora.
	 * Niektóre mogą być niemożliwe do zrealizowania.
	 */
	public long getLong()
		throws GeneratorException
	{
		try {
			Object xn = parameters.get(XN);
			if ( xn != null )
				return Convert.tryToLong( xn );
		} catch ( ClassCastException e ) {}
		return 0;
	}

	public int getInt()
		throws GeneratorException
	{
		try {
			Object xn = parameters.get(XN);
			if ( xn != null )
				return Convert.tryToInt( xn );
		} catch ( ClassCastException e ) {}
		return 0;
	}

	public float getFloat()
		throws GeneratorException
	{
		try {
			Object xn = parameters.get(XN);
			if ( xn != null )
				return Convert.tryToFloat( xn );
		} catch ( ClassCastException e ) {}
		return 0;
	}

	public double getDouble()
		throws GeneratorException
	{
		try {
			Object xn = parameters.get(XN);
			if ( xn != null )
				return Convert.tryToDouble( xn );
		} catch ( ClassCastException e ) {}
		return 0;
	}

	public void rawFill(Object table)
		throws GeneratorException
	{
		try {
			if ( table instanceof int[] ) {
				int [] tmp = Convert.tryToTInt( table );
				int f = getInt();
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = f;
			} else if ( table instanceof long[] ) {
				long [] tmp = Convert.tryToTLong( table );
				long f = getLong();
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = f;
			} else if ( table instanceof float[] ) {
				float [] tmp = Convert.tryToTFloat( table );
				float f = getFloat();
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = f;
			} else if ( table instanceof double[] ) {
				double [] tmp = Convert.tryToTDouble( table );
				double f = getDouble();
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = f;
			}
		} catch ( ClassCastException e ) {}
		return;
	}

	@Override
	public void rawFill(NumberWriter writer, ClassEnumerator c, int size)
			throws GeneratorException {
		try {
			if ( c == ClassEnumerator.INTEGER ) {
				while ( size-- > 0 )
					writer.write(getInt());
			} else if ( c == ClassEnumerator.FLOAT ) {
				while ( size-- > 0 )
					writer.write(getFloat());
			} else if ( c == ClassEnumerator.LONG ) {
				while ( size-- > 0 )
					writer.write(getLong());
			} else if ( c == ClassEnumerator.DOUBLE ) {
				while ( size-- > 0 )
					writer.write(getDouble());
			} 
		} catch (ClassCastException e) {
			throw new GeneratorException(e);
		} catch (IOException e) {
			throw new GeneratorException(e);
		}
		
	}
};
