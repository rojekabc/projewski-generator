package pl.projewski.generator.tools;

public class VectorInteger {
    int[] data = null;

    public VectorInteger() {
    }

    public VectorInteger(final String inp) {
        this.add(inp);
    }

    public void add(final int n) {
        data = ArrayUtil.putLast(data, n);
    }

    public void add(final Integer n) {
        data = ArrayUtil.putLast(data, n.intValue());
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
                final int x = Convert.tryToInt(value);
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

    public int get(final int i) {
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

    public int[] toArray() {
        return data;
    }
}
