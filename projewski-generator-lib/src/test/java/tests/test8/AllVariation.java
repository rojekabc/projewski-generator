package tests.test8;

import tests.Wariancja;

/*
 * Przetestowanie wszystkich wariacji M, A, C,
 * ale z warunkiem, ze c <= m i a <= m
 */
public class AllVariation implements TestInterface {

	Wariancja wpar;
	int parametrM = 7;
	
	public AllVariation(int m)
	{
		this.parametrM = m;
	}

	public int[] getNextToTest() {
		int [] tab = wpar.next();
		while ( tab != null )
		{
			if ( tab[0] < tab[1] )
				tab = wpar.next();
			else if ( tab[0] < tab[2] )
				tab = wpar.next();
			else
				break;
		}
		return tab;
	}

	public void init() {
		wpar = new Wariancja(3, 1, this.parametrM);
	}

}
