/*
 * Generator rozkładu normalnego. Stosuje metodę C. Hastingsa.
 * W przybliżeniu powstaje rozkład Gauss'a N(0,1), czyli podstawie
 * 0.0 i odchyleniu stadardowym 1.0
 */

package pl.projewski.generator.generator;

import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.stream.NumberWriter;

public class GeneratorSimConst
	extends GeneratorInterface
{
	
	public final static String CONST = "constans";
	public final static String GENERATOR = "generator";
	public final static String MULTIP = "multiplier";
	public final static String XN = "zm. losowa";
	
	public void initParameterInterface()
	{
		parameters.put(CONST, Double.valueOf(0.0));
		parameters.put(GENERATOR, null);
		parameters.put(MULTIP, Double.valueOf(1.0));
		parameters.put(XN, Double.valueOf(0.0));
	}


	public Class<?> [] getAllowedClass(String param) throws ParameterException {
		Class<?> [] tmp;
		if ( param.equals(XN)
				|| param.equals(CONST)
				|| param.equals(MULTIP)
			)
		{
			tmp = new Class[1];
			tmp[0] = Double.class;
		}
		else if ( param.equals(GENERATOR) )
		{
			tmp = new Class[1];
			tmp[0] = GeneratorInterface.class;
		}
		else
		{
			return new Class[0];
		}
		return tmp;
	}

	public void init() 
		throws GeneratorException
	{
		Object generator = parameters.get(GENERATOR);
		if ( parameters.get(CONST) == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, CONST
					);

		if ( parameters.get(MULTIP) == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, MULTIP
					);
		
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
			parameters.put(XN, getAsObject( Double.class ));
		}	
		else
			throw new GeneratorException(
						GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
						GENERATOR);
	}

	public void reinit()
		throws GeneratorException
	{
		Object generator = parameters.get(GENERATOR);
		if ( parameters.get(CONST) == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, CONST
					);

		if ( parameters.get(MULTIP) == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, MULTIP
					);
		
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
			parameters.put(XN, getAsObject( Double.class ));
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
		Object objConst = parameters.get(CONST);
		Object objMultip = parameters.get(MULTIP);
		Object generator = parameters.get(GENERATOR);
		if ( objConst == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, CONST
					);

		if ( objMultip == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, MULTIP
					);

		if ( generator == null )
			throw new GeneratorException(
						GeneratorException.NULL_PARAMETER_ERROR, GENERATOR
					);

		if ( !(generator instanceof GeneratorInterface) )
			throw new GeneratorException(
						GeneratorException.WRONG_PARAMETER_INSTANCE_ERROR,
						GENERATOR	);

		GeneratorInterface gi = (GeneratorInterface)generator;
		double g = gi.getDouble();
		double r = Convert.tryToDouble( objConst );
		g *= Convert.tryToDouble( objMultip );
		r += g;
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
