package pk.ie.proj.abstracts;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.tools.Mysys;

/*
 * Interfejs obsługi parametrów i opisów.
 */
public abstract class AbstractParameter implements ParameterInterface
{
	protected Map<String, Object> parameters;
//	protected String [] paramName;
//	protected Object [] paramValue;

	public abstract void initParameterInterface();

	
	protected AbstractParameter()
	{
//		paramName = null;
//		paramValue = null;
		
		parameters = new LinkedHashMap<String, Object>();
		initParameterInterface();
	}
	/** 
	* Lista nazw parametrów.
 */
	public String [] listParameters() 
		throws ParameterException
	{
		Set<String> keys = parameters.keySet();
		String [] arr = new String[keys.size()];
		keys.toArray(arr);
		return arr;
//		return paramName;
	}
	/** 
	* Lista wartości parametrów.
 */
/*	public Object [] listValues()
		throws ParameterException
	{
		return paramValue;
	}
*/	/** M4_GEN_PI_GET_ALLOWED_CLASSES_S */
//	public Class [] getAllowedClass(String param)
//		throws ParameterException
//	{
//
//	}
	/** M4_GEN_PI_GET_ALLOWED_CLASSES_I */
//	public abstract Class [] getAllowedClass(int paramIx)
//		throws ParameterException;
	/** 
	* Funkcja pozwala na ustalenie wartości parametru.
	* @param param Nazwa parametru.
	* @param value Przypisywana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
	public void setParameter(String param, Object value)
		throws ParameterException
	{
		// sprawdź, czy ustawiany parametr jest na liście dozwolonych
		Class<?> [] allowed = getAllowedClass(param);
		boolean isAllowed = false;
		
		if ( value == null )
			isAllowed = true;
		else
		{
			for ( int i=0; i<allowed.length; i++ )
			{
				if ( allowed[i].isInstance(value) )
				{
					isAllowed = true;
					break;
				}
			}
		}
		
		if ( isAllowed == false )
			throw new ParameterException(ParameterException.PARAMETER_CLASS_ERROR, param);
		
		// ustaw parametr
		if ( this.parameters.containsKey(param) )
			this.parameters.put(param, value);
	}

	/** 
	* Funkcja pozwala na ustalenie wartości parametru.
	* @param paramIx Indeks parametru.
	* @param value Przypisywana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
/*	public void setParameter(int paramIx, Object value)
		throws ParameterException
	{
		paramValue[paramIx] = value;
	}
*/	/** 
	* Funkcja pozwala uzyskać aktualną wartość parametru.
	* @param param Nazwa parametru.
	* @return Aktualnie przypisana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
	public Object getParameter(String param)
		throws ParameterException
	{
		if ( this.parameters.containsKey(param) )
			return this.parameters.get(param);
		return null;
	}
	/** 
	* Funkcja pozwala uzyskać aktualną wartość parametru.
	* @param paramIx Indeks parametru parametru.
	* @return Aktualnie przypisana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
/*	public Object getParameter(int paramIx)
		throws ParameterException
	{
		return paramValue[paramIx];
	}
*/
	public abstract String getTypeName();
/*	protected String getTypeName()
	{
		if ( this instanceof GeneratorInterface )
			return "generator";
		else if ( this instanceof LaborDataInterface )
			return "labordata";
		else if ( this instanceof ViewDataInterface )
			return "viewdata";
		else if ( this instanceof DistributionAbstract )
			return "distribution";
		else
			return "other";
	}
*/	/** 
	* Funkcja pozwala uzyskać opis parametru.
	* @param param Nazwa parametru.
	* @return Opis parametru.
	* @exception ParameterException
	* @see ParameterException
 */
	public String getParameterDescription(String param)
		throws ParameterException
	{			
		String classname = this.getClass().getSimpleName();
		return Mysys.getDescription(classname, param, this.getTypeName(), null);
	}
	/** 
	* Funkcja pozwala uzyskać opis klasy.
	* @return Opis klasy.
	* @exception ParameterException
	* @see ParameterException
 */
	public String getDescription()
		throws ParameterException
	{
		String classname = this.getClass().getSimpleName();
		return  Mysys.getDescription(classname, classname, this.getTypeName(), null);
	}
	
	public String toString()
	{
		return toString(null);
	}


	public String toString(String prefix) {
		StringBuffer sb = new StringBuffer();
		String[] params = null;
		try {
			params = this.listParameters();
		} catch (ParameterException e) {
			Mysys.error(e);
		}
		int i;
		if ( prefix == null )
			prefix = "";
		if ( params == null )
			return sb.toString();
		for (i=0; i<params.length; i++)
		{
			sb.append(prefix);
			sb.append(params[i]);
			sb.append(" = ");
			try {
				Object parameter = this.getParameter(params[i]);
				if ( parameter != null )
				{
					if ( parameter instanceof AbstractParameter )
						sb.append(((AbstractParameter)this.getParameter(params[i])).toString(prefix));
					else
						sb.append(this.getParameter(params[i]).toString());
				}
				else
					sb.append("(null)");
			} catch (ParameterException e) {
				e.printStackTrace();
			}
			sb.append("\n");
		}
		return sb.toString();
	}	
}
