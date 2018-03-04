/*
 * Obilczanie cz�stotliwo�ci wyst�pie� danych
 * Nie implementuje funkcji przechowywania danych.
 * Dane s� przeliczane bezpo�rednio.
 */
//TODO: Obliczenia dla zmiennych przecinkowych po ustaleniu parametru
//	dok�adno�ci, lub liczby podzia�u przedzia�u pomiaru na mniejsze cz�ci
//TODO: Je�eli zakres jest zbyt du�y mo�e oferowa� zapami�tywanie
// niekt�rych tylko pozycji lub zg�osi� wyj�tek, �e nie jest w stanie
// zapami�ta�
package pk.ie.proj.labordata;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

// 1. Ustalenie minimum i maksimum danych oraz ich liczebno�ci
// 1a. Przechowywanie podawanych danych w pliku tymczasowym
// 1b. Je�eli minimum i maksimum zadane przez u�ytkownika to nie poszukuj
// --2. Ustalenie rozst�pu cechy
// --2a. Posortowa� dane
// --2b. Znale�� rozst�p miedzy warto�ciami
// --2c. Znale�� NWD rozst�p�w mi�dzy warto�ciami
// --2d. Jest on r�wnoznaczny z rozst�pem cechy
// 3. Utworzenie klas
// 4. zliczenie warto�ci w klasach
// Rozst�p dla ca�kowitych b�dzie zawsze 1, fla float ..., double ....
// Nie powinno byc wi�cej klas ni� 30

