/**
 * 
 */
package pl.projewski.generator.tools.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.NumberStoreException;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;

/**
 * @author projewski
 *
 */
public class SeparatorStreamReader implements NumberReader {
	InputStreamReader is;
	int separator = ' ';
	
	public SeparatorStreamReader(InputStream in) {
		this.is = new InputStreamReader(in);
	}
	
	protected String readToSeparator() throws NumberStoreException
	{
		int c;
		StringBuilder str = new StringBuilder();
		try {
			while (( c = is.read() ) != -1 )
			{
				if ( c == separator )
					break;
				str.append((char)c);
			}
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
		if (( str.length() == 0 ) && ( c == -1 ))
			throw new NumberStoreException("Brak danych");
		
		return str.toString();
		
	}
	
	public int readInt() throws NumberStoreException
	{		
		return Integer.parseInt(readToSeparator());
	}
	
	public long readLong() throws NumberStoreException
	{		
		return Long.parseLong(readToSeparator());
	}
	
	public float readFloat() throws NumberStoreException
	{		
		return Float.parseFloat(readToSeparator());
	}
	
	public double readDouble() throws NumberStoreException
	{		
		return Double.parseDouble(readToSeparator());
	}
	
	public int read(int [] a) throws NumberStoreException
	{
		int i = 0;
		try {
			while (is.ready() && (i<a.length))
			{
				a[i++] = readInt();
			}
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
		return i;
	}
	
	public int read(long [] a) throws NumberStoreException
	{
		int i = 0;
		try {
			while (is.ready() && (i<a.length))
			{
				a[i++] = readLong();
			}
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
		return i;
	}

	public int read(float [] a) throws NumberStoreException
	{
		int i = 0;
		try {
			while (is.ready() && (i<a.length))
			{
				a[i++] = readFloat();
			}
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
		return i;
	}
	
	public int read(double [] a) throws NumberStoreException
	{
		int i = 0;
		try {
			while (is.ready() && (i<a.length))
			{
				a[i++] = readDouble();
			}
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
		return i;
	}
	
	public Object readAsObject(ClassEnumerator c) throws NumberStoreException
	{
		if ( c == ClassEnumerator.INTEGER )
			return Integer.valueOf(readInt());
		else if ( c == ClassEnumerator.LONG )
			return Long.valueOf(readLong());
		else if ( c == ClassEnumerator.FLOAT )
			return Float.valueOf(readFloat());
		else if ( c == ClassEnumerator.DOUBLE )
			return Double.valueOf(readDouble());
		else
			return null;
	}

	public boolean hasNext() throws NumberStoreException {
		try {
			return is.ready();
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
	}

	public void close() throws NumberStoreException {
		try {
			is.close();
		} catch (IOException e) {
			throw new NumberStoreException(e);
		}
	}
}
