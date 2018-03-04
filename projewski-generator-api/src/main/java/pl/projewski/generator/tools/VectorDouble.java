package pl.projewski.generator.tools;

public class VectorDouble {
	
	private double [] data = null;

	public VectorDouble()
	{
	}
	
	public VectorDouble(double [] arr)
	{
		add(arr);
	}
	
	public VectorDouble(String inp)
	{
		this.add(inp);
	}
	
	public void add(double n)
	{
		data = ArrayUtil.putLast(data, n);
	}
	
	public void add(double [] arr)
	{
		data = ArrayUtil.putLast(data, arr);
	}
	
	public void add(Double n)
	{
		data = ArrayUtil.putLast(data, n.doubleValue());
	}
	
	/*
	 * W Stringu moze wystepowac pare wartosci
	 * Wartosci te powinny byc rozdzielone znakami spacji
	 * W razie problemow z konwesja zostaja porzucone wyjatki
	 */
	public void add(String s)
	{
		String c = null;
		int lastpos = 0;
		int pos = 0;
		s = s.trim();
		while ( (pos = s.indexOf(" ", lastpos)) != -1 )
		{
			c = s.substring(lastpos, pos).trim();
			if ( c.length() != 0 )
				data = ArrayUtil.putLast(data, Convert.tryToDouble(c));
			lastpos = pos;
			lastpos++;
		}
		c = s.substring(lastpos).trim();
		if ( c.length() != 0 )
			data = ArrayUtil.putLast(data, Convert.tryToDouble(c));
	}
	
	public int size()
	{
		if ( data != null )
			return data.length;
		return 0;
	}
	
	public double get(int i)
	{
		return data[i];
	}
	
	public double[] toArray()
	{
		return data;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		int i;
		if ( data != null )
			for (i=0; i<data.length; i++)
			{
				sb.append(data[i]);
				if ( i!=data.length-1 )
					sb.append(" ");
			}
		return sb.toString();
	}
}
