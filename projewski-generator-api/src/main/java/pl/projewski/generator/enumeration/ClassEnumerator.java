package pl.projewski.generator.enumeration;

public enum ClassEnumerator {
	INTEGER,
	LONG,
	FLOAT,
	DOUBLE;
	
	public static ClassEnumerator getType(Object obj)
	{
		if ( obj instanceof int[] )
			return INTEGER;
		else if ( obj instanceof long[] )
			return LONG;
		else if ( obj instanceof float[] )
			return FLOAT;
		else if ( obj instanceof double[] )
			return DOUBLE;
		return null;
	}
	
	public static ClassEnumerator getType(int a)
	{
		return INTEGER;
	}
	
	public static ClassEnumerator getType(long a)
	{
		return LONG;
	}
	
	public static ClassEnumerator getType(float a)
	{
		return FLOAT;
	}
	
	public static ClassEnumerator getType(double a)
	{
		return DOUBLE;
	}
	
	public static Class<?> getAsClass(ClassEnumerator e)
	{
		if ( e == INTEGER )
			return int[].class;
		else if ( e == LONG )
			return long[].class;
		else if ( e == FLOAT )
			return float[].class;
		else if ( e == DOUBLE )
			return double[].class;
		return null;
	}
}
