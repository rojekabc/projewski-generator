package pl.projewski.generator.tools;

public class VectorLong {

    private long[] data = null;

    public VectorLong() {
    }

    public VectorLong(final long[] a) {
        data = ArrayUtil.putLast(data, a);
    }

    public VectorLong(final String inp) {
        this.add(inp);
    }

    public void add(final long n) {
        data = ArrayUtil.putLast(data, n);
    }

    public void add(final Long n) {
        data = ArrayUtil.putLast(data, n);
    }

    /*
     * W Stringu moze wystepowac pare wartosci
     * Wartosci te powinny byc rozdzielone znakami spacji
     * W razie problemow z konwesja zostaja porzucone wyjatki
     */
    public void add(String s) {
        String c = null;
        int lastpos = 0;
        int pos = 0;
        s = s.trim();
        while ((pos = s.indexOf(" ", lastpos)) != -1) {
            c = s.substring(lastpos, pos).trim();
            if (c.length() != 0) {
                data = ArrayUtil.putLast(data, Convert.tryToLong(c));
            }
            lastpos = pos;
            lastpos++;
        }
        c = s.substring(lastpos).trim();
        if (c.length() != 0) {
            data = ArrayUtil.putLast(data, Convert.tryToLong(c));
        }
    }

    long[] toArray() {
        return data;
    }

    public int size() {
        return data.length;
    }

    public long get(final int i) {
        return data[i];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
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
