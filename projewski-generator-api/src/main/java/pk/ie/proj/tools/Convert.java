package pk.ie.proj.tools;


/*
 * Klasa różnych przyjaznych konwersji, co by kod nie był zbytnio wzbogacony
 * o rózne takie dziwne odwołania
 */
public class Convert {
	public static int tryToInt(Object inp)
		throws ClassCastException, NumberFormatException
	{
		if ( Integer.class.isInstance(inp) )
			return ((Integer)inp).intValue();
		if ( Long.class.isInstance(inp) )
			return ((Long)inp).intValue();
		if ( Float.class.isInstance(inp) )
			return ((Float)inp).intValue();
		if ( Double.class.isInstance(inp) )
			return ((Double)inp).intValue();
		if ( String.class.isInstance(inp) )
			return Integer.parseInt((String)inp);
		throw new ClassCastException();
	}
	
	public static float tryToFloat(Object inp)
		throws ClassCastException, NumberFormatException
	{
		if ( Float.class.isInstance(inp) )
			return ((Float)inp).floatValue();
		if ( Double.class.isInstance(inp) )
			return ((Double)inp).floatValue();
		if ( Integer.class.isInstance(inp) )
			return ((Integer)inp).floatValue();
		if ( Long.class.isInstance(inp) )
			return ((Long)inp).floatValue();
		if ( String.class.isInstance(inp) )
			return Float.parseFloat((String)inp);
		// inaczej nie wiem jak
		throw new ClassCastException();
	}
	
	public static long tryToLong(Object inp)
		throws ClassCastException, NumberFormatException
	{
		if ( Long.class.isInstance(inp) )
			return ((Long)inp).longValue();
		if ( Integer.class.isInstance(inp) )
			return ((Integer)inp).longValue();
		if ( Float.class.isInstance(inp) )
			return ((Float)inp).longValue();
		if ( Double.class.isInstance(inp) )
			return ((Double)inp).longValue();
		if ( String.class.isInstance(inp) )
			return Long.parseLong((String)inp);
		// inaczej nie wiem jak
		throw new ClassCastException();
	}

	public static double tryToDouble(Object inp)
		throws ClassCastException, NumberFormatException
	{
		if ( Double.class.isInstance(inp) )
			return ((Double)inp).doubleValue();
		if ( Float.class.isInstance(inp) )
			return ((Float)inp).doubleValue();
		if ( Integer.class.isInstance(inp) )
			return ((Integer)inp).doubleValue();
		if ( Long.class.isInstance(inp) )
			return ((Long)inp).doubleValue();
		if ( String.class.isInstance(inp) )
			return Double.parseDouble((String)inp);
		// inaczej nie wiem jak
		throw new ClassCastException();
	}
	
	public static boolean tryToBoolean(Object inp)
		throws ClassCastException, NumberFormatException
	{
		if (String.class.isInstance(inp))
			return Boolean.getBoolean((String)inp);
		throw new ClassCastException();		
	}
	
	public static Object tryToClass(String inp, Class<?> cl)
		throws ClassCastException, NumberFormatException
	{
		if (inp == null)
			return null;
		if ( cl == Integer.class )
			return Integer.valueOf(inp);
		else if ( cl == Long.class )
			return Long.valueOf(inp);
		else if ( cl == Float.class )
			return Float.valueOf(inp);
		else if ( cl == Double.class )
			return Double.valueOf(inp);
		else if ( cl == Boolean.class )
			return Boolean.valueOf(inp);
		else if ( cl == VectorInteger.class )
			return new VectorInteger(inp);
		else if ( cl == VectorLong.class )
			return new VectorLong(inp);
		else if ( cl == VectorFloat.class )
			return new VectorFloat(inp);
		else if ( cl == VectorDouble.class )
			return new VectorDouble(inp);
		else if ( cl == String.class )
			return inp;
		if (( inp == null ) || ( inp.length() == 0 ))
		{
			try {
				return cl.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		throw new ClassCastException();
	}
	
	public static int [] tryToTInt(Object inp)
		throws ClassCastException
	{
		if ( int[].class.isInstance(inp) )
			return (int[]) inp;
		throw new ClassCastException();
	}
	
	public static float [] tryToTFloat(Object inp)
		throws ClassCastException
	{
		if ( float[].class.isInstance(inp) )
			return (float[]) inp;
		throw new ClassCastException();
	}
	
	public static long [] tryToTLong(Object inp)
		throws ClassCastException
	{
		if ( long[].class.isInstance(inp) )
			return (long[]) inp;
		if ( inp instanceof VectorLong )
			return ((VectorLong)inp).toArray();
		throw new ClassCastException();
	}

	public static double [] tryToTDouble(Object inp)
		throws ClassCastException
	{
		if ( double[].class.isInstance(inp) )
			return (double[]) inp;
		throw new ClassCastException();
	}
	
	public static int [] tryToTInt(int inp)
	{
		int [] tmp = new int [1];
		tmp[0] = inp;
		return tmp;
	}
	
	public static float [] tryToTFloat(float inp)
		throws ClassCastException
	{
		float [] tmp = new float [1];
		tmp[0] = inp;
		return tmp;
	}
	
	public static long [] tryToTLong(long inp)
		throws ClassCastException
	{
		long [] tmp = new long [1];
		tmp[0] = inp;
		return tmp;
	}

	public static double [] tryToTDouble(double inp)
		throws ClassCastException
	{
		double [] tmp = new double [1];
		tmp[0] = inp;
		return tmp;
	}

	public static String stringWrap( String msg, int msgLength )
	{
		String msgShow = "";
		if ( msg == null )
			return msgShow;
		if ( msgLength != 0 )
		{
			java.util.Vector<String> words = new java.util.Vector<String>();
			String tmp = msg;
			String tmp2;
			int idx;

			while ( (idx = tmp.indexOf(' ')) != -1 )
			{
				words.add( tmp.substring(0, idx) );
				tmp = tmp.substring(idx+1);
			}
			words.add( tmp );
			
			tmp = "";
			for ( int i=0; i<words.size(); i++ )
			{
				tmp += words.get(i) + " ";
				if ( i < words.size()-1 )
				{
					tmp2 = tmp + words.get(i+1);
					if ( tmp2.length() > msgLength )
					{
						msgShow += tmp + "\n";
						tmp = "";
					}
				} else
					msgShow += tmp;
			}
		}
		else
			msgShow = msg;
		return msgShow;
	}


	// ustaw okienko centralnie na przestrzeni fizycznej
	// jezeli ma za duzy rozmiar to przytnij do wielkosci okna
	public static void setCentral(java.awt.Window w, int width, int height)
	{
		java.awt.GraphicsConfiguration gc = w.getGraphicsConfiguration();
		java.awt.Rectangle b = gc.getBounds();
		java.awt.Rectangle s = new java.awt.Rectangle();
		s.width = width;
		s.height = height;
		if ( s.width > b.width )
			s.width = b.width;
		if ( s.height > b.height )
			s.height = b.height;
		s.x = b.x + ((b.width - s.width)>>1);
		s.y = b.y + ((b.height - s.height)>>1);
		w.setBounds( s );
	}
	
	// Jesli obiekt obj jest rowny null to zwroci wartosc domyslna def,
	// w przeciwnym razie wartosc obiektu obj
	public static Object assignIfNull(Object obj, Object def)
	{
		if ( obj == null )
			return def;
		else
			return obj;
	}

}
