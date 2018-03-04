package pl.projewski.generator.generproj.tests;

import pl.projewski.generator.generproj.GraphScal;

public class Test4
{

	protected static void doTest1()
	{
		
		int [] min = {0, 0, 0, 1, -1, 10, 13, -14, -32, -10, -10, -242, -1024};
		int [] max = {0, 1, 5, 9,  1, 10, 14, -13,  -2, 123, 238,  236,  2345};
		int i;
		for ( i=0; i<min.length && i<max.length; i++ )
		{
			GraphScal gs = new GraphScal( min[i], max[i] );
			System.out.println("For min="+min[i]+" and max="+max[i]+" scal is "+gs.getMin() + ":" +gs.getMax());
		}
	}

	public static void main(String [] args)
	{
		try
		{
			doTest1();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		System.exit(0);
	}
}
