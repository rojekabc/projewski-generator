package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.NumberInterface;

import java.util.List;

public class ArithmeticMean extends LaborDataBase {
    private double mean;
    private int counter;

    public ArithmeticMean() {
        mean = 0.0;
        counter = 0;
    }

    @Override
    protected void initParameters() {
    }

    public void initParameterInterface() {
    }

    // podawanie danych wejściowych
    @Override
    public void setInputData(final NumberInterface data) {
        try (NumberReader reader = data.getNumberReader()) {
            // Odczytywanie bez wzgledu na rodzaj danych zapisanych
            // jako double
            while (reader.hasNext()) {
                counter++;
                mean += (reader.readDouble() - mean) / counter;
            }
        }
    }

    public double getArithmeticMean() {
        return mean;
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public boolean getOutputData(final NumberInterface gdt) {
        gdt.setStoreClass(ClassEnumerator.DOUBLE);
        gdt.setSize(1);
        try (NumberWriter writer = gdt.getNumberWriter()) {
            writer.write(mean);
        }
        return true;
    }
}
