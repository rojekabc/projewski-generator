package tests;

import pl.projewski.generator.tools.VectorDouble;
import pl.projewski.generator.tools.VectorFloat;
import pl.projewski.generator.tools.VectorInteger;
import pl.projewski.generator.tools.VectorLong;

/*
 * Test na VectorInteger/Long/Double/Float
 */
public class Test5 {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final VectorInteger vi = new VectorInteger();
        final VectorLong vl = new VectorLong();
        final VectorFloat vf = new VectorFloat();
        VectorDouble vd = new VectorDouble();
        vi.add(5);
        vi.add(Integer.valueOf(10));
        vi.add("   3  4 5      12   ");
        System.out.println(vi.toString());
        vl.add(" 32   323 535 33");
        vl.add(23);
        vl.add(Long.valueOf(120));
        System.out.println(vl.toString());
        vf.add(5.0f);
        vf.add(Float.valueOf(10.0f));
        vf.add("   3.0  4.0 5.2      12.322   ");
        System.out.println(vf.toString());
        vd.add(5.234);
        vd.add(Double.valueOf(10.32));
        vd.add("   3.1  4.32 5      12.123423   ");
        System.out.println(vd.toString());
        vd = new VectorDouble("");
        System.out.println(vd.size());
        vd.add("");
        System.out.println(vd.size());
    }

}
