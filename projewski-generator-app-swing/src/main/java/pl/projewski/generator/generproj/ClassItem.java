/**
 * 
 */
package pl.projewski.generator.generproj;

public class ClassItem {
	private Class<?> klasa;
	
	public ClassItem(Class<?> klasa)
	{
		this.klasa = klasa;
	}
	
	public String toString()
	{
		return klasa.getSimpleName();
	}
	
	public Class<?> getClassItem()
	{
		return klasa;
	}
}
