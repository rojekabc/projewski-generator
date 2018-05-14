/*
 * Sortowanie przeznaczone do porzšdkowania zbiorów liczb o dużej liczebności.
 * Ma na celu zachowanie zasobów pamięciowych w jak największym stopniu.
 * W zamian wykorzystuje przestrzeń dyskować.
 *
 * Algorytm, który tutaj implementuję polega na sortowaniu z zasadš Merge.
 * W pierwszym kroku jest rezerwowany odpowiedni bufor w pamięci.
 * Następnie do bufora tego sš dokładane przypływajace dane.
 * Do zadecydowania: Dane w buforze sš sortowane wraz z ich napływaniem lub
 * po zapełnieniu bufora. Krokiem wyjściowym tego etapu ma być uzyskanie
 * posortowanego bufora. Kolejny krok następuje po otrzymaniu jednego z dwóch
 * sygnałów. Jets to zapełenienie bufora lub otrzymanie sygnału końca danych
 * w strumieniu.
 * Wtedy posortowany(!) bufor jest zapisywany do pliku o unikatowej nazwie.
 * Nazwa pliku jest przechowywana.
 * Jeżeli nie otrzymano końca otrzymywania danych, bufor jest czyszczony
 * i ponownie ładuje się do niego dane.
 * Jeśli jest już koniec następuje wejście w fazę łączenia plików, merge.
 * Z dostępnych plików wynikowych odczytuje się jedną daną (lub więcej danych)
 * i te sortuje się, zapamiętując z jakiego pliku pochodzą. Bierze się
 * najmniejszš i przesyła do pliku wynikowego, w którym zostana zamieszczone
 * posortowane wszystkie dane. Teraz z pliku, który był źródłem
 * danej o najmniejszej wartości odczytuje się kolejną wartość i dodaje do
 * wartości z pozostałych plików, ale tak, aby była dodana w organizacji
 * posortowanej. Proces powtarza się, aż do otrzymania końców wszystkich
 * plików.
 */
package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.exceptions.WriteFileGeneratorException;
import pl.projewski.generator.tools.stream.SeparatorStreamReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

