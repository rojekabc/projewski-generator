/*
 * Sortowanie przeznaczone do porzšdkowania zbiorów liczb o dużej liczebności.
 * Ma na celu zachowanie zasobów pamięciowych w jak największym stopniu.
 * W zamian wykorzystuje przestrzeń dyskować.
 *
 * Algorytm, który tutaj implementuję polega na sortowaniu z zasadš Merge.
 * W pierwszym kroku jest rezerwowany odpowiedni bufor w pamięci.
 * Następnie do bufora tego sš dokładane przypływajace dane.
 * Do zadecydowania: Dane w buforze sš sortowane wraz z ich napływaniem lub
 * po zapełnieniu bufora. Krokiem wyjściowym tego etapu ma być uzyskanie
 * posortowanego bufora. Kolejny krok następuje po otrzymaniu jednego z dwóch
 * sygnałów. Jets to zapełenienie bufora lub otrzymanie sygnału końca danych
 * w strumieniu.
 * Wtedy posortowany(!) bufor jest zapisywany do pliku o unikatowej nazwie.
 * Nazwa pliku jest przechowywana.
 * Jeżeli nie otrzymano końca otrzymywania danych, bufor jest czyszczony
 * i ponownie ładuje się do niego dane.
 * Jeśli jest już koniec następuje wejście w fazę łączenia plików, merge.
 * Z dostępnych plików wynikowych odczytuje się jedną daną (lub więcej danych)
 * i te sortuje się, zapamiętując z jakiego pliku pochodzą. Bierze się
 * najmniejszš i przesyła do pliku wynikowego, w którym zostana zamieszczone
 * posortowane wszystkie dane. Teraz z pliku, który był źródłem
 * danej o najmniejszej wartości odczytuje się kolejną wartość i dodaje do
 * wartości z pozostałych plików, ale tak, aby była dodana w organizacji
 * posortowanej. Proces powtarza się, aż do otrzymania końców wszystkich
 * plików.
 */
package pk.ie.proj.labordata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;
import pk.ie.proj.tools.stream.SeparatorStreamReader;

