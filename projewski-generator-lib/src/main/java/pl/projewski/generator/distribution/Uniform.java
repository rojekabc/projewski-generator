package pl.projewski.generator.distribution;
import pk.ie.proj.abstracts.AbstractDistribution;
import pk.ie.proj.tools.Fraction;

public class Uniform
	extends AbstractDistribution
{
	// Inverse CDF
	public double getInverse(double propability)
	{
		if ( (propability >= 0.0) && (propability <= 1.0) )
			return propability;
		else
			return Double.NaN;
	}
	// PDF
	public double getDensity(double normvalue)
	{
		if ( normvalue >= 0.0 && normvalue <= 1.0 )
			return 1.0;
		return 0.0;
	}
	// CDF
	public double getPropability(double normvalue)
	{
		if ( normvalue <= 0.0 )
			return 0.0;
		else if ( normvalue <= 1.0 )
			return normvalue;
		else
			return 1.0;
	}
	public Fraction getPropability(Fraction normvalue)
	{
		if ( normvalue.getDouble() < 0.0 )
			return new Fraction(0,0);
		else if ( normvalue.getDouble() <= 1.0 )
			return new Fraction(normvalue);
		else
			return new Fraction(1,1);
	}
	
	public void initParameterInterface()
	{
	}
	public Class<?> [] getAllowedClass(String param)
	{
		return new Class[0];
	}
}
