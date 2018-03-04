/**
 * 
 */
package tests;

import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.generator.GenericLCG;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.ArrayUtil;
import pk.ie.proj.tools.VectorLong;

/**
 * @author projewski
 *
 */
public class GenericLCGTest {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		GenericLCGTest test = new GenericLCGTest();
		try {
			test.testEasyGenericLCG();
		} catch (GeneratorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// podstawowy generator, na którym mogą sprawdzić poprawność obliczeń
	private void testEasyGenericLCG() throws GeneratorException, ParameterException {
		long initA[] = {1, 1, 1, 1, 1, 1};
		long counted[] = {1, 2, 3, 4, 5, 6};
		long c = 3;
		long m = 7;
		int numOfGeneration = 1000;
		boolean printGenerated = false;
		
		int i, j, n;
		
		GeneratorInterface gi = new GenericLCG();
		gi.setParameter(GenericLCG.A, new VectorLong(initA));
		gi.setParameter(GenericLCG.SEED, new VectorLong(counted));
		gi.setParameter(GenericLCG.C, Long.valueOf(c));
		gi.setParameter(GenericLCG.M, Long.valueOf(m));
		gi.init();
		
		System.out.println("Liczba kontrolnych generacji: " + numOfGeneration);
		System.out.println(gi.toString());
		n = 40;
		for ( j=0; j<numOfGeneration; j++ )
		{
			long generated = gi.getLong();
			long x = c;
			for (i=0; i<counted.length; i++)
				x += counted[counted.length - i - 1] * initA[i];
			x %= m;
			ArrayUtil.constPutLast(counted, x);
			if ( printGenerated )
			{
				System.out.print(generated + " ");
				n--;
				if ( n == 0 )
				{
					n = 40;
					System.out.println();
				}
			}
			if ( generated != x )
			{
				System.out.println("Wygenerowano: " + generated + ", oczekiwano: " + x );
				System.out.println("Pozycja b��dnej pr�bki: " + j);
				System.out.println("Typ generacji: getLong");
				System.exit(0);
			}
		}
		System.out.println("Funkcja getLong: Ok");
		
		gi.reinit();
		n = 5;
		for ( j=0; j<numOfGeneration; j++ )
		{
			double generated = gi.getDouble();
			long x = c;
			for (i=0; i<counted.length; i++)
				x += counted[i] * initA[i];
			x %= m;
			ArrayUtil.constPutLast(counted, x);
			if ( printGenerated )
			{
				System.out.print(generated + " ");
				n--;
				if ( n == 0 )
				{
					n = 5;
					System.out.println();
				}
			}
			if ( Double.compare(generated, (double)x/(double)m) != 0 )
			{
				System.out.println("Wygenerowano: " + generated + ", oczekiwano: " + x );
				System.out.println("Pozycja b��dnej pr�bki: " + j);
				System.out.println("Typ generacji: getDouble");
				System.exit(0);			
			}
		}
		System.out.println("Funkcja getDouble: Ok");
		
	}

}
