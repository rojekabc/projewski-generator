package pl.projewski.generator.labordata;
import java.io.IOException;
import java.util.Arrays;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

/*
 * Jest to sortowanie, które sortuje tylko obecnie otrzymany pakiet. Nie przechowuje
 * cišgu otrzymywanych pakietów !!!
 */
public class InternalSort
	extends LaborDataInterface
{
	public final static String REMOVETHESAME = "Usuń podobne";

	protected Object _result;
	
	public void initParameterInterface()
	{
		parameters.put(REMOVETHESAME, Boolean.valueOf(false));
	}
	
	
	/** M4_GEN_PI_GET_ALLOWED_CLASSES_I */
	public Class<?> [] getAllowedClass(String param)
		throws ParameterException
	{
		return new Class<?>[0];
	}

	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException {
		boolean remts = false;
		
		if ( parameters.get(REMOVETHESAME) != null )
			remts = ((Boolean)parameters.get(REMOVETHESAME)).booleanValue();
		
		NumberWriter writer = null;
		try
		{
			ClassEnumerator c = ClassEnumerator.getType( _result );
			int cnt = 0;
			writer = data.getNumberWriter();
			data.setStoreClass(c);
			
			if ( c == ClassEnumerator.INTEGER )
			{
				int [] a = (int[])_result;
				cnt = a.length;
				if ( remts )
				{
					int tmp = a[0];
					writer.write(tmp);
					for (int i=1; i<a.length; i++)
					{
						if ( tmp == a[i] )
						{
							cnt--;
							continue;
						}
						tmp = a[i];
						writer.write(tmp);
					}
				}
				else
				{
					writer.write(a);
				}
			}
			else if ( c == ClassEnumerator.LONG )
			{
				long [] a = (long[])_result;
				cnt = a.length;
				if ( remts )
				{
					long tmp = a[0];
					writer.write(tmp);
					for (int i=1; i<a.length; i++)
					{
						if ( tmp == a[i] )
						{
							cnt--;
							continue;
						}
						tmp = a[i];
						writer.write(tmp);
					}
				}
				else
				{
					writer.write(a);
				}
				
			}
			else if ( c == ClassEnumerator.FLOAT )
			{
				float [] a = (float[])_result;
				cnt = a.length;
				if ( remts )
				{
					float tmp = a[0];
					writer.write(tmp);
					for (int i=1; i<a.length; i++)
					{
						if ( tmp == a[i] )
						{
							cnt--;
							continue;
						}
						tmp = a[i];
						writer.write(tmp);
					}
				}
				else
				{
					writer.write(a);
				}				
			}
			else if ( c == ClassEnumerator.DOUBLE )
			{
				double [] a = (double[])_result;
				cnt = a.length;
				if ( remts )
				{
					double tmp = a[0];
					writer.write(tmp);
					for (int i=1; i<a.length; i++)
					{
						if ( tmp == a[i] )
						{
							cnt--;
							continue;
						}
						tmp = a[i];
						writer.write(tmp);
					}
				}
				else
				{
					writer.write(a);
				}				
			}
			data.setSize(cnt);
		} catch (IOException e) {
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(writer);
		}
		
		return true;
	}
	
	public void setInputData(NumberReader is, ClassEnumerator classEnumerator, int size) throws LaborDataException
	{
		try {
			if (classEnumerator == null)
				return; // TODO: Throw info about null data type

			if ( classEnumerator == ClassEnumerator.INTEGER )
			{
				int [] out = new int [size];
				if ( is.read(out) != size )
					return; // TODO: Brak danych

				// sortowanie
				Arrays.sort(out);
				
				_result = out;
			}
			else if ( classEnumerator == ClassEnumerator.LONG )
			{
				long [] out = new long[size];
				if ( is.read(out) != size )
					return;
				
				Arrays.sort(out);
				
				_result = out;
			}
			else if ( classEnumerator == ClassEnumerator.FLOAT )
			{
				float [] out = new float[size];
				if (is.read(out) != size )
					return;
				
				Arrays.sort(out);
				_result = out;
			}
			else if ( classEnumerator == ClassEnumerator.DOUBLE )
			{
				double [] out = new double[size];
				if ( is.read(out) != size )
					return;
				Arrays.sort(out);
				_result = out;
			}
			else
				return; // TODO: Exception Unknown Input Data Type
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);			
		}
		
	}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {
		NumberReader reader = null;
		try {
			reader = data.getNumberReader();
			setInputData(reader, data.getStoreClass(), data.getSize());
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(reader);
		}
	}

}