// TODO: Remove duplicated code, if possible
public class ExternalSort extends LaborDataBase {
    public final static String REMOVETHESAME = "Usuń podobne";
    private final java.util.Vector<File> vecFiles = new java.util.Vector<>();
    private ClassEnumerator storeCl = null;

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
        try {
            // Jesli nie ma strumienia danych posortowanych utwórz go i wykonaj zapisywanie
            // w nim posortowanych danych ze strumieni pomocniczych
            //			if ( sortedStream == null )
            //			{
            if (vecFiles.isEmpty()) {
                return false;
            }
            if (storeCl == null) {
                return false; // TODO: Exception
            }

            // Wynikowy plik sortowania
            //				java.io.File plik = java.io.File.createTempFile("extsort", ".gpr");
            //				java.io.FileOutputStream fos = new java.io.FileOutputStream( plik );
            //				java.io.DataOutputStream dos = new java.io.DataOutputStream( fos );

            ExternalSortQuery sortseed = new ExternalSortQuery(storeCl);
            // Liczba fragmentarycznych plików
            final int n = vecFiles.size();
            // Tablica strumieni do odczytu
            // Jeżeli null, to dany strumień już sie wyczerpał
            for (final File vecFile : vecFiles) {
                sortseed.addInputStream(
                        new SeparatorStreamReader(
                                new FileInputStream(
                                        vecFile.toString())));
            }
	
	/*				// Operacje sortowania strumieniowego
					if ( storeCl == int[].class )
					{
						while ( sortseed.isData() )
							dos.writeInt(sortseed.nextInt());
					}
					else if (storeCl == float[].class )
					{
						while ( sortseed.isData() )
							dos.writeFloat(sortseed.nextFloat());
					}
					else if (storeCl == long[].class )
					{
						while ( sortseed.isData() )
							dos.writeLong(sortseed.nextLong());
					}
					else if (storeCl == double[].class )
					{
						while ( sortseed.isData() )
							dos.writeDouble(sortseed.nextDouble());
					}
					else
						return false; // TODO: Exception
					dos.close();*/
	/*				// Zamień na strumień wejściowy
					sortedStream = new SeparatorStreamReader(
							new java.io.FileInputStream( plik ) );
					sortedStreamFile = plik;
	*/    //		}

            // Zapisywanie strumienia wyjsciowego z rownoczesnym sortowaniem
            // strumieniowym
            final NumberWriter writer = data.getNumberWriter();
            boolean remts = false;
            if (parameters.get(REMOVETHESAME) != null) {
                remts = (Boolean) parameters.get(REMOVETHESAME);
            }

            boolean start = false;
            int dataCnt = 0;

            if (storeCl == ClassEnumerator.INTEGER) {
                int tmp;
                int preread = 0;
                while (sortseed.isData()) {
                    if (start) {
                        tmp = sortseed.getInt();
                        if ((tmp == preread) && (remts)) {
                            continue;
                        }
                        preread = tmp;
                    } else {
                        start = true;
                        preread = sortseed.getInt();
                    }
                    dataCnt++;
                    writer.write(preread);
                }
            } else if (storeCl == ClassEnumerator.LONG) {
                long tmp;
                long preread = 0l;
                while (sortseed.isData()) {
                    if (start) {
                        tmp = sortseed.getLong();
                        if ((tmp == preread) && remts) {
                            continue;
                        }
                        preread = tmp;
                    } else {
                        start = true;
                        preread = sortseed.getLong();
                    }
                    dataCnt++;
                    writer.write(preread);
                }
            } else if (storeCl == ClassEnumerator.FLOAT) {
                float tmp;
                float preread = 0.0f;
                while (sortseed.isData()) {
                    if (start) {
                        tmp = sortseed.getFloat();
                        if ((tmp == preread) && remts) {
                            continue;
                        }
                        preread = tmp;
                    } else {
                        start = true;
                        preread = sortseed.getFloat();
                    }
                    dataCnt++;
                    writer.write(preread);
                }
            } else if (storeCl == ClassEnumerator.DOUBLE) {
                double tmp;
                double preread = 0.0;
                while (sortseed.isData()) {
                    if (start) {
                        tmp = sortseed.getDouble();
                        if ((tmp == preread) && (remts)) {
                            continue;
                        }
                        preread = tmp;
                    } else {
                        start = true;
                        preread = sortseed.getDouble();
                    }
                    dataCnt++;
                    writer.write(preread);
                }
            }
            sortseed = null;

            writer.close();
            data.setStoreClass(storeCl);
            data.setSize(dataCnt);
        } catch (final IOException e) {
            throw new WriteFileGeneratorException(null, e);
        } finally {
            for (final File vecFile : vecFiles) {
                if (!vecFile.delete()) {
                    Mysys.error("Nie moge usunac tymczasowego pliku sortowania");
                }
            }
        }
        return true;
    }

    @Override
    public void setInputData(final NumberInterface data) {
        try (final NumberReader gis = data.getNumberReader()) {
            final ClassEnumerator c = data.getStoreClass();
            int counter = data.getSize();
            final InternalSort sorter = new InternalSort();
            sorter.setParameter(InternalSort.REMOVETHESAME, parameters.get(REMOVETHESAME));
            storeCl = data.getStoreClass();

            // Dzielenie duzych danych do sorotwania na mniejsze dla sortowania wewnetrznego
            while (gis.hasNext()) {
                // przenoszenie pozyskanych danych do pliku do strumienia wejsciowego
                sorter.setInputData(gis, c, (counter > Mysys.getPackageSize() ? Mysys.getPackageSize() : counter));
                counter -= Mysys.getPackageSize();
                // sortowanie z zapisaniem do tymczasowego pliku
                final GeneratedData iSort = GeneratedData.createTemporary();
                sorter.getOutputData(iSort);
                vecFiles.add(new File(iSort.getDataFilename()));
            }
        }
    }

    @Override
    protected void initParameters() {
    }
}

class ExternalSortQuery {
    private final ClassEnumerator storeCl;
    private Object tval;
    private SeparatorStreamReader[] tfis;

    ExternalSortQuery(final ClassEnumerator cl) {
        storeCl = cl;
        tfis = new SeparatorStreamReader[0];
        if (cl == ClassEnumerator.INTEGER) {
            tval = new int[0];
        } else if (cl == ClassEnumerator.LONG) {
            tval = new long[0];
        } else if (cl == ClassEnumerator.FLOAT) {
            tval = new float[0];
        } else if (cl == ClassEnumerator.DOUBLE) {
            tval = new double[0];
        }
    }

