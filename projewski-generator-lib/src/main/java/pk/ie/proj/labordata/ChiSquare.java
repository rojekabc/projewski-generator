package pk.ie.proj.labordata;

import java.io.FileNotFoundException;
import java.io.IOException;

import pk.ie.proj.abstracts.AbstractDistribution;
import pk.ie.proj.distribution.Uniform;
import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.tools.Fraction;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.NumberStoreOne;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.tools.stream.NumberWriter;

// Obliczanie wartości ChiSquare. TO jeszcze nie test !

public class ChiSquare
	extends LaborDataInterface
{
	// maksymalna dozwolona liczba grup (lub tez maksymalna liczba v=k-1 stopni swobody)
	public static final int MAX_AMMOUNT = 100;
	
//	Class _cl = null;
	int _ammount = 0;
	long _probeammount = 0;
	Fraction chisquare = new Fraction(0,0);

	public final static String DISTRIBUTION = "rozkład oczekiwany";

	public void initParameterInterface()
	{
		// Dane testowane z danych wejściowych, które stanowią już
		// obliczoną tablicę częstotliowści występowania 
		// lub pobieranie z generatora danych i liczenie częstości występowań.
		parameters.put(DISTRIBUTION, new Uniform());
	}

	public Class<?> [] getAllowedClass(String param)
	{
		if ( param.equals(DISTRIBUTION) )
			return new Class<?>[] {AbstractDistribution.class};
		return new Class<?>[0];
	}
	@Override
	public boolean getOutputData(NumberInterface data) throws LaborDataException {
		// ustaw wynik
		NumberWriter writer = null;
		try {
			data.setStoreClass(ClassEnumerator.DOUBLE);
			data.setSize(1);
			writer = data.getNumberWriter();
			writer.write(chisquare.getDouble());
		} catch (FileNotFoundException e) {
			throw new LaborDataException(e);
		} catch (IOException e) {
			throw new LaborDataException(e);
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(writer);
		}
		return true;
	}
	
	public double getChiSquare()
	{
		return chisquare.getDouble();
	}
	
	@Override
	public void setInputData(NumberInterface data) throws LaborDataException {
		NumberReader is = null;
		try
		{
			ClassEnumerator icl;
			
			_probeammount = 0;
			_ammount = 0;
			chisquare = new Fraction(0,0);
			
			icl = data.getStoreClass();
			if ( (icl != ClassEnumerator.INTEGER) && (icl != ClassEnumerator.LONG) )
			{
				// TODO: false. akceptuje tylko tablice int i long
				Mysys.println("Tablica innego typu niż int lub long");
				return;
			}
			
			// Zliczanie danych i obliczanie ogolnej sumy
			long tmp;
			is = data.getNumberReader();
			
			while ( is.hasNext() )
			{
				tmp = is.readLong();
				_ammount++;
				_probeammount += tmp;
			}
			
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(is);
		}
		
		try
		{
			int i;
			
			Object distribution = parameters.get(DISTRIBUTION);
	//		System.out.println("Get Output Data");
			if ( distribution == null )
			{
				// TODO:
				System.out.println("No distribution");
				return;
			}
			if ( _ammount < 2 )
			{
				// TODO:
				// Nie pozwala na otrzymanie liczby stopni swobody mniejszej niz 1
				System.out.println("Group ammount to low");
				return;
			}
			if ( _ammount > MAX_AMMOUNT )
			{
				// TODO:
				// Nie pozwalaj na obliczenia powyżej liczebnosci grup 100
				System.out.println("Group ammoun to high");
				return;
			}
			
			if ( data.getSize() != _ammount )
				throw new LaborDataException("Wymagana liczba danych: " + _ammount + ", dostępne: " + data.getSize());
			
			is = data.getNumberReader();
			if ( distribution instanceof AbstractDistribution )
			{
				AbstractDistribution di;
				di = (AbstractDistribution)distribution;
				for (i=0; i<_ammount; i++)
				{
					Fraction statammount = di.getPropability(new Fraction(i+1,_ammount));
					Mysys.debugln("Stat all: " + statammount.getDouble());
					statammount.sub(di.getPropability( new Fraction(i, _ammount) ));
					Mysys.debugln("Stat propab: " + statammount.getDouble());
					Mysys.debugln("Probe Ammount "+_probeammount);
					statammount.mul( new Fraction( _probeammount, 1 ) );
					Mysys.debugln("After mul: " + statammount.getDouble());
					Fraction diff = null;
					diff = new Fraction( is.readLong(), 1 );
					Mysys.debugln("Realammount is "+diff.getDouble()+" Statammount is "+statammount.getDouble());
					diff.sub( statammount );
					diff.mul( new Fraction(diff) );
					diff.div( statammount );
					chisquare.add( diff );
	//				chisquare += diff*diff/statammount;
					Mysys.debugln("Now chisquare is "+chisquare.getDouble());
				}
			}
			else if ( distribution instanceof NumberStoreOne )
			{
				NumberStoreOne dist = (NumberStoreOne)distribution;
				if ( dist.getStoreClass() != ClassEnumerator.INTEGER )
				{
					// TODO:
					// nieodpowiedni typ
					System.out.println("Wrong class distribution");
					return;
				}
				if ( dist.getSize() != _ammount )
				{
					// TODO:
					// niezgodna licznosc grup
					System.out.println("Wrong group ammount to distribution ammount");
					return;
				}
				int [] statammount = dist.getTInt();
				for (i=0; i<_ammount; i++)
				{
					long realammount = is.readLong();
					long diff = realammount - statammount[i];
					chisquare.add( new Fraction(diff*diff, statammount[i]) );
				}
			}
			else
			{
				// TODO:
				System.out.println("Unknown distribution class");
				return;
			}
			return;
		} catch (NumberStoreException e) {
			throw new LaborDataException(e);
		} finally {
			Mysys.closeQuiet(is);
		}
		
	}
}
