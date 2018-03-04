package pk.ie.proj.tools;

import java.io.PrintStream;

import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.ParameterInterface;

public class ParameterStoreFile
//extends DefaultHandler
{
	private java.util.Vector<Object> createdObject = null;
	private java.util.Vector<String> createdNames = null;

	public ParameterStoreFile()
	{
		createdObject = new java.util.Vector<Object>();
		createdNames = new java.util.Vector<String>();
	}

	/**
	 * Otworz w wektorze obiekt do utworzenia.
	 * Do wektora nazw zostaje dodana nazwa parametru tworzonego.
	 * Do wektora stworzonych obiektow zostaje dodana nazwa klasy
	 * obiektu, jaki ma byc utworzony.
	 * @param parameterName
	 * @param className
	 * @return
	 */
	public boolean openObject(String parameterName, String className)
	{
		try
		{
//			if ( className == null )
//				return false;
//			if ( className.length() == 0 )
//				return false;
//			Object obj = null;
			Mysys.debugln("Open " + className + " : " + parameterName);
			if (( className != null ) && ( className.length() != 0 ))
				createdObject.add( className );
			else
				createdObject.add(null);
/*
 				Class cl = Class.forName(className);
				
				if ( cl == Integer.class )
					obj = new Integer(0);
				else if ( cl == Long.class )
					obj = new Long(0);
				else if ( cl == Float.class )
					obj = new Float(0.0f);
				else if ( cl == Double.class )
					obj = new Double(0.0);
				else
					obj = Class.forName(className).newInstance();
			createdObject.add(obj);*/
			if ( parameterName != null )
				createdNames.add(parameterName);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Ostatniemu elementowi tworzonego wektora obiektow zostaje
	 * przypisany określony obiekt. Typ obiektu jest ustalany na
	 * podstawie zamieszczonej nazwy klasy.
	 * Jezeli nazwa klasy nie zostala okreslona, to poyostanie tam
	 * wartość parametru null.
	 * @param value
	 */
	public void setObjectValue(String value)
	{
		Object o = null;
		Class<?> cl = null;
		if ( createdObject.isEmpty() )
			return;
		o = createdObject.lastElement();
		if ( o instanceof ParameterInterface )
			return;
		if ( o != null )
		{
			try {
				cl = Class.forName((String)o);
			} catch (ClassCastException e) {
				System.err.println("Blad rzutowania na String z "+o.getClass().toString()+". Przypisywana wartość to ["+value+"]");
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
			try {
				o = Convert.tryToClass(value, cl);
				createdObject.set( createdObject.size()-1,
						o );
			} catch (ClassCastException e) {
				// Nieudane przez Convert.tryToClass
				System.err.println("Blad rzutowania na "+cl.toString()+". Przypisywana wartość to ["+value+"]");
				e.printStackTrace();
				return;
			}
		}
/*				new Integer(Convert.tryToInt(value)) );
		
		if ( o instanceof java.lang.Integer )
		{
			createdObject.set( createdObject.size()-1,
					new Integer(Convert.tryToInt(value)) );
		}
		else if ( o instanceof java.lang.Long )
		{
			createdObject.set( createdObject.size()-1,
					new Long(Convert.tryToLong(value)) );
		}
		else if ( o instanceof java.lang.Float )
		{
			createdObject.set( createdObject.size()-1,
					new Float(Convert.tryToFloat(value)) );
		}
		else if ( o instanceof java.lang.Double )
		{
			createdObject.set( createdObject.size()-1,
					new Double(Convert.tryToDouble(value)) );
		}*/
	}

	public void closeObject()
	{
		try
		{
			// jeżeli obiektów jest więcej niż jeden, to z pewnością
			// zamykany obecnie obiekt jest obiektem podrzędnym dla
			// obiektu instancji ParameterInterface
			if ( createdObject.size() > 1 )
			{
				ParameterInterface pi;
				Object o = createdObject.get(createdObject.size()-2); 
				// sprawdź, czy obiekt już zamieniono na odpowiednik,
				// czy nadal jest nazwą
				if ( o instanceof String )
				{
					pi = (ParameterInterface)Class.forName((String)o).newInstance();
					createdObject.set( createdObject.size()-2, pi );
				}
				else
					pi = (ParameterInterface)o;
				pi.setParameter( (String)createdNames.lastElement(),
						createdObject.lastElement());
				createdNames.remove(createdNames.lastElement());
			}
			createdObject.remove(createdObject.lastElement());
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public Object getLastObject()
	{
		Mysys.debugln("Close " + createdObject.lastElement().getClass().getName());
		return createdObject.lastElement();
	}

	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_NUMBER_STORE_PS_NSO */
	public static void writeNumberStore( PrintStream ps, NumberStoreOne nso )
	{
			ps.println();
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_VECTOR_PS_V */
	public static void writeVector( PrintStream ps, java.util.Vector<?> vec )
	{
			ps.println();
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_INTEGER_PS_I */
	public static void writeInteger( PrintStream ps, java.lang.Integer vec )
	{
			ps.println(vec.toString());
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_LONG_PS_L */
	public static void writeLong( PrintStream ps, java.lang.Long vec )
	{
			ps.println(vec.toString());
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_FLOAT_PS_F */
	public static void writeFloat( PrintStream ps, java.lang.Float vec )
	{
			ps.println(vec.toString());
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_DOUBLE_PS_D */
	public static void writeDouble( PrintStream ps, java.lang.Double vec )
	{
			ps.println(vec.toString());
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_PARAMETER_PS_PI */
	public static void writeParameter( PrintStream ps, ParameterInterface pin )
	{
		try {
			String [] lpar = pin.listParameters();
			if ( lpar == null )
				return;
			for ( int i = 0; i < lpar.length; i++ )
			{
				Object value = pin.getParameter(lpar[i]);
				ps.println("<parameter name='"+ lpar[i] +"' class='"+ (value!=null ? value.getClass().getName() : "" ) +"'>");
				if ( value == null )
					ps.println("NULL");
				else if ( value instanceof java.lang.Integer )
					writeInteger( ps, (java.lang.Integer)value );
				else if ( value instanceof java.lang.Float )
					writeFloat( ps, (java.lang.Float)value );
				else if ( value instanceof java.lang.Long )
					writeLong( ps, (java.lang.Long)value );
				else if ( value instanceof java.lang.Double )
					writeDouble( ps, (java.lang.Double)value );
				else if ( value instanceof GeneratorInterface )
					writeGenerator( ps, (GeneratorInterface)value );
				else if ( value instanceof java.util.Vector )
					writeVector( ps, (java.util.Vector<?>)value );
				else if ( value instanceof NumberStoreOne )
					writeNumberStore( ps, (NumberStoreOne)value );
				else if ( value instanceof ParameterInterface )
					writeParameterInterface( ps, (ParameterInterface)value, "ParameterInterface" );
				else if ( value instanceof String )
					ps.println(value.toString());
				else if ( value instanceof VectorDouble )
					ps.println(value.toString());
				else if ( value instanceof VectorFloat )
					ps.println(value.toString());
				else if ( value instanceof VectorLong )
					ps.println(value.toString());
				else if ( value instanceof VectorInteger )
					ps.println(value.toString());
				else
					Mysys.error("Cannot write parameter " + lpar[i] + ". Unknown class to write " + value.getClass().getName());
				ps.println("</parameter>");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	/** M4_GENERATOR_STORE_FILE_FUN_WRITE_GENERATOR_PS_GI */
	public static void writeGenerator( PrintStream ps, GeneratorInterface gen )
	{
		try {
			writeParameter( ps, gen );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void writeParameterInterface( PrintStream ps, ParameterInterface param, String name )
	{
		try {
			ps.println("<"+name+" class='"+ param.getClass().getName() +"'>");
			writeParameter( ps, param );
			ps.println("</"+name+">");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

}
