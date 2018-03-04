package pl.projewski.generator.interfaces;

import pk.ie.proj.exceptions.ParameterException;

/*
 * Interfejs obsługi parametrów i opisów.
 */
public interface ParameterInterface
{
	/** 
	* Lista nazw parametrów.
 */
	public String [] listParameters() 
		throws ParameterException;
	/** 
	* Lista wartości parametrów.
 */
//	public Object [] listValues()
//		throws ParameterException;
	/** M4_GEN_PI_GET_ALLOWED_CLASSES_S */
	public Class<?> [] getAllowedClass(String param)
		throws ParameterException;
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
		throws ParameterException;

	/** 
	* Funkcja pozwala na ustalenie wartości parametru.
	* @param paramIx Indeks parametru.
	* @param value Przypisywana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
//	public void setParameter(int paramIx, Object value)
//		throws ParameterException;
	/** 
	* Funkcja pozwala uzyskać aktualną wartość parametru.
	* @param param Nazwa parametru.
	* @return Aktualnie przypisana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
	public Object getParameter(String param)
		throws ParameterException;
	/** 
	* Funkcja pozwala uzyskać aktualną wartość parametru.
	* @param paramIx Indeks parametru parametru.
	* @return Aktualnie przypisana wartość.
	* @exception ParameterException
	* @see ParameterException
 */
//	public Object getParameter(int paramIx)
//		throws ParameterException;
	/** 
	* Funkcja pozwala uzyskać opis parametru.
	* @param paramIx Indeks parametru.
	* @return Opis parametru.
	* @exception ParameterException
	* @see ParameterException
 */
//	public String getParameterDescription(int paramIx)
//		throws ParameterException;
	/** 
	* Funkcja pozwala uzyskać opis parametru.
	* @param param Nazwa parametru.
	* @return Opis parametru.
	* @exception ParameterException
	* @see ParameterException
 */
	public String getParameterDescription(String param)
		throws ParameterException;
	/** 
	* Funkcja pozwala uzyskać opis klasy.
	* @return Opis klasy.
	* @exception ParameterException
	* @see ParameterException
 */
	public String getDescription()
		throws ParameterException;
}
