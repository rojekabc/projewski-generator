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

public class FindMin extends LaborDataInterface
{
	private Number minimum = null;
	// public final static String MIN = "Minimum";

	public void initParameterInterface()
	{
//		parameters.put(MIN, null);
	}

	/*
	 * // podawanie danych wej�ciowych public void setInputData(NumberStoreOne
	 * data, boolean isLast) throws LaborDataException { try { Class cl =
	 * data.storedType(); Object objMin = parameters.get(MIN); if (cl == null)
	 * return; // TODO: Throw info about null data
	 * 
	 * if ( cl == int[].class ) { int min = 0; int [] inp = data.getTInt();
	 * 
	 * // TODO: Check, that it's an Integer class if ( objMin != null ) min =
	 * ((Integer)objMin).intValue(); else min = inp[0];
	 * 
	 * for ( int i=1; i<inp.length; i++ ) if ( min > inp[i] ) min = inp[i];
	 * 
	 * parameters.put(MIN, new Integer( min )); } else if ( cl == long[].class )
	 * { long [] inp = data.getTLong(); long min = 0;
	 * 
	 * if ( objMin != null ) min = ((Long)objMin).longValue(); else min =
	 * inp[0];
	 * 
	 * for ( int i=1; i<inp.length; i++ ) if ( min > inp[i] ) min = inp[i];
	 * 
	 * parameters.put(MIN, new Long( min )); } else if ( cl == float[].class ) {
	 * float [] inp = data.getTFloat(); float min = 0.0f;
	 * 
	 * if ( objMin != null ) min = ((Float)objMin).floatValue(); else min =
	 * inp[0];
	 * 
	 * for ( int i=1; i<inp.length; i++ ) if ( min > inp[i] ) min = inp[i];
	 * 
	 * parameters.put(MIN, new Float( min )); } else if ( cl == double[].class )
	 * { double [] inp = data.getTDouble(); double min = 0.0;
	 * 
	 * if ( objMin != null ) min = ((Double)objMin).doubleValue(); else min =
	 * inp[0];
	 * 
	 * for ( int i=1; i<inp.length; i++ ) if ( min > inp[i] ) min = inp[i];
	 * 
	 * parameters.put(MIN, new Double( min )); } else return; // TODO: Exception
	 * Unknown Input Data Type // return new NumberStoreOne(outData); } catch
	 * (NumberStoreException e) { throw new LaborDataException(
	 * LaborDataException.NUMBER_STORE_ERROR, e.toString() ); } catch
	 * (ClassCastException e) { throw new LaborDataException(
	 * LaborDataException.WRONG_TYPE_ERROR ); } // throw new LaborDataException(
	 * // LaborDataException.NOT_IMPLEMENTED_ERROR); }
	 * 
	 * // pobieranie danych wynikowych public boolean
	 * getOutputData(NumberStoreOne outData) throws LaborDataException { Object
	 * out = parameters.get(MIN);
	 * 
	 * if ( out == null ) return false; if (outData == null) return false; //
	 * TODO: NULL Ecxeption try {
	 * 
	 * if (out instanceof Integer) outData.set( ((Integer)out).intValue() );
	 * else if (out instanceof Long) outData.set( ((Long)out).longValue() );
	 * else if (out instanceof Float) outData.set( ((Float)out).floatValue() );
	 * else if (out instanceof Double) outData.set( ((Double)out).doubleValue()
	 * ); else return false; // TODO: Wrong Output Data } catch (
	 * NumberStoreException e ) {
	 * System.out.println("FindMax.java:getOutputData - EXCEPTION ??"); return
	 * false; // TODO: throw exception }
	 * 
	 * parameters.put(MIN, null); return true; }
	 */
	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException
	{
//		Object out = parameters.get(MIN);

		// No more data for send, so give a false code
		if (minimum == null)
			return false;
		if (data == null)
			return false; // TODO: NULL Ecxeption

		NumberWriter writer = null;
		try
		{
			writer = data.getNumberWriter();

			if (minimum instanceof Integer)
			{
				writer.write(((Integer) minimum).intValue());
				data.setStoreClass(ClassEnumerator.INTEGER);
			}
			else if (minimum instanceof Long)
			{
				writer.write(((Long) minimum).longValue());
				data.setStoreClass(ClassEnumerator.LONG);
			}
			else if (minimum instanceof Float)
			{
				writer.write(((Float) minimum).floatValue());
				data.setStoreClass(ClassEnumerator.FLOAT);
			}
			else if (minimum instanceof Double)
			{
				writer.write(((Double) minimum).doubleValue());
				data.setStoreClass(ClassEnumerator.DOUBLE);
			}
			else
				return false; // TODO: Wrong Output Data
			data.setSize(1);
		}
		catch (FileNotFoundException e)
		{
			throw new LaborDataException(e);
		}
		catch (IOException e)
		{
			throw new LaborDataException(e);
		}
		catch (NumberStoreException e)
		{
			throw new LaborDataException(e);
		}
		finally
		{
			Mysys.closeQuiet(writer);
		}

//		parameters.put(MIN, null);
		return true;
	}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException
	{
		NumberReader reader = null;
		try
		{
			ClassEnumerator cl = data.getStoreClass();
			if (cl == null)
				throw new LaborDataException(LaborDataException.NOT_IMPLEMENTED_ERROR);

			reader = data.getNumberReader();

			if (cl == ClassEnumerator.INTEGER)
			{
				int min = Integer.MAX_VALUE;
				int tmp;
				while (reader.hasNext())
				{
					tmp = reader.readInt();
					if (min > tmp)
						min = tmp;
				}
				minimum = Integer.valueOf(min);
			}
			else if (cl == ClassEnumerator.LONG)
			{
				long min = Long.MAX_VALUE;
				long tmp;
				while (reader.hasNext())
				{
					tmp = reader.readLong();
					if (min > tmp)
						min = tmp;
				}
				minimum = Long.valueOf(min);
			}
			else if (cl == ClassEnumerator.FLOAT)
			{
				float min = Float.MAX_VALUE;
				float tmp;
				while (reader.hasNext())
				{
					tmp = reader.readFloat();
					if (min > tmp)
						min = tmp;
				}
				minimum = Float.valueOf(min);
			}
			else if (cl == ClassEnumerator.DOUBLE)
			{
				double min = Double.MAX_VALUE;
				double tmp;
				while (reader.hasNext())
				{
					tmp = reader.readDouble();
					if (min > tmp)
						min = tmp;
				}
				minimum = Double.valueOf(min);
			}
			else
				throw new LaborDataException(LaborDataException.NOT_IMPLEMENTED_ERROR);
			// return new NumberStoreOne(outData);
		}
		catch (ClassCastException e)
		{
			throw new LaborDataException(LaborDataException.WRONG_TYPE_ERROR);
		}
		catch (NumberStoreException e)
		{
			throw new LaborDataException(e);
		}
		finally
		{
			Mysys.closeQuiet(reader);
		}

	}
	
	public Number getMinimum()
	{
		return minimum;
	}

	public Class<?>[] getAllowedClass(String param)
	{
		return new Class[0];
	}
}
