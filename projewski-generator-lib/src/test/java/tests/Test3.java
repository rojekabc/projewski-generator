package tests;
import pk.ie.proj.tools.Fraction;

public class Test3
{
	protected static void doTest6()
	{
		long a1[] = {0, 0, 0, 0, 2, 1};
		long b1[] = {1, 0, 1, 1, 0, 1};
		long c1[] = {2, 0, 2, 2, 0, 2};
		long a2[] = {0, 0, 0, 2, 0, 0};
		long b2[] = {1, 1, 0, 0, 1,-1};
		long c2[] = {2, 2, 0, 0, 2, 2};
		int i;
		System.out.println("Division Fraction Test");
		for ( i = 0; i<b1.length; i++ )
		{
			try
			{
			System.out.print("Div "+a1[i]+"+"+b1[i]+"/"+c1[i]+" to "+a2[i]+"+"+b2[i]+"/"+c2[i]+" and ");
			Fraction f1 = new Fraction(a1[i], b1[i], c1[i]);
			Fraction f2 = new Fraction(a2[i], b2[i], c2[i]);
			f1.div(f2);
			System.out.println("Read "+f1.toString()+" which is "+f1.getDouble());
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	protected static void doTest5()
	{
		long a1[] = {0,  0,  0, 0,  0,  0,  0,  0, 1, 0, 1, 3, 0, 0, 9,  0, 3};
		long b1[] = {1,  1, -1, 1,  1, -1, -1, -1, 0, 0, 7, 0, 0, 1, 0, -1, 0};
		long c1[] = {2,  2,  2, 2,  2,  2,  2,  2, 0, 0, 8, 0, 0, 2, 0,  2, 0};
		long a2[] = {0,  0,  0, 0,  0,  0,  0,  0, 1, 0, 1, 0, 0, 0, 0,  9, 1};
		long b2[] = {1, -1,  1, 1, -1,  1,  2, -2, 0, 0, 7, 1, 1, 0, 1,  0, 1};
		long c2[] = {2,  2,  2, 3,  3,  3,  3,  3, 0, 0, 8, 3, 2, 0, 2,  0, 3};
		int i;
		System.out.println("Mul Fraction Test");
		for ( i = 0; i<b1.length; i++ )
		{
			try
			{
			System.out.print("Mul "+a1[i]+"+"+b1[i]+"/"+c1[i]+" to "+a2[i]+"+"+b2[i]+"/"+c2[i]+" and ");
			Fraction f1 = new Fraction(a1[i], b1[i], c1[i]);
			Fraction f2 = new Fraction(a2[i], b2[i], c2[i]);
			f1.mul(f2);
			System.out.println("Read "+f1.toString()+" which is "+f1.getDouble());
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
	protected static void doTest4()
	{
		long a1[] = {0,  0,  0, 0,  0,  0,  0, 3, 3, 1};
		long b1[] = {1,  1, -1, 1,  1, -1, -1, 0, 0, 1};
		long c1[] = {2,  2,  2, 2,  2,  2,  2, 0, 0, 3};
		long a2[] = {0,  0,  0, 0,  0,  0,  0, 1, 1, 3};
		long b2[] = {1, -1,  1, 1, -1,  1,  2, 0, 1, 0};
		long c2[] = {2,  2,  2, 3,  3,  3,  3, 0, 3, 0};
		int i;
		System.out.println("Subdition Fraction Test");
		for ( i = 0; i<b1.length; i++ )
		{
			try
			{
			System.out.print("Sub "+a1[i]+"+"+b1[i]+"/"+c1[i]+" to "+a2[i]+"+"+b2[i]+"/"+c2[i]+" and ");
			Fraction f1 = new Fraction(a1[i], b1[i], c1[i]);
			Fraction f2 = new Fraction(a2[i], b2[i], c2[i]);
			f1.sub(f2);
			System.out.println("Read "+f1.toString()+" which is "+f1.getDouble());
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	protected static void doTest3()
	{
		long b1[] = {1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0,
			-1, -1, -1, -1, -1, -2, -9, -2, -1, -9, -1, -18, -21, -22, -1, -0,
			-1, -1, -1, -1, -1, -2, -9, -2, -1, -9, -1, -18, -21, -22, -1, -0,
			-1, -1, -1, -1, -1, -2, -9, -2, -1, -9, -1, -18, -21, -22, -1, -0};
		long c1[] = {2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10,
			-2, -3, -4, -5, -6, -4, -12, -3, -11, -18, -101, -9, -9, -9, -0, -10,
			2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10,
			2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10};
		long b2[] = {1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0,
			-1, -1, -1, -1, -1, -2, -9, -2, -1, -9, -1, -18, -21, -22, -1, -0,
			-1, -1, -1, -1, -1, -2, -9, -2, -1, -9, -1, -18, -21, -22, -1, -0,
			1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0};
		long c2[] = {2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10,
			-2, -3, -4, -5, -6, -4, -12, -3, -11, -18, -101, -9, -9, -9, -0, -10,
			2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10,
			2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10};
		int i;
		System.out.println("Addition Fraction Test");
		for ( i = 0; i<b1.length; i++ )
		{
			try
			{
			System.out.print("Add "+b1[i]+"/"+c1[i]+" to "+b2[i]+"/"+c2[i]+" and ");
			Fraction f1 = new Fraction(b1[i], c1[i]);
			Fraction f2 = new Fraction(b2[i], c2[i]);
			f1.add(f2);
			System.out.println("Read "+f1.toString()+" which is "+f1.getDouble());
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	protected static void doTest2()
	{
		long a[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1};
		long b[] = {1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0, -1, -1, 1, -1, -1, 1};
		long c[] = {2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10, -2, 2, -2, -2, 2, -2};
		int i;
		System.out.println("Set Fraction Test of a+b/c");
		for ( i = 0; i<b.length; i++ )
		{
			try
			{
			System.out.print("Set "+a[i]+"+"+b[i]+"/"+c[i]+" and ");
			Fraction f = new Fraction(a[i], b[i], c[i]);
			System.out.println("Read "+f.toString()+" which is "+f.getDouble());
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}

	protected static void doTest1()
	{
		long b[] = {1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0, -1, -1, 1};
		long c[] = {2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10, -2, 2, -2};
		int i;
		System.out.println("Set Fraction Test of b/c");
		for ( i = 0; i<b.length; i++ )
		{
			try
			{
			System.out.print("Set "+b[i]+"/"+c[i]+" and ");
			Fraction f = new Fraction(b[i], c[i]);
			System.out.println("Read "+f.toString()+" which is "+f.getDouble());
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}

	public static void main(String [] args)
	{
		try
		{
//			doTest1();
//			doTest2();
//			doTest3();
			doTest4();
//			doTest5();
//			doTest6();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		System.exit(0);
	}
}
