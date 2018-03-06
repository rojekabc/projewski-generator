package pl.projewski.generator.tools;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.tools.stream.NumberStoreReader;
import pl.projewski.generator.tools.stream.NumberStoreWriter;

public class NumberStoreOne implements NumberInterface {
    Object datastore = null; // przechowywany zawsze jako tablica typu bazowego
    ParameterInterface dataSource = null;

    // przechowywany typ danych
    ClassEnumerator typeOfClass = null;

    public NumberStoreOne(final Object data)
            throws NumberStoreException {
        set(data);
    }

    public NumberStoreOne()
            throws NumberStoreException {
    }

    public NumberStoreOne(final ClassEnumerator storedType) {
        typeOfClass = storedType;
    }

    // wyczyszczenie posiadanych danych
    public int clear() {
        datastore = null;
        return 0;
    }

    // ustawienie danych roznych typów przez wykonanie ! kopii !
    public int set(final Object data)
            throws NumberStoreException {
        setStoreClass(ClassEnumerator.getType(data));
        datastore = data;
        return 0;
    }

    public int set(final int num) throws NumberStoreException {
        return set(Convert.tryToTInt(num));
    }

    public int set(final long num) throws NumberStoreException {
        return set(Convert.tryToTLong(num));
    }

    public int set(final float num) throws NumberStoreException {
        return set(Convert.tryToTFloat(num));
    }

    public int set(final double num) throws NumberStoreException {
        return set(Convert.tryToTDouble(num));
    }

    // pobranie tablicy
    public Object get() throws NumberStoreException {
        return datastore;
    }

    // dodanie danych
    public int add(final Object data) throws NumberStoreException {
        setStoreClass(ClassEnumerator.getType(data)); // Kontrola zgodności typów
        if (typeOfClass == ClassEnumerator.INTEGER) {
            datastore = ArrayUtil.putLast((int[]) datastore, (int[]) data);
        } else if (typeOfClass == ClassEnumerator.LONG) {
            datastore = ArrayUtil.putLast((long[]) datastore, (long[]) data);
        } else if (typeOfClass == ClassEnumerator.DOUBLE) {
            datastore = ArrayUtil.putLast((double[]) datastore, (double[]) data);
        } else if (typeOfClass == ClassEnumerator.FLOAT) {
            datastore = ArrayUtil.putLast((float[]) datastore, (float[]) data);
        }
        return 0;
    }

    // TODO: Przy konwersji przechwytywac wyjatek
    public void add(final int num) throws NumberStoreException {
        setStoreClass(ClassEnumerator.INTEGER);
        datastore = ArrayUtil.putLast((int[]) datastore, num);
    }

    public void add(final long num) throws NumberStoreException {
        setStoreClass(ClassEnumerator.LONG);
        datastore = ArrayUtil.putLast((long[]) datastore, num);
    }

    public void add(final float num) throws NumberStoreException {
        setStoreClass(ClassEnumerator.FLOAT);
        datastore = ArrayUtil.putLast((float[]) datastore, num);
    }

    public void add(final double num) throws NumberStoreException {
        setStoreClass(ClassEnumerator.DOUBLE);
        datastore = ArrayUtil.putLast((double[]) datastore, num);
    }

    // jakie typy klas wejściowych są obecnie dozowolone
    public Class<?>[] allowedTypes() throws NumberStoreException {
        final Class<?>[] tmp;
        if (typeOfClass == null) {
            tmp = new Class<?>[4];
            tmp[0] = int[].class;
            tmp[1] = long[].class;
            tmp[2] = float[].class;
            tmp[3] = double[].class;
        } else {
            tmp = new Class[1];
            tmp[0] = ClassEnumerator.getAsClass(typeOfClass);
        }
        return tmp;
    }

    // ustawianie na podstawie innych magazynów
    // sprawdzana jest obecność w magazynie tego samego typu
    public int set(final NumberStoreOne numStore)
            throws NumberStoreException {
        final ClassEnumerator c = numStore.getStoreClass();
        // Czy kopiowany magazyn jest pusty ? Jeżeli tak to ten także opróżnij
        // i ustaw go na beztypie
        if (c == null) {
            clear();
            return 0;
        }
        if (typeOfClass != null && typeOfClass != c) {
            throw new NumberStoreException(
                    NumberStoreException.INCORRECT_TYPE_ERROR);
        }
        set(numStore.get());
        return 0;
    }

