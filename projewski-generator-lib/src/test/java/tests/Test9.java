package tests;

import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.GeneratorStoreFile;

/**
 * @author projewski
 */
public class Test9 {

    private final static String GeneratorFileName = "../GenerProj/Fishman_Testowy.gen";

    private static void testLoad_01() {
        try {
            final GeneratorInterface gen = GeneratorStoreFile.loadGeneratorFromFile(GeneratorFileName);
            if (gen == null) {
                throw new Exception("Generator is null");
            }
            System.out.println(gen.toString());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        testLoad_01();


    }

}
