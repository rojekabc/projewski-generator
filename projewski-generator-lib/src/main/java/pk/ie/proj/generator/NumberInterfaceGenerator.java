/**
 * 
 */
package pk.ie.proj.generator;

import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

/**
 * @author projewski
 *
 * To jest generator, który dane czerpie z interfejsu NumberInterface.
 * Dzięki temu można podpišć wygenerowany plik, jako generator zestawu liczb.
 */
public class NumberInterfaceGenerator extends GeneratorInterface {
	
	public static final String NUMBERSOURCE = "ródło danych";
	public static final String ROLLING = "Przewijanie";
	
	@Override
	public Class<?>[] getAllowedClass(String param) throws ParameterException {
		return super.getAllowedClass(param);
	}

	@Override
	public void initParameterInterface() {
		super.initParameterInterface();
		parameters.put(NUMBERSOURCE, ni);
		parameters.put(ROLLING, rolling);
	}

	NumberInterface ni = null;
	NumberReader reader = null;
	boolean rolling = false;

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getDouble()
	 */
	@Override
	public double getDouble() throws GeneratorException {
		try {
			if ( !reader.hasNext() )
			{
				reader.close();
				if ( rolling )
					reader = ni.getNumberReader();
			}
			return reader.readDouble();
		} catch (NumberStoreException e) {
			throw new GeneratorException(e);
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getFloat()
	 */
	@Override
	public float getFloat() throws GeneratorException {
		try {
			if ( !reader.hasNext() )
			{
				reader.close();
				if ( rolling )
					reader = ni.getNumberReader();
			}
			return reader.readFloat();
		} catch (NumberStoreException e) {
			throw new GeneratorException(e);
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getInt()
	 */
	@Override
	public int getInt() throws GeneratorException {
		try {
			if ( !reader.hasNext() )
			{
				reader.close();
				if ( rolling )
					reader = ni.getNumberReader();
			}
			return reader.readInt();
		} catch (NumberStoreException e) {
			throw new GeneratorException(e);
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#getLong()
	 */
	@Override
	public long getLong() throws GeneratorException {
		try {
			if ( !reader.hasNext() )
			{
				reader.close();
				if ( rolling )
					reader = ni.getNumberReader();
			}
			return reader.readLong();
		} catch (NumberStoreException e) {
			throw new GeneratorException(e);
		}
	}
	
	private void initGenerator(boolean isReinit) throws GeneratorException
	{
		Object o = parameters.get(NUMBERSOURCE);
		if ( o == null )
			throw new GeneratorException(GeneratorException.NULL_PARAMETER_ERROR, NUMBERSOURCE);
		ni = (NumberInterface)o;
		o = parameters.get(ROLLING);
		if ( o == null )
			throw new GeneratorException(GeneratorException.NULL_PARAMETER_ERROR, ROLLING);
		rolling = ((Boolean)o).booleanValue();
		
		try {
			if ( reader != null )
				reader.close();
			reader = ni.getNumberReader();
		} catch (NumberStoreException e) {
			throw new GeneratorException(e);
		}
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
	public void rawFill(Object tablica) throws GeneratorException {
		try {
			if ( tablica instanceof int[] ) {
				int [] tmp = Convert.tryToTInt(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = getInt();
			} else if ( tablica instanceof float[] ) {
				float [] tmp = Convert.tryToTFloat(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = getFloat();
			} else if ( tablica instanceof long[] ) {
				long [] tmp = Convert.tryToTLong(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = getLong();
			} else if ( tablica instanceof double[] ) {
				double [] tmp = Convert.tryToTDouble(tablica);
				for ( int i = 0; i < tmp.length; i++ )
					tmp[i] = getDouble();
			} 
		} catch (ClassCastException e) {
			throw new GeneratorException(e);
		}
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.GeneratorInterface#rawFill(pk.ie.proj.tools.stream.NumberWriter, pk.ie.proj.enumeration.ClassEnumerator, int)
	 */
	@Override
	public void rawFill(NumberWriter writer, ClassEnumerator cl, int size)
			throws GeneratorException {
		try {
			if ( cl == ClassEnumerator.INTEGER ) {
				while ( size-- > 0 )
					writer.write(getInt());
			} else if ( cl == ClassEnumerator.FLOAT ) {
				while ( size-- > 0 )
					writer.write(getFloat());
			} else if ( cl == ClassEnumerator.LONG ) {
				while ( size-- > 0 )
					writer.write(getLong());
			} else if ( cl == ClassEnumerator.DOUBLE ) {
				while ( size-- > 0 )
					writer.write(getDouble());
			} 
		}
		catch (ClassCastException e) {
			throw new GeneratorException(e);
		} catch (IOException e) {
			throw new GeneratorException(e);
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
