package pk.ie.proj.tools;

public class VectorFloat {
	float [] data = null;

	public VectorFloat()
	{
	}
	
	public VectorFloat(String inp) {
		this.add(inp);
	}
	
	public void add(float n)
	{
		data = ArrayUtil.putLast(data, n);
	}
	
	public void add(Float n)
	{
		data = ArrayUtil.putLast(data, n.floatValue());
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
				data = ArrayUtil.putLast(data, Convert.tryToFloat(c));
			lastpos = pos;
			lastpos++;
		}
		c = s.substring(lastpos).trim();
		if ( c.length() != 0 )
			data = ArrayUtil.putLast(data, Convert.tryToFloat(c));
	}
	
	public int size()
	{
		return data.length;
	}
	
	public float get(int i)
	{
		return data[i];
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		int i;
		if ( data != null )
			for (i=0; i<data.length; i++)
			{
				sb.append(data[i]);
				if ( i != data.length-1 )
					sb.append(" ");
			}
		return sb.toString();
	}
}
