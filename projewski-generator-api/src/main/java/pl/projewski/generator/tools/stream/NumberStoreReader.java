/**
 * 
 */
package pl.projewski.generator.tools.stream;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.tools.NumberStoreOne;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;

/**
 * @author projewski
 *
 */
public class NumberStoreReader implements NumberReader {
	
	NumberStoreOne nso = null;
	int pos;
	
	public NumberStoreReader(NumberStoreOne ns)
	{
		nso = ns;
		pos = 0;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#read(int[])
	 */
	public int read(int[] a) throws NumberStoreException {
		int i = 0;
		int [] table = nso.getTInt();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		while (( pos < table.length ) && ( i < a.length ))
			a[i++] = table[pos++];
		return i;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#read(long[])
	 */
	public int read(long[] a) throws NumberStoreException {
		int i = 0;
		long [] table = nso.getTLong();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		while (( pos < table.length ) && ( i < a.length ))
			a[i++] = table[pos++];
		return i;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#read(float[])
	 */
	public int read(float[] a) throws NumberStoreException {
		int i = 0;
		float [] table = nso.getTFloat();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		while (( pos < table.length ) && ( i < a.length ))
			a[i++] = table[pos++];
		return i;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#read(double[])
	 */
	public int read(double[] a) throws NumberStoreException {
		int i = 0;
		double [] table = nso.getTDouble();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		while (( pos < table.length ) && ( i < a.length ))
			a[i++] = table[pos++];
		return i;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#readAsObject(java.lang.Class)
	 */
	public Object readAsObject(ClassEnumerator c) throws NumberStoreException {
		if ( c == ClassEnumerator.INTEGER )
			return Integer.valueOf(readInt());
		else if ( c == ClassEnumerator.LONG )
			return Long.valueOf(readLong());
		else if ( c == ClassEnumerator.FLOAT )
			return Float.valueOf(readFloat());
		else if ( c == ClassEnumerator.DOUBLE )
			return Double.valueOf(readDouble());
		return null;
	}
	
	protected double getDouble() throws NumberStoreException
	{
		double [] table = nso.getTDouble();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		return table[pos++];		
	}
	
	protected float getFloat() throws NumberStoreException
	{
		float [] table = nso.getTFloat();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		return table[pos++];
	}

	protected int getInt() throws NumberStoreException
	{
		int [] table = nso.getTInt();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		return table[pos++];
	}
	protected long getLong() throws NumberStoreException
	{
		long [] table = nso.getTLong();
		if ( pos >= table.length )
			throw new NumberStoreException("Brak danych");
		return table[pos++];
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#readDouble()
	 */
	public double readDouble() throws NumberStoreException {
		ClassEnumerator c = nso.getStoreClass();
		if ( c == ClassEnumerator.DOUBLE )
			return getDouble();
		if ( c == ClassEnumerator.FLOAT )
			return getFloat();
		if ( c == ClassEnumerator.LONG )
			return getLong();
		if ( c == ClassEnumerator.INTEGER )
			return getInt();
		throw new NumberStoreException("Unknown type");
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#readFloat()
	 */
	public float readFloat() throws NumberStoreException {
		ClassEnumerator c = nso.getStoreClass();
		if ( c == ClassEnumerator.FLOAT )
			return getFloat();
		if ( c == ClassEnumerator.DOUBLE )
			return (float)getDouble();
		if ( c == ClassEnumerator.LONG )
			return getLong();
		if ( c == ClassEnumerator.INTEGER )
			return getInt();
		throw new NumberStoreException("Unknown type");
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#readInt()
	 */
	public int readInt() throws NumberStoreException {
		ClassEnumerator c = nso.getStoreClass();
		if ( c == ClassEnumerator.INTEGER )
			return getInt();
		if ( c == ClassEnumerator.LONG )
			return (int)getLong();
		if ( c == ClassEnumerator.DOUBLE )
			return (int)getDouble();
		if ( c == ClassEnumerator.FLOAT )
			return (int)getFloat();
		
		throw new NumberStoreException("Unknown type");
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.NumberReader#readLong()
	 */
	public long readLong() throws NumberStoreException {
		ClassEnumerator c = nso.getStoreClass();
		if ( c == ClassEnumerator.LONG )
			return getLong();
		if ( c == ClassEnumerator.INTEGER )
			return getInt();
		if ( c == ClassEnumerator.DOUBLE )
			return (long)getDouble();
		if ( c == ClassEnumerator.FLOAT )
			return (long)getFloat();
		else
			throw new NumberStoreException("Unknown type");
	}

	public boolean hasNext() throws NumberStoreException {
		if ( pos > nso.getSize() )
		{
			System.out.println("Pos is " + pos);
			System.out.println("Size is " + nso.getSize());
		}
		return (pos < nso.getSize());
	}

	public void close() throws NumberStoreException {
	}

}
