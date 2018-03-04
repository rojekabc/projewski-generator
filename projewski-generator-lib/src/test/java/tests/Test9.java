/**
 * Testowanie klasy GeneratorStoreFile
 */
package tests;

import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.GeneratorStoreFile;

/**
 * @author projewski
 *
 */
public class Test9 {
	
	public final static String GeneratorFileName = "../GenerProj/Fishman_Testowy.gen";
		
	public static void testLoad_01()
	{
		try {
			GeneratorInterface gen = GeneratorStoreFile.loadGeneratorFromFile(GeneratorFileName);
			if ( gen == null )
				throw new Exception("Generator is null");
			System.out.println(gen.toString());
		} catch (GeneratorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		testLoad_01();
		

	}

}
