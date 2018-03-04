package tests;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.generator.GeneratorLCG;
import pl.projewski.generator.tools.Convert;
import tests.test8.OneGenerator;
import tests.test8.TestInterface;

/*
 * Program przyjmuje parametr m
 * Parametr m mówi o wielkości próby jaka zostanie wygenerowana (cała możliwa maksymalna generacja generatora), oraz określa parametr M generatora LCG (test będzie od 1 do M)
 * Parametr x określany jest jako m-1 wszystkie wariancje parametrów A i C generatora LCG w przedziale <1, x>, pod kątem których generator będzie badany
 * Wartość początkowa generatora zostanie zainicjowana liczbą od 1 do m-1
 *
 * 
 * Ziarno nie ma wpływu na długość generacji, tylko na początkową wartość. Zakładam więc, że
 * nie będzie potrzebne w analizie
 * 
 * Prawdopodobnie przy liczbach, które nie są pierwszymi, występuje przypadek generacji drugiej
 * podobnej (skrócenie od drugiej, a pierwsza inicjowana nie zostaje powtorzona) (prawdopodobnie
 * co tyle pozycji ile podzielnik tej liczby)
 * Prawdopodobnie przy liczbach, które nie są pierwszymi można wygenerować przypadek, w którym
 * występuje generacja co 1 drugiej liczby generacji po zainicjowaniu i w różnica tej liczby
 * w stosunku do seed'a równa jest wartości największego podzielnika tej liczby (prawdopodobnie)
 * Teza zanegowana - znalazłem różnice, która jest wielokrotnością podzielnika
 * 
 * Dla a=m-1 zawsze powstaje przypadek beznadziejny, w którym ciagi są generowane o długości 2
 * i jest to zarówno dla liczb pierwszych jak i nie
 */
class Test8
{
	public static boolean PrintGeneratedNumbers = false;
	public static int Seed = 1;
//	public static TestInterface ti = new AllVariation(7);
//	public static TestInterface ti = new OneMVariation(11);
	public static TestInterface ti = new OneGenerator(101, 29, 5);
//	public static TestInterface ti = new TypeOne(23);
	

	public static void printGeneratorInfo(GeneratorLCG gen) throws ParameterException
	{
		System.out.print("Generator ");
		String params[] = gen.listParameters();
		for ( int i=0; i<params.length; i++ )
			System.out.print(params[i] + "=" + gen.getParameter(params[i]) + " ");
		System.out.println();
	}
	
	
	/**
	 * Testowanie typu jaki przyjmuje generator kongruentny
	 * Z(x) - zapetlony wokół liczbty x
	 * a+Z(x) - zapetlony wokół liczby x po wsytąpieniu wartości a
	 * C(n=x) - cykliczny z dlugoscia generacji x
	 * a+C(n=x) - cykliczny z dlugoscia generacji x po wystapieniu wartosci a
	 * P - pelny
	 * P- - prawie pelny, bez jednej wartosci
	 * 
	 * @param gen Generator do przetestowania
	 * @return ciag znakow okreslajacy jaki typ generatora wykryto
	 * @throws NumberFormatException
	 * @throws ClassCastException
	 * @throws ParameterException
	 * @throws GeneratorException
	 */
	public static String testGeneratorType(GeneratorLCG gen) throws NumberFormatException, ClassCastException, ParameterException, GeneratorException
	{
		int i;
		int m = Convert.tryToInt(gen.getParameter(GeneratorLCG.M));
		int first = gen.getInt();
		int second = gen.getInt();
		if ( first == second )
			return "Z(" + first + ")";
		int third = gen.getInt();
		if ( second == third )
			return first + "+Z(" + second +")";
		if ( first == third )
			return "C(n=2)";
					
		for ( i=0; i<m-3; i++ )
		{
			int num = gen.getInt();
			if ( num == first )
			{
				if ( i == m-4 )
					return"P-";
				else
					return "C(n=" + (i+3) + ")";
			}
			if ( num == second )
				return first + "+C(n="+ (i+2) +")";
		} // for
		return "P";
	}
	
	public static void main(String [] args)
	{
		try
		{
			GeneratorLCG gen;
			int [] tab;
			int i;
			
			// m - parametr m i liczba losowań, wariacje od 1 do x
			// x - górna granicja wariancji
			ti.init();
			
//			Wariancja wpar = new Wariancja(3, 1, 1);
			while ( (tab = ti.getNextToTest()) != null )
			{
				gen = new GeneratorLCG();
				// zawsze od 1 zacznij
				gen.setParameter(GeneratorLCG.SEED, Long.valueOf(Test8.Seed));
				// m od użytkownika
				gen.setParameter(GeneratorLCG.M, Long.valueOf(tab[0]));
				// a i c z wariancji
				gen.setParameter(GeneratorLCG.A, Long.valueOf(tab[1]));
				gen.setParameter(GeneratorLCG.C, Long.valueOf(tab[2]));
				gen.init();
				
				System.out.print(
						"[" + gen.getParameter(GeneratorLCG.M) + ", "
						+ gen.getParameter(GeneratorLCG.A) + ", "
						+ gen.getParameter(GeneratorLCG.C) + ", "
						+ gen.getParameter(GeneratorLCG.SEED) + "] => ");
				
				if ( Test8.PrintGeneratedNumbers )
				{
					System.out.print(" [");
					for (i=0; i<tab[0]-1; i++)
						System.out.print(gen.getInt() + ", ");
					System.out.print(gen.getInt());
					System.out.print("] => ");
					gen.reinit();
				}
				
				System.out.println(Test8.testGeneratorType(gen));
				
			}
			System.exit(0);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
