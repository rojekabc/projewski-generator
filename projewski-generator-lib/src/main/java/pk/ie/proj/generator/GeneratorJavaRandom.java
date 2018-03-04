/*
 *	Generator implementujący interfejs GeneratorInterface.
 *	Generator oparty na klasie javy java.util.Random.
 */
// PO WSTRZYMANIU PRACY GENERATOR JAVY NIE POZWLA NA PONOWNE WZNOWIENIE
// POWOD - NALEZABLOBY PRZEJSC TYLE SAMO KROKOW OD INICJACI DO OTRZYMANIA
// DANEJ WARTOSCI
package pk.ie.proj.generator;
import java.io.IOException;
import java.util.Random;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberWriter;

public class GeneratorJavaRandom
	extends GeneratorInterface
{
	protected Random javaRandom = null;

	public final static String XN = "zm. losowa";
	public final static String BOUND = "maksimum";
	public final static String SEED = "ziarno";
	
	public void initParameterInterface()
	{
		this.parameters.put(XN, Long.valueOf(0));
		this.parameters.put(BOUND, Integer.valueOf(0));
		this.parameters.put(SEED, Integer.valueOf(0));
	}

	/* zainicjowanie generatora wedle określonych uprzednio paramertrów */
	public void init() 
		throws GeneratorException
	{
		Object seed = this.parameters.get(SEED);
		Object xn = null;
		if ( seed != null ) 
		{
			if ( seed instanceof GeneratorInterface )
			{
				GeneratorInterface gi = (GeneratorInterface)seed;
				gi.init();
				Object obj = gi.getAsObject( Long.class );
				if ( obj != null )
					xn = obj;
			} else {
				xn = seed;
			}
		}
		try {
			this.parameters.put(XN, xn);
			javaRandom = new java.util.Random( Convert.tryToLong(this.parameters.get(XN)) );
		} catch ( ClassCastException e ) {
			System.out.println("Sytuacja anormalna - Random ma nieznany seed");
			javaRandom = new java.util.Random();
		}
	}
	/* przeinicjowanie generatora po zmianie parametrów */
	public void reinit()
		throws GeneratorException
	{
		Object seed = this.parameters.get(SEED);
		Object xn = null;
		if ( seed != null ) 
		{
			if ( seed instanceof GeneratorInterface )
			{
				GeneratorInterface gi = (GeneratorInterface)seed;
				gi.reinit();
				Object obj = gi.getAsObject( Long.class );
				if ( obj != null )
					xn = obj;
			} else {
				xn = seed;
			}
		}
		try {
			this.parameters.put(XN, xn);
			javaRandom = new java.util.Random( Convert.tryToLong(xn) );
		} catch ( ClassCastException e ) {
			System.out.println("Sytuacja anormalna - Random ma nieznany seed");
			javaRandom = new java.util.Random();
		}
	}

	public Class<?> [] getAllowedClass(String param) {
		Class<?> [] tmp;
		if ( param.equals(XN) )
		{
			tmp = new Class[4];
			tmp[0] = Integer.class;
			tmp[1] = Long.class;
			tmp[2] = Float.class;
			tmp[3] = Double.class;
		}
		else if ( param.equals(BOUND) )
		{
			tmp = new Class[1];
			tmp[0] = Integer.class;
		}
		else if ( param.equals(SEED) )
		{
			tmp = new Class[3];
			tmp[0] = GeneratorInterface.class;
			tmp[1] = Integer.class;
			tmp[2] = Long.class;
		}
		else
		{
			tmp = new Class[0];
		}
		return tmp;
	}

	/*
	 * Funkcje uzyskiwania wartości od generatora.
	 * Typy zależne mogą być od samego generatora.
	 * Niektóre mogą być niemożliwe do zrealizowania.
	 */
	public int getInt()
		throws GeneratorException
	{
		int ret = 0;
		if (javaRandom != null) {
			Object seed = this.parameters.get(SEED);
			if (seed == null)
				ret = javaRandom.nextInt();
			else {
				try {
					ret = javaRandom.nextInt( Convert.tryToInt(seed) );
				} catch ( ClassCastException e ) {}
			}
		}
		this.parameters.put(XN, Integer.valueOf( ret ));
		return ret;
	}

	public float getFloat()
		throws GeneratorException
	{
		float ret = 0.0f;
		if (javaRandom != null)
			ret = javaRandom.nextFloat();
		this.parameters.put(XN, Float.valueOf( ret ));
		return ret;
	}

	public long getLong()
		throws GeneratorException
	{
		long ret = 0;
		if (javaRandom != null)
			ret = javaRandom.nextLong();
		this.parameters.put(XN, Long.valueOf( ret ));
		return ret;
	}

	public double getDouble()
		throws GeneratorException
	{
		double ret = 0.0;
		if (javaRandom != null)
			ret = javaRandom.nextDouble();
		this.parameters.put(XN, Double.valueOf( ret ));
		return ret;
	}

	public void rawFill(Object tablica)
		throws GeneratorException
	{
		int bound = 0;
		if (javaRandom == null)
			return;
		
		try {
			Object obj = this.parameters.get(BOUND);
			if (obj == null)
				bound = Convert.tryToInt( obj );
		} catch ( ClassCastException e ) {}
		
		try {
			if ( tablica instanceof int[] ) {
				int [] tmp = Convert.tryToTInt(tablica);
				if ( bound == 0 )
					for ( int i = 0; i < tmp.length; i++ )
						tmp[i] = javaRandom.nextInt();
				else
					for ( int i = 0; i < tmp.length; i++ )
						tmp[i] = javaRandom.nextInt( bound );
				this.parameters.put(XN, Integer.valueOf( tmp[tmp.length - 1] ));
			} else if ( tablica instanceof float[] ) {
				float [] tmp = Convert.tryToTFloat(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = javaRandom.nextFloat();
				this.parameters.put(XN, Float.valueOf( tmp[tmp.length - 1] ));
			} else if ( tablica instanceof long[] ) {
				long [] tmp = Convert.tryToTLong(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = javaRandom.nextLong();
				this.parameters.put(XN, Long.valueOf( tmp[tmp.length - 1] ));
			} else if ( tablica instanceof double[] ) {
				double [] tmp = Convert.tryToTDouble(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = javaRandom.nextDouble();
				this.parameters.put(XN, Double.valueOf( tmp[tmp.length - 1] ));
			} 
		} catch (ClassCastException e) {}
	}

	@Override
	public void rawFill(NumberWriter writer, ClassEnumerator c, int size)
			throws GeneratorException {
		int bound = 0;
		if (javaRandom == null)
			return;
		
		try {
			Object obj = this.parameters.get(BOUND);
			if (obj == null)
				bound = Convert.tryToInt( obj );
		} catch ( ClassCastException e ) {}
		
		try {
			if ( c == ClassEnumerator.INTEGER ) {
				int tmp = 0;
				if ( bound == 0 )
					while ( size-- > 0 )
						writer.write(tmp = javaRandom.nextInt());
				else
					while ( size-- > 0 )
						writer.write(tmp = javaRandom.nextInt( bound ));
				this.parameters.put(XN, Integer.valueOf( tmp ));
			} else if ( c == ClassEnumerator.FLOAT ) {
				float tmp = 0.0f;
				while ( size-- > 0 )
					writer.write(tmp = javaRandom.nextFloat());
				this.parameters.put(XN, Float.valueOf( tmp ));
			} else if ( c == ClassEnumerator.LONG ) {
				long tmp = 0l;
				while ( size-- > 0 )
					writer.write(tmp = javaRandom.nextLong());
				this.parameters.put(XN, Long.valueOf( tmp ));
			} else if ( c == ClassEnumerator.DOUBLE ) {
				double tmp = 0.0;
				while ( size-- > 0 )
					writer.write(tmp = javaRandom.nextDouble());
				this.parameters.put(XN, Double.valueOf( tmp ));
			} 
		} catch (ClassCastException e) {
			throw new GeneratorException(e);
		} catch (IOException e) {
			throw new GeneratorException(e);
		}
	}

};
