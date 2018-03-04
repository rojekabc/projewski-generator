package pk.ie.proj.generproj;

import pk.ie.proj.tools.Mysys;

public class GraphScal
{
	protected Object _min, _max, _jmp;

	protected int findNumScal(int num, int chg)
	{
		return (int)findNumScal((long)num, chg);
	}
	protected long findNumScal(long num, int chg)
	{
		long sav = num;
		long nten;
		long scal;
		// wyznacz liczbe cyfr - 1 w numerze
		if ( num == 0 )
			nten = 0;
		else
			nten = (long)Math.floor(Math.log(Math.abs(num))/Math.log(10));
		// oblicz skale w 10^nten
		scal = 1;
		while (nten != 0)
		{
			scal *= 10;
			nten--;
		}
		// przystosuj liczbe do skali
		num /= scal;
		num *= scal;
		// dobierz ja tak, aby objela swym zasiegiem
		// wymagana wartosc
		if (chg>0)
		{
			while (num < sav)
				num += scal>>1;
		}
		if (chg<0)
		{
			while (num > sav)
				num -= scal>>1;
		}
		// zwroc ustalona wartosc
		return num;
	}
	
	protected double findNumScal(double num, int chg)
	{
		if ( Math.floor(Math.abs(num)) > 0 )
			return (double)findNumScal((long)num, chg);
		if (num == 0.0)
			return num;
		Mysys.debugln("Warning: GraphScal.findNumScal double, not implemented all");
		return 0.0;
	}
	
	protected float findNumScal(float num, int chg)
	{
		// tak duze, ze wieksze od jednosci
		if ( Math.floor(Math.abs(num)) > 0 )
			return (float)findNumScal((long)num, chg);
		// rowne zero
		if (num == 0.0f)
			return num;
		// TODO: powinno zostac uzyte skalowanie po precyzji
		Mysys.debugln("Warning: GraphScal.findNumScal float, not implemented all");
		return 0.0f;		
	}

	public Object getMin()
	{
		return _min;
	}
	public Object getMax()
	{
		return _max;
	}
	public Object getJmp()
	{
		return _jmp;
	}
	
	public GraphScal(int min, int max)
	{
		if ( min < 0 )
			_min = Integer.valueOf( findNumScal(min, -1) );
		else
			_min = Integer.valueOf( findNumScal(min, 0) );
		if ( max > 0 )
			_max = Integer.valueOf( findNumScal(max, 1) );
		else
			_max = Integer.valueOf( findNumScal(max, 0) );
	}
	
	public GraphScal(long min, long max)
	{
		if ( min < 0 )
			_min = Long.valueOf( findNumScal(min, -1) );
		else
			_min = Long.valueOf( findNumScal(min, 0) );
		if ( max > 0 )
			_max = Long.valueOf( findNumScal(max, 1) );
		else
			_max = Long.valueOf( findNumScal(max, 0) );
	}
	
	public GraphScal(float min, float max)
	{
		if ( min < 0 )
			_min = Float.valueOf( findNumScal(min, -1) );
		else
			_min = Float.valueOf( findNumScal(min, 0) );
		if ( max > 0 )
			_max = Float.valueOf( findNumScal(max, 1) );
		else
			_max = Float.valueOf( findNumScal(max, 0) );
	}
	
	public GraphScal(double min, double max)
	{
		if ( min < 0 )
			_min = Double.valueOf( findNumScal(min, -1) );
		else
			_min = Double.valueOf( findNumScal(min, 0) );
		if ( max > 0 )
			_max = Double.valueOf( findNumScal(max, 1) );
		else
			_max = Double.valueOf( findNumScal(max, 0) );
	}
}
