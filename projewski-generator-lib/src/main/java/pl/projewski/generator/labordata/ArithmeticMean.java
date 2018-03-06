package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.LaborDataException;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.Mysys;

import java.util.List;

public class ArithmeticMean extends LaborDataBase {
    double _mean;
    int _cnt;

    public ArithmeticMean() {
        _mean = 0.0;
        _cnt = 0;
    }

    @Override
    protected void initParameters() {
    }

    public void initParameterInterface() {
    }

    // podawanie danych wejściowych
    @Override
    public void setInputData(final NumberInterface data)
            throws LaborDataException {
        NumberReader is = null;
        try {
            is = data.getNumberReader();

            // Odczytywanie bez wzgledu na rodzaj danych zapisanych
            // jako double
            while (is.hasNext()) {
                _cnt++;
                _mean += (is.readDouble() - _mean) / _cnt;
            }
        } catch (final NumberStoreException e) {
            throw new LaborDataException(e);
        } finally {
            Mysys.closeQuiet(is);
        }
    }

    public double getArithmeticMean() {
        return _mean;
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public boolean getOutputData(final NumberInterface gdt) throws LaborDataException {
        NumberWriter writer = null;
        try {
            gdt.setStoreClass(ClassEnumerator.DOUBLE);
            gdt.setSize(1);
            writer = gdt.getNumberWriter();
            writer.write(_mean);
        } catch (final Exception e) {
            throw new LaborDataException(e);
        } finally {
            Mysys.closeQuiet(writer);
        }
        return true;
    }
}
