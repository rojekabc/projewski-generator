/*
 */
package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.NumberInterface;

import java.util.List;

public class FindMax
        extends LaborDataBase {
    private Number maksimum = null;

    @Override
    public void initParameters() {
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public boolean getOutputData(final NumberInterface data) {
        // No more data for send, so give a false code
        if (maksimum == null) {
            return false;
        }
        if (data == null) {
            return false; // TODO: NULL Ecxeption
        }

        try (final NumberWriter writer = data.getNumberWriter()) {
            if (maksimum instanceof Integer) {
                writer.write(maksimum.intValue());
                data.setStoreClass(ClassEnumerator.INTEGER);
            } else if (maksimum instanceof Long) {
                writer.write(maksimum.longValue());
                data.setStoreClass(ClassEnumerator.LONG);
            } else if (maksimum instanceof Float) {
                writer.write(maksimum.floatValue());
                data.setStoreClass(ClassEnumerator.FLOAT);
            } else if (maksimum instanceof Double) {
                writer.write(maksimum.doubleValue());
                data.setStoreClass(ClassEnumerator.DOUBLE);
            } else {
                return false; // TODO: Wrong Output Data
            }
            data.setSize(1);
        }

        return true;
    }

    @Override
    public void setInputData(final NumberInterface data) {
        try (NumberReader reader = data.getNumberReader()) {
            final ClassEnumerator cl = data.getStoreClass();
            if (cl == null) {
                return; // TODO: Throw info about null data
            }

            if (cl == ClassEnumerator.INTEGER) {
                int max = 0;
                int tmp;
                while (reader.hasNext()) {
                    tmp = reader.readInt();
                    if (max < tmp) {
                        max = tmp;
                    }
                }
                maksimum = max;
            } else if (cl == ClassEnumerator.LONG) {
                long max = 0;
                long tmp;
                while (reader.hasNext()) {
                    tmp = reader.readLong();
                    if (max < tmp) {
                        max = tmp;
                    }
                }
                maksimum = max;
            } else if (cl == ClassEnumerator.FLOAT) {
                float max = 0;
                float tmp;
                while (reader.hasNext()) {
                    tmp = reader.readFloat();
                    if (max < tmp) {
                        max = tmp;
                    }
                }
                maksimum = max;
            } else if (cl == ClassEnumerator.DOUBLE) {
                double max = 0;
                double tmp;
                int x = 0;
                while (reader.hasNext()) {
                    tmp = reader.readDouble();
                    if (max < tmp) {
                        max = tmp;
                    }
                    x++;
                }
                maksimum = max;
            } else {
                return; // TODO: Exception Unknown Input Data Type
            }
//			return new NumberStoreOne(outData);
        }

    }

    public Number getMaximum() {
        return maksimum;
    }
}