public class ExternalSort
	extends LaborDataInterface
{
	java.util.Vector<File> vecFiles = new java.util.Vector<File>();
	ClassEnumerator storeCl = null;
//	java.io.File sortedStreamFile = null;
//	SeparatorStreamReader sortedStream = null;
//	int pkgSize = 0;

	public final static String REMOVETHESAME = "Usuń podobne";
		
	public void initParameterInterface()
	{
		parameters.put(REMOVETHESAME, Boolean.valueOf(false));
	}

	/** M4_GEN_PI_GET_ALLOWED_CLASSES_I */
	public Class<?> [] getAllowedClass(String param)
		throws ParameterException
	{
		return new Class[0];
	}

	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException {
		try {
				// Jeli nie ma strumienia danych posortowanych utwórz go i wykonaj zapisywanie
				// w nim posortowanych danych ze strumieni pomocniczych
	//			if ( sortedStream == null )
	//			{
					if ( vecFiles.isEmpty() )
						return false;
					if ( storeCl == null )
						return false; // TODO: Exception
	
					// Wynikowy plik sortowania
	//				java.io.File plik = java.io.File.createTempFile("extsort", ".gpr");
	//				java.io.FileOutputStream fos = new java.io.FileOutputStream( plik );
	//				java.io.DataOutputStream dos = new java.io.DataOutputStream( fos );
	
					ExternalSortQuery sortseed = new ExternalSortQuery(storeCl);
					// Liczba fragmentarycznych plików
					int n = vecFiles.size();
					// Tablica strumieni do odczytu
					// Jeżeli null, to dany strumień już sie wyczerpał
					for ( int i=0; i<n; i++ )
					{
						sortseed.addInputStream(
							new SeparatorStreamReader(
								new java.io.FileInputStream(
									vecFiles.get(i).toString())) );
					}
	
	/*				// Operacje sortowania strumieniowego
					if ( storeCl == int[].class )
					{
						while ( sortseed.isData() )
							dos.writeInt(sortseed.getInt());
					}
					else if (storeCl == float[].class )
					{
						while ( sortseed.isData() )
							dos.writeFloat(sortseed.getFloat());
					}
					else if (storeCl == long[].class )
					{
						while ( sortseed.isData() )
							dos.writeLong(sortseed.getLong());
					}
					else if (storeCl == double[].class )
					{
						while ( sortseed.isData() )
							dos.writeDouble(sortseed.getDouble());
					}
					else
						return false; // TODO: Exception
					dos.close();*/
	/*				// Zamień na strumień wejściowy
					sortedStream = new SeparatorStreamReader(
							new java.io.FileInputStream( plik ) );
					sortedStreamFile = plik;
	*/	//		}
				
				// Zapisywanie strumienia wyjsciowego z rownoczesnym sortowaniem
				// strumieniowym
				NumberWriter writer = data.getNumberWriter();
				boolean remts = false;
				if ( parameters.get(REMOVETHESAME) != null )
					remts = ((Boolean)parameters.get(REMOVETHESAME)).booleanValue();
	
				boolean start = false;
				int dataCnt = 0;
				
				if ( storeCl == ClassEnumerator.INTEGER )
				{
					int tmp;
					int preread = 0;
					while ( sortseed.isData() )
					{
						if ( start )
						{
							tmp = sortseed.getInt();
							if (( tmp == preread ) && ( remts ))
								continue;
							preread = tmp;
						}
						else
						{
							start = true;
							preread = sortseed.getInt();
						}
						dataCnt++;
						writer.write(preread);
					}
				}
				else if ( storeCl == ClassEnumerator.LONG )
				{
					long tmp;
					long preread = 0l;
					while ( sortseed.isData() )
					{
						if ( start )
						{
							tmp = sortseed.getLong();
							if (( tmp == preread ) && remts)
								continue;
							preread = tmp;
						}
						else
						{
							start = true;
							preread = sortseed.getLong();
						}
						dataCnt++;
						writer.write(preread);
					}
				}
				else if ( storeCl == ClassEnumerator.FLOAT )
				{
					float tmp;
					float preread = 0.0f;
					while ( sortseed.isData() )
					{
						if ( start )
						{
							tmp = sortseed.getFloat();
							if (( tmp == preread ) && remts)
								continue;
							preread = tmp;
						}
						else
						{
							start = true;
							preread = sortseed.getFloat();
						}
						dataCnt++;
						writer.write(preread);
					}
				}
				else if ( storeCl == ClassEnumerator.DOUBLE )
				{
					double tmp;
					double preread = 0.0;
					while ( sortseed.isData() )
					{
						if ( start )
						{
							tmp = sortseed.getDouble();
							if (( tmp == preread ) && (remts))
								continue;
							preread = tmp;
						}
						else
						{
							start = true;
							preread = sortseed.getDouble();
						}
						dataCnt++;
						writer.write(preread);
					}
				}
				sortseed = null;
				
				writer.close();
				data.setStoreClass(storeCl);
				data.setSize(dataCnt);
			} catch (FileNotFoundException e) {
				throw new LaborDataException(e);
			} catch (NumberStoreException e) {
				throw new LaborDataException(e);
			} catch (IOException e) {
				throw new LaborDataException(e);
			} finally {			
				for ( int i=0; i<vecFiles.size(); i++ )
				{
					if ( !vecFiles.get(i).delete() )
						Mysys.error("Nie moge usunac tymczasowego pliku sortowania");
				}
			}
			return true;
		}

	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {
		NumberReader gis = null;
		try
		{
			gis = data.getNumberReader();
			ClassEnumerator c = data.getStoreClass();
			int counter = data.getSize();
			InternalSort sorter = new InternalSort();
			sorter.setParameter(InternalSort.REMOVETHESAME, parameters.get(REMOVETHESAME));
			storeCl = data.getStoreClass();
			
			// Dzielenie duzych danych do sorotwania na mniejsze dla sortowania wewnetrznego
			while ( gis.hasNext() )
			{
				// przenoszenie pozyskanych danych do pliku do strumienia wejsciowego
				sorter.setInputData(gis, c, (counter > Mysys.getPackageSize() ? Mysys.getPackageSize() : counter));
				counter -= Mysys.getPackageSize();
				// sortowanie z zapisaniem do tymczasowego pliku
				GeneratedData iSort = GeneratedData.createTemporary();
				sorter.getOutputData(iSort);
				vecFiles.add(new File(iSort.getDataFilename()));				
			}
		}
		catch (ParameterException e)
		{
			throw new LaborDataException(e);
		}
		catch (java.io.IOException e)
		{
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(gis);
		}
	}
}

class ExternalSortQuery
{
	private ClassEnumerator storeCl;
	private Object tval;
	private SeparatorStreamReader [] tfis;

	public ExternalSortQuery(ClassEnumerator cl)
	{
		storeCl = cl;
		tfis = new SeparatorStreamReader[0];
		if ( cl == ClassEnumerator.INTEGER )
		{
			tval = new int [0];
		}
		else if ( cl == ClassEnumerator.LONG )
		{
			tval = new long [0];
		}
		else if ( cl == ClassEnumerator.FLOAT )
		{
			tval = new float [0];
		}
		else if ( cl == ClassEnumerator.DOUBLE )
		{
			tval = new double [0];
		}
	}

