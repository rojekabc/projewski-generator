package pl.projewski.generator.distribution;
import pk.ie.proj.abstracts.AbstractDistribution;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Fraction;

public class FromVector
	extends AbstractDistribution
{
	// Inverse CDF
	public double getInverse(double propability)
	{
		// Pobierz parametr
		Object obj = this.parameters.get(INVERSECDF);
		if ( obj == null )
			return Double.NaN;
		double [] vec = Convert.tryToTDouble(obj);
		if ( vec.length == 0 )
			return Double.NaN;
		// Ustal wartosc zwrotna
		if ( (propability >= 0.0) && (propability <= 1.0) )
		{
			// TODO: teraz wpasuj w przedzial
			return propability;
		}
		else
			return Double.NaN;
	}
	// PDF
	public double getDensity(double normvalue)
	{
		// Pobierz parametr
		Object obj = this.parameters.get(PDF);
		if ( obj == null )
			return 0.0;
		double [] vec = Convert.tryToTDouble(obj);
		if ( vec.length == 0 )
			return 0.0;
		// Ustal wartosc zwrotna
		if ( normvalue >= 0.0 && normvalue <= 1.0 )
		{
			// TODO: wpasuj w przedzial
			return 1.0;
		}
		return 0.0;
	}
	// CDF
	public double getPropability(double normvalue)
	{
		// Pobierz parametr
		Object obj = this.parameters.get(CDF);
		if ( obj == null )
			return 0.0;
		double [] vec = Convert.tryToTDouble(obj);
		if ( vec.length == 0 )
			return 0.0;
		// Ustal wartosc zwrotna
		if ( normvalue <= 0.0 )
			return 0.0;
		else if ( normvalue < 1.0 )
		{
			// TODO: wpasuj w przedzial
			return normvalue;
		}
		else
			return 1.0;
	}
	public Fraction getPropability(Fraction normvalue)
	{
		return new Fraction(0,0);
	}

	public static final String CDF = "CDF";
	public static final String PDF = "PDF";
	public static final String INVERSECDF = "Inverse CDF";
	
	public void initParameterInterface()
	{
		this.parameters.put(CDF, null);
		this.parameters.put(PDF, null);
		this.parameters.put(INVERSECDF, null);
	}
	
	public Class<?>[] getAllowedClass(String param) throws ParameterException {
		Class<?> [] tmp = new Class[1];
		tmp[0] = double[].class;
		return tmp;
	}
}
