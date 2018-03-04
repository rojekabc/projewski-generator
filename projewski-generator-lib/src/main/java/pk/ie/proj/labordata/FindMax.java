/*
 */
package pk.ie.proj.labordata;
import java.io.FileNotFoundException;
import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

public class FindMax
	extends LaborDataInterface
{
	Number maksimum = null;

	public void initParameterInterface()
	{
	}
/*
	// podawanie danych wej�ciowych
	public void setInputData(NumberStoreOne data, boolean isLast)
		throws LaborDataException
	{
		try {
			Class cl = data.storedType();
			Object objMax = parameters.get(MAX);
			if (cl == null)
				return; // TODO: Throw info about null data

			if ( cl == int[].class )
			{
				int max = 0;
				int [] inp = data.getTInt();

				// TODO: Check, that it's an Integer class
				if ( objMax != null )
					max = ((Integer)objMax).intValue();
				else
					max = inp[0];

				for ( int i=1; i<inp.length; i++ )
				{
					if ( max < inp[i] )
						max = inp[i];
				}

				parameters.put(MAX, new Integer( max ));
			}
			else if ( cl == long[].class )
			{
				long [] inp = data.getTLong();
				long max = 0;

				if ( objMax != null )
					max = ((Long)objMax).longValue();
				else
					max = inp[0];

				for ( int i=1; i<inp.length; i++ )
					if ( max < inp[i] )
						max = inp[i];

				parameters.put(MAX, new Long( max ));
			}
			else if ( cl == float[].class )
			{
				float [] inp = data.getTFloat();
				float max = 0.0f;

				if ( objMax != null )
					max = ((Float)objMax).floatValue();
				else
					max = inp[0];

				for ( int i=1; i<inp.length; i++ )
					if ( max < inp[i] )
						max = inp[i];

				parameters.put(MAX, new Float( max ));
			}
			else if ( cl == double[].class )
			{
				double [] inp = data.getTDouble();
				double max = 0.0;

				if ( objMax != null )
					max = ((Double)objMax).doubleValue();
				else
					max = inp[0];

				for ( int i=1; i<inp.length; i++ )
					if ( max < inp[i] )
						max = inp[i];

				parameters.put(MAX, new Double( max ));
			}
			else
				return; // TODO: Exception Unknown Input Data Type
//			return new NumberStoreOne(outData);
		} catch (NumberStoreException e) {
			throw new LaborDataException(
						LaborDataException.NUMBER_STORE_ERROR,	e.toString()
					);
		} catch (ClassCastException e) {
			throw new LaborDataException(
						LaborDataException.WRONG_TYPE_ERROR
					);
		}
//		throw new LaborDataException(
//				LaborDataException.NOT_IMPLEMENTED_ERROR);
	}
	
	// pobieranie danych wynikowych
	public boolean getOutputData(NumberStoreOne outData)
		throws LaborDataException
	{
		Object out = parameters.get(MAX);

		// No more data for send, so give a false code
		if ( out == null )
			return false;
		if (outData == null)
			return false; // TODO: NULL Ecxeption
		try
		{
			
			if (out instanceof Integer)
				outData.set( ((Integer)out).intValue() );
			else if (out instanceof Long)
				outData.set( ((Long)out).longValue() );
			else if (out instanceof Float)
				outData.set( ((Float)out).floatValue() );
			else if (out instanceof Double)
				outData.set( ((Double)out).doubleValue() );
			else
				return false; // TODO: Wrong Output Data
		}
		catch ( NumberStoreException e )
		{
			System.out.println("FindMax.java:getOutputData - EXCEPTION ??");
			return false; // TODO: throw exception
		}

		parameters.put(MAX, null);
		return true;
	}
*/
	public Class<?> [] getAllowedClass(String param)
	{
		return new Class[0];
	}

	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException {
		// No more data for send, so give a false code
		if ( maksimum == null )
			return false;
		if (data == null)
			return false; // TODO: NULL Ecxeption
		
		try
		{
			NumberWriter writer = data.getNumberWriter();
			
			if (maksimum instanceof Integer)
			{
				writer.write( ((Integer)maksimum).intValue() );
				data.setStoreClass(ClassEnumerator.INTEGER);
			}
			else if (maksimum instanceof Long)
			{
				writer.write( ((Long)maksimum).longValue() );
				data.setStoreClass(ClassEnumerator.LONG);
			}
			else if (maksimum instanceof Float)
			{
				writer.write( ((Float)maksimum).floatValue() );
				data.setStoreClass(ClassEnumerator.FLOAT);
			}
			else if (maksimum instanceof Double)
			{
				writer.write( ((Double)maksimum).doubleValue() );
				data.setStoreClass(ClassEnumerator.DOUBLE);
			}
			else
				return false; // TODO: Wrong Output Data
			data.setSize(1);
			writer.close();
		}
		catch (FileNotFoundException e) {
			throw new LaborDataException(e);
		} catch (IOException e) {
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		}

		return true;
	}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {
		NumberReader reader = null;
		try {
			ClassEnumerator cl = data.getStoreClass();
			if (cl == null)
				return; // TODO: Throw info about null data

			reader = data.getNumberReader();
			
			if ( cl == ClassEnumerator.INTEGER )
			{
				int max = 0;
				int tmp;
				while ( reader.hasNext() )
				{
					tmp = reader.readInt();
					if ( max < tmp )
						max = tmp;
				}
				maksimum = Integer.valueOf( max );
			}
			else if ( cl == ClassEnumerator.LONG )
			{
				long max = 0;
				long tmp;
				while ( reader.hasNext() )
				{
					tmp = reader.readLong();
					if ( max < tmp )
						max = tmp;
				}
				maksimum = Long.valueOf( max );
			}
			else if ( cl == ClassEnumerator.FLOAT )
			{
				float max = 0;
				float tmp;
				while ( reader.hasNext() )
				{
					tmp = reader.readFloat();
					if ( max < tmp )
						max = tmp;
				}
				maksimum = Float.valueOf( max );
			}
			else if ( cl == ClassEnumerator.DOUBLE )
			{
				double max = 0;
				double tmp;
				int x = 0;
				while ( reader.hasNext() )
				{
					tmp = reader.readDouble();
					if ( max < tmp )
						max = tmp;
					x++;
				}
				maksimum = Double.valueOf( max );
			}
			else
				return; // TODO: Exception Unknown Input Data Type
//			return new NumberStoreOne(outData);
		} catch (ClassCastException e) {
			throw new LaborDataException(
						LaborDataException.WRONG_TYPE_ERROR
					);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);			
		} finally {
			Mysys.closeQuiet(reader);
		}

	}
	
	public Number getMaximum()
	{
		return maksimum;
	}
}
