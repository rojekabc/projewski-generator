/**
 * 
 */
package pl.projewski.generator.tools.stream.interfaces;

import pl.projewski.generator.tools.stream.ScanedInputStream;

/**
 * @author piotrek
 *
 */
public interface IScaner
{
	/**
	 * Ustawienie dla skanera klasy grupujacej dany skaner, laczacej pomiedzy
	 * soba inne skanery
	 * 
	 * @param c klasa grupujaca
	 */
	public void setGroupClass(Class<?> c);
	public Class<?> getGroupClass();
	
	public void read(byte b, int streamposition, ScanedInputStream stream);
	
	public void setListener(IScanerListener listener);
	public void clearListeners();

}
