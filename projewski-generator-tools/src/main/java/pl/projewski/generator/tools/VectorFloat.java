package pl.projewski.generator.tools;

public class VectorFloat {
    float[] data = null;

    public VectorFloat() {
    }

    public VectorFloat(final String inp) {
        this.add(inp);
    }

    public void add(final float n) {
        data = ArrayUtil.putLast(data, n);
    }

    public void add(final Float n) {
        data = ArrayUtil.putLast(data, n.floatValue());
    }

    /*
     * W Stringu moze wystepowac pare wartosci
     * Wartosci te powinny byc rozdzielone znakami spacji
     * W razie problemow z konwesja zostaja porzucone wyjatki
     */
    public void add(final String s) {
        if (s != null) {
            final String[] values = s.trim().split("[ \t\n\r]+");
            for (final String value : values) {
                final float x = Convert.tryToFloat(value);
                data = ArrayUtil.putLast(data, x);
            }
        }

    }

    public int size() {
        return data.length;
    }

    public float get(final int i) {
        return data[i];
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        int i;
        if (data != null) {
            for (i = 0; i < data.length; i++) {
                sb.append(data[i]);
                if (i != data.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}
