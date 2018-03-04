/**
 * 
 */
package tests;

import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.generator.GeneratorGausHastings;
import pk.ie.proj.generator.GeneratorJavaRandom;
import pk.ie.proj.generator.GeneratorLCG;
import pk.ie.proj.generator.GeneratorSimConst;
import pk.ie.proj.generator.GeneratorSystemTime;
import pk.ie.proj.interfaces.GeneratorInterface;

/**
 * @author maq
 *
 * Sprawdzenie funckjonowania opisow do elementow
 */
public class Test6 {
	
	public static void opisGeneratora(GeneratorInterface generator)
	{
		try {
			String [] params = generator.listParameters();
			int i;
			String desc;
			
			desc = generator.getDescription();
			if ( desc == null )
				desc = "";
			System.out.println("Generator " + generator.getClass().getSimpleName() + " : " + desc);
			for ( i=0; i<params.length; i++ )
			{
				desc = generator.getParameterDescription(params[i]);
				System.out.print("Parametr [" + params[i] + "]: ");
				if ( desc != null )
					System.out.println(desc);
				else
					System.out.println();
			}
		} catch (ParameterException e) {
			e.printStackTrace();
		}		
		System.out.println("");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		opisGeneratora( new GeneratorGausHastings() );
		opisGeneratora( new GeneratorJavaRandom() );
		opisGeneratora( new GeneratorLCG() );
		opisGeneratora( new GeneratorSimConst() );
		opisGeneratora(new GeneratorSystemTime() );
	}

}
