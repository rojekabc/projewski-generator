package tests.test8;

import tests.Wariancja;

/**
 * Wygenerowanie serii parametrów, w których parametr M jest stały,
 * a parametry A i C podlegajš zmianom od 1 do M
 */
public class OneMVariation implements TestInterface {
	Wariancja wpar;
	public int parametrM = 7;
	
	public OneMVariation(int m)
	{
		this.parametrM = m;
	}

	public int[] getNextToTest() {
		int [] x = wpar.next();
		int [] ret = new int[3];
		if ( x == null )
			return null;
		ret[0] = this.parametrM;
		ret[1] = x[0];
		ret[2] = x[1];
		return ret;
	}

	public void init() {
		wpar = new Wariancja(2, 1, this.parametrM);
	}

}
