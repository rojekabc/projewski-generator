package pl.projewski.generator.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author projewski
 * <p>
 * Test klasy ArrayUtil
 */
class ArrayUtilTest {
    @Test
    void testEquals_double_null() {
        final double[] tmp = null;
        assertTrue(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_double_eptyAndNull() {
        final double[] tmp = new double[]{};
        assertFalse(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_double_empty() {
        assertTrue(ArrayUtil.equals(new float[]{}, new float[]{}, 0));
    }

    @Test
    void testEquals_double_same() {
        final double[] a = new double[]{0, 5, 4, 2, 5, 7};
        final double[] b = new double[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < a.length; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
    }

    @Test
    void testEquals_double_different() {
        final double[] a = new double[]{0, 5, 4, 2, 3, 7};
        final double[] b = new double[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < 4; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
        assertFalse(ArrayUtil.equals(a, b, 5));
    }

    @Test
    void testEquals_float_null() {
        final float[] tmp = null;
        assertTrue(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_float_eptyAndNull() {
        final float[] tmp = new float[]{};
        assertFalse(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_float_empty() {
        assertTrue(ArrayUtil.equals(new float[]{}, new float[]{}, 0));
    }

    @Test
    void testEquals_float_same() {
        final float[] a = new float[]{0, 5, 4, 2, 5, 7};
        final float[] b = new float[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < a.length; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
    }

    @Test
    void testEquals_float_different() {
        final float[] a = new float[]{0, 5, 4, 2, 3, 7};
        final float[] b = new float[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < 4; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
        assertFalse(ArrayUtil.equals(a, b, 5));
    }

    @Test
    void testEquals_int_null() {
        final int[] tmp = null;
        assertTrue(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_int_eptyAndNull() {
        final int[] tmp = new int[]{};
        assertFalse(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_int_empty() {
        assertTrue(ArrayUtil.equals(new int[]{}, new int[]{}, 0));
    }

    @Test
    void testEquals_int_same() {
        final int[] a = new int[]{0, 5, 4, 2, 5, 7};
        final int[] b = new int[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < a.length; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
    }

    @Test
    void testEquals_int_different() {
        final int[] a = new int[]{0, 5, 4, 2, 3, 7};
        final int[] b = new int[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < 4; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
        assertFalse(ArrayUtil.equals(a, b, 5));
    }

    @Test
    void testEquals_long_null() {
        final long[] tmp = null;
        assertTrue(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_long_eptyAndNull() {
        final long[] tmp = new long[]{};
        assertFalse(ArrayUtil.equals(tmp, null, 100));
    }

    @Test
    void testEquals_long_empty() {
        assertTrue(ArrayUtil.equals(new long[]{}, new long[]{}, 0));
    }

    @Test
    void testEquals_long_same() {
        final long[] a = new long[]{0, 5, 4, 2, 5, 7};
        final long[] b = new long[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < a.length; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
    }

    @Test
    void testEquals_long_different() {
        final long[] a = new long[]{0, 5, 4, 2, 3, 7};
        final long[] b = new long[]{0, 5, 4, 2, 5, 7};
        assertEquals(a.length, b.length);
        for (int i = 0; i < 4; i++) {
            assertTrue(ArrayUtil.equals(a, b, i));
        }
        assertFalse(ArrayUtil.equals(a, b, 5));
    }

    @Test
    void testPutLast_int_null() {
        int outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, null);
        assertNull(outArr);
    }

    @Test
    void testPutLast_int_putSingle() {
        final int[] arrayOfInputs = {0, 5, 32, 5, 23, 3, 54, -12, 432, 320, -15, -1, 0, 32};
        int outArr[] = null;
        for (int i = 0; i < arrayOfInputs.length; i++) {
            outArr = ArrayUtil.putLast(outArr, arrayOfInputs[i]);
            assertNotNull(outArr);
            assertEquals(i + 1, outArr.length);
            assertTrue(ArrayUtil.equals(arrayOfInputs, outArr, outArr.length));
        }
    }

    @Test
    void testPutLast_int_putArray() {
        final int[] arrayOfInputs = {0, 5, 3, 5, 23, 32, 54, -12, 432, 320, -15, -1, 0, 32};
        int outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, arrayOfInputs);
        assertNotNull(outArr);
        assertEquals(arrayOfInputs, outArr);
    }

    @Test
    void testPutLast_int_putNull() {
        final int[] array = {0, 1};
        final int[] outArray = ArrayUtil.putLast(array, null);
        assertNotNull(outArray);
        assertEquals(array, outArray);
    }

    @Test
    void testPutLast_int_connectArray() {
        final int[] array = {0, 1};
        final int[] inArray = {2, 3};
        final int[] expected = {0, 1, 2, 3};
        final int[] outArray = ArrayUtil.putLast(array, inArray);
        assertNotNull(outArray);
        assertArrayEquals(expected, outArray);
    }

    @Test
    void testPutLast_long_null() {
        long outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, null);
        assertNull(outArr);
    }

    @Test
    void testPutLast_long_putSingle() {
        final long[] arrayOfInputs = {0L, 5L, 32L, 5L, 23L, 3L, 54L, -12L, 432, 320, -15L, -1, 0, 32};
        long outArr[] = null;
        for (int i = 0; i < arrayOfInputs.length; i++) {
            outArr = ArrayUtil.putLast(outArr, arrayOfInputs[i]);
            assertNotNull(outArr);
            assertEquals(i + 1, outArr.length);
            assertTrue(ArrayUtil.equals(arrayOfInputs, outArr, outArr.length));
        }
    }

    @Test
    void testPutLast_long_putArray() {
        final long[] arrayOfInputs = {0, 5, 3L, 5L, 23L, 32L, 54, -12, 432, 320, -15L, -1, 0, 32};
        long outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, arrayOfInputs);
        assertNotNull(outArr);
        assertEquals(arrayOfInputs, outArr);
    }

    @Test
    void testPutLast_long_putNull() {
        final long[] array = {0, 1};
        final long[] outArray = ArrayUtil.putLast(array, null);
        assertNotNull(outArray);
        assertEquals(array, outArray);
    }

    @Test
    void testPutLast_long_connectArray() {
        final long[] array = {0L, 1L};
        final long[] inArray = {2L, 3L};
        final long[] expected = {0L, 1L, 2L, 3L};
        final long[] outArray = ArrayUtil.putLast(array, inArray);
        assertNotNull(outArray);
        assertArrayEquals(expected, outArray);
    }

    @Test
    void testPutLast_float_null() {
        float outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, null);
        assertNull(outArr);
    }

    @Test
    void testPutLast_float_putSingle() {
        final float[] arrayOfInputs = {0, 5, 3.2f, 5, 2.3f, 3.2f, 54, -12, 432, 320, -1.5f, -1, 0, 32};
        float outArr[] = null;
        for (int i = 0; i < arrayOfInputs.length; i++) {
            outArr = ArrayUtil.putLast(outArr, arrayOfInputs[i]);
            assertNotNull(outArr);
            assertEquals(i + 1, outArr.length);
            assertTrue(ArrayUtil.equals(arrayOfInputs, outArr, outArr.length));
        }
    }

    @Test
    void testPutLast_float_putArray() {
        final float[] arrayOfInputs = {0, 5, 3.2f, 5, 2.3f, 3.2f, 54, -12, 432, 320, -1.5f, -1, 0, 32};
        float outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, arrayOfInputs);
        assertNotNull(outArr);
        assertEquals(arrayOfInputs, outArr);
    }

    @Test
    void testPutLast_float_putNull() {
        final float[] array = {0.0f, 1.0f};
        final float[] outArray = ArrayUtil.putLast(array, null);
        assertNotNull(outArray);
        assertEquals(array, outArray);
    }

    @Test
    void testPutLast_float_connectArray() {
        final float[] array = {0.0f, 1.0f};
        final float[] inArray = {2.0f, 3.0f};
        final float[] expected = {0.0f, 1.0f, 2.0f, 3.0f};
        final float[] outArray = ArrayUtil.putLast(array, inArray);
        assertNotNull(outArray);
        assertArrayEquals(expected, outArray);
    }

    @Test
    void testPutLast_double_null() {
        double outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, null);
        assertNull(outArr);
    }

    @Test
    void testPutLast_double_putSingle() {
        final double[] arrayOfInputs = {0, 5, 3.2, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
        double outArr[] = null;
        for (int i = 0; i < arrayOfInputs.length; i++) {
            outArr = ArrayUtil.putLast(outArr, arrayOfInputs[i]);
            assertNotNull(outArr);
            assertEquals(i + 1, outArr.length);
            assertTrue(ArrayUtil.equals(arrayOfInputs, outArr, outArr.length));
        }
    }

    @Test
    void testPutLast_double_putArray() {
        final double[] arrayOfInputs = {0, 5, 3.2, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
        double outArr[] = null;
        outArr = ArrayUtil.putLast(outArr, arrayOfInputs);
        assertNotNull(outArr);
        assertEquals(arrayOfInputs, outArr);
    }

    @Test
    void testPutLast_double_putNull() {
        final double[] array = {0.0, 1.0};
        final double[] outArray = ArrayUtil.putLast(array, null);
        assertNotNull(outArray);
        assertEquals(array, outArray);
    }

    @Test
    void testPutLast_double_connectArray() {
        final double[] array = {0.0, 1.0};
        final double[] inArray = {2.0, 3.0};
        final double[] expected = {0.0, 1.0, 2.0, 3.0};
        final double[] outArray = ArrayUtil.putLast(array, inArray);
        assertNotNull(outArray);
        assertArrayEquals(expected, outArray);
    }

    @Test
    void testRevers_int() {
        final int[] array = {0, 5, 3, 5, 23, 32, 54, -12, 432, 320, -1, -1, 0, 32};
        final int[] expectedArray = {32, 0, -1, -1, 320, 432, -12, 54, 32, 23, 5, 3, 5, 0};
        assertNotNull(ArrayUtil.reverse(array));
        assertArrayEquals(expectedArray, array);
    }

    @Test
    void testConstPutFirst_int() {
        final int length;
        final int[] putArr = {0, 5, -1, 23, 1, -200};
        final int[][] expectedArray = {
                {0, 0, -4, 3},
                {5, 0, 0, -4},
                {-1, 5, 0, 0},
                {23, -1, 5, 0},
                {1, 23, -1, 5},
                {-200, 1, 23, -1}
        };
        final int[] outArr = {0, -4, 3, -2};
        length = outArr.length;

        for (int i = 0; i < putArr.length; i++) {
            ArrayUtil.constPutFirst(outArr, putArr[i]);
            assertEquals(length, outArr.length);
            assertArrayEquals(expectedArray[i], outArr);
        }
    }

    @Test
    void testEquals_intArray_null() {
        assertTrue(ArrayUtil.equals(null, (int[]) null, 100));
    }

}
