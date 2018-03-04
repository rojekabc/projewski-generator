package tests;

/*
 * Testy na obliczanie ChiSquare
 */

import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.labordata.ChiSquare;
import pl.projewski.generator.tools.NumberStoreOne;

public class Test2
{
	// Knuth 3.3.1.A
	protected static void setTest1(NumberStoreOne real, NumberStoreOne stat)
	{
		try
		{
		int [] realtab;
		int [] stattab;
		realtab = new int[] {2, 4, 10, 12, 22, 29, 21, 15, 14, 9, 6};
		stattab = new int[] {4, 8, 12, 16, 20, 24, 20, 16, 12, 8, 4};
		real.set(realtab);
		stat.set(stattab);
		} 
		catch ( Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	protected static void setTest2(NumberStoreOne real, NumberStoreOne stat)
	{
		try
		{
		int [] realtab;
		int [] stattab;
		realtab = new int[] {4, 10, 10, 13, 20, 18, 18, 11, 13, 14, 13};
		stattab = new int[] {4, 8, 12, 16, 20, 24, 20, 16, 12, 8, 4};
		real.set(realtab);
		stat.set(stattab);
		} 
		catch ( Exception e)
		{
			System.out.println(e.toString());
		}
	}

	protected static void setTest3(NumberStoreOne real, NumberStoreOne stat)
	{
		try
		{
		int [] realtab;
		int [] stattab;
		realtab = new int[] {3, 7, 11, 15, 19, 24, 21, 17, 13, 9, 5};
		stattab = new int[] {4, 8, 12, 16, 20, 24, 20, 16, 12, 8, 4};
		real.set(realtab);
		stat.set(stattab);
		} 
		catch ( Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	protected static void doTest(
		NumberInterface real,
		NumberInterface stat,
		double res,
		String desc
		)
	{
		try
		{
			ChiSquare chiSquare = new ChiSquare();
			chiSquare.setParameter(ChiSquare.DISTRIBUTION, stat);
			chiSquare.setInputData(real);
			if ( chiSquare.getChiSquare() == res )
			{
				System.out.println(desc+" : ok. " + chiSquare.getChiSquare());
			}
			else
			{
				System.out.println(desc+" : error");
				System.out.println("\tNeed: "+res+" Get: " + chiSquare.getChiSquare());
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
	}

	public static void main(String [] args)
	{
		try
		{
			NumberStoreOne realvalues = new NumberStoreOne();
			NumberStoreOne statvalues = new NumberStoreOne();
			setTest1(realvalues, statvalues);
			doTest(realvalues, statvalues, (7.0+7.0/48.0),"test1");
			setTest2(realvalues, statvalues);
			doTest(realvalues, statvalues, (29.0+59.0/120.0),"test2");
			setTest3(realvalues, statvalues);
			doTest(realvalues, statvalues, (1.0+17.0/120.0),"test3");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
		System.exit(0);
	}
}