    public int add(final NumberStoreOne numStore)
            throws NumberStoreException {
        final ClassEnumerator c = numStore.getStoreClass();
        // Czy kopiowany magazyn jest pusty ? Jeżeli tak to koniec
        if (c == null) {
            return 0;
        }
        if (typeOfClass != null && c != typeOfClass) {
            throw new NumberStoreException(
                    NumberStoreException.INCORRECT_TYPE_ERROR);
        }
        add(numStore.get());
        return 0;
    }

    public int[] getTInt()
            throws NumberStoreException {
        if (typeOfClass == ClassEnumerator.INTEGER) {
            return (int[]) datastore;
        }
        return new int[0];
    }

    public long[] getTLong()
            throws NumberStoreException {
        if (typeOfClass == ClassEnumerator.LONG) {
            return (long[]) datastore;
        }
        return new long[0];
    }

    public float[] getTFloat()
            throws NumberStoreException {
        if (typeOfClass == ClassEnumerator.FLOAT) {
            return (float[]) datastore;
        }
        return new float[0];
    }

    public double[] getTDouble()
            throws NumberStoreException {
        if (typeOfClass == ClassEnumerator.DOUBLE) {
            return (double[]) datastore;
        }
        return new double[0];
    }

    // pobranie jako interpretacji String
    public String getAsString(final int position)
            throws NumberStoreException {
        if (typeOfClass == null) {
            return "";
        }
        if (typeOfClass == ClassEnumerator.INTEGER) {
            return Integer.toString(((int[]) datastore)[position]);
        } else if (typeOfClass == ClassEnumerator.LONG) {
            return Long.toString(((long[]) datastore)[position]);
        } else if (typeOfClass == ClassEnumerator.FLOAT) {
            return Float.toString(((float[]) datastore)[position]);
        } else if (typeOfClass == ClassEnumerator.DOUBLE) {
            return Double.toString(((double[]) datastore)[position]);
        }
        return "";
    }

    @Override
    public NumberReader getNumberReader() throws NumberStoreException {
        return new NumberStoreReader(this);
    }

    @Override
    public NumberWriter getNumberWriter() throws NumberStoreException {
//		if ( typeOfClass == null )
//			throw new NumberStoreException(NumberStoreException.INCORRECT_TYPE_ERROR);
        return new NumberStoreWriter(this);
    }

    @Override
    public int getSize() throws NumberStoreException {
        if (typeOfClass == null) {
            return 0;
        }
        if (typeOfClass == ClassEnumerator.INTEGER) {
            return ((int[]) datastore).length;
        }
        if (typeOfClass == ClassEnumerator.LONG) {
            return ((long[]) datastore).length;
        }
        if (typeOfClass == ClassEnumerator.FLOAT) {
            return ((float[]) datastore).length;
        }
        if (typeOfClass == ClassEnumerator.DOUBLE) {
            return ((double[]) datastore).length;
        }
        throw new NumberStoreException(NumberStoreException.INCORRECT_TYPE_ERROR);
    }

    @Override
    public void setSize(final int size) {

    }

    @Override
    public ClassEnumerator getStoreClass() {
        return typeOfClass;
    }

    @Override
    public void setStoreClass(final ClassEnumerator c) throws NumberStoreException {
        final ClassEnumerator toSet = c;

        if (typeOfClass == null) {
            typeOfClass = toSet;
        } else if (toSet != typeOfClass) {
            throw new NumberStoreException(
                    NumberStoreException.INCORRECT_TYPE_ERROR);
        }
    }

    @Override
    public ParameterInterface getDataSource() throws NumberStoreException {
        return this.dataSource;
    }

    @Override
    public void setDataSource(final ParameterInterface dataSource)
            throws NumberStoreException {
        this.dataSource = dataSource;

    }

}
