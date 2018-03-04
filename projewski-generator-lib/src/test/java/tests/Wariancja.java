package tests;
/*
 * Klasa generujšca kolejne n-elementowe zbiory liczb nalezacych do przedzialu <min, max>
 * i liczb całkowitych, które stanowiš kolejne wariancje z powtórzeniami
 */
public class Wariancja
{
	int [] _war;
	int _n, _min, _max;

	/*
	 * n - liczba pozycji do wygenerowania
	 * min - wartosc minimalna
	 * max - wartosc maksymalna
	 */
	public Wariancja(int n, int min, int max)
	{
		int i;
		_n = n;
		_min = min;
		_max = max;
		_war = new int [_n]; 
		for  (i=0; i<_war.length; i++)
			_war[i] = _min;
	}
	
	/*
	 * Podaj tablicę następnych, wygenerowanych wartoci
	 */
	public int [] next()
	{
		int [] tmp;
		int i;
		if ( _war == null )
			return null;
		tmp = new int [_war.length];
		for (i=0; i<_war.length; i++)
			tmp[i] = _war[i];
		for (i=_war.length-1; i>=0; i--)
		{
			_war[i]++;
			if ( _war[i] != _max+1 )
				break;
			else
			{
				_war[i] = _min;
				if ( i==0 )
				{
					_war = null;
				}
			}
		}
		return tmp;
	}
	
	/*
	 * Pobranie ostatnio wygenerowanej wariancji
	 */
	public int [] get()
	{
		return _war;
	}
	
	/*
	 * Program testujšcy działanie klasy
	 */
	public static void main(String [] args)
	{
		int n, min, max;
		if ( args.length < 3 )
		{
			System.out.println("Argumenty: n wmin wmax");
			System.out.println("\tn - liczba pól");
			System.out.println("\twmin - ograniczenie wartości dolne");
			System.out.println("\twmax - ograniczenie wartości górne");
			System.exit(1);
		}
		n = Integer.parseInt(args[0]);
		min = Integer.parseInt(args[1]);
		max = Integer.parseInt(args[2]);
		if ( n > 20 )
		{
			System.out.println("n ma wartość większą od 20");
			System.exit(1);
		}
		if ( (min < 0) || (max < 0) )
		{
			System.out.println("Wartości ograniczające są mniejsze od zera");
			System.exit(1);
		}
		if ( (min > 50) || (max > 50) )
		{
			System.out.println("Wartości ograniczające są większe od 50");
			System.exit(1);
		}
		if ( min > max )
		{
			System.out.println("Wartość dolna jest większa od górnej");
			System.exit(1);
		}
		Wariancja w = new Wariancja(n, min, max);
		int [] tab;
		int i;
		while ( (tab = w.next()) != null )
		{
			for (i=0; i<tab.length; i++)
				System.out.print(""+tab[i]+" ");
			System.out.println("");
		}
		System.exit(0);
	}
}
