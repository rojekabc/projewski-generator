package pl.projewski.generator.abstracts;

import pl.projewski.generator.tools.Fraction;

public abstract class AbstractDistribution
	extends AbstractParameter
{
	// wartosci wejsciowe sa z zakresu 0.0 do 1.0 i dystrybuanta powinna
	// byc tak unormowana, ze dla wartosci 0.0 przyjmuje prawdopodobienstwo
	// 0.0, a dla wartosci 1.0 1.0
	// Matlab CDF
	public abstract double getPropability(double normvalue);
	public abstract Fraction getPropability(Fraction normvalue);
	
	// wartosci wejsciowe sa z zakresu 0.0 do 1.0 i jest prawdopodobienstwem
	// Matlab Inverse CDF
	public abstract double getInverse(double propability);
	// wartosc znormalizowana tak, ze znajduje sie pomiedzy 0.0 a 1.0
	// Matlab PDF
	public abstract double getDensity(double normvalue);

	public String getTypeName() {
		return "distribution";
	}
	
	public String toString()
	{
		return toString(null);
	}
	
	public String toString(String prefix)
	{
		StringBuffer strbuf = new StringBuffer();
		if ( prefix == null )
			prefix = "";
		strbuf.append("Distribution ");
		strbuf.append(this.getClass().getSimpleName());
		strbuf.append('\n');
		strbuf.append(prefix);
		strbuf.append("[\n");
		strbuf.append(super.toString(prefix + '\t'));
		strbuf.append(prefix);
		strbuf.append(']');
		return strbuf.toString();
	}
	
}
