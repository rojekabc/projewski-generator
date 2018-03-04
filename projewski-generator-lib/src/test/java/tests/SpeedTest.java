/**
 * 
 */
package tests;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.generator.GeneratorLCGAnd;
import pl.projewski.generator.interfaces.GeneratorInterface;

/**
 * @author projewski
 *
 */
public class SpeedTest {
	
	// ile danych wylosować w jednej próbie
	static int PROBKA = 1000000;
	// ile prób przeprowadzić
	static int LICZBA = 10;
	// Tutaj stworzyć instancję generatora, który ma podlegać testowaniu
	public static GeneratorInterface getGenerator()
	{
		GeneratorInterface gi = new GeneratorLCGAnd();
		
		/*
		GeneratorInterface gi = new GenericLCG();
		try {
			gi.setParameter(GenericLCG.A, new VectorLong("1 1 1 1 1 1"));
			gi.setParameter(GenericLCG.SEED, new VectorLong("1 2 3 4 5 6"));
			gi.setParameter(GenericLCG.C, new Long(3));
			gi.setParameter(GenericLCG.M, new Long(7));
		} catch (ParameterException e) {
			e.printStackTrace();
		}
		*/
		return gi;
	}
	
	public static void getIntTest(GeneratorInterface gi)
	{
		long startTime, endTime;
		int exCnt;
		
		try {
			gi.init();
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<LICZBA; i++)
		{
			
			try {
				gi.reinit();
			} catch (GeneratorException e) {
				e.printStackTrace();
			}
			
			exCnt = 0;
			startTime = System.currentTimeMillis();
			
			for (int j=0; j<PROBKA; j++)
			{
				try {
					gi.getInt();
				} catch ( GeneratorException e ) {
					exCnt++;
				}
			}
			
			endTime = System.currentTimeMillis();
			System.out.println("getInt, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms" );
		}
	}

	public static void getLongTest(GeneratorInterface gi)
	{
		long startTime, endTime;
		int exCnt;
		
		try {
			gi.init();
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<LICZBA; i++)
		{
			
			try {
				gi.reinit();
			} catch (GeneratorException e) {
				e.printStackTrace();
			}
			
			exCnt = 0;
			startTime = System.currentTimeMillis();
			
			for (int j=0; j<PROBKA; j++)
			{
				try {
					gi.getLong();
				} catch ( GeneratorException e ) {
					exCnt++;
				}
			}
			
			endTime = System.currentTimeMillis();
			System.out.println("getLong, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms" );
		}
	}
	
	public static void getFloatTest(GeneratorInterface gi)
	{
		long startTime, endTime;
		int exCnt;
		
		try {
			gi.init();
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<LICZBA; i++)
		{
			
			try {
				gi.reinit();
			} catch (GeneratorException e) {
				e.printStackTrace();
			}
			
			exCnt = 0;
			startTime = System.currentTimeMillis();
			
			for (int j=0; j<PROBKA; j++)
			{
				try {
					gi.getFloat();
				} catch ( GeneratorException e ) {
					exCnt++;
				}
			}
			
			endTime = System.currentTimeMillis();
			System.out.println("getFloat, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms" );
		}
	}
	public static void getDoubleTest(GeneratorInterface gi)
	{
		long startTime, endTime;
		int exCnt;
		
		try {
			gi.init();
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<LICZBA; i++)
		{
			
			try {
				gi.reinit();
			} catch (GeneratorException e) {
				e.printStackTrace();
			}
			
			exCnt = 0;
			startTime = System.currentTimeMillis();
			
			for (int j=0; j<PROBKA; j++)
			{
				try {
					gi.getDouble();
				} catch ( GeneratorException e ) {
					exCnt++;
				}
			}
			
			endTime = System.currentTimeMillis();
			System.out.println("getDouble, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms" );
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		GeneratorInterface gi = getGenerator();
		getIntTest(gi);
		getLongTest(gi);
		getFloatTest(gi);
		getDoubleTest(gi);
		System.exit(0);
	}

}
