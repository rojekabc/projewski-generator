package tests.test8;

/**
 * Przetestowanie konkretnego generatora o parametrach M A C
 */
public class OneGenerator implements TestInterface {
	
	int [] tab;
	
	public OneGenerator(int m, int a, int c) {
		this.tab = new int[3];
		this.tab[0] = m;
		this.tab[1] = a;
		this.tab[2] = c;
	}

	public int[] getNextToTest() {
		int [] tmp = this.tab;
		this.tab = null;
		return tmp;
	}

	public void init() {
	}

}
