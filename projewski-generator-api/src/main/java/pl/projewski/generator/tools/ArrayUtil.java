/**
 * 
 */
package pl.projewski.generator.tools;

import java.io.IOException;
import java.io.OutputStream;

import pk.ie.proj.tools.stream.SeparatorStreamWriter;
import pl.projewski.generator.tools.stream.SeparatorStreamWriter;

/**
 * @author projewski
 *
 */
public class ArrayUtil {

	/**
	 * Reallocates an array with a new size, and copies the contents
	 * of the old array to the new array.
	 *  @param oldArray  the old array, to be reallocated.
	 *  @param newSize   the new array size.
	 *  @return          A new array with the same contents.
	 */
	public static Object resize(Object oldArray, int newSize)
	{
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(
				elementType,newSize);
		int preserveLength = Math.min(oldSize,newSize);
		if (preserveLength > 0)
			System.arraycopy (oldArray,0,newArray,0,preserveLength);
		return newArray;
	}
	
	/**
	 * Umieszczenie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static int [] putFirst(int [] array, int element)
	{
		int [] newArray = new int[array.length + 1];
		System.arraycopy(array, 0, newArray, 1, array.length);
		newArray[0] = element;
		return newArray;
	}
	
	/**
	 * Umieszczenie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static long [] putFirst(long [] array, long element)
	{
		long [] newArray = new long[array.length + 1];
		System.arraycopy(array, 0, newArray, 1, array.length);
		newArray[0] = element;
		return newArray;
	}
	
	/**
	 * Umieszczenie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static float [] putFirst(float [] array, float element)
	{
		float [] newArray = new float[array.length + 1];
		System.arraycopy(array, 0, newArray, 1, array.length);
		newArray[0] = element;
		return newArray;
	}
	
	/**
	 * Umieszczenie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static double [] putFirst(double [] array, double element)
	{
		double [] newArray = new double[array.length + 1];
		System.arraycopy(array, 0, newArray, 1, array.length);
		newArray[0] = element;
		return newArray;
	}
	
	/**
	 * Usunięcie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static int remFirst(int [] array)
	{
		int removed = array[0];
		System.arraycopy(array, 1, array, 0, array.length-1);
		return removed;
	}
	
	/**
	 * Usunięcie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static long remFirst(long [] array)
	{
		long removed = array[0];
		System.arraycopy(array, 1, array, 0, array.length-1);
		return removed;
	}
	
	/**
	 * Usunięcie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static float remFirst(float [] array)
	{
		float removed = array[0];
		System.arraycopy(array, 1, array, 0, array.length-1);
		return removed;
	}
	
	/**
	 * Usunięcie elementu na czele tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static double remFirst(double [] array)
	{
		double removed = array[0];
		System.arraycopy(array, 1, array, 0, array.length-1);
		return removed;
	}
	
	/**
	 * Umieszczenie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static int [] putLast(int [] array, int element)
	{
		int [] newArray;
		if ( array != null )
		{
			newArray = new int[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[array.length] = element;
		}
		else
		{
			newArray = new int[1];
			newArray[0] = element;
		}
		return newArray;
	}
	
	public static int [] putLast(int [] array, int [] elements)
	{
		int [] newArray;
		if ( elements == null || elements.length == 0 )
			return array;
		if ( array != null )
		{
			newArray = new int[array.length + elements.length];
			System.arraycopy(array, 0, newArray, 0, array.length);
			System.arraycopy(elements, 0, newArray, array.length, elements.length);
		}
		else
		{
			return elements;
		}
		return newArray;		
	}
	
	/**
	 * Umieszczenie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static long [] putLast(long [] array, long element)
	{
		long [] newArray;
		if ( array != null )
		{
			newArray = new long[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[array.length] = element;
		}
		else
		{
			newArray = new long[1];
			newArray[0] = element;
		}
		return newArray;
	}
	
	public static long [] putLast(long [] array, long [] elements)
	{
		long [] newArray;
		if ( elements == null || elements.length == 0 )
			return array;
		if ( array != null )
		{
			newArray = new long[array.length + elements.length];
			System.arraycopy(array, 0, newArray, 0, array.length);
			System.arraycopy(elements, 0, newArray, array.length, elements.length);
		}
		else
		{
			return elements;
		}
		return newArray;		
	}

	/**
	 * Umieszczenie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static float [] putLast(float [] array, float element)
	{
		float [] newArray;
		if ( array != null )
		{
			newArray = new float[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[array.length] = element;
		}
		else
		{
			newArray = new float[1];
			newArray[0] = element;
		}
		return newArray;
	}
	
	public static float [] putLast(float [] array, float [] elements)
	{
		float [] newArray;
		if ( elements == null || elements.length == 0 )
			return array;
		if ( array != null )
		{
			newArray = new float[array.length + elements.length];
			System.arraycopy(array, 0, newArray, 0, array.length);
			System.arraycopy(elements, 0, newArray, array.length, elements.length);
		}
		else
		{
			return elements;
		}
		return newArray;		
	}
	/**
	 * Umieszczenie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @param element element do umieszczenia
	 * @return tablica wyjsciowa
	 */
	public static double [] putLast(double [] array, double element)
	{
		double [] newArray;
		if ( array != null )
		{
			newArray = new double[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[array.length] = element;
		}
		else
		{
			newArray = new double[1];
			newArray[0] = element;
		}
		return newArray;
	}
	
	public static double [] putLast(double [] array, double [] elements)
	{
		double [] newArray;
		if ( elements == null || elements.length == 0 )
			return array;
		if ( array != null )
		{
			newArray = new double[array.length + elements.length];
			System.arraycopy(array, 0, newArray, 0, array.length);
			System.arraycopy(elements, 0, newArray, array.length, elements.length);
		}
		else
		{
			return elements;
		}
		return newArray;		
	}
	/**
	 * Usunięcie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static int remLast(int [] array)
	{
		if ( array != null )
		{
			int removed = array[array.length - 1];
			System.arraycopy(array, 0, array, 0, array.length-1);
			return removed;
		}
		else
			return 0;
	}
	
	/**
	 * Usunięcie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static long remLast(long [] array)
	{
		if ( array != null )
		{
			long removed = array[array.length - 1];
			System.arraycopy(array, 0, array, 0, array.length-1);
			return removed;
		}
		else
			return 0;
	}
	
	/**
	 * Usunięcie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static float remLast(float [] array)
	{
		if ( array != null )
		{
			float removed = array[array.length - 1];
			System.arraycopy(array, 0, array, 0, array.length-1);
			return removed;
		}
		else
			return 0.0f;
	}
	
	/**
	 * Usunięcie elementu na końcu tablicy
	 * @param array tablica wejsciowa
	 * @return usunięty element
	 */
	public static double remLast(double [] array)
	{
		if ( array != null )
		{
			double removed = array[array.length - 1];
			System.arraycopy(array, 0, array, 0, array.length-1);
			return removed;
		}
		else
			return 0.0;
	}
	
	/**
	 * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
	 * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return ostatni element
	 */
	public static int constPutFirst(int [] array, int element)
	{
		if (array == null)
			return 0;
		int lastElement = array[array.length - 1];
		System.arraycopy(array, 0, array, 1, array.length - 1);
		array[0] = element;
		return lastElement;
	}

	/**
	 * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
	 * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return ostatni element
	 */
	public static long constPutFirst(long [] array, long element)
	{
		if (array == null)
			return 0;
		long lastElement = array[array.length - 1];
		System.arraycopy(array, 0, array, 1, array.length - 1);
		array[0] = element;
		return lastElement;
	}

	/**
	 * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
	 * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return ostatni element
	 */
	public static float constPutFirst(float [] array, float element)
	{
		if (array == null)
			return 0.0f;
		float lastElement = array[array.length - 1];
		System.arraycopy(array, 0, array, 1, array.length - 1);
		array[0] = element;
		return lastElement;
	}

	/**
	 * Przesunięcie całej tablicy w prawo. Ostatni element wypada i jest zwracany przez
	 * funkcję. Pierwszy element zostaje zastšpiony przez podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return ostatni element
	 */
	public static double constPutFirst(double [] array, double element)
	{
		if (array == null)
			return 0.0;
		double lastElement = array[array.length - 1];
		System.arraycopy(array, 0, array, 1, array.length - 1);
		array[0] = element;
		return lastElement;
	}

	/**
	 * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
	 * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return pierwszy element
	 */
	public static int constPutLast(int [] array, int element)
	{
		if (array == null)
			return 0;
		int firstElement = array[0];
		System.arraycopy(array, 1, array, 0, array.length - 1);
		array[array.length - 1] = element;
		return firstElement;
	}
	/**
	 * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
	 * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return pierwszy element
	 */
	public static long constPutLast(long [] array, long element)
	{
		if (array == null)
			return 0;
		long firstElement = array[0];
		System.arraycopy(array, 1, array, 0, array.length - 1);
		array[array.length - 1] = element;
		return firstElement;
	}
	/**
	 * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
	 * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return pierwszy element
	 */
	public static float constPutLast(float [] array, float element)
	{
		if (array == null)
			return 0.0f;
		float firstElement = array[0];
		System.arraycopy(array, 1, array, 0, array.length - 1);
		array[array.length - 1] = element;
		return firstElement;
	}
	/**
	 * Przesunięcie całej tablicy w lewo. Pierwszy element wypada i jest zwracany przez
	 * funkcję. Na końcu zostaje umieszczony podany element. Rozmiar tablicy
	 * nie podlega zmianie.
	 * 
	 * @param array zmieniana tablica
	 * @param element umieszczany element
	 * @return pierwszy element
	 */
	public static double constPutLast(double [] array, double element)
	{
		if (array == null)
			return 0.0;
		double firstElement = array[0];
		System.arraycopy(array, 1, array, 0, array.length - 1);
		array[array.length - 1] = element;
		return firstElement;
	}
	
	public static boolean equals(int [] a, int [] b, int count)
	{
        if (a == b)
            return true;
        if (count == 0)
        	return true;
        if (a == null || b == null)
            return false;
        
        if ( count > a.length )
        	return false;
        if ( count > b.length )
        	return false;

        for (int i=0; i<count; i++)
            if ( a[i] != b[i] )
                return false;

        return true;
	}
	public static boolean equals(long [] a, long [] b, int count)
	{
        if (a == b)
            return true;
        if (count == 0)
        	return true;
        if (a == null || b == null)
            return false;
        
        if ( count > a.length )
        	return false;
        if ( count > b.length )
        	return false;

        for (int i=0; i<count; i++)
            if ( a[i] != b[i] )
                return false;

        return true;
	}
	public static boolean equals(float [] a, float [] b, int count)
	{
        if (a == b)
            return true;
        if (count == 0)
        	return true;
        if (a == null || b == null)
            return false;
        
        if ( count > a.length )
        	return false;
        if ( count > b.length )
        	return false;

        for (int i=0; i<count; i++)
            if ( a[i] != b[i] )
                return false;

        return true;
	}
	public static boolean equals(double [] a, double [] b, int count)
	{
        if (a == b)
            return true;
        if (count == 0)
        	return true;
        if (a == null || b == null)
            return false;
        
        if ( count > a.length )
        	return false;
        if ( count > b.length )
        	return false;

        for (int i=0; i<count; i++)
            if ( a[i] != b[i] )
                return false;

        return true;
	}
	
	/**
	 * NOT TESTED
	 * @param a
	 * @param b
	 * @param count
	 * @return
	 */
	public static boolean equals(byte [] a, byte [] b, int count)
	{
        if (a == b)
            return true;
        if (count == 0)
        	return true;
        if (a == null || b == null)
            return false;
        
        if ( count > a.length )
        	return false;
        if ( count > b.length )
        	return false;

        for (int i=0; i<count; i++)
            if ( a[i] != b[i] )
                return false;

        return true;		
	}
	
	/**
	 * Sprawdzanie, czy ciag a zaczyna sie od ciagu b
	 * NOT TESTESTD
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean startsWith(byte [] a, byte [] b)
	{
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
	public static boolean equals(byte [] a, byte [] b, int starta, int startb, int count)
	{
        if (a == b)
            return true;
        if (count == 0)
        	return true;
        if (a == null || b == null)
            return false;
        
        if ( starta + count > a.length )
        	return false;
        if ( startb + count > b.length )
        	return false;

        for (int i=0; i<count; i++)
            if ( a[starta + i] != b[startb + i] )
                return false;

        return true;		
	}

	/**
	 * Znalezienie pierwszego wystapienia tablicy b w tablicy a
	 * Poszukiwanie od podanego miejsca
	 * NOT TESTED
	 * @param a
	 * @param b
	 * @param fromIndex
	 * @return -1 jezeli nie znaleziono
	 */
	public static int indexOf(byte [] a, byte [] b, int fromIndex)
	{
        if (a == b)
        {
        	if (fromIndex == 0)
        		return 0;
        	else
        		return -1;
        }
        if (a == null || b == null)
            return -1;

        int counter = a.length - fromIndex - b.length;
        
        if ( counter < 0 )
        	return -1;
        if ( counter == 0 )
        	return equals(a, b, fromIndex, 0, a.length) ? 0 : -1;
        
        counter += fromIndex; // aby odliczac od fromIndex, musze przesunac licznik granicy
        P1: for (int i=fromIndex; i<counter; i++)
        {
        	for (int j=0; j<b.length; j++, i++)
        	{
        		if (a[i] != b[j])
        			continue P1;
        	}
        	return i - b.length;
        }
        return -1;
	}

	/**
	 * Znalezienie pierwszego wystapienia tablicy b w tablicy a
	 * NOT TESTED
	 * @param a
	 * @param b
	 * @return -1 jezeli nie znaleziono
	 */
	public static int indexOf(byte [] a, byte [] b)
	{
		return indexOf(a, b, 0);
	}

	/**
	 * Sprawdzenie, czy tablica a zawiera gdzies elementy z tablicy b w tej samej kolejnosci
	 * NOT TESTED
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean contains(byte [] a, byte [] b)
	{
		return indexOf(a, b, 0) != -1;
	}
	
	public static void dump(int [] a, OutputStream os)
	{
		SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
		try {
			if ( a != null )
				writer.write(a);
			else
				writer.write("<null>");
			writer.flush();
		} catch (IOException e) {
			Mysys.error("dump Error");
		}
	}
	
	public static void dump(long [] a, OutputStream os)
	{
		SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
		try {
			if ( a != null )
				writer.write(a);
			else
				writer.write("<null>");
			writer.flush();
		} catch (IOException e) {
			Mysys.error("dump Error");
		}
	}
	public static void dump(float [] a, OutputStream os)
	{
		SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
		try {
			if ( a != null )
				writer.write(a);
			else
				writer.write("<null>");
			writer.flush();
		} catch (IOException e) {
			Mysys.error("dump Error");
		}
	}
	public static void dump(double [] a, OutputStream os)
	{
		SeparatorStreamWriter writer = new SeparatorStreamWriter(os);
		try {
			if ( a != null )
				writer.write(a);
			else
				writer.write("<null>");
			writer.flush();
		} catch (IOException e) {
			Mysys.error("dump Error");
		}
	}
	
	// zwracana jest tablica wejsciowa, która została przekształcona
	public static int [] reverse(int [] a)
	{
		if ( a == null )
			return a;
		
		int left = 0;
		int right = a.length-1;
		int tmp;
		
		while (left < right) {
			tmp = a[left];
			a[left]  = a[right];
			a[right] = tmp;
			left++;
			right--;
		}
		
		return a;
	}
	// zwracana jest tablica wejsciowa, która została przekształcona
	public static long [] reverse(long [] a)
	{
		if ( a == null )
			return a;
		
		int left = 0;
		int right = a.length-1;
		long tmp;
		
		while (left < right) {
			tmp = a[left];
			a[left]  = a[right];
			a[right] = tmp;
			left++;
			right--;
		}
		
		return a;
	}
	// zwracana jest tablica wejsciowa, która została przekształcona
	public static float [] reverse(float [] a)
	{
		if ( a == null )
			return a;
		
		int left = 0;
		int right = a.length-1;
		float tmp;
		
		while (left < right) {
			tmp = a[left];
			a[left]  = a[right];
			a[right] = tmp;
			left++;
			right--;
		}
		
		return a;
	}
	// zwracana jest tablica wejsciowa, która została przekształcona
	public static double [] reverse(double [] a)
	{
		if ( a == null )
			return a;
		
		int left = 0;
		int right = a.length-1;
		double tmp;
		
		while (left < right) {
			tmp = a[left];
			a[left]  = a[right];
			a[right] = tmp;
			left++;
			right--;
		}
		
		return a;
	}
	// zwracana jest tablica wejsciowa, która została przekształcona
	public static byte [] reverse(byte [] a)
	{
		if ( a == null )
			return a;
		
		int left = 0;
		int right = a.length-1;
		byte tmp;
		
		while (left < right) {
			tmp = a[left];
			a[left]  = a[right];
			a[right] = tmp;
			left++;
			right--;
		}
		
		return a;
	}
	public static char [] reverse(char [] a)
	{
		if ( a == null )
			return a;
		
		int left = 0;
		int right = a.length-1;
		char tmp;
		
		while (left < right) {
			tmp = a[left];
			a[left]  = a[right];
			a[right] = tmp;
			left++;
			right--;
		}
		
		return a;
	}
}
