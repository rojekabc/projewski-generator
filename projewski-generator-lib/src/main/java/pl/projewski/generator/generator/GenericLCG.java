/**
 * 
 */
package pl.projewski.generator.generator;

import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.ArrayUtil;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.VectorLong;
import pk.ie.proj.tools.stream.NumberWriter;

/**
 * @author projewski
 * 
 * Ogólny schemat generatora LCG, który działa zgodnie z regułš
 *          s
 *          _
 * X[n+1] = >   A[k]*X[n-k] + C mod M, gdzie s < n 
 *          -
 *        k = 0
 * 
 * Elementów s będzie przechowywanych tyle, ile zostanie podanych parametrów do tablicy SEED,
 * która inicjuje poczštkowy zestaw parametrów X[0..s], a jeżeli jest to generator to będzie ona
 * zgodna z liczbš danych podanych w A
 */
public class GenericLCG extends GeneratorInterface {
	
	public final static String XN = "zm. losowa";
	public final static String A = "a";
	public final static String C = "c";
	public final static String M = "m";
	public final static String SEED = "ziarno";
	
	private long [] a;
	private long c;
	private long [] xn;
	private long m;

	@Override
	public Class<?>[] getAllowedClass(String param) throws ParameterException {
		if ( param.equals(A) || param.equals(XN) )
			return new Class[] { VectorLong.class };
		else if ( param.equals(C) || param.equals(M) )
			return new Class[] { Long.class };
		else if ( param.equals(SEED) )
			return new Class[] { VectorLong.class, GeneratorInterface.class };
		else
			return super.getAllowedClass(param);
	}

	@Override
	public void initParameterInterface() {
		parameters.put(A, new VectorLong());
		parameters.put(C, Long.valueOf(0));
		parameters.put(M, Long.valueOf(0));
		parameters.put(SEED, Long.valueOf(2));
		parameters.put(XN, new VectorLong());
		super.initParameterInterface();
	}
	
	/**
	 * Wyznacza kolejnš wartoć generatora,
	 * Nie zmienia jego stanu w parametrach
	 * Aktualizuje tablicę z aktualnymi wartociami
	 * Zwraca nowootrzymanš wartoć
	 */
	private long next()
	{
		long newxn = 0;
		
		for (int i=0; i<xn.length; i++)
		{
			newxn += (a[i]*xn[xn.length - i - 1])%m;
			newxn %= m;
		}
		
		newxn += c;
		newxn %= m;
		ArrayUtil.constPutLast(xn, newxn);
		return newxn;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getDouble()
	 */
	@Override
	public double getDouble() throws GeneratorException {
		long m = Convert.tryToLong( parameters.get(M) );
		return (double)getLong()/(double)m;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getFloat()
	 */
	@Override
	public float getFloat() throws GeneratorException {
		long m = Convert.tryToLong( parameters.get(M) );
		return (float)getLong()/(float)m;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getInt()
	 */
	@Override
	public int getInt() throws GeneratorException {
		return (int)getLong();
	}
	
	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getLong()
	 */
	@Override
	public long getLong() throws GeneratorException {
		long n = next();
		parameters.put(XN, new VectorLong(xn));
		return n;
	}
	
	private void initGenerator(boolean isReinit) throws GeneratorException
	{
		// pobranie parametrów poczštkowych
		Object seedObj = parameters.get(SEED);
		Object aObj = parameters.get(A);
		Object cObj = parameters.get(C);
		Object mObj = parameters.get(M);

		// sprawdzenie wartoci
		if (  seedObj == null )
			throw new GeneratorException(
					GeneratorException.NULL_PARAMETER_ERROR, SEED
				);
		
		if (  aObj == null )
			throw new GeneratorException(
					GeneratorException.NULL_PARAMETER_ERROR, A
				);

		if (  cObj == null )
			throw new GeneratorException(
					GeneratorException.NULL_PARAMETER_ERROR, C
				);
		
		if ( mObj == null )
			throw new GeneratorException(
					GeneratorException.NULL_PARAMETER_ERROR, M
				);
		
//		VectorLong a = (VectorLong)aObj;
		a = Convert.tryToTLong(aObj);
//		VectorLong xn = new VectorLong();
		m = Convert.tryToLong(mObj);
		c = Convert.tryToLong(cObj);

		// budowanie poczatkowego xn z wyrównaniem parametrów
		if ( seedObj instanceof GeneratorInterface )
		{
			GeneratorInterface seedGI = (GeneratorInterface)seedObj;
			
			if ( isReinit )
				seedGI.reinit();
			else
				seedGI.init();
			
			xn = new long [a.length];
			for ( int i=0; i<a.length; i++ )
			{
				xn[i] = seedGI.getLong();
				xn[i] %= m;
			}
		}
		else
		{
			VectorLong seed = (VectorLong)seedObj;
			xn = new long [seed.size()];
			for ( int i=0; i<seed.size(); i++ )
			{
				xn[i] = seed.get(i);
				xn[i] %= m;
			}
		}
		parameters.put(XN, new VectorLong(xn));
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#init()
	 */
	@Override
	public void init() throws GeneratorException {
		initGenerator(false);
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#rawFill(java.lang.Object)
	 */
	@Override
	public void rawFill(Object arg0) throws GeneratorException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#rawFill(pk.ie.proj.tools.stream.NumberWriter, pk.ie.proj.enumeration.ClassEnumerator, int)
	 */
	@Override
	public void rawFill(NumberWriter writer, ClassEnumerator cl, int size)
			throws GeneratorException {
		try {
			if ( cl == ClassEnumerator.LONG )
			{
				while (size-- > 0)
					writer.write(this.getLong());
			}
			else if ( cl == ClassEnumerator.INTEGER )
			{
				while (size-- > 0)
					writer.write(this.getInt());				
			}
			else if ( cl == ClassEnumerator.FLOAT )
			{
				while (size-- > 0)
					writer.write(this.getFloat());				
			}
			else if ( cl == ClassEnumerator.DOUBLE )
			{
				while (size-- > 0)
					writer.write(this.getDouble());				
			}			
		} catch (IOException e) {
			throw new GeneratorException( e );
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#reinit()
	 */
	@Override
	public void reinit() throws GeneratorException {
		initGenerator(true);
	}

}
