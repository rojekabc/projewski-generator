package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.NumberInterface;

import java.util.Arrays;
import java.util.List;

/*
 * Jest to sortowanie, które sortuje tylko obecnie otrzymany pakiet. Nie przechowuje
 * cišgu otrzymywanych pakietów !!!
 */
public class InternalSort extends LaborDataBase {
    public final static String REMOVETHESAME = "Usuń podobne";

    private Object _result;

    public void initParameterInterface() {
        parameters.put(REMOVETHESAME, Boolean.FALSE);
    }


    /**
     * M4_GEN_PI_GET_ALLOWED_CLASSES_I
     */
    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public boolean getOutputData(final NumberInterface data) {
        boolean remts = false;

        if (parameters.get(REMOVETHESAME) != null) {
            remts = (Boolean) parameters.get(REMOVETHESAME);
        }

        final ClassEnumerator c = ClassEnumerator.getType(_result);
        int cnt = 0;
        try (final NumberWriter writer = data.getNumberWriter()) {
            data.setStoreClass(c);

            if (c == ClassEnumerator.INTEGER) {
                final int[] a = (int[]) _result;
                cnt = a.length;
                if (remts) {
                    int tmp = a[0];
                    writer.write(tmp);
                    for (int i = 1; i < a.length; i++) {
                        if (tmp == a[i]) {
                            cnt--;
                            continue;
                        }
                        tmp = a[i];
                        writer.write(tmp);
                    }
                } else {
                    writer.write(a);
                }
            } else if (c == ClassEnumerator.LONG) {
                final long[] a = (long[]) _result;
                cnt = a.length;
                if (remts) {
                    long tmp = a[0];
                    writer.write(tmp);
                    for (int i = 1; i < a.length; i++) {
                        if (tmp == a[i]) {
                            cnt--;
                            continue;
                        }
                        tmp = a[i];
                        writer.write(tmp);
                    }
                } else {
                    writer.write(a);
                }

            } else if (c == ClassEnumerator.FLOAT) {
                final float[] a = (float[]) _result;
                cnt = a.length;
                if (remts) {
                    float tmp = a[0];
                    writer.write(tmp);
                    for (int i = 1; i < a.length; i++) {
                        if (tmp == a[i]) {
                            cnt--;
                            continue;
                        }
                        tmp = a[i];
                        writer.write(tmp);
                    }
                } else {
                    writer.write(a);
                }
            } else if (c == ClassEnumerator.DOUBLE) {
                final double[] a = (double[]) _result;
                cnt = a.length;
                if (remts) {
                    double tmp = a[0];
                    writer.write(tmp);
                    for (int i = 1; i < a.length; i++) {
                        if (tmp == a[i]) {
                            cnt--;
                            continue;
                        }
                        tmp = a[i];
                        writer.write(tmp);
                    }
                } else {
                    writer.write(a);
                }
            }
            data.setSize(cnt);
        }

        return true;
    }

    public void setInputData(final NumberReader is, final ClassEnumerator classEnumerator, final int size) {
        if (classEnumerator == null) {
            return; // TODO: Throw info about null data type
        }

        if (classEnumerator == ClassEnumerator.INTEGER) {
            final int[] out = new int[size];
            if (is.read(out) != size) {
                return; // TODO: Brak danych
            }

            // sortowanie
            Arrays.sort(out);

            _result = out;
        } else if (classEnumerator == ClassEnumerator.LONG) {
            final long[] out = new long[size];
            if (is.read(out) != size) {
                return;
            }

            Arrays.sort(out);

            _result = out;
        } else if (classEnumerator == ClassEnumerator.FLOAT) {
            final float[] out = new float[size];
            if (is.read(out) != size) {
                return;
            }

            Arrays.sort(out);
            _result = out;
        } else if (classEnumerator == ClassEnumerator.DOUBLE) {
            final double[] out = new double[size];
            if (is.read(out) != size) {
                return;
            }
            Arrays.sort(out);
            _result = out;
        } else {
            return; // TODO: Exception Unknown Input Data Type
        }

    }

    @Override
    public void setInputData(final NumberInterface data) {
        try (final NumberReader reader = data.getNumberReader()) {
            setInputData(reader, data.getStoreClass(), data.getSize());
        }
    }

    @Override
    protected void initParameters() {
    }
}
