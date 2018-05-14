package tests;

import pl.projewski.generator.generator.GenericLCG;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.ArrayUtil;
import pl.projewski.generator.tools.VectorLong;

/**
 * @author projewski
 */
public class GenericLCGTest {

    public static void main(final String[] args) {
        final GenericLCGTest test = new GenericLCGTest();
        try {
            test.testEasyGenericLCG();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    // podstawowy generator, na którym mogą sprawdzić poprawność obliczeń
    private void testEasyGenericLCG() {
        final long[] initA = {1, 1, 1, 1, 1, 1};
        final long[] counted = {1, 2, 3, 4, 5, 6};
        final long c = 3;
        final long m = 7;
        final int numOfGeneration = 1000;
        final boolean printGenerated = false;

        int i, j, n;

        final GeneratorInterface gi = new GenericLCG();
        gi.setParameter(GenericLCG.A, new VectorLong(initA));
        gi.setParameter(GenericLCG.SEED, new VectorLong(counted));
        gi.setParameter(GenericLCG.C, c);
        gi.setParameter(GenericLCG.M, m);
        gi.init();

        System.out.println("Liczba kontrolnych generacji: " + numOfGeneration);
        System.out.println(gi.toString());
        n = 40;
        for (j = 0; j < numOfGeneration; j++) {
            final long generated = gi.nextLong();
            long x = c;
            for (i = 0; i < counted.length; i++) {
                x += counted[counted.length - i - 1] * initA[i];
            }
            x %= m;
            ArrayUtil.constPutLast(counted, x);
            if (printGenerated) {
                System.out.print(generated + " ");
                n--;
                if (n == 0) {
                    n = 40;
                    System.out.println();
                }
            }
            if (generated != x) {
                System.out.println("Wygenerowano: " + generated + ", oczekiwano: " + x);
                System.out.println("Pozycja b��dnej pr�bki: " + j);
                System.out.println("Typ generacji: nextLong");
                System.exit(0);
            }
        }
        System.out.println("Funkcja nextLong: Ok");

        gi.reinit();
        n = 5;
        for (j = 0; j < numOfGeneration; j++) {
            final double generated = gi.nextDouble();
            long x = c;
            for (i = 0; i < counted.length; i++) {
                x += counted[i] * initA[i];
            }
            x %= m;
            ArrayUtil.constPutLast(counted, x);
            if (printGenerated) {
                System.out.print(generated + " ");
                n--;
                if (n == 0) {
                    n = 5;
                    System.out.println();
                }
            }
            if (Double.compare(generated, (double) x / (double) m) != 0) {
                System.out.println("Wygenerowano: " + generated + ", oczekiwano: " + x);
                System.out.println("Pozycja b��dnej pr�bki: " + j);
                System.out.println("Typ generacji: nextDouble");
                System.exit(0);
            }
        }
        System.out.println("Funkcja nextDouble: Ok");

    }

}
