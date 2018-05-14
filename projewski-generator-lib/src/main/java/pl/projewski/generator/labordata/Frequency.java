/*
 * Obilczanie cz�stotliwo�ci wyst�pie� danych
 * Nie implementuje funkcji przechowywania danych.
 * Dane s� przeliczane bezpo�rednio.
 */
//TODO: Obliczenia dla zmiennych przecinkowych po ustaleniu parametru
//	dok�adno�ci, lub liczby podzia�u przedzia�u pomiaru na mniejsze cz�ci
//TODO: Je�eli zakres jest zbyt du�y mo�e oferowa� zapami�tywanie
// niekt�rych tylko pozycji lub zg�osi� wyj�tek, �e nie jest w stanie
// zapami�ta�
package pl.projewski.generator.labordata;

// 1. Ustalenie minimum i maksimum danych oraz ich liczebno�ci
// 1a. Przechowywanie podawanych danych w pliku tymczasowym
// 1b. Je�eli minimum i maksimum zadane przez u�ytkownika to nie poszukuj
// --2. Ustalenie rozst�pu cechy
// --2a. Posortowa� dane
// --2b. Znale�� rozst�p miedzy warto�ciami
// --2c. Znale�� NWD rozst�p�w mi�dzy warto�ciami
// --2d. Jest on r�wnoznaczny z rozst�pem cechy
// 3. Utworzenie klas
// 4. zliczenie warto�ci w klasach
// Rozst�p dla ca�kowitych b�dzie zawsze 1, fla float ..., double ....
// Nie powinno byc wi�cej klas ni� 30

import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.Convert;

import java.util.Collections;
import java.util.List;

