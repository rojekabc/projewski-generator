package tests;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.LaborDataInterface;
import pl.projewski.generator.labordata.TestChiSquare;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.GeneratorStoreFile;

/**
 * @author projewski
 * <p>
 * Sprawdzenie klasy pk.ie.proj.labordata.TestChiSquare
 */
public class Test11 {

    public static void main(final String[] args) {
        final LaborDataInterface ld = new TestChiSquare();
        final GeneratorInterface gi;
        final GeneratedData gdt;
        try {
            ld.setParameter(TestChiSquare.GENERATIONAMMOUNT, "100");
            ld.setParameter(TestChiSquare.TESTAMMOUNT, "5");
            gi = GeneratorStoreFile.loadGeneratorFromFile("E:\\Eclipse\\GenerProj\\Fishman_Testowy.gen");
            ld.setParameter(TestChiSquare.GENERATOR, gi);
            gdt = GeneratedData.createTemporary();
            ld.getOutputData(gdt);
            final NumberReader reader = gdt.getNumberReader();
            int size = gdt.getSize();
            System.out.println("Otrzymano danych: " + size);
            while (size-- > 0) {
                System.out.println(reader.readDouble());
            }
            gdt.delete();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
