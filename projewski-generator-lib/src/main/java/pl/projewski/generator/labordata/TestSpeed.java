package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.WrongParameterGeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.NumberInterface;

import java.util.Collections;
import java.util.List;

/**
 * @author projewski
 * <p>
 * Testowanie szybkoci generowania danych.
 * Dane wyjciowe podawane sš w milisekundach.
 * Testy wykonywane sš kolejno dla typów int, long, float, double poprzez pobieranie
 * pojedyncze (nie wykonywane jest rawFill)
 */
public class TestSpeed extends LaborDataBase {

    public static final String GENERATOR = "Generator";
    public static final String NUMBEROFTESTS = "Liczba testów";
    public static final String NUMBEROFGENERATION = "Liczba generacji";
    public static final String TESTINTEGER = "Testuj nextInt";
    public static final String TESTLONG = "Testuj nextLong";
    public static final String TESTFLOAT = "Testuj nextFloat";
    public static final String TESTDOUBLE = "Testuj nextDouble";

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.LaborDataInterface#getOutputData(pk.ie.proj.interfaces.NumberInterface)
     */
    @Override
    public boolean getOutputData(final NumberInterface numberinterface) {
        // sprawdzenie i ustalenie parametrów poczštkowych
        Object o = parameters.get(GENERATOR);
        if (o == null) {
            throw new WrongParameterGeneratorException(GENERATOR);
        }
        final GeneratorInterface gi = (GeneratorInterface) o;

        o = parameters.get(NUMBEROFGENERATION);
        if (o == null) {
            throw new WrongParameterGeneratorException(NUMBEROFGENERATION);
        }
        final long numGen = (Long) o;

        o = parameters.get(NUMBEROFTESTS);
        if (o == null) {
            throw new WrongParameterGeneratorException(NUMBEROFTESTS);
        }
        final int numTest = (Integer) o;

        // licznik zapisywanych danych
        int dataSize = 0;

        // uzyskanie strumienia wyjciowego
        try (final NumberWriter writer = numberinterface.getNumberWriter()) {
            // nadanie typu tworzonych danych
            numberinterface.setStoreClass(ClassEnumerator.LONG);

            // inicjowanie generatora
            gi.init();
            long timeCounter;

            // testy dla int
            if ((Boolean) parameters.get(TESTINTEGER)) {
                for (int j = 0; j < numTest; j++) {
                    gi.reinit();
                    long i = numGen;
                    timeCounter = System.currentTimeMillis();
                    try {
                        while (i-- > 0) {
                            gi.nextInt();
                        }
                        timeCounter = System.currentTimeMillis() - timeCounter;
                    } catch (final GeneratorException e) {
                        timeCounter = -1;
                    }
                    dataSize++;
                    writer.write(timeCounter);
                }
            }

            // testy dla long
            if ((Boolean) parameters.get(TESTLONG)) {
                for (int j = 0; j < numTest; j++) {
                    gi.reinit();
                    long i = numGen;
                    timeCounter = System.currentTimeMillis();
                    try {
                        while (i-- > 0) {
                            gi.nextLong();
                        }
                        timeCounter = System.currentTimeMillis() - timeCounter;
                    } catch (final GeneratorException e) {
                        timeCounter = -1;
                    }
                    dataSize++;
                    writer.write(timeCounter);
                }
            }

            // testy dla float
            if ((Boolean) parameters.get(TESTFLOAT)) {
                for (int j = 0; j < numTest; j++) {
                    gi.reinit();
                    long i = numGen;
                    timeCounter = System.currentTimeMillis();
                    try {
                        while (i-- > 0) {
                            gi.nextFloat();
                        }
                        timeCounter = System.currentTimeMillis() - timeCounter;
                    } catch (final GeneratorException e) {
                        timeCounter = -1;
                    }
                    dataSize++;
                    writer.write(timeCounter);
                }
            }

            // testy dla double
            if ((Boolean) parameters.get(TESTDOUBLE)) {
                for (int j = 0; j < numTest; j++) {
                    gi.reinit();
                    long i = numGen;
                    timeCounter = System.currentTimeMillis();
                    try {
                        while (i-- > 0) {
                            gi.nextDouble();
                        }
                        timeCounter = System.currentTimeMillis() - timeCounter;
                    } catch (final GeneratorException e) {
                        timeCounter = -1;
                    }
                    dataSize++;
                    writer.write(timeCounter);
                }
            }

            numberinterface.setSize(dataSize);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.LaborDataInterface#setInputData(pk.ie.proj.interfaces.NumberInterface)
     */
    @Override
    public void setInputData(final NumberInterface numberinterface) {

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.abstracts.ParameterAbstract#initParameters()
     */
    @Override
    public void initParameters() {
        parameters.put(GENERATOR, null);
        parameters.put(NUMBEROFTESTS, 10);
        parameters.put(NUMBEROFGENERATION, 1000000L);
        parameters.put(TESTINTEGER, Boolean.TRUE);
        parameters.put(TESTLONG, Boolean.TRUE);
        parameters.put(TESTFLOAT, Boolean.TRUE);
        parameters.put(TESTDOUBLE, Boolean.TRUE);
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
     */
    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        switch (param) {
        case GENERATOR:
            return Collections.singletonList(GeneratorInterface.class);
        case NUMBEROFGENERATION:
            return Collections.singletonList(Long.class);
        case NUMBEROFTESTS:
            return Collections.singletonList(Integer.class);
        case TESTINTEGER:
        case TESTLONG:
        case TESTFLOAT:
        case TESTDOUBLE:
            return Collections.singletonList(Boolean.class);
        default:
            return ListUtils.EMPTY_LIST;
        }
    }

}
