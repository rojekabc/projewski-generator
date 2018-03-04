package pl.projewski.generator.labordata;

import java.io.IOException;

import pk.ie.proj.distribution.Uniform;
import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.NumberStoreOne;
import pk.ie.proj.tools.stream.NumberWriter;
import pl.projewski.generator.distribution.Uniform;

/*
 * Klasa wykonuje test chi-square na wybranym generatorze. Przed ka�dym
 * rozpocz�ciem testu generator jest inicjowany od nowa i tym sposobem
 * wykonywane jest pobranie kolejnej pr�by testowej. Dane wej�ciowe nie s�
 * uwzdl�dniane.
 */

public class TestChiSquare
	extends LaborDataInterface
{
	ChiSquare _chisquare = null;

	public static final String DISTRIBUTION = "rozkład oczekiwany";
	public static final String TESTAMMOUNT = "liczba testów";
	public static final String GENERATOR = "generator";
	public static final String GENERATIONAMMOUNT = "liczba generacji";
	public static final String V = "liczba stopni swobody";

	public void initParameterInterface()
	{
		parameters.put(DISTRIBUTION, new Uniform());
		parameters.put(TESTAMMOUNT, Integer.valueOf(15));
		parameters.put(GENERATOR, null);
		parameters.put(GENERATIONAMMOUNT, null);
		parameters.put(V, Integer.valueOf(20));
	}

	public void setInputData(NumberStoreOne data, boolean isLast)
		throws LaborDataException
	{
	}

	public boolean getOutputData(NumberInterface data) throws LaborDataException
	{
		NumberWriter resWriter = null;
		try
		{
			final Object generator = parameters.get(GENERATOR);
			final Object ammountGeneration = parameters.get(GENERATIONAMMOUNT);
			final Object distribution = parameters.get(DISTRIBUTION);
			final Object paramV = parameters.get(V);
			
			if ( generator == null )
				throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, GENERATOR);
			if ( ammountGeneration == null )
				throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, GENERATIONAMMOUNT);
			if ( distribution == null )
				throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, DISTRIBUTION);
			
			final ChiSquare _chisquare = new ChiSquare();
			
			// Wyznacz liczbe testow do przeprowadzenia
			int n = Convert.tryToInt(parameters.get(TESTAMMOUNT));
			
			// inicjuj proces obliczania chisquare
			_chisquare.setParameter(ChiSquare.DISTRIBUTION, distribution);
			
//			double [] result = new double [n];
			GeneratorInterface gi = (GeneratorInterface)generator;
			Frequency freq = new Frequency();
			freq.setParameter(Frequency.CLASSAMMOUNT,
				Integer.valueOf(Convert.tryToInt(paramV)+1));
//			NumberStoreOne freqStore = new NumberStoreOne();
//			NumberStoreOne chisquareout = new NumberStoreOne();
			resWriter = data.getNumberWriter();
			data.setStoreClass(ClassEnumerator.DOUBLE);
			data.setSize(n);

			// Inicjowanie generatora przed rozpocz�ciem test�w
			gi.init();

			// wykonywanie poszczegolnych testow
			while ( n-- > 0 )
			{
				Mysys.debugln("Test: " + n);
				int genn = Convert.tryToInt(ammountGeneration);
				GeneratedData gdtFreqIn = GeneratedData.createTemporary();
				NumberWriter writer = gdtFreqIn.getNumberWriter();
				// generowanie danych
				Mysys.debugln("Generowanie danych");
				gdtFreqIn.setSize(genn);
				gdtFreqIn.setStoreClass(ClassEnumerator.DOUBLE);
				gi.rawFill(writer, ClassEnumerator.DOUBLE, genn);
				writer.close();
				
				// zliczanie czestotliowsci
				Mysys.debugln("Zliczanie czestotliowci");
				freq.setInputData(gdtFreqIn);
				GeneratedData gdtFreqOut = GeneratedData.createTemporary();
				freq.getOutputData(gdtFreqOut);
				
				// wyznaczanie wyniku chisquare
				Mysys.debugln("Wyznaczanie chisquare rozmiar = " + gdtFreqOut.getSize());
				_chisquare.setInputData(gdtFreqOut);
				resWriter.write(_chisquare.getChiSquare());
				
				// usuwanie tymczasowych danych
				if ( !gdtFreqIn.delete() )
					Mysys.error("Nieudane usuwanie tymczasowych danych");
				if ( !gdtFreqOut.delete() )
					Mysys.error("Nieudane usuwanie tymczasowych danych");
				// kolejny test
			}
			// sprzatanie
			return true;
		} catch (ParameterException e) {
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} catch (IOException e) {
			throw new LaborDataException(e);
		} catch (GeneratorException e) {
			throw new LaborDataException(e);
		}
		finally {
			Mysys.closeQuiet(resWriter);
		}
	}

	public Class<?> [] getAllowedClass(String param)
	{
		if ( param.equals(GENERATOR))
			return new Class<?> [] {GeneratorInterface.class};
		else if ( param.equals(GENERATIONAMMOUNT) || param.equals(TESTAMMOUNT) || param.equals(V))
			return new Class<?> [] {Integer.class};
		else
			return new Class<?> [] {};
	}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {
		// TODO Auto-generated method stub
		
	}

}
