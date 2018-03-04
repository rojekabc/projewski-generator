/**
 * 
 */
package tests;

import java.io.IOException;

import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.exceptions.LaborDataException;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.labordata.TestChiSquare;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.GeneratorStoreFile;
import pk.ie.proj.tools.stream.NumberReader;

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
