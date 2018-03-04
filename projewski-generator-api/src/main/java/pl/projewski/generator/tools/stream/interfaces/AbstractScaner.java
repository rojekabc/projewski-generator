/**
 * 
 */
package pl.projewski.generator.tools.stream.interfaces;

/**
 * @author piotrek
 *
 */
public abstract class AbstractScaner implements IScaner
{
	private Class<?> groupClass = null;
	protected IScanerListener listener = null;
	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.interfaces.IScaner#setClass(java.lang.Class)
	 */
	public void setGroupClass(Class<?> c)
	{
		groupClass = c;
	}
	
	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.interfaces.IScaner#getGroupClass()
	 */
	public Class<?> getGroupClass() {
		return this.groupClass;
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.interfaces.IScaner#clearListeners()
	 */
	public void clearListeners() {
		listener = null;
		
	}

	/* (non-Javadoc)
	 * @see pk.ie.proj.tools.stream.interfaces.IScaner#setListener(pk.ie.proj.tools.stream.interfaces.IScanerListener)
	 */
	public void setListener(IScanerListener listener) {
		this.listener = listener;
	}

}
