/**
 * 
 */
package pl.projewski.generator.labordata;

import java.io.FileNotFoundException;
import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberWriter;

/**
 * @author projewski
 *
 * Testowanie szybkoci generowania danych.
 * Dane wyjciowe podawane sš w milisekundach.
 * Testy wykonywane sš kolejno dla typów int, long, float, double poprzez pobieranie
 * pojedyncze (nie wykonywane jest rawFill)
 */
public class TestSpeed extends LaborDataInterface {

	public static final String GENERATOR = "Generator";
	public static final String NUMBEROFTESTS = "Liczba testów";
	public static final String NUMBEROFGENERATION = "Liczba generacji";
	public static final String TESTINTEGER = "Testuj getInt";
	public static final String TESTLONG = "Testuj getLong";
	public static final String TESTFLOAT = "Testuj getFloat";
	public static final String TESTDOUBLE = "Testuj getDouble";

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.LaborDataInterface#getOutputData(pk.ie.proj.interfaces.NumberInterface)
	 */
	@Override
	public boolean getOutputData(NumberInterface numberinterface)
			throws LaborDataException {
		NumberWriter writer = null;
		try {
			// sprawdzenie i ustalenie parametrów poczštkowych
			Object o = parameters.get(GENERATOR);
			if ( o == null )
				throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, GENERATOR);
			GeneratorInterface gi = (GeneratorInterface)o;
			
			o = parameters.get(NUMBEROFGENERATION);
			if ( o == null )
				throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, NUMBEROFGENERATION);
			long numGen = ((Long)o).longValue();
			
			o = parameters.get(NUMBEROFTESTS);
			if ( o == null )
				throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, NUMBEROFTESTS);
			int numTest = ((Integer)o).intValue();
			
			// licznik zapisywanych danych
			int dataSize = 0;
			
			// uzyskanie strumienia wyjciowego
			writer = numberinterface.getNumberWriter();
			// nadanie typu tworzonych danych
			numberinterface.setStoreClass(ClassEnumerator.LONG);

			// inicjowanie generatora
			gi.init();
			long timeCounter;
			
			// testy dla int
			if ( (Boolean)parameters.get(TESTINTEGER) )
				for ( int j=0; j<numTest; j++ )
				{
					gi.reinit();
					long i = numGen;
					timeCounter = System.currentTimeMillis();
					try {
						while (i-- > 0)
							gi.getInt();
						timeCounter = System.currentTimeMillis() - timeCounter;
					} catch ( GeneratorException e ) {
						timeCounter = -1;
					}
					dataSize++;
					writer.write(timeCounter);
				}
			
			// testy dla long 
			if ( (Boolean)parameters.get(TESTLONG) )
				for ( int j=0; j<numTest; j++ )
				{
					gi.reinit();
					long i = numGen;
					timeCounter = System.currentTimeMillis();
					try {
						while (i-- > 0)
							gi.getLong();
						timeCounter = System.currentTimeMillis() - timeCounter;
					} catch ( GeneratorException e ) {
						timeCounter = -1;
					}
					dataSize++;
					writer.write(timeCounter);
				}
			
			// testy dla float 
			if ( (Boolean)parameters.get(TESTFLOAT) )
				for ( int j=0; j<numTest; j++ )
				{
					gi.reinit();
					long i = numGen;
					timeCounter = System.currentTimeMillis();
					try {
						while (i-- > 0)
							gi.getFloat();
						timeCounter = System.currentTimeMillis() - timeCounter;
					} catch ( GeneratorException e ) {
						timeCounter = -1;
					}
					dataSize++;
					writer.write(timeCounter);
				}
			
			// testy dla double 
			if ( (Boolean)parameters.get(TESTDOUBLE) )
				for ( int j=0; j<numTest; j++ )
				{
					gi.reinit();
					long i = numGen;
					timeCounter = System.currentTimeMillis();
					try {
						while (i-- > 0)
							gi.getDouble();
						timeCounter = System.currentTimeMillis() - timeCounter;
					} catch ( GeneratorException e ) {
						timeCounter = -1;
					}
					dataSize++;
					writer.write(timeCounter);
				}
			
			numberinterface.setSize(dataSize);
				
		} catch (FileNotFoundException e) {
			throw new LaborDataException(e);
		} catch (GeneratorException e) {
			throw new LaborDataException(e);
		} catch (IOException e) {
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(writer);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.LaborDataInterface#setInputData(pk.ie.proj.interfaces.NumberInterface)
	 */
	@Override
	public void setInputData(NumberInterface numberinterface)
			throws LaborDataException {

	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.abstracts.ParameterAbstract#initParameterInterface()
	 */
	@Override
	public void initParameterInterface() {
		parameters.put(GENERATOR, null);
		parameters.put(NUMBEROFTESTS, Integer.valueOf(10));
		parameters.put(NUMBEROFGENERATION, Long.valueOf(1000000));
		parameters.put(TESTINTEGER, Boolean.valueOf(true));
		parameters.put(TESTLONG, Boolean.valueOf(true));
		parameters.put(TESTFLOAT, Boolean.valueOf(true));
		parameters.put(TESTDOUBLE, Boolean.valueOf(true));
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
	 */
	public Class<?>[] getAllowedClass(String param) throws ParameterException {
		if ( param.equals(GENERATOR) )
			return new Class<?> [] {GeneratorInterface.class};
		else if ( param.equals(NUMBEROFGENERATION) )
			return new Class<?> [] {Long.class};
		else if ( param.equals(NUMBEROFTESTS) )
			return new Class<?> [] {Integer.class};
		else if ( param.equals(TESTINTEGER) || param.equals(TESTLONG)
				|| param.equals(TESTFLOAT) || param.equals(TESTDOUBLE))
			return new Class<?> [] {Boolean.class};
		else
			return new Class<?> [] {};
	}

}
