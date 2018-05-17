package pl.projewski.generator.labordata;

import org.junit.jupiter.api.Test;
import pl.projewski.generator.tools.NumberStoreOne;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChiSquareTest {

    // Based on Knuth 3.3.1.A
    @Test
    void testGetChiSquare_variant_1() {
        final NumberStoreOne realValues = new NumberStoreOne();
        final NumberStoreOne statValues = new NumberStoreOne();
        realValues.set(new int[]{2, 4, 10, 12, 22, 29, 21, 15, 14, 9, 6});
        statValues.set(new int[]{4, 8, 12, 16, 20, 24, 20, 16, 12, 8, 4});
        final double expected = 7.0 + 7.0 / 48.0;
        final ChiSquare chiSquare = new ChiSquare();
        chiSquare.setParameter(ChiSquare.DISTRIBUTION, statValues);
        chiSquare.setInputData(realValues);
        assertEquals(expected, chiSquare.getChiSquare());
    }

    @Test
    void testGetChiSquare_variant_2() {
        final NumberStoreOne realValues = new NumberStoreOne();
        final NumberStoreOne statValues = new NumberStoreOne();
        realValues.set(new int[]{4, 10, 10, 13, 20, 18, 18, 11, 13, 14, 13});
        statValues.set(new int[]{4, 8, 12, 16, 20, 24, 20, 16, 12, 8, 4});
        final double expected = 29.0 + 59.0 / 120.0;
        final ChiSquare chiSquare = new ChiSquare();
        chiSquare.setParameter(ChiSquare.DISTRIBUTION, statValues);
        chiSquare.setInputData(realValues);
        assertEquals(expected, chiSquare.getChiSquare());
    }

    @Test
    void testGetChiSquare_variant_3() {
        final NumberStoreOne realValues = new NumberStoreOne();
        final NumberStoreOne statValues = new NumberStoreOne();
        realValues.set(new int[]{3, 7, 11, 15, 19, 24, 21, 17, 13, 9, 5});
        statValues.set(new int[]{4, 8, 12, 16, 20, 24, 20, 16, 12, 8, 4});
        final double expected = 1.0 + 17.0 / 120.0;
        final ChiSquare chiSquare = new ChiSquare();
        chiSquare.setParameter(ChiSquare.DISTRIBUTION, statValues);
        chiSquare.setInputData(realValues);
        assertEquals(expected, chiSquare.getChiSquare());
    }
}
