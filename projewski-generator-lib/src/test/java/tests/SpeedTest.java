/**
 *
 */
package tests;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.generator.GeneratorLCGAnd;
import pl.projewski.generator.interfaces.GeneratorInterface;

/**
 * @author projewski
 */
public class SpeedTest {

    // ile danych wylosować w jednej próbie
    static int PROBKA = 1000000;
    // ile prób przeprowadzić
    static int LICZBA = 10;

    // Tutaj stworzyć instancję generatora, który ma podlegać testowaniu
    public static GeneratorInterface getGenerator() {
        final GeneratorInterface gi = new GeneratorLCGAnd();
		
		/*
		GeneratorInterface gi = new GenericLCG();
		try {
			gi.setParameter(GenericLCG.A, new VectorLong("1 1 1 1 1 1"));
			gi.setParameter(GenericLCG.SEED, new VectorLong("1 2 3 4 5 6"));
			gi.setParameter(GenericLCG.C, new Long(3));
			gi.setParameter(GenericLCG.M, new Long(7));
		} catch (ParameterException e) {
			e.printStackTrace();
		}
		*/
        return gi;
    }

    public static void getIntTest(final GeneratorInterface gi) {
        long startTime, endTime;
        int exCnt;

        try {
            gi.init();
        } catch (final GeneratorException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < LICZBA; i++) {

            try {
                gi.reinit();
            } catch (final GeneratorException e) {
                e.printStackTrace();
            }

            exCnt = 0;
            startTime = System.currentTimeMillis();

            for (int j = 0; j < PROBKA; j++) {
                try {
                    gi.nextInt();
                } catch (final GeneratorException e) {
                    exCnt++;
                }
            }

            endTime = System.currentTimeMillis();
            System.out.println(
                    "nextInt, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms");
        }
    }

    public static void getLongTest(final GeneratorInterface gi) {
        long startTime, endTime;
        int exCnt;

        try {
            gi.init();
        } catch (final GeneratorException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < LICZBA; i++) {

            try {
                gi.reinit();
            } catch (final GeneratorException e) {
                e.printStackTrace();
            }

            exCnt = 0;
            startTime = System.currentTimeMillis();

            for (int j = 0; j < PROBKA; j++) {
                try {
                    gi.nextLong();
                } catch (final GeneratorException e) {
                    exCnt++;
                }
            }

            endTime = System.currentTimeMillis();
            System.out.println(
                    "nextLong, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms");
        }
    }

    public static void getFloatTest(final GeneratorInterface gi) {
        long startTime, endTime;
        int exCnt;

        try {
            gi.init();
        } catch (final GeneratorException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < LICZBA; i++) {

            try {
                gi.reinit();
            } catch (final GeneratorException e) {
                e.printStackTrace();
            }

            exCnt = 0;
            startTime = System.currentTimeMillis();

            for (int j = 0; j < PROBKA; j++) {
                try {
                    gi.nextFloat();
                } catch (final GeneratorException e) {
                    exCnt++;
                }
            }

            endTime = System.currentTimeMillis();
            System.out.println(
                    "nextFloat, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms");
        }
    }

    public static void getDoubleTest(final GeneratorInterface gi) {
        long startTime, endTime;
        int exCnt;

        try {
            gi.init();
        } catch (final GeneratorException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < LICZBA; i++) {

            try {
                gi.reinit();
            } catch (final GeneratorException e) {
                e.printStackTrace();
            }

            exCnt = 0;
            startTime = System.currentTimeMillis();

            for (int j = 0; j < PROBKA; j++) {
                try {
                    gi.nextDouble();
                } catch (final GeneratorException e) {
                    exCnt++;
                }
            }

            endTime = System.currentTimeMillis();
            System.out.println(
                    "nextDouble, test: " + i + " Liczba wyjatkow: " + exCnt + " Czas: " + (endTime - startTime) + "ms");
        }
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final GeneratorInterface gi = getGenerator();
        getIntTest(gi);
        getLongTest(gi);
        getFloatTest(gi);
        getDoubleTest(gi);
        System.exit(0);
    }

}
