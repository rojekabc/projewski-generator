package pl.projewski.generator.tools;

public class VectorDouble {

    private double[] data = null;

    public VectorDouble() {
    }

    public VectorDouble(final double[] arr) {
        add(arr);
    }

    public VectorDouble(final String inp) {
        this.add(inp);
    }

    public void add(final double n) {
        data = ArrayUtil.putLast(data, n);
    }

    public void add(final double[] arr) {
        data = ArrayUtil.putLast(data, arr);
    }

    public void add(final Double n) {
        data = ArrayUtil.putLast(data, n.doubleValue());
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
                final double x = Convert.tryToDouble(value);
                data = ArrayUtil.putLast(data, x);
            }
        }

    }

    public int size() {
        if (data != null) {
            return data.length;
        }
        return 0;
    }

    public double get(final int i) {
        return data[i];
    }

    public double[] toArray() {
        return data;
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