public class Frequency
	extends LaborDataInterface
{
	public static final String MINIMUM = "Minimum";
	public static final String MAXIMUM = "Maksimum";
	public static final String CLASSAMMOUNT = "Liczebność";

	public static final int MAXIMUMCLASSAMMOUNT = 100;
	public static final int INTQUALITY = 1;
	public static final long LONGQUALITY = 1;
	public static final float FLOATQUALITY = 0.0001f;
	public static final double DOUBLEQUALITY = 0.000001;
	
	private int [] frequency;
	
	public void initParameterInterface()
	{
		parameters.put(MINIMUM, null);
		parameters.put(MAXIMUM, null);
		parameters.put(CLASSAMMOUNT, null);
	}

	protected int[] countForInt(NumberReader numberReader)
	{
		int min, max;
		int clnum = MAXIMUMCLASSAMMOUNT;
		double [] classLevel; // poziom danej klasy
		
		try
		{
			min = Convert.tryToInt(parameters.get(MINIMUM));
			max = Convert.tryToInt(parameters.get(MAXIMUM));

			/* Wyznaczenie liczby grup podstawowe */
			clnum = Convert.tryToInt( parameters.get(CLASSAMMOUNT) );
			
			/* Ograniczenie liczby grup */
			if ( clnum > MAXIMUMCLASSAMMOUNT )
				clnum = MAXIMUMCLASSAMMOUNT;
			
			// Ustalenie prog�w dla ka�dego poziomu
			int j;
			classLevel = new double[clnum];
			for (j=0; j<clnum; j++)
				classLevel[j] = ((double)min) + (1.0d/clnum)*(j+1)*(max-min);

			/* Zlicznie */
			frequency = new int[clnum];
			while ( numberReader.hasNext() )
			{
				int tmp = numberReader.readInt();

				// Najpierw wyjdz poza minimum, jesli taka konieczno��
				if ( tmp < min )
					continue;
				// Teraz na maksimum
				if ( tmp > max )
					continue;
				j=0;
				// Teraz kt�ry przedzia�
				while (tmp > classLevel[j] )
					j++;
//				while ( ((double)(tmp-min))/((double)(max-min)) > jmp*(j+1) )
//					j++;
				frequency[j]++;
			}
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return frequency;
	}
	protected int[] countForLong(NumberReader numberReader)
	{
		long min, max;
		int clnum = MAXIMUMCLASSAMMOUNT;
		double [] classLevel; // poziom danej klasy

		try
		{
			min = Convert.tryToLong(parameters.get(MINIMUM));
			max = Convert.tryToLong(parameters.get(MAXIMUM));
			
			/* Wyznaczenie liczby grup podstawowe */
			clnum = Convert.tryToInt( parameters.get(CLASSAMMOUNT) );
			
			/* Ograniczenie liczby grup */
			if ( clnum > MAXIMUMCLASSAMMOUNT )
				clnum = MAXIMUMCLASSAMMOUNT;
			
			int j;
			classLevel = new double[clnum];
			for (j=0; j<clnum; j++)
				classLevel[j] = ((double)min) + (1.0d/clnum)*(j+1)*(max-min);

			/* Zlicznie */
			frequency = new int[clnum];
			
			while ( numberReader.hasNext() )
			{
				long tmp = numberReader.readLong();

				// Najpierw wyjdz poza minimum, jesli taka konieczno��
				if ( tmp < min )
					continue;
				// Teraz na maksimum
				if ( tmp > max )
					continue;
					// Teraz kt�ry przedzia�
				j=0;
				while (  tmp > classLevel[j] )
					j++;
				frequency[j]++;
			}
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return frequency;
	}
	protected int[] countForFloat(NumberReader numberReader)
	{
		float min, max;
		int clnum = MAXIMUMCLASSAMMOUNT;
		double [] classLevel; // poziom danej klasy

		try
		{
			min = Convert.tryToFloat(parameters.get(MINIMUM));
			max = Convert.tryToFloat(parameters.get(MAXIMUM));
			
			/* Wyznaczenie liczby grup podstawowe */
			clnum = Convert.tryToInt( parameters.get(CLASSAMMOUNT) );

			/* Ograniczenie liczby grup */
			if ( clnum > MAXIMUMCLASSAMMOUNT )
				clnum = MAXIMUMCLASSAMMOUNT;

			int j;
			classLevel = new double[clnum];
			for (j=0; j<clnum; j++)
				classLevel[j] = ((double)min) + (1.0d/clnum)*(j+1)*(max-min);

			/* Zlicznie */
			frequency = new int[clnum];
			while ( numberReader.hasNext() )
			{
				float tmp = numberReader.readFloat();

				j=0;
				// Najpierw wyjdz poza minimum, jesli taka konieczno��
				if ( tmp < min )
					continue;
				// Teraz na maksimum
				if ( tmp > max )
					continue;
				// Teraz kt�ry przedzia�
				while ( tmp > classLevel[j] )
					j++;
				frequency[j]++;
			}
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return frequency;
	}
	protected int[] countForDouble(NumberReader numberReader)
	{
		double min, max;
		int clnum = MAXIMUMCLASSAMMOUNT;
		double [] classLevel; // poziom danej klasy

		try
		{
			min = Convert.tryToDouble(parameters.get(MINIMUM));
			max = Convert.tryToDouble(parameters.get(MAXIMUM));

			/* Wyznaczenie liczby grup podstawowe */
			clnum = Convert.tryToInt( parameters.get(CLASSAMMOUNT) );

			/* Ograniczenie liczby grup */
			if ( clnum > MAXIMUMCLASSAMMOUNT )
				clnum = MAXIMUMCLASSAMMOUNT;

			int j;
			classLevel = new double[clnum];
			for (j=0; j<clnum; j++)
				classLevel[j] = ((double)min) + (1.0d/clnum)*(j+1)*(max-min);
			
			/* Zlicznie */
			frequency = new int[clnum];
			while ( numberReader.hasNext() )
			{
				double tmp = numberReader.readDouble();

				j=0;
				// Najpierw wyjdz poza minimum, jesli taka konieczno��
				if ( tmp < min )
					continue;
				if ( tmp > max )
					continue;
				// Teraz kt�ry przedzia�
				while ( tmp > classLevel[j] )
					j++;
				frequency[j]++;
			}
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return frequency;
	}

	public Class<?> [] getAllowedClass(String param) {
		if ( param.equals(CLASSAMMOUNT) )
			return new Class<?> [] {Integer.class};
		else
			return new Class<?> [] {Double.class};
	}

	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException {
		NumberWriter writer = null;
		try {			
			if ( frequency == null )
				return false;
			
			data.setStoreClass(ClassEnumerator.INTEGER);
			data.setSize(frequency.length);
			writer = data.getNumberWriter();
			for ( int i=0; i<frequency.length; i++ )
				writer.write(frequency[i]);
			return true;
		}
		catch ( Exception e )
		{
			throw new LaborDataException(e);
		} finally {
			Mysys.debugln("Closing stream for writing freq");
			Mysys.closeQuiet(writer);
		}
	}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {

		// LaborDataInterface labordata = null;
		NumberReader reader = null;
		
		try
		{
			// wyznaczenie minimum
			if ( parameters.get(MINIMUM) == null )
			{
				FindMin min = new FindMin();
				min.setInputData(data);
				parameters.put(MINIMUM, min.getMinimum());
			}
			
			// wyznaczenie maksimum
			if ( parameters.get(MAXIMUM) == null )
			{
				FindMax max = new FindMax();
				max.setInputData(data);
				parameters.put(MAXIMUM, max.getMaximum());
			}
			
			// ustalenie classammount
			if ( parameters.get(CLASSAMMOUNT) == null )
				parameters.put(CLASSAMMOUNT, Integer.valueOf(20));
			
			// posortowanie otrzymanych danych - to jesli chcemy inaczej policzyc czestotliwosc
			// labordata = new ExternalSort();
			// labordata.setInputData( data );
			
			// obliczenie liczebnosci wystepowania
			ClassEnumerator c = data.getStoreClass();
			reader = data.getNumberReader();
			
			if ( c == ClassEnumerator.INTEGER )
				frequency = countForInt(reader);
			else if ( c == ClassEnumerator.LONG )
				frequency = countForLong(reader);
			else if ( c == ClassEnumerator.FLOAT )
				frequency = countForFloat(reader);
			else if ( c == ClassEnumerator.DOUBLE )
				frequency = countForDouble(reader);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(reader);
		}
		
	}
	
	public int [] getFrequency()
	{
		return frequency;
	}
}