	// Dodaj do tablicy wraz z sortowaniem podle najmniejszy -> najwiekszy
	public void addInputStream(SeparatorStreamReader fis)
	{
		try {
		if ( fis == null )
			return;
		if ( !fis.hasNext() )
		{
			fis.close();
			return;
		}

		if ( storeCl == ClassEnumerator.INTEGER )
		{
			int [] tmp = (int[])tval;
			int [] tnew = new int[tmp.length+1];
			SeparatorStreamReader [] tfisnew =
				new SeparatorStreamReader[tfis.length+1];
			int val = fis.readInt();
			int i;
			for (i=0; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i] = tmp[i];
					tfisnew[i] = tfis[i];
				}
			}
			tnew[i] = val;
			tfisnew[i] = fis;
			i++;
			if ( tmp.length > 0 )
				for(; i<tnew.length; i++)
				{
					tnew[i] = tmp[i-1];
					tfisnew[i] = tfis[i-1];
				}
			tval = tnew;
			tfis = tfisnew;
		}
		else if ( storeCl == ClassEnumerator.LONG )
		{
			long [] tmp = (long[])tval;
			long [] tnew = new long[tmp.length+1];
			SeparatorStreamReader [] tfisnew =
				new SeparatorStreamReader[tfis.length+1];
			long val = fis.readLong();
			int i;
			for (i=0; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i] = tmp[i];
					tfisnew[i] = tfis[i];
				}
			}
			tnew[i] = val;
			tfisnew[i] = fis;
			i++;
			if ( tmp.length > 0 )
				for(; i<tnew.length; i++)
				{
					tnew[i] = tmp[i-1];
					tfisnew[i] = tfis[i-1];
				}
			tval = tnew;
			tfis = tfisnew;
		}
		else if ( storeCl == ClassEnumerator.FLOAT )
		{
			float [] tmp = (float[])tval;
			float [] tnew = new float[tmp.length+1];
			SeparatorStreamReader [] tfisnew =
				new SeparatorStreamReader[tfis.length+1];
			float val = fis.readFloat();
			int i;
			for (i=0; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i] = tmp[i];
					tfisnew[i] = tfis[i];
				}
			}
			tnew[i] = val;
			tfisnew[i] = fis;
			i++;
			if ( tmp.length > 0 )
				for(; i<tnew.length; i++)
				{
					tnew[i] = tmp[i-1];
					tfisnew[i] = tfis[i-1];
				}
			tval = tnew;
			tfis = tfisnew;
		}
		else if ( storeCl == ClassEnumerator.DOUBLE )
		{
			double [] tmp = (double[])tval;
			double [] tnew = new double[tmp.length+1];
			SeparatorStreamReader [] tfisnew =
				new SeparatorStreamReader[tfis.length+1];
			double val = fis.readDouble();
			int i;
			for (i=0; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i] = tmp[i];
					tfisnew[i] = tfis[i];
				}
			}
			tnew[i] = val;
			tfisnew[i] = fis;
			i++;
			if ( tmp.length > 0 )
				for(; i<tnew.length; i++)
				{
					tnew[i] = tmp[i-1];
					tfisnew[i] = tfis[i-1];
				}
			tval = tnew;
			tfis = tfisnew;
		}
		} catch ( Exception e )
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	// Odczytaj następnš danš ze strumienia elementu 0
	private void readNextInt() throws LaborDataException
	{
		int [] tmp = (int[])tval;
		int [] tnew;
		SeparatorStreamReader [] tfisnew;
		try
		{
		if ( tfis[0].hasNext() )
		{ // read and sort
			tnew = new int[tmp.length];
			tfisnew = new SeparatorStreamReader[tfis.length];
			int val = 0;
			val = tfis[0].readInt();

			int i;
			for (i=1; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i-1] = tmp[i];
					tfisnew[i-1] = tfis[i];
				}
			}
			tnew[i-1] = val;
			tfisnew[i-1] = tfis[0];
			for(; i<tmp.length; i++)
			{
				tnew[i] = tmp[i];
				tfisnew[i] = tfis[i];
			}
			tval = tnew;
			tfis = tfisnew;
		}
		else
		{ // remove first position
			tnew = new int[tmp.length-1];
			tfisnew = new SeparatorStreamReader[tfis.length-1];
			tfis[0].close();
			for ( int i=0; i<tnew.length; i++)
			{
				tnew[i] = tmp[i+1];
				tfisnew[i] = tfis[i+1];
			}
		}
		tval = tnew;
		tfis = tfisnew;
		}
		catch ( NumberStoreException e )
		{
			throw new LaborDataException(e);
		}
	}

	// Odczytaj następnš danš ze strumienia elementu 0
	private void readNextLong() throws LaborDataException
	{
		long [] tmp = (long[])tval;
		long [] tnew;
		SeparatorStreamReader [] tfisnew;
		try
		{
		if ( tfis[0].hasNext() )
		{ // read and sort
			tnew = new long[tmp.length];
			tfisnew = new SeparatorStreamReader[tfis.length];
			long val = 0;
			val = tfis[0].readLong();

			int i;
			for (i=1; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i-1] = tmp[i];
					tfisnew[i-1] = tfis[i];
				}
			}
			tnew[i-1] = val;
			tfisnew[i-1] = tfis[0];
			for(; i<tmp.length; i++)
			{
				tnew[i] = tmp[i];
				tfisnew[i] = tfis[i];
			}
			tval = tnew;
			tfis = tfisnew;
		}
		else
		{ // remove first position
			tnew = new long[tmp.length-1];
			tfisnew = new SeparatorStreamReader[tfis.length-1];
			tfis[0].close();
			for ( int i=0; i<tnew.length; i++)
			{
				tnew[i] = tmp[i+1];
				tfisnew[i] = tfis[i+1];
			}
		}
		tval = tnew;
		tfis = tfisnew;
		}
		catch ( NumberStoreException e )
		{
			throw new LaborDataException(e);
		}
	}

	// Odczytaj następnš danš ze strumienia elementu 0
	private void readNextFloat() throws LaborDataException
	{
		float [] tmp = (float[])tval;
		float [] tnew;
		SeparatorStreamReader [] tfisnew;
		try
		{
		if ( tfis[0].hasNext() )
		{ // read and sort
			tnew = new float[tmp.length];
			tfisnew = new SeparatorStreamReader[tfis.length];
			float val = 0;
			val = tfis[0].readFloat();

			int i;
			for (i=1; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i-1] = tmp[i];
					tfisnew[i-1] = tfis[i];
				}
			}
			tnew[i-1] = val;
			tfisnew[i-1] = tfis[0];
			for(; i<tmp.length; i++)
			{
				tnew[i] = tmp[i];
				tfisnew[i] = tfis[i];
			}
			tval = tnew;
			tfis = tfisnew;
		}
		else
		{ // remove first position
			tnew = new float[tmp.length-1];
			tfisnew = new SeparatorStreamReader[tfis.length-1];
			tfis[0].close();
			for ( int i=0; i<tnew.length; i++)
			{
				tnew[i] = tmp[i+1];
				tfisnew[i] = tfis[i+1];
			}
		}
		tval = tnew;
		tfis = tfisnew;
		}
		catch ( NumberStoreException e )
		{
			throw new LaborDataException(e);
		}
	}
	// Odczytaj następnš danš ze strumienia elementu 0
	private void readNextDouble() throws LaborDataException
	{
		double [] tmp = (double[])tval;
		double [] tnew;
		SeparatorStreamReader [] tfisnew;
		try
		{
		if ( tfis[0].hasNext() )
		{ // read and sort
			tnew = new double[tmp.length];
			tfisnew = new SeparatorStreamReader[tfis.length];
			double val = 0;
			val = tfis[0].readDouble();

			int i;
			for (i=1; i<tmp.length; i++)
			{
				if ( tmp[i] > val )
				{
					break;
				}
				else
				{
					tnew[i-1] = tmp[i];
					tfisnew[i-1] = tfis[i];
				}
			}
			tnew[i-1] = val;
			tfisnew[i-1] = tfis[0];
			for(; i<tmp.length; i++)
			{
				tnew[i] = tmp[i];
				tfisnew[i] = tfis[i];
			}
			tval = tnew;
			tfis = tfisnew;
		}
		else
		{ // remove first position
			tnew = new double[tmp.length-1];
			tfisnew = new SeparatorStreamReader[tfis.length-1];
			tfis[0].close();
			for ( int i=0; i<tnew.length; i++)
			{
				tnew[i] = tmp[i+1];
				tfisnew[i] = tfis[i+1];
			}
		}
		tval = tnew;
		tfis = tfisnew;
		}
		catch ( NumberStoreException e )
		{
			throw new LaborDataException(e);
		}
	}

	public boolean isData()
	{
		if ( tfis.length != 0 )
			return true;
		else
			return false;
	}

	// pobierz dane jako dany typ i wczytaj następnš dostępnš danš
	public int getInt() throws LaborDataException
	{
		int [] tmp = (int[])tval;
		int ret = tmp[0];
		readNextInt();
		return ret;
	}
	public long getLong() throws LaborDataException
	{
		long [] tmp = (long[])tval;
		long ret = tmp[0];
		readNextLong();
		return ret;
	}
	public float getFloat() throws LaborDataException
	{
		float [] tmp = (float[])tval;
		float ret = tmp[0];
		readNextFloat();
		return ret;
	}
	public double getDouble() throws LaborDataException
	{
		double [] tmp = (double[])tval;
		double ret = tmp[0];
		readNextDouble();
		return ret;
	}
};
