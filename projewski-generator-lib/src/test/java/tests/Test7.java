package tests;

import java.math.BigInteger;

/**
 * Przetestowanie klasy BigInteger
 * @author projewski
 *
 */
public class Test7 {
	public static void main(String [] args)
	{
		int i;
		BigInteger bg1 = new BigInteger((Long.valueOf(Long.MAX_VALUE)).toString());
		
		for ( i=0; i<960; i++ )
		{
			bg1 = bg1.add(bg1);
		}
		System.out.println(bg1.toString());
		System.out.println(bg1.doubleValue());
		System.exit(0);
	}

}