    // Dodaj do tablicy wraz z sortowaniem podle najmniejszy -> najwiekszy
    void addInputStream(final SeparatorStreamReader fis) {
        try {
            if (fis == null) {
                return;
            }
            if (!fis.hasNext()) {
                fis.close();
                return;
            }

            if (storeCl == ClassEnumerator.INTEGER) {
                final int[] tmp = (int[]) tval;
                final int[] tnew = new int[tmp.length + 1];
                final SeparatorStreamReader[] tfisnew =
                        new SeparatorStreamReader[tfis.length + 1];
                final int val = fis.readInt();
                int i;
                for (i = 0; i < tmp.length; i++) {
                    if (tmp[i] > val) {
                        break;
                    } else {
                        tnew[i] = tmp[i];
                        tfisnew[i] = tfis[i];
                    }
                }
                tnew[i] = val;
                tfisnew[i] = fis;
                i++;
                if (tmp.length > 0) {
                    for (; i < tnew.length; i++) {
                        tnew[i] = tmp[i - 1];
                        tfisnew[i] = tfis[i - 1];
                    }
                }
                tval = tnew;
                tfis = tfisnew;
            } else if (storeCl == ClassEnumerator.LONG) {
                final long[] tmp = (long[]) tval;
                final long[] tnew = new long[tmp.length + 1];
                final SeparatorStreamReader[] tfisnew =
                        new SeparatorStreamReader[tfis.length + 1];
                final long val = fis.readLong();
                int i;
                for (i = 0; i < tmp.length; i++) {
                    if (tmp[i] > val) {
                        break;
                    } else {
                        tnew[i] = tmp[i];
                        tfisnew[i] = tfis[i];
                    }
                }
                tnew[i] = val;
                tfisnew[i] = fis;
                i++;
                if (tmp.length > 0) {
                    for (; i < tnew.length; i++) {
                        tnew[i] = tmp[i - 1];
                        tfisnew[i] = tfis[i - 1];
                    }
                }
                tval = tnew;
                tfis = tfisnew;
            } else if (storeCl == ClassEnumerator.FLOAT) {
                final float[] tmp = (float[]) tval;
                final float[] tnew = new float[tmp.length + 1];
                final SeparatorStreamReader[] tfisnew =
                        new SeparatorStreamReader[tfis.length + 1];
                final float val = fis.readFloat();
                int i;
                for (i = 0; i < tmp.length; i++) {
                    if (tmp[i] > val) {
                        break;
                    } else {
                        tnew[i] = tmp[i];
                        tfisnew[i] = tfis[i];
                    }
                }
                tnew[i] = val;
                tfisnew[i] = fis;
                i++;
                if (tmp.length > 0) {
                    for (; i < tnew.length; i++) {
                        tnew[i] = tmp[i - 1];
                        tfisnew[i] = tfis[i - 1];
                    }
                }
                tval = tnew;
                tfis = tfisnew;
            } else if (storeCl == ClassEnumerator.DOUBLE) {
                final double[] tmp = (double[]) tval;
                final double[] tnew = new double[tmp.length + 1];
                final SeparatorStreamReader[] tfisnew =
                        new SeparatorStreamReader[tfis.length + 1];
                final double val = fis.readDouble();
                int i;
                for (i = 0; i < tmp.length; i++) {
                    if (tmp[i] > val) {
                        break;
                    } else {
                        tnew[i] = tmp[i];
                        tfisnew[i] = tfis[i];
                    }
                }
                tnew[i] = val;
                tfisnew[i] = fis;
                i++;
                if (tmp.length > 0) {
                    for (; i < tnew.length; i++) {
                        tnew[i] = tmp[i - 1];
                        tfisnew[i] = tfis[i - 1];
                    }
                }
                tval = tnew;
                tfis = tfisnew;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    // Odczytaj następnš danš ze strumienia elementu 0
    private void readNextInt() {
        final int[] tmp = (int[]) tval;
        final int[] tnew;
        final SeparatorStreamReader[] tfisnew;
        if (tfis[0].hasNext()) { // read and sort
            tnew = new int[tmp.length];
            tfisnew = new SeparatorStreamReader[tfis.length];
            int val = 0;
            val = tfis[0].readInt();

            int i;
            for (i = 1; i < tmp.length; i++) {
                if (tmp[i] > val) {
                    break;
                } else {
                    tnew[i - 1] = tmp[i];
                    tfisnew[i - 1] = tfis[i];
                }
            }
            tnew[i - 1] = val;
            tfisnew[i - 1] = tfis[0];
            for (; i < tmp.length; i++) {
                tnew[i] = tmp[i];
                tfisnew[i] = tfis[i];
            }
            tval = tnew;
            tfis = tfisnew;
        } else { // remove first position
            tnew = new int[tmp.length - 1];
            tfisnew = new SeparatorStreamReader[tfis.length - 1];
            tfis[0].close();
            for (int i = 0; i < tnew.length; i++) {
                tnew[i] = tmp[i + 1];
                tfisnew[i] = tfis[i + 1];
            }
        }
        tval = tnew;
        tfis = tfisnew;
    }

    // Odczytaj następnš danš ze strumienia elementu 0
    private void readNextLong() {
        final long[] tmp = (long[]) tval;
        final long[] tnew;
        final SeparatorStreamReader[] tfisnew;
        if (tfis[0].hasNext()) { // read and sort
            tnew = new long[tmp.length];
            tfisnew = new SeparatorStreamReader[tfis.length];
            long val = 0;
            val = tfis[0].readLong();

            int i;
            for (i = 1; i < tmp.length; i++) {
                if (tmp[i] > val) {
                    break;
                } else {
                    tnew[i - 1] = tmp[i];
                    tfisnew[i - 1] = tfis[i];
                }
            }
            tnew[i - 1] = val;
            tfisnew[i - 1] = tfis[0];
            for (; i < tmp.length; i++) {
                tnew[i] = tmp[i];
                tfisnew[i] = tfis[i];
            }
            tval = tnew;
            tfis = tfisnew;
        } else { // remove first position
            tnew = new long[tmp.length - 1];
            tfisnew = new SeparatorStreamReader[tfis.length - 1];
            tfis[0].close();
            for (int i = 0; i < tnew.length; i++) {
                tnew[i] = tmp[i + 1];
                tfisnew[i] = tfis[i + 1];
            }
        }
        tval = tnew;
        tfis = tfisnew;
    }

    // Odczytaj następnš danš ze strumienia elementu 0
    private void readNextFloat() {
        final float[] tmp = (float[]) tval;
        final float[] tnew;
        final SeparatorStreamReader[] tfisnew;
        if (tfis[0].hasNext()) { // read and sort
            tnew = new float[tmp.length];
            tfisnew = new SeparatorStreamReader[tfis.length];
            float val = 0;
            val = tfis[0].readFloat();

            int i;
            for (i = 1; i < tmp.length; i++) {
                if (tmp[i] > val) {
                    break;
                } else {
                    tnew[i - 1] = tmp[i];
                    tfisnew[i - 1] = tfis[i];
                }
            }
            tnew[i - 1] = val;
            tfisnew[i - 1] = tfis[0];
            for (; i < tmp.length; i++) {
                tnew[i] = tmp[i];
                tfisnew[i] = tfis[i];
            }
            tval = tnew;
            tfis = tfisnew;
        } else { // remove first position
            tnew = new float[tmp.length - 1];
            tfisnew = new SeparatorStreamReader[tfis.length - 1];
            tfis[0].close();
            for (int i = 0; i < tnew.length; i++) {
                tnew[i] = tmp[i + 1];
                tfisnew[i] = tfis[i + 1];
            }
        }
        tval = tnew;
        tfis = tfisnew;
    }

    // Odczytaj następnš danš ze strumienia elementu 0
    private void readNextDouble() {
        final double[] tmp = (double[]) tval;
        final double[] tnew;
        final SeparatorStreamReader[] tfisnew;
        if (tfis[0].hasNext()) { // read and sort
            tnew = new double[tmp.length];
            tfisnew = new SeparatorStreamReader[tfis.length];
            double val = 0;
            val = tfis[0].readDouble();

            int i;
            for (i = 1; i < tmp.length; i++) {
                if (tmp[i] > val) {
                    break;
                } else {
                    tnew[i - 1] = tmp[i];
                    tfisnew[i - 1] = tfis[i];
                }
            }
            tnew[i - 1] = val;
            tfisnew[i - 1] = tfis[0];
            for (; i < tmp.length; i++) {
                tnew[i] = tmp[i];
                tfisnew[i] = tfis[i];
            }
            tval = tnew;
            tfis = tfisnew;
        } else { // remove first position
            tnew = new double[tmp.length - 1];
            tfisnew = new SeparatorStreamReader[tfis.length - 1];
            tfis[0].close();
            for (int i = 0; i < tnew.length; i++) {
                tnew[i] = tmp[i + 1];
                tfisnew[i] = tfis[i + 1];
            }
        }
        tval = tnew;
        tfis = tfisnew;
    }

    public boolean isData() {
        return tfis.length != 0;
    }

    // pobierz dane jako dany typ i wczytaj następnš dostępnš danš
    public int getInt() {
        final int[] tmp = (int[]) tval;
        final int ret = tmp[0];
        readNextInt();
        return ret;
    }

    public long getLong() {
        final long[] tmp = (long[]) tval;
        final long ret = tmp[0];
        readNextLong();
        return ret;
    }

    public float getFloat() {
        final float[] tmp = (float[]) tval;
        final float ret = tmp[0];
        readNextFloat();
        return ret;
    }

    public double getDouble() {
        final double[] tmp = (double[]) tval;
        final double ret = tmp[0];
        readNextDouble();
        return ret;
    }
};
