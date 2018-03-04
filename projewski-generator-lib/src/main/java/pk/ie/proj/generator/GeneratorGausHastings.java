/*
 * Generator rozkładu normalnego. Stosuje metodę C. Hastingsa.
 * W przybliżeniu powstaje rozkład Gauss'a N(0,1), czyli podstawie
 * 0.0 i odchyleniu stadardowym 1.0
 */

package pk.ie.proj.generator;

import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberWriter;

public class GeneratorGausHastings
	extends GeneratorInterface
{
	
	public final static String XN = "zm. losowa";
	public final static String GENERATOR = "generator";
	
	public void initParameterInterface()
	{
		this.parameters.put(XN, new Double(0.0));
		this.parameters.put(GENERATOR, null);
	}

	public Class<?> [] getAllowedClass(String param) {
		Class<?> [] tmp;
		
		if ( param.equals(XN) )
		{
			tmp = new Class[2];
			tmp[0] = Float.class;
			tmp[1] = Double.class;
		}
		else if ( param.equals(GENERATOR) )
		{
			tmp = new Class[1];
			tmp[0] = GeneratorInterface.class;
		}
		else
		{
			tmp = new Class[0];
		}
		return tmp;
	}

	public void init() 
		throws GeneratorException
	{
		Object generator = this.parameters.get(GENERATOR);
		if ( generator == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
					);
		
		if ( generator instanceof GeneratorInterface )
		{
			// inicjuj generator podrzędny
			GeneratorInterface gi = (GeneratorInterface)generator;
			gi.init();
			// pobierz pierwszą wartość
			this.parameters.put(XN, getAsObject( Double.class ));
		}	
		else
			throw new GeneratorException(
						GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
						GENERATOR );
	}

	public void reinit()
		throws GeneratorException
	{
		Object generator = this.parameters.get(GENERATOR);
		if ( generator == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
					);
		
		if ( generator instanceof GeneratorInterface )
		{
			// reinicjuj generator podrzędny
			GeneratorInterface gi = (GeneratorInterface)generator;
			gi.reinit();
			// pobierz pierwszą wartość
			this.parameters.put(XN, getAsObject( Double.class ));
		}	
		else
			throw new GeneratorException(
						GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
						GENERATOR);
	}

	public long getLong()
		throws GeneratorException
	{
			throw new GeneratorException(
						GeneratorException.NOT_ALLOWED_USE_ERROR,
						"Long value generation" );
	}

	public int getInt()
		throws GeneratorException
	{
			throw new GeneratorException(
						GeneratorException.NOT_ALLOWED_USE_ERROR,
						"Integer value generation" );
	}
	
	public float getFloat()
		throws GeneratorException
	{
		float r = (float)getDouble();
		return r;
	}
	
	public double getDouble()
		throws GeneratorException
	{
		Object generator = this.parameters.get(GENERATOR);
		if ( generator == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
					);

		if ( !(generator instanceof GeneratorInterface) )
			throw new GeneratorException(
						GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
						GENERATOR);

		GeneratorInterface gi = (GeneratorInterface)generator;
		double g = gi.getDouble();
		double r = 0.0;
		double y = 0.0;

		if ( g >= 0.5 )
		{
			y = Math.sqrt( -2 * Math.log( 1-g ) );
			r = y - ( (2.30753 + 0.27061*y) / (1 + 0.99229*y + 0.04481*y*y ) );
		}
		else
		{
			y = Math.sqrt( -2 * Math.log( g ) );
			r = ( (2.30753 + 0.27061*y) / (1 + 0.99229*y + 0.04481*y*y ) ) - y;
		}

		return r;
	}

	public void rawFill(Object tablica)
		throws GeneratorException
	{
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
		} catch (ClassCastException e) {}
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
}
