package tests;

import java.io.File;

import pk.ie.proj.generator.GeneratorLCG;

/*
 * Program przyjmuje parametr m oraz parametr x.
 * Parametr m mówi o wielkości próby jaka zostanie wygenerowana (cała możliwa maksymalna generacja generatora), oraz określa parametr M generatora LCG
 * Parametr x określa wszystkie wariancje parametrów A i C generatora LCG w przedziale <1, x>, pod kątem których generator będzie badany
 * Wartość początkowa generatora zostanie zainicjowana liczbą 1
 * 
 * Najlepszy przypadek to podanie parametru m jako liczba pierwsza, a parametru x jako m-1
 * 
 * Założenia:
 * m = liczba pierwsza > 2
 * x = m-1
 * 
 * Proba1:
 * m = 11; x = 10
 * Obserwacje:
 * Dla a = 1 wszystkie generacje pelne (ale to prosty przypadek z przesunieciem o dodawanie)
 * Dla a = 2 wszystkie niepełne (brak tylko 1 cyfry), same 1 dla c = 10
 * Dla a = 3 wszystkie niepełne (długość 5), same 1 dla c = 9
 * Dla a = 4 wszystkie niepełne (długość 5), same 1 dla c = 8
 * Dla a = 5 wszystkie niepełne (długość 5), same 1 dla c = 7
 * Dla a = 6 wszystkie niepełne (brak tylko 1 cyfry), same 1 dla c = 6
 * Dla a = 7 wszystkie niepełne (brak tylko 1 cyfry), same 1 dla c = 5
 * Dla a = 8 wszystkie niepełne (brak tylko 1 cyfry), same 1 dla c = 4
 * Dla a = 9 wsztstkie niepełne (długość 5), same 1 dla c = 3
 * Dla a = 10 wszystkie niepełne (długość 2), same 1 dla c = 2
 */
class Test1
{
	public static void main(String [] args)
	{
		try
		{
		GeneratorLCG gen;
		int x;
		int m;
		int [] tab;
		int i;
		
		System.out.println("File.separator " + File.separator);
		System.out.println("File.pathseparator " + File.pathSeparator);
		// m - parametr m i liczba losowań, wariacje od 1 do x
		// x - górna granicja wariancji
		if ( args.length < 2 )
			return;
		m = Integer.parseInt(args[0]);
		x = Integer.parseInt(args[1]);
		Wariancja wpar = new Wariancja(2, 1, x);
		while ( (tab = wpar.next()) != null )
		{
			gen = new GeneratorLCG();
			// zawsze od 1 zacznij
			gen.setParameter(GeneratorLCG.SEED, Long.valueOf(1));
			// m od użytkownika
			gen.setParameter(GeneratorLCG.M, Long.valueOf(m));
			// a i c z wariancji
			gen.setParameter(GeneratorLCG.A, Long.valueOf(tab[0]));
			gen.setParameter(GeneratorLCG.C, Long.valueOf(tab[1]));
			gen.init();
			System.out.print("Genracja: ");
			for ( i=0; i<m; i++ )
			{
				System.out.print("" + gen.getInt() + " ");
			}
			System.out.println(" dla generatora z a="+tab[0]+" c="+tab[1]);
		}
		System.exit(0);
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
		}
	}
}
