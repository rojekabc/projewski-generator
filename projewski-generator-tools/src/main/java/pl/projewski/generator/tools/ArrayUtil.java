package pl.projewski.generator.tools;

import pl.projewski.generator.tools.stream.SeparatorStreamWriter;

import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author projewski
 */
public class ArrayUtil {

    /**
     * Reallocates an array with a new size, and copies the contents
     * of the old array to the new array.
     *
     * @param oldArray the old array, to be reallocated.
     * @param newSize  the new array size.
     * @return A new array with the same contents.
     */
    public static Object resize(final Object oldArray, final int newSize) {
        final int oldSize = java.lang.reflect.Array.getLength(oldArray);
        final Class<?> elementType = oldArray.getClass().getComponentType();
        final Object newArray = java.lang.reflect.Array.newInstance(
                elementType, newSize);
        final int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }
        return newArray;
    }

    /**
     * Umieszczenie elementu na czele tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static int[] putFirst(final int[] array, final int element) {
        final int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 1, array.length);
        newArray[0] = element;
        return newArray;
    }

    /**
     * Umieszczenie elementu na czele tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static long[] putFirst(final long[] array, final long element) {
        final long[] newArray = new long[array.length + 1];
        System.arraycopy(array, 0, newArray, 1, array.length);
        newArray[0] = element;
        return newArray;
    }

    /**
     * Umieszczenie elementu na czele tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static float[] putFirst(final float[] array, final float element) {
        final float[] newArray = new float[array.length + 1];
        System.arraycopy(array, 0, newArray, 1, array.length);
        newArray[0] = element;
        return newArray;
    }

    /**
     * Umieszczenie elementu na czele tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static double[] putFirst(final double[] array, final double element) {
        final double[] newArray = new double[array.length + 1];
        System.arraycopy(array, 0, newArray, 1, array.length);
        newArray[0] = element;
        return newArray;
    }

    /**
     * Usunięcie elementu na czele tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static int remFirst(final int[] array) {
        final int removed = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        return removed;
    }

    /**
     * Usunięcie elementu na czele tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static long remFirst(final long[] array) {
        final long removed = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        return removed;
    }

    /**
     * Usunięcie elementu na czele tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static float remFirst(final float[] array) {
        final float removed = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        return removed;
    }

    /**
     * Usunięcie elementu na czele tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static double remFirst(final double[] array) {
        final double removed = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        return removed;
    }

    /**
     * Umieszczenie elementu na końcu tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static int[] putLast(final int[] array, final int element) {
        final int[] newArray;
        if (array != null) {
            newArray = new int[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = element;
        } else {
            newArray = new int[1];
            newArray[0] = element;
        }
        return newArray;
    }

    public static int[] putLast(final int[] array, final int[] elements) {
        final int[] newArray;
        if (elements == null || elements.length == 0) {
            return array;
        }
        if (array != null) {
            newArray = new int[array.length + elements.length];
            System.arraycopy(array, 0, newArray, 0, array.length);
            System.arraycopy(elements, 0, newArray, array.length, elements.length);
        } else {
            return elements;
        }
        return newArray;
    }

    /**
     * Umieszczenie elementu na końcu tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static long[] putLast(final long[] array, final long element) {
        final long[] newArray;
        if (array != null) {
            newArray = new long[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = element;
        } else {
            newArray = new long[1];
            newArray[0] = element;
        }
        return newArray;
    }

    public static long[] putLast(final long[] array, final long[] elements) {
        final long[] newArray;
        if (elements == null || elements.length == 0) {
            return array;
        }
        if (array != null) {
            newArray = new long[array.length + elements.length];
            System.arraycopy(array, 0, newArray, 0, array.length);
            System.arraycopy(elements, 0, newArray, array.length, elements.length);
        } else {
            return elements;
        }
        return newArray;
    }

    /**
     * Umieszczenie elementu na końcu tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static float[] putLast(final float[] array, final float element) {
        final float[] newArray;
        if (array != null) {
            newArray = new float[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = element;
        } else {
            newArray = new float[1];
            newArray[0] = element;
        }
        return newArray;
    }

    public static float[] putLast(final float[] array, final float[] elements) {
        final float[] newArray;
        if (elements == null || elements.length == 0) {
            return array;
        }
        if (array != null) {
            newArray = new float[array.length + elements.length];
            System.arraycopy(array, 0, newArray, 0, array.length);
            System.arraycopy(elements, 0, newArray, array.length, elements.length);
        } else {
            return elements;
        }
        return newArray;
    }

    /**
     * Umieszczenie elementu na końcu tablicy
     *
     * @param array   tablica wejsciowa
     * @param element element do umieszczenia
     * @return tablica wyjsciowa
     */
    public static double[] putLast(final double[] array, final double element) {
        final double[] newArray;
        if (array != null) {
            newArray = new double[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = element;
        } else {
            newArray = new double[1];
            newArray[0] = element;
        }
        return newArray;
    }

    public static double[] putLast(final double[] array, final double[] elements) {
        final double[] newArray;
        if (elements == null || elements.length == 0) {
            return array;
        }
        if (array != null) {
            newArray = new double[array.length + elements.length];
            System.arraycopy(array, 0, newArray, 0, array.length);
            System.arraycopy(elements, 0, newArray, array.length, elements.length);
        } else {
            return elements;
        }
        return newArray;
    }

    /**
     * Usunięcie elementu na końcu tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static int remLast(final int[] array) {
        if (array != null) {
            final int removed = array[array.length - 1];
            System.arraycopy(array, 0, array, 0, array.length - 1);
            return removed;
        } else {
            return 0;
        }
    }

    /**
     * Usunięcie elementu na końcu tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static long remLast(final long[] array) {
        if (array != null) {
            final long removed = array[array.length - 1];
            System.arraycopy(array, 0, array, 0, array.length - 1);
            return removed;
        } else {
            return 0;
        }
    }

    /**
     * Usunięcie elementu na końcu tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static float remLast(final float[] array) {
        if (array != null) {
            final float removed = array[array.length - 1];
            System.arraycopy(array, 0, array, 0, array.length - 1);
            return removed;
        } else {
            return 0.0f;
        }
    }

    /**
     * Usunięcie elementu na końcu tablicy
     *
     * @param array tablica wejsciowa
     * @return usunięty element
     */
    public static double remLast(final double[] array) {
        if (array != null) {
            final double removed = array[array.length - 1];
            System.arraycopy(array, 0, array, 0, array.length - 1);
            return removed;
        } else {
            return 0.0;
        }
    }

    /**
     * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
     * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return ostatni element
     */
    public static int constPutFirst(final int[] array, final int element) {
        if (array == null) {
            return 0;
        }
        final int lastElement = array[array.length - 1];
        System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = element;
        return lastElement;
    }

    /**
     * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
     * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return ostatni element
     */
    public static long constPutFirst(final long[] array, final long element) {
        if (array == null) {
            return 0;
        }
        final long lastElement = array[array.length - 1];
        System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = element;
        return lastElement;
    }

    /**
     * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
     * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return ostatni element
     */
    public static float constPutFirst(final float[] array, final float element) {
        if (array == null) {
            return 0.0f;
        }
        final float lastElement = array[array.length - 1];
        System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = element;
        return lastElement;
    }

    /**
     * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
     * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return ostatni element
     */
    public static double constPutFirst(final double[] array, final double element) {
        if (array == null) {
            return 0.0;
        }
        final double lastElement = array[array.length - 1];
        System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = element;
        return lastElement;
    }

    /**
     * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
     * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return pierwszy element
     */
    public static int constPutLast(final int[] array, final int element) {
        if (array == null) {
            return 0;
        }
        final int firstElement = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        array[array.length - 1] = element;
        return firstElement;
    }

    /**
     * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
     * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return pierwszy element
     */
    public static long constPutLast(final long[] array, final long element) {
        if (array == null) {
            return 0;
        }
        final long firstElement = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        array[array.length - 1] = element;
        return firstElement;
    }

    /**
     * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
     * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return pierwszy element
     */
    public static float constPutLast(final float[] array, final float element) {
        if (array == null) {
            return 0.0f;
        }
        final float firstElement = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        array[array.length - 1] = element;
        return firstElement;
    }

    /**
     * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
     * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
     * nie podlega zmianie.
     *
     * @param array   zmieniana tablica
     * @param element umieszczany element
     * @return pierwszy element
     */
    public static double constPutLast(final double[] array, final double element) {
        if (array == null) {
            return 0.0;
        }
        final double firstElement = array[0];
        System.arraycopy(array, 1, array, 0, array.length - 1);
        array[array.length - 1] = element;
        return firstElement;
    }

    public static boolean equals(final int[] a, final int[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equals(final int[] a, final int[] b, final int count) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (count == 0) {
            return true;
        }

        if (count > a.length) {
            return false;
        }
        if (count > b.length) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(final long[] a, final long[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equals(final long[] a, final long[] b, final int count) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (count == 0) {
            return true;
        }

        if (count > a.length) {
            return false;
        }
        if (count > b.length) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(final float[] a, final float[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equals(final float[] a, final float[] b, final int count) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (count == 0) {
            return true;
        }

        if (count > a.length) {
            return false;
        }
        if (count > b.length) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(final double[] a, final double[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equals(final double[] a, final double[] b, final int count) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (count == 0) {
            return true;
        }

        if (count > a.length) {
            return false;
        }
        if (count > b.length) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(final byte[] a, final byte[] b) {
        return Arrays.equals(a, b);
    }

    /**
     * NOT TESTED
     *
     * @param a
     * @param b
     * @param count
     * @return
     */
    public static boolean equals(final byte[] a, final byte[] b, final int count) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (count == 0) {
            return true;
        }

        if (count > a.length) {
            return false;
        }
        if (count > b.length) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Sprawdzanie, czy ciag a zaczyna sie od ciagu b
     * NOT TESTESTD
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean startsWith(final byte[] a, final byte[] b) {
        return equals(a, b, b.length);
    }

    /**
     * NOT TESTED
     * Sprawdzanie zgodnosci tablic od wskazanych miejsc na okreslona dlugosc
     *
     * @param a
     * @param b
     * @param starta
     * @param startb
     * @param count
     * @return
     */
    public static boolean equals(final byte[] a, final byte[] b, final int starta, final int startb, final int count) {
        if (a == b) {
            return true;
        }
        if (count == 0) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }

        if (starta + count > a.length) {
            return false;
        }
        if (startb + count > b.length) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            if (a[starta + i] != b[startb + i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Znalezienie pierwszego wystapienia tablicy b w tablicy a
     * Poszukiwanie od podanego miejsca
     * NOT TESTED
     *
     * @param a
     * @param b
     * @param fromIndex
     * @return -1 jezeli nie znaleziono
     */
    public static int indexOf(final byte[] a, final byte[] b, final int fromIndex) {
        if (a == b) {
            if (fromIndex == 0) {
                return 0;
            } else {
                return -1;
            }
        }
        if (a == null || b == null) {
            return -1;
        }

        int counter = a.length - fromIndex - b.length;

        if (counter < 0) {
            return -1;
        }
        if (counter == 0) {
            return equals(a, b, fromIndex, 0, a.length) ? 0 : -1;
        }

        counter += fromIndex; // aby odliczac od fromIndex, musze przesunac licznik granicy
        P1:
        for (int i = fromIndex; i < counter; i++) {
            for (int j = 0; j < b.length; j++, i++) {
                if (a[i] != b[j]) {
                    continue P1;
                }
            }
            return i - b.length;
        }
        return -1;
    }

    /**
     * Znalezienie pierwszego wystapienia tablicy b w tablicy a
     * NOT TESTED
     *
     * @param a
     * @param b
     * @return -1 jezeli nie znaleziono
     */
    public static int indexOf(final byte[] a, final byte[] b) {
        return indexOf(a, b, 0);
    }

    /**
     * Sprawdzenie, czy tablica a zawiera gdzies elementy z tablicy b w tej samej kolejnosci
     * NOT TESTED
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean contains(final byte[] a, final byte[] b) {
        return indexOf(a, b, 0) != -1;
    }

    public static void dump(final int[] a, final OutputStream os) {
        final SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
        if (a != null) {
            writer.write(a);
        } else {
            writer.write("<null>");
        }
        writer.flush();
    }

    public static void dump(final long[] a, final OutputStream os) {
        final SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
        if (a != null) {
            writer.write(a);
        } else {
            writer.write("<null>");
        }
        writer.flush();
    }

    public static void dump(final float[] a, final OutputStream os) {
        final SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
        if (a != null) {
            writer.write(a);
        } else {
            writer.write("<null>");
        }
        writer.flush();
    }

    public static void dump(final double[] a, final OutputStream os) {
        final SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
        if (a != null) {
            writer.write(a);
        } else {
            writer.write("<null>");
        }
        writer.flush();
    }

    // zwracana jest tablica wejsciowa, która została przekształcona
    public static int[] reverse(final int[] a) {
        if (a == null) {
            return a;
        }

        int left = 0;
        int right = a.length - 1;
        int tmp;

        while (left < right) {
            tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;
            left++;
            right--;
        }

        return a;
    }

    // zwracana jest tablica wejsciowa, która została przekształcona
    public static long[] reverse(final long[] a) {
        if (a == null) {
            return a;
        }

        int left = 0;
        int right = a.length - 1;
        long tmp;

        while (left < right) {
            tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;
            left++;
            right--;
        }

        return a;
    }

    // zwracana jest tablica wejsciowa, która została przekształcona
    public static float[] reverse(final float[] a) {
        if (a == null) {
            return a;
        }

        int left = 0;
        int right = a.length - 1;
        float tmp;

        while (left < right) {
            tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;
            left++;
            right--;
        }

        return a;
    }

    // zwracana jest tablica wejsciowa, która została przekształcona
    public static double[] reverse(final double[] a) {
        if (a == null) {
            return a;
        }

        int left = 0;
        int right = a.length - 1;
        double tmp;

        while (left < right) {
            tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;
            left++;
            right--;
        }

        return a;
    }

    // zwracana jest tablica wejsciowa, która została przekształcona
    public static byte[] reverse(final byte[] a) {
        if (a == null) {
            return a;
        }

        int left = 0;
        int right = a.length - 1;
        byte tmp;

        while (left < right) {
            tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;
            left++;
            right--;
        }

        return a;
    }

    public static char[] reverse(final char[] a) {
        if (a == null) {
            return a;
        }

        int left = 0;
        int right = a.length - 1;
        char tmp;

        while (left < right) {
            tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;
            left++;
            right--;
        }

        return a;
    }
}
