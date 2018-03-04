package tests.test8;

/**
 * Jest to testowanie przypadku generatorow typu (M, A=M-2, C=M-1)
 */
public class TypeOne implements TestInterface {
	
	public int M;
	public int i;
	
	public TypeOne(int m)
	{
		this.M = m;
	}

	public int[] getNextToTest() {
		int [] ret;
		this.i++;
		if ( this.i > this.M )
			return null;
		ret = new int[] { this.i, this.i-2, this.i-1 };
		return ret;
	}

	public void init() {
		this.i = 2;
	}

}
