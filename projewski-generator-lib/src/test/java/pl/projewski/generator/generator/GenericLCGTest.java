package pl.projewski.generator.generator;

import org.junit.jupiter.api.Test;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.VectorLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author projewski
 */
class GenericLCGTest {

    @Test
    void testNextLong_sameAsGeneratorLCG() {
        final int numOfGeneration = 1000;
        // create generator GenericLCG
        final GeneratorInterface genericLCG = new GenericLCG();
        genericLCG.setParameter(GenericLCG.A, new VectorLong(new long[]{7}));
        genericLCG.setParameter(GenericLCG.SEED, new VectorLong(new long[]{2}));
        genericLCG.setParameter(GenericLCG.C, 0L);
        genericLCG.setParameter(GenericLCG.M, 11L);
        genericLCG.init();
        // create generator GeneratorLCG
        final GeneratorInterface generatorLCG = new GeneratorLCG();
        generatorLCG.setParameter(GenericLCG.A, 7L);
        generatorLCG.setParameter(GenericLCG.SEED, 2L);
        generatorLCG.setParameter(GenericLCG.C, 0L);
        generatorLCG.setParameter(GenericLCG.M, 11L);
        generatorLCG.init();
        // check, that generation is same
        for (int i = 0; i < numOfGeneration; i++) {
            assertEquals(generatorLCG.nextLong(), genericLCG.nextLong());
        }
    }

    @Test
    void testNextDouble_sameAsGeneratorLCG() {
        final int numOfGeneration = 1000;
        // create generator GenericLCG
        final GeneratorInterface genericLCG = new GenericLCG();
        genericLCG.setParameter(GenericLCG.A, new VectorLong(new long[]{7}));
        genericLCG.setParameter(GenericLCG.SEED, new VectorLong(new long[]{2}));
        genericLCG.setParameter(GenericLCG.C, 0L);
        genericLCG.setParameter(GenericLCG.M, 11L);
        genericLCG.init();
        // create generator GeneratorLCG
        final GeneratorInterface generatorLCG = new GeneratorLCG();
        generatorLCG.setParameter(GenericLCG.A, 7L);
        generatorLCG.setParameter(GenericLCG.SEED, 2L);
        generatorLCG.setParameter(GenericLCG.C, 0L);
        generatorLCG.setParameter(GenericLCG.M, 11L);
        generatorLCG.init();
        // check, that generation is same
        for (int i = 0; i < numOfGeneration; i++) {
            assertEquals(generatorLCG.nextDouble(), genericLCG.nextDouble());
        }
    }
}
