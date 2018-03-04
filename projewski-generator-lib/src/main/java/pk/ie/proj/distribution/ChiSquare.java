package pk.ie.proj.distribution;
import pk.ie.proj.abstracts.AbstractDistribution;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Fraction;

public class ChiSquare
	extends AbstractDistribution
{
	public static final String V = "liczba stopni swobody";

	Object [] Inverse =
	{
		new int[] {19, 20},
		new double[][]
		{
			{0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.2, 0.3,
			0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.975,
			0.99, 0.995, 0.999}, // 19
			{0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.2, 0.3,
			0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.975,
			0.99, 0.995, 0.999} // 20
		},
		new double[][]
		{
			{5.407, 6.844, 7.633, 8.907, 10.117, 11.651, 13.716, 15.352,
			16.850, 18.338, 19.910, 21.689, 23.900, 27.204, 30.144, 32.852,
			36.191, 38.582, 43.819}, //19
			{5.921, 7.434, 8.260, 9.591, 10.851, 12.443, 14.578, 16.266,
			17.809, 19.337, 20.951, 22.775, 25.038, 28.412, 31.410, 34.170,
			37.566, 39.997, 45.314} // 20
		}
	};
	
	// Inverse CDF
	public double getInverse(double propability)
	{
		Object parV = this.parameters.get(V);
		if (parV == null )
		{
			System.out.println("[WARN]["+this.getClass().getName()+"] Pusty stopien swobody");
			return Double.NaN;
		}
		if ( (propability > 0.0) && (propability < 1.0) )
		{
			int f = Convert.tryToInt(parV);
			int [] t = (int[])Inverse[0];
			int i;
			int j;
//			System.out.println("t.length="+t.length+" f="+f);
			for (i=0; i<t.length; i++)
			{
				if ( t[i] == f )
					break;
			}
			if ( i == t.length )
			{
				System.out.println("Nie odnaleziony stopien swobody");
				return Double.NaN;
			}
			double [][] p = ((double[][])(Inverse[1]));
			for (j=0; j<p[i].length; j++)
			{
				if ( propability == p[i][j] )
					break;
			}
			if ( j == p[i].length )
			{
				System.out.println("Nie odnalezione prawdopodobienstwo "+propability);
				return Double.NaN;
			}
			return ((double[][])Inverse[2])[i][j];
		}
		else
			return Double.NaN;
	}
	
	// PDF
	public double getDensity(double normvalue)
	{
		return 0.0;
	}
	
	// CDF
	public double getPropability(double normvalue)
	{
		return 0.0;
	}
	
	public Fraction getPropability(Fraction normvalue)
	{
		return new Fraction(0,0);
	}
	
	public void initParameterInterface()
	{
		this.parameters.put(V, Integer.valueOf(20));		
	}
	
	public Class<?>[] getAllowedClass(String param) throws ParameterException {
		if ( param.equals(V) )
			return new Class<?>[] {Integer.class};
		return new Class<?>[0];
	}
}
