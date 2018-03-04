/**
 * 
 */
package tests;

import pk.ie.proj.tools.ArrayUtil;
import pk.ie.proj.tools.Mysys;

/**
 * @author projewski
 *
 * Test klasy ArrayUtil
 */
public class ArrayUtilTest {
	
	public final static String errString = "Błąd podczas przeprowadzania testu";
	
	// test na equals dla int
	public static void test01()
	{
		int [] tmp = null;
		
		if ( ! ArrayUtil.equals((int[])null, (int[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new int[] {}, (int[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new int[] {}, new int [] {}, 0) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		tmp = new int [] {};
		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new int [] {0}, new int [] {0}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new int [] {0, 1, 2, 3, 4, 5, 6}, new int [] {0, 1, 2, 3, 4, 7, 8}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new int [] {0, 1, 2, 3, 4, 5, 6}, new int [] {0, 1, 2, 3, 4, 7, 8}, 5) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new int [] {0, 1, 2, 3, 4, 5, 6}, new int [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new int [] {0, 1, 2, 3, 4, 5, 6}, new int [] {0, 1, 2, 3, 4, 7, 8}, 7) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new int [] {0, 1, 2, 3, 4, 5, 6}, new int [] {0, 1, 2, 3, 4}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new int [] {0, 1, 2, 3, 4}, new int [] {0, 1, 2, 3, 4, 7, 8}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new int [] {0, 1, 2, 3, 4}, new int [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new int [] {0, 1, 2, 3, 4, 5, 6}, new int [] {0, 1, 2, 3, 4, 7, 8}, 0) )
		{
			Mysys.error(errString);
			return;
		}
	}
	// test na equals dla long
	public static void test02()
	{
		long [] tmp = null;
		
		if ( ! ArrayUtil.equals((long[])null, (long[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new long[] {}, (long[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new long[] {}, new long [] {}, 0) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		tmp = new long [] {};
		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new long [] {0}, new long [] {0}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new long [] {0, 1, 2, 3, 4, 5, 6}, new long [] {0, 1, 2, 3, 4, 7, 8}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new long [] {0, 1, 2, 3, 4, 5, 6}, new long [] {0, 1, 2, 3, 4, 7, 8}, 5) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new long [] {0, 1, 2, 3, 4, 5, 6}, new long [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new long [] {0, 1, 2, 3, 4, 5, 6}, new long [] {0, 1, 2, 3, 4, 7, 8}, 7) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new long [] {0, 1, 2, 3, 4, 5, 6}, new long [] {0, 1, 2, 3, 4}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new long [] {0, 1, 2, 3, 4}, new long [] {0, 1, 2, 3, 4, 7, 8}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new long [] {0, 1, 2, 3, 4}, new long [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new long [] {0, 1, 2, 3, 4, 5, 6}, new long [] {0, 1, 2, 3, 4, 7, 8}, 0) )
		{
			Mysys.error(errString);
			return;
		}
	}
	// test na equals dla float
	public static void test03()
	{
		float [] tmp = null;
		
		if ( ! ArrayUtil.equals((float[])null, (float[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new float[] {}, (float[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new float[] {}, new float [] {}, 0) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		tmp = new float [] {};
		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new float [] {0}, new float [] {0}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new float [] {0, 1, 2, 3, 4, 5, 6}, new float [] {0, 1, 2, 3, 4, 7, 8}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new float [] {0, 1, 2, 3, 4, 5, 6}, new float [] {0, 1, 2, 3, 4, 7, 8}, 5) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new float [] {0, 1, 2, 3, 4, 5, 6}, new float [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new float [] {0, 1, 2, 3, 4, 5, 6}, new float [] {0, 1, 2, 3, 4, 7, 8}, 7) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new float [] {0, 1, 2, 3, 4, 5, 6}, new float [] {0, 1, 2, 3, 4}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new float [] {0, 1, 2, 3, 4}, new float [] {0, 1, 2, 3, 4, 7, 8}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new float [] {0, 1, 2, 3, 4}, new float [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new float [] {0, 1, 2, 3, 4, 5, 6}, new float [] {0, 1, 2, 3, 4, 7, 8}, 0) )
		{
			Mysys.error(errString);
			return;
		}
	}
	// test na equals dla double
	public static void test04()
	{
		double [] tmp = null;
		
		if ( ! ArrayUtil.equals((double[])null, (double[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new double[] {}, (double[])null, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new double[] {}, new double [] {}, 0) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}
		
		tmp = new double [] {};
		if ( ! ArrayUtil.equals(tmp, tmp, 100) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new double [] {0}, new double [] {0}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new double [] {0, 1, 2, 3, 4, 5, 6}, new double [] {0, 1, 2, 3, 4, 7, 8}, 1) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new double [] {0, 1, 2, 3.2, 4, 5, 6}, new double [] {0, 1, 2, 3.2, 4, 7, 8}, 5) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new double [] {0, 1, 2, 3, 4, 5, 6}, new double [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ArrayUtil.equals(new double [] {0, 1, 2, 3, 4, 5, 6}, new double [] {0, 1, 2, 3, 4, 7, 8}, 7) )
		{
			Mysys.error(errString);
			return;
		}

		if ( ! ArrayUtil.equals(new double [] {0, 1, 2, 3, 4, 5, 6}, new double [] {0, 1, 2, 3, 4}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new double [] {0, 1, 2, 3, 4}, new double [] {0, 1, 2, 3, 4, 7, 8}, 4) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ArrayUtil.equals(new double [] {0, 1, 2, 3, 4}, new double [] {0, 1, 2, 3, 4, 7, 8}, 6) )
		{
			Mysys.error(errString);
			return;
		}
		
		if ( ! ArrayUtil.equals(new double [] {0, 1, 2, 3, 4, 5, 6}, new double [] {0, 1, 2, 3, 4, 7, 8}, 0) )
		{
			Mysys.error(errString);
			return;
		}
	}
	
	// test na putLast dla int
	public static void test10()
	{
		int inArr [] = {0, 5, 3, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
		int multinArr [][] =
		{
				null,
				{},
				{0, 5, 3},
				null,
				{},
				{5},
				{23, 32, 54, -12},
				{432, 320},
				{-1, -1, 0, 32},
				{},
				null
		}; 
		int outArr [] = null;
		int i;
		
		outArr = ArrayUtil.putLast(outArr, null);
		if ( outArr != null )
		{
			Mysys.error(errString);
			return;
		}
		
		for (i=0; i<inArr.length; i++)
		{
			outArr = ArrayUtil.putLast(outArr, inArr[i]);
			
			if ( outArr == null )
			{
				Mysys.error(errString);
				return;
			}
			
			if ( i+1 != outArr.length )
			{
				Mysys.error(errString);
				return;
			}

			if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
			{
				Mysys.error(errString);
				return;
			}
		}
		
		outArr = null;
		outArr = ArrayUtil.putLast(outArr, inArr);
		if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
		{
			Mysys.error(errString);
			return;
		}
		
		outArr = null;
		int size = 0;
		for ( i=0; i<multinArr.length; i++ )
		{
			if (multinArr[i] != null )
				size += multinArr[i].length;
			outArr = ArrayUtil.putLast(outArr, multinArr[i]);
			if ( ! ArrayUtil.equals(inArr, outArr, size) )
			{
				Mysys.error(errString);
				return;				
			}
		}
	}
	// test na putLast dla long
	public static void test11()
	{
		long inArr [] = {0, 5, 3, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
		long multinArr [][] =
		{
				null,
				{},
				{0, 5, 3},
				null,
				{},
				{5},
				{23, 32, 54, -12},
				{432, 320},
				{-1, -1, 0, 32},
				{},
				null
		}; 
		long outArr [] = null;
		int i;
		
		outArr = ArrayUtil.putLast(outArr, null);
		if ( outArr != null )
		{
			Mysys.error(errString);
			return;
		}
		
		for (i=0; i<inArr.length; i++)
		{
			outArr = ArrayUtil.putLast(outArr, inArr[i]);
			
			if ( outArr == null )
			{
				Mysys.error(errString);
				return;
			}
			
			if ( i+1 != outArr.length )
			{
				Mysys.error(errString);
				return;
			}

			if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
			{
				Mysys.error(errString);
				return;
			}
		}
		
		outArr = null;
		outArr = ArrayUtil.putLast(outArr, inArr);
		if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
		{
			Mysys.error(errString);
			return;
		}
		
		outArr = null;
		int size = 0;
		for ( i=0; i<multinArr.length; i++ )
		{
			if (multinArr[i] != null )
				size += multinArr[i].length;
			outArr = ArrayUtil.putLast(outArr, multinArr[i]);
			if ( ! ArrayUtil.equals(inArr, outArr, size) )
			{
				Mysys.error(errString);
				return;				
			}
		}
	}
	// test na putLast dla float
	public static void test12()
	{
		float inArr [] = {0, 5, 3, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
		float multinArr [][] =
		{
				null,
				{},
				{0, 5, 3},
				null,
				{},
				{5},
				{23, 32, 54, -12},
				{432, 320},
				{-1, -1, 0, 32},
				{},
				null
		}; 
		float outArr [] = null;
		int i;
		
		outArr = ArrayUtil.putLast(outArr, null);
		if ( outArr != null )
		{
			Mysys.error(errString);
			return;
		}
		
		for (i=0; i<inArr.length; i++)
		{
			outArr = ArrayUtil.putLast(outArr, inArr[i]);
			
			if ( outArr == null )
			{
				Mysys.error(errString);
				return;
			}
			
			if ( i+1 != outArr.length )
			{
				Mysys.error(errString);
				return;
			}

			if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
			{
				Mysys.error(errString);
				return;
			}
		}
		
		outArr = null;
		outArr = ArrayUtil.putLast(outArr, inArr);
		if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
		{
			Mysys.error(errString);
			return;
		}
		
		outArr = null;
		int size = 0;
		for ( i=0; i<multinArr.length; i++ )
		{
			if (multinArr[i] != null )
				size += multinArr[i].length;
			outArr = ArrayUtil.putLast(outArr, multinArr[i]);
			if ( ! ArrayUtil.equals(inArr, outArr, size) )
			{
				Mysys.error(errString);
				return;				
			}
		}
	}
	// test na putLast dla double
	public static void test13()
	{
		double inArr [] = {0, 5, 3.2, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
		double multinArr [][] =
		{
				null,
				{},
				{0, 5, 3.2},
				null,
				{},
				{5},
				{23, 32, 54, -12},
				{432, 320},
				{-1, -1, 0, 32},
				{},
				null
		}; 
		double outArr [] = null;
		int i;
		
		outArr = ArrayUtil.putLast(outArr, null);
		if ( outArr != null )
		{
			Mysys.error(errString);
			return;
		}
		
		for (i=0; i<inArr.length; i++)
		{
			outArr = ArrayUtil.putLast(outArr, inArr[i]);
			
			if ( outArr == null )
			{
				Mysys.error(errString);
				return;
			}
			
			if ( i+1 != outArr.length )
			{
				Mysys.error(errString);
				return;
			}

			if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
			{
				Mysys.error(errString);
				return;
			}
		}
		
		outArr = null;
		outArr = ArrayUtil.putLast(outArr, inArr);
		if ( ArrayUtil.equals(inArr, outArr, outArr.length) == false )
		{
			Mysys.error(errString);
			return;
		}
		
		outArr = null;
		int size = 0;
		for ( i=0; i<multinArr.length; i++ )
		{
			if (multinArr[i] != null )
				size += multinArr[i].length;
			outArr = ArrayUtil.putLast(outArr, multinArr[i]);
			if ( ! ArrayUtil.equals(inArr, outArr, size) )
			{
				Mysys.error(errString);
				return;				
			}
		}
	}
	
	// reverse int
	public static void test20()
	{
		int inArr [] = {0, 5, 3, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
		int testArr [] = {32, 0, -1, -1, 320, 432, -12, 54, 32, 23, 5, 3, 5, 0};
		ArrayUtil.reverse(inArr);
		if ( !ArrayUtil.equals(inArr, testArr, inArr.length) )
			Mysys.error(errString);
	}
	
	// putConstFirst int
	public static void test30()
	{
		int length; 
		int putArr[] = {0, 5, -1, 23, 1, -200};
		int cmpArr[][] = {
				{0, 0, -4, 3},
				{5, 0, 0, -4},
				{-1, 5, 0, 0},
				{23, -1, 5, 0},
				{1, 23, -1, 5},
				{-200, 1, 23, -1}
		};
		int outArr[] = {0, -4, 3, -2};
		length = outArr.length;
		
		for (int i=0; i<putArr.length; i++)
		{
			ArrayUtil.constPutFirst(outArr, putArr[i]);
			if ( outArr.length != length )
			{
				Mysys.error(errString);
				return;				
			}
			if ( !ArrayUtil.equals(outArr, cmpArr[i], length) )
			{
				Mysys.error(errString);
				return;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// testy equals
		test01();
		test02();
		test03();
		test04();
		
		// testy putLast
		test10();
		test11();
		test12();
		test13();
		
		// reverse
		test20();
		
		// putConstFirst
		test30();

	}

}
