package pl.projewski.generator.interfaces;

import pl.projewski.generator.abstracts.AbstractParameter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.tools.stream.NumberWriter;

/** 
	* Interfejs Generatora, pozwalający na implementowanie dowolnego generatora
	* w wybranym programie.
  */
public abstract class GeneratorInterface
	extends AbstractParameter
{
	public static final String INFOFIELD = "Informacja"; 
	
	@Override
	public void initParameterInterface() {
		parameters.put(GeneratorInterface.INFOFIELD, null);
	}

	@Override
	public String getTypeName() {
		return "generator";
	}
	
/*	public Class[] getAllowedClass(String param) throws ParameterException
	{
		return null;
	}
*/	/** 
	* Metoda inicjalizująca pracę generatora. Parametry inicjalizujące są
	* pobierane przy pomocy interfejsu ParameterInterface.
	* @exception GeneratorException
	* @see GeneratorException
	* @see ParameterInterface
  */
	public abstract void init() 
		throws GeneratorException;
	/** 
	* Metoda reinicjalizująca pracę generatora.
	* @exception GeneratorException
	* @see GeneratorException
  */
	public abstract void reinit()
		throws GeneratorException;
	/** 
	* Metoda pobierająca z generatora kolejną wartość.
	* @return Zwracana wygenerowana wartość jest typu long.
	* @exception GeneratorException
	* @see GeneratorException
  */
	public abstract long getLong()
		throws GeneratorException;
	/** 
	* Metoda pobierająca z generatora kolejną wartość.
	* @return Zwracana wygenerowana wartość jest typu int.
	* @exception GeneratorException
	* @see GeneratorException
  */
	public abstract int getInt()
		throws GeneratorException;
	/** 
	* Metoda pobierająca z generatora kolejną wartość.
	* @return Zwracana wygenerowana wartość jest typu double.
	* @exception GeneratorException
	* @see GeneratorException
  */
	public abstract double getDouble()
		throws GeneratorException;
	/** 
	* Metoda pobierająca z generatora kolejną wartość.
	* @return Zwracana wygenerowana wartość jest typu float.
	* @exception GeneratorException
	* @see GeneratorException
  */
	public abstract float getFloat()
		throws GeneratorException;
	
	/** 
	* Metoda, która wypełnia podaną tablicę kolejnymi wartościami z
	* generatora. Założeniem tej metody jest jej wykorzystanie do
	* przyspieszania pracy algorytmu dla pobierania wielu wartości
	* w pojedyncze tablice.
	* @param tab Obiekt wejściowy w postaci tablicy
	* @exception GeneratorException
	* @see GeneratorException
  */
	public abstract void rawFill(Object table)
		throws GeneratorException;
	
	public abstract void rawFill(NumberWriter writer, ClassEnumerator c, int num)
		throws GeneratorException;
	
	public Class<?>[] getAllowedClass(String param)
		throws ParameterException
	{
		if ( param.equals(GeneratorInterface.INFOFIELD) )
			return new Class[] {String.class};
		return new Class[0];	}
	
	public String toString()
	{
		return toString(null);
	}
	
	public Object getAsObject(Class<?> retType)
	throws GeneratorException
	{
		if ( retType == Integer.class )
			return Integer.valueOf( getInt() );
		else if (retType == Long.class )
			return Long.valueOf( getLong() );
		else if (retType == Float.class )
			return Float.valueOf( getFloat() );
		else if (retType == Double.class )
			return Double.valueOf( getDouble() );
		else
			return null;
	}
	
	public String toString(String prefix)
	{
		StringBuffer sb = new StringBuffer();
		if ( prefix == null )
			prefix = "";
		sb.append("Generator ");
		sb.append(this.getClass().getSimpleName());
		sb.append('\n');
		sb.append(prefix);
		sb.append("[\n");
		sb.append(super.toString(prefix + '\t'));
		sb.append(prefix);
		sb.append(']');
		return sb.toString();
	}
	
};
