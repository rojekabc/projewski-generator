package pk.ie.proj.tools;

public class VectorLong {

	private long [] data = null; 

	public VectorLong()
	{
	}
	
	public VectorLong(long [] a)
	{
		data = ArrayUtil.putLast(data, a);
	}
	
	public VectorLong(String inp) {
		this.add(inp);
	}
	
	public void add(long n)
	{
		data = ArrayUtil.putLast(data, n);
	}
	
	public void add(Long n)
	{
		data = ArrayUtil.putLast(data, n.longValue());
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
				data = ArrayUtil.putLast(data, Convert.tryToLong(c));
			lastpos = pos;
			lastpos++;
		}
		c = s.substring(lastpos).trim();
		if ( c.length() != 0 )
			data = ArrayUtil.putLast(data, Convert.tryToLong(c));
	}
	
	public long[] toArray()
	{
		return data;
	}
	
	public int size()
	{
		return data.length;
	}
	
	public long get(int i)
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
