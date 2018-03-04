/**
 * 
 */
package tests;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.LaborDataException;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.LaborDataInterface;
import pl.projewski.generator.labordata.TestChiSquare;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.GeneratorStoreFile;
import pl.projewski.generator.tools.stream.NumberReader;

import java.io.IOException;

/**
 * @author projewski
 *
 * Sprawdzenie klasy pk.ie.proj.labordata.TestChiSquare
 */
public class Test11 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LaborDataInterface ld = new TestChiSquare();
		GeneratorInterface gi = null;
		GeneratedData gdt = null;
		try {
			ld.setParameter(TestChiSquare.GENERATIONAMMOUNT, "100");
			ld.setParameter(TestChiSquare.TESTAMMOUNT, "5");
			gi = GeneratorStoreFile.loadGeneratorFromFile("E:\\Eclipse\\GenerProj\\Fishman_Testowy.gen");
			ld.setParameter(TestChiSquare.GENERATOR, gi);
			gdt = GeneratedData.createTemporary();
			ld.getOutputData(gdt);
			NumberReader reader = gdt.getNumberReader();
			int size = gdt.getSize();
			System.out.println("Otrzymano danych: " + size);
			while ( size-- > 0 )
				System.out.println(reader.readDouble());
			gdt.delete();
		} catch (ParameterException e) {
			e.printStackTrace();
		} catch (GeneratorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LaborDataException e) {
			e.printStackTrace();
		} catch (NumberStoreException e) {
			e.printStackTrace();
		}
//		System.out.println(ld.toString());
	}

}
