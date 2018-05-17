package pl.projewski.generator.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorLongTest {
    @Test
    void testAdd() {
        final VectorLong vl = new VectorLong();
        vl.add(" 32   323 535 33\t \t41  \n   13\t ");
        vl.add(23);
        vl.add(Long.valueOf(120));
        assertEquals(8, vl.size());
        assertArrayEquals(new long[]{32, 323, 535, 33, 41, 13, 23, 120}, vl.toArray());
    }
}
