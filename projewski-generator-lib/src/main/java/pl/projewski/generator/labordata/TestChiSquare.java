package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.distribution.Uniform;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.LaborDataException;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.NumberStoreOne;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 * Klasa wykonuje test chi-square na wybranym generatorze. Przed ka�dym
 * rozpocz�ciem testu generator jest inicjowany od nowa i tym sposobem
 * wykonywane jest pobranie kolejnej pr�by testowej. Dane wej�ciowe nie s�
 * uwzdl�dniane.
 */

public class TestChiSquare
        extends LaborDataBase {
    public static final String DISTRIBUTION = "rozkład oczekiwany";
    public static final String TESTAMMOUNT = "liczba testów";
    public static final String GENERATOR = "generator";
    public static final String GENERATIONAMMOUNT = "liczba generacji";
    public static final String V = "liczba stopni swobody";
    ChiSquare _chisquare = null;

    @Override
    public void initParameters() {
        parameters.put(DISTRIBUTION, new Uniform());
        parameters.put(TESTAMMOUNT, Integer.valueOf(15));
        parameters.put(GENERATOR, null);
        parameters.put(GENERATIONAMMOUNT, null);
        parameters.put(V, Integer.valueOf(20));
    }

    public void setInputData(final NumberStoreOne data, final boolean isLast)
            throws LaborDataException {
    }

    @Override
    public boolean getOutputData(final NumberInterface data) throws LaborDataException {
        NumberWriter resWriter = null;
        try {
            final Object generator = parameters.get(GENERATOR);
            final Object ammountGeneration = parameters.get(GENERATIONAMMOUNT);
            final Object distribution = parameters.get(DISTRIBUTION);
            final Object paramV = parameters.get(V);

            if (generator == null) {
                throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, GENERATOR);
            }
            if (ammountGeneration == null) {
                throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, GENERATIONAMMOUNT);
            }
            if (distribution == null) {
                throw new LaborDataException(LaborDataException.WRONG_VALUES_ERROR, DISTRIBUTION);
            }

            final ChiSquare _chisquare = new ChiSquare();

            // Wyznacz liczbe testow do przeprowadzenia
            int n = Convert.tryToInt(parameters.get(TESTAMMOUNT));

            // inicjuj proces obliczania chisquare
            _chisquare.setParameter(ChiSquare.DISTRIBUTION, distribution);

//			double [] result = new double [n];
            final GeneratorInterface gi = (GeneratorInterface) generator;
            final Frequency freq = new Frequency();
            freq.setParameter(Frequency.CLASSAMMOUNT,
                    Integer.valueOf(Convert.tryToInt(paramV) + 1));
//			NumberStoreOne freqStore = new NumberStoreOne();
//			NumberStoreOne chisquareout = new NumberStoreOne();
            resWriter = data.getNumberWriter();
            data.setStoreClass(ClassEnumerator.DOUBLE);
            data.setSize(n);

            // Inicjowanie generatora przed rozpocz�ciem test�w
            gi.init();

            // wykonywanie poszczegolnych testow
            while (n-- > 0) {
                Mysys.debugln("Test: " + n);
                final int genn = Convert.tryToInt(ammountGeneration);
                final GeneratedData gdtFreqIn = GeneratedData.createTemporary();
                final NumberWriter writer = gdtFreqIn.getNumberWriter();
                // generowanie danych
                Mysys.debugln("Generowanie danych");
                gdtFreqIn.setSize(genn);
                gdtFreqIn.setStoreClass(ClassEnumerator.DOUBLE);
                gi.rawFill(writer, ClassEnumerator.DOUBLE, genn);
                writer.close();

                // zliczanie czestotliowsci
                Mysys.debugln("Zliczanie czestotliowci");
                freq.setInputData(gdtFreqIn);
                final GeneratedData gdtFreqOut = GeneratedData.createTemporary();
                freq.getOutputData(gdtFreqOut);

                // wyznaczanie wyniku chisquare
                Mysys.debugln("Wyznaczanie chisquare rozmiar = " + gdtFreqOut.getSize());
                _chisquare.setInputData(gdtFreqOut);
                resWriter.write(_chisquare.getChiSquare());

                // usuwanie tymczasowych danych
                if (!gdtFreqIn.delete()) {
                    Mysys.error("Nieudane usuwanie tymczasowych danych");
                }
                if (!gdtFreqOut.delete()) {
                    Mysys.error("Nieudane usuwanie tymczasowych danych");
                }
                // kolejny test
            }
            // sprzatanie
            return true;
        } catch (final ParameterException e) {
            throw new LaborDataException(e);
        } catch (final NumberStoreException e) {
            throw new LaborDataException(e);
        } catch (final IOException e) {
            throw new LaborDataException(e);
        } catch (final GeneratorException e) {
            throw new LaborDataException(e);
        } finally {
            Mysys.closeQuiet(resWriter);
        }
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(GENERATOR)) {
            return Arrays.asList(GeneratorInterface.class);
        } else if (param.equals(GENERATIONAMMOUNT) || param.equals(TESTAMMOUNT) || param.equals(V)) {
            return Arrays.asList(Integer.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public void setInputData(final NumberInterface data) throws LaborDataException {
        // TODO Auto-generated method stub

    }

}
