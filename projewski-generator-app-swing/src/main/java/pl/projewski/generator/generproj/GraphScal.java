package pl.projewski.generator.generproj;

import pl.projewski.generator.tools.Mysys;

public class GraphScal {
    private final Object _min;
    private final Object _max;
    private Object _jmp;

    public GraphScal(final int min, final int max) {
        if (min < 0) {
            _min = findNumScal(min, -1);
        } else {
            _min = findNumScal(min, 0);
        }
        if (max > 0) {
            _max = findNumScal(max, 1);
        } else {
            _max = findNumScal(max, 0);
        }
    }

    public GraphScal(final long min, final long max) {
        if (min < 0) {
            _min = findNumScal(min, -1);
        } else {
            _min = findNumScal(min, 0);
        }
        if (max > 0) {
            _max = findNumScal(max, 1);
        } else {
            _max = findNumScal(max, 0);
        }
    }

    public GraphScal(final float min, final float max) {
        if (min < 0) {
            _min = findNumScal(min, -1);
        } else {
            _min = findNumScal(min, 0);
        }
        if (max > 0) {
            _max = findNumScal(max, 1);
        } else {
            _max = findNumScal(max, 0);
        }
    }

    public GraphScal(final double min, final double max) {
        if (min < 0) {
            _min = findNumScal(min, -1);
        } else {
            _min = findNumScal(min, 0);
        }
        if (max > 0) {
            _max = findNumScal(max, 1);
        } else {
            _max = findNumScal(max, 0);
        }
    }

    private int findNumScal(final int num, final int chg) {
        return (int) findNumScal((long) num, chg);
    }

    private long findNumScal(long num, final int chg) {
        final long sav = num;
        long nten;
        long scal;
        // wyznacz liczbe cyfr - 1 w numerze
        if (num == 0) {
            nten = 0;
        } else {
            nten = (long) Math.floor(Math.log(Math.abs(num)) / Math.log(10));
        }
        // oblicz skale w 10^nten
        scal = 1;
        while (nten != 0) {
            scal *= 10;
            nten--;
        }
        // przystosuj liczbe do skali
        num /= scal;
        num *= scal;
        // dobierz ja tak, aby objela swym zasiegiem
        // wymagana wartosc
        if (chg > 0) {
            while (num < sav) {
                num += scal >> 1;
            }
        }
        if (chg < 0) {
            while (num > sav) {
                num -= scal >> 1;
            }
        }
        // zwroc ustalona wartosc
        return num;
    }

    private double findNumScal(final double num, final int chg) {
        if (Math.floor(Math.abs(num)) > 0) {
            return (double) findNumScal((long) num, chg);
        }
        if (num == 0.0) {
            return num;
        }
        Mysys.debugln("Warning: GraphScal.findNumScal double, not implemented all");
        return 0.0;
    }

    private float findNumScal(final float num, final int chg) {
        // tak duze, ze wieksze od jednosci
        if (Math.floor(Math.abs(num)) > 0) {
            return (float) findNumScal((long) num, chg);
        }
        // rowne zero
        if (num == 0.0f) {
            return num;
        }
        // TODO: powinno zostac uzyte skalowanie po precyzji
        Mysys.debugln("Warning: GraphScal.findNumScal float, not implemented all");
        return 0.0f;
    }

    public Object getMin() {
        return _min;
    }

    public Object getMax() {
        return _max;
    }

    public Object getJmp() {
        return _jmp;
    }
}