public class Frequency
        extends LaborDataBase {
    public static final String MINIMUM = "Minimum";
    public static final String MAXIMUM = "Maksimum";
    public static final String CLASSAMMOUNT = "Liczebność";

    public static final int MAXIMUMCLASSAMMOUNT = 100;
    public static final int INTQUALITY = 1;
    public static final long LONGQUALITY = 1;
    public static final float FLOATQUALITY = 0.0001f;
    public static final double DOUBLEQUALITY = 0.000001;

    private int[] frequency;

    @Override
    public void initParameters() {
        parameters.put(MINIMUM, null);
        parameters.put(MAXIMUM, null);
        parameters.put(CLASSAMMOUNT, null);
    }

    protected int[] countForInt(final NumberReader numberReader) {
        final int min;
        final int max;
        int clnum;
        final double[] classLevel; // poziom danej klasy

        try {
            min = Convert.tryToInt(parameters.get(MINIMUM));
            max = Convert.tryToInt(parameters.get(MAXIMUM));

            /* Wyznaczenie liczby grup podstawowe */
            clnum = Convert.tryToInt(parameters.get(CLASSAMMOUNT));

            /* Ograniczenie liczby grup */
            if (clnum > MAXIMUMCLASSAMMOUNT) {
                clnum = MAXIMUMCLASSAMMOUNT;
            }

            // Ustalenie prog�w dla ka�dego poziomu
            int j;
            classLevel = new double[clnum];
            for (j = 0; j < clnum; j++) {
                classLevel[j] = ((double) min) + (1.0d / clnum) * (j + 1) * (max - min);
            }

            /* Zlicznie */
            frequency = new int[clnum];
            while (numberReader.hasNext()) {
                final int tmp = numberReader.readInt();

                // Najpierw wyjdz poza minimum, jesli taka konieczno��
                if (tmp < min) {
                    continue;
                }
                // Teraz na maksimum
                if (tmp > max) {
                    continue;
                }
                j = 0;
                // Teraz kt�ry przedzia�
                while (tmp > classLevel[j]) {
                    j++;
                }
                //				while ( ((double)(tmp-min))/((double)(max-min)) > jmp*(j+1) )
                //					j++;
                frequency[j]++;
            }
        } catch (final Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return frequency;
    }

    protected int[] countForLong(final NumberReader numberReader) {
        final long min;
        final long max;
        int clnum;
        final double[] classLevel; // poziom danej klasy

        try {
            min = Convert.tryToLong(parameters.get(MINIMUM));
            max = Convert.tryToLong(parameters.get(MAXIMUM));

            /* Wyznaczenie liczby grup podstawowe */
            clnum = Convert.tryToInt(parameters.get(CLASSAMMOUNT));

            /* Ograniczenie liczby grup */
            if (clnum > MAXIMUMCLASSAMMOUNT) {
                clnum = MAXIMUMCLASSAMMOUNT;
            }

            int j;
            classLevel = new double[clnum];
            for (j = 0; j < clnum; j++) {
                classLevel[j] = ((double) min) + (1.0d / clnum) * (j + 1) * (max - min);
            }

            /* Zlicznie */
            frequency = new int[clnum];

            while (numberReader.hasNext()) {
                final long tmp = numberReader.readLong();

                // Najpierw wyjdz poza minimum, jesli taka konieczno��
                if (tmp < min) {
                    continue;
                }
                // Teraz na maksimum
                if (tmp > max) {
                    continue;
                }
                // Teraz kt�ry przedzia�
                j = 0;
                while (tmp > classLevel[j]) {
                    j++;
                }
                frequency[j]++;
            }
        } catch (final Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return frequency;
    }

    protected int[] countForFloat(final NumberReader numberReader) {
        final float min;
        final float max;
        int clnum;
        final double[] classLevel; // poziom danej klasy

        try {
            min = Convert.tryToFloat(parameters.get(MINIMUM));
            max = Convert.tryToFloat(parameters.get(MAXIMUM));

            /* Wyznaczenie liczby grup podstawowe */
            clnum = Convert.tryToInt(parameters.get(CLASSAMMOUNT));

            /* Ograniczenie liczby grup */
            if (clnum > MAXIMUMCLASSAMMOUNT) {
                clnum = MAXIMUMCLASSAMMOUNT;
            }

            int j;
            classLevel = new double[clnum];
            for (j = 0; j < clnum; j++) {
                classLevel[j] = ((double) min) + (1.0d / clnum) * (j + 1) * (max - min);
            }

            /* Zlicznie */
            frequency = new int[clnum];
            while (numberReader.hasNext()) {
                final float tmp = numberReader.readFloat();

                j = 0;
                // Najpierw wyjdz poza minimum, jesli taka konieczno��
                if (tmp < min) {
                    continue;
                }
                // Teraz na maksimum
                if (tmp > max) {
                    continue;
                }
                // Teraz kt�ry przedzia�
                while (tmp > classLevel[j]) {
                    j++;
                }
                frequency[j]++;
            }
        } catch (final Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return frequency;
    }

    protected int[] countForDouble(final NumberReader numberReader) {
        final double min;
        final double max;
        int clnum;
        final double[] classLevel; // poziom danej klasy

        try {
            min = Convert.tryToDouble(parameters.get(MINIMUM));
            max = Convert.tryToDouble(parameters.get(MAXIMUM));

            /* Wyznaczenie liczby grup podstawowe */
            clnum = Convert.tryToInt(parameters.get(CLASSAMMOUNT));

            /* Ograniczenie liczby grup */
            if (clnum > MAXIMUMCLASSAMMOUNT) {
                clnum = MAXIMUMCLASSAMMOUNT;
            }

            int j;
            classLevel = new double[clnum];
            for (j = 0; j < clnum; j++) {
                classLevel[j] = ((double) min) + (1.0d / clnum) * (j + 1) * (max - min);
            }

            /* Zlicznie */
            frequency = new int[clnum];
            while (numberReader.hasNext()) {
                final double tmp = numberReader.readDouble();

                j = 0;
                // Najpierw wyjdz poza minimum, jesli taka konieczno��
                if (tmp < min) {
                    continue;
                }
                if (tmp > max) {
                    continue;
                }
                // Teraz kt�ry przedzia�
                while (tmp > classLevel[j]) {
                    j++;
                }
                frequency[j]++;
            }
        } catch (final Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return frequency;
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(CLASSAMMOUNT)) {
            return Collections.singletonList(Integer.class);
        } else {
            return Collections.singletonList(Double.class);
        }
    }

    @Override
    public boolean getOutputData(final NumberInterface data) {
        if (frequency == null) {
            return false;
        }
        data.setStoreClass(ClassEnumerator.INTEGER);
        data.setSize(frequency.length);

        try (final NumberWriter writer = data.getNumberWriter()) {
            for (final int aFrequency : frequency) {
                writer.write(aFrequency);
            }
            return true;
        }
    }

    @Override
    public void setInputData(final NumberInterface data) {
        // wyznaczenie minimum
        if (parameters.get(MINIMUM) == null) {
            final FindMin min = new FindMin();
            min.setInputData(data);
            parameters.put(MINIMUM, min.getMinimum());
        }

        // wyznaczenie maksimum
        if (parameters.get(MAXIMUM) == null) {
            final FindMax max = new FindMax();
            max.setInputData(data);
            parameters.put(MAXIMUM, max.getMaximum());
        }

        // ustalenie classammount
        parameters.putIfAbsent(CLASSAMMOUNT, 20);

        // posortowanie otrzymanych danych - to jesli chcemy inaczej policzyc czestotliwosc
        // labordata = new ExternalSort();
        // labordata.setInputData( data );

        try (final NumberReader reader = data.getNumberReader()) {

            // obliczenie liczebnosci wystepowania
            final ClassEnumerator c = data.getStoreClass();

            if (c == ClassEnumerator.INTEGER) {
                frequency = countForInt(reader);
            } else if (c == ClassEnumerator.LONG) {
                frequency = countForLong(reader);
            } else if (c == ClassEnumerator.FLOAT) {
                frequency = countForFloat(reader);
            } else if (c == ClassEnumerator.DOUBLE) {
                frequency = countForDouble(reader);
            }
        }

    }

    public int[] getFrequency() {
        return frequency;
    }
}
