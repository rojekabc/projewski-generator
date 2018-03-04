/*
 * Generator Linear Congruential
 * Ogólny schemat długiej genracji
 * m - liczba pierwsza => okres = m-1
 * a<m-1, c>=0
 */

// Algorytm nie należy do najszybszych z powodu licznych konwersji
// Mozna zastosowac parametry wewnatrz klasy dla przyspieszenia
package pk.ie.proj.generator;

import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberWriter;

public class GeneratorLCG 
	extends GeneratorInterface
{
	public final static String XN = "zm. losowa";
	public final static String A = "a";
	public final static String C = "c";
	public final static String M = "m";
	public final static String SEED = "ziarno";
	
	// dane wykorzystywane przy obliczaniu - przyspiesza niesamowicie !
	private long a;
	private long c;
	private long m;
	private long x;
	
	public void initParameterInterface()
	{
		this.parameters.put(A, Integer.valueOf(7));
		this.parameters.put(C, Integer.valueOf(0));
		this.parameters.put(M, Integer.valueOf(11));
		this.parameters.put(SEED, Integer.valueOf(2));
		this.parameters.put(XN, Long.valueOf(0));
		super.initParameterInterface();
	}

	public Class<?> [] getAllowedClass(String param) throws ParameterException
	{
		if ( param.equals(XN) 
				|| param.equals(A) 
				|| param.equals(C) 
				|| param.equals(M)
			)
		{
			return new Class[] {
					Integer.class,
					Long.class
				};
		}
		else if ( param.equals(SEED) )
		{
			return new Class[] {
					Integer.class,
					Long.class,
					GeneratorInterface.class
				};
		}
		else
		{
			return super.getAllowedClass(param);
		}
	}
	
	private void initGenerator(boolean isReinit) throws GeneratorException
	{
		Object seed = parameters.get(SEED);
		Object mObj = parameters.get(M);
		
		if (  seed == null )
			throw new GeneratorException(
					GeneratorException.NULL_PARAMETER_ERROR, SEED
				);
		if (parameters.get(A) == null)
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, A
					);
		if (parameters.get(C) == null)
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, C
					);
		if (mObj == null)
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, M
					);

		a = Convert.tryToLong(parameters.get(A));
		c = Convert.tryToLong(parameters.get(C));
		m = Convert.tryToLong(parameters.get(M));
		
		a %= m;
		c %= m;

		// zainicjowanie initializerem
		if ( seed instanceof GeneratorInterface ) {
			GeneratorInterface gi = (GeneratorInterface)seed;
			if ( isReinit )
				gi.reinit();
			else
				gi.init();
			x = gi.getLong();
		} else {
			x = Convert.tryToLong(seed);
		}
		x %= m;
		parameters.put(XN, Long.valueOf(x));	
	}

	public void init() 
		throws GeneratorException
	{
		initGenerator(false);
	}

	public void reinit()
		throws GeneratorException
	{
		initGenerator(true);
	}
	
	protected long next()
	{
		x *= a;
//		x %= m;
		x += c;
		x %= m;
		return x;
	}

	public long getLong()
		throws GeneratorException
	{
		try {
			parameters.put(XN, Long.valueOf(next()));
			return x;
		} catch (ClassCastException e) {
			throw new GeneratorException(
					GeneratorException.PARAMETER_CONVERT_ERROR
					);
		}
	}

	public int getInt()
		throws GeneratorException
	{
		return (int)getLong();
	}
	
	public float getFloat()
		throws GeneratorException
	{
		// hmm, ciekawe, ale szybciej działa niż (float)getLong()/(float)m 
		return (float)getDouble();
	}

	public double getDouble()
		throws GeneratorException
	{
		return (double)getLong() / (double)m;
	}

	public void rawFill(Object tablica)
		throws GeneratorException
	{
		try {
			if ( tablica instanceof int[] ) {
				int [] tmp = Convert.tryToTInt(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = (int)next();
			} else if ( tablica instanceof float[] ) {
				float [] tmp = Convert.tryToTFloat(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = (float)next()/(float)m;
			} else if ( tablica instanceof long[] ) {
				long [] tmp = Convert.tryToTLong(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = next();
			} else if ( tablica instanceof double[] ) {
				double [] tmp = Convert.tryToTDouble(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = (double)next()/(double)m;
			} 
			parameters.put(XN, Long.valueOf(x));
		} catch (ClassCastException e) {}
	}

	@Override
	public void rawFill(NumberWriter writer, ClassEnumerator cl, int size)
			throws GeneratorException {
		try {
			if ( cl == ClassEnumerator.INTEGER ) {
				while ( size-- > 0 )
					writer.write((int)next());
			} else if ( cl == ClassEnumerator.FLOAT ) {
				while ( size-- > 0 )
					writer.write((float)next()/(float)m);
			} else if ( cl == ClassEnumerator.LONG ) {
				while ( size-- > 0 )
					writer.write(next());
			} else if ( cl == ClassEnumerator.DOUBLE ) {
				while ( size-- > 0 )
					writer.write((double)next()/(double)m);
			} 
			parameters.put(XN, Long.valueOf(x));
		}
		catch (ClassCastException e) {
			throw new GeneratorException(e);
		} catch (IOException e) {
			throw new GeneratorException(e);
		}
	}
}
