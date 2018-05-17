package pl.projewski.generator.common;

/*
 * Ułamek postaci: a + b/c
 * Założenie: b < c
 * c jest > 0
 * jeśli c = 0 to będzie ArithmeticException div 0
 *
 * Nie zaleca się stosowania operacji wielokrotnie, które mogą powodować znaczące przyrosty
 * liczb stanowiących składowe liczników/mianowników, gdyż może to doprowadzić do przepełnienia
 * i generowania błędnych wyników.
 * TODO: W celu zniwelowania tego efektu, zostanie dopisana funkcja, która będzie pozwalała
 * na generowanie liczby z jej reprezentacji zmiennoprzecinkowej z uwzględnieniem ucięcia
 * do określonej liczby miejsc po przecinku, co oczywiście w pewien sposób przyczyni się do
 * zatracenia perłnej wartości licznowej
 * TODO: Rozważenie wprowadzenia klasy pozwalającej na wykonywanie operacji na bardzo dużych
 * liczbach - typu BigInteger
 */
public class Fraction {
    public final static Fraction NEGATIVE_INFINITY = new Fraction(0, -1, 0);
    public final static Fraction POSITIVE_INFINITY = new Fraction(0, 1, 0);
    public final static Fraction NAN = new Fraction(0, 0, 0);

    private long a, b, c;

    public Fraction(final Fraction f) {
        this.a = f.a;
        this.b = f.b;
        this.c = f.c;
    }

    public Fraction(final long a) {
        this(a, 0, 1);
    }

    public Fraction(final long b, final long c) {
        this(0, b, c);
    }

    public Fraction(final long a, long b, long c) {
        final long gcd;
        this.a = a;
        if (b < 0 && c < 0) {
            this.b = -b;
            this.c = -c;
        }
        if (b == 0) {
            this.b = b;
            this.c = c;
            return;
        }
        if (c != 0) {
            if (b >= c || -b >= c) {
                this.a += b / c;
                b = Math.abs(b) % c * Long.signum(b);
            }
            gcd = gcdLong(b, c);
            if (gcd > 1) {
                b /= gcd;
                c /= gcd;
            }
        }
        this.b = b;
        this.c = c;
    }

    private long gcdLong(long a, long b) {
        if ((a == 0) || (b == 0)) {
            return 0; // TODO: Exception
        }
        a = Math.abs(a);
        b = Math.abs(b);
        while (a != b) {
            if (a > b) {
                if (a % b == 0) {
                    return b;
                }
                a -= (a / b) * b;
            } else {
                if (b % a == 0) {
                    return a;
                }
                b -= (b / a) * a;
            }
        }
        return a;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        // put a
        if (a != 0) {
            sb.append(a);
        }
        // put sign
        final int signB = Long.signum(b);
        final int signC = Long.signum(c);
        // - sign plus
        if (a != 0 && (signB == -1 && signC == -1) || (signB >= 0 && signC >= 0)) {
            sb.append('+');
        } else {
            sb.append('-');
        }

        // value b/c
        sb.append(Math.abs(b));
        sb.append('/');
        sb.append(Math.abs(c));
        return sb.toString();
    }

    public float getFloat() {
        float ret;
        ret = (float) b / (float) c;
        if (a != 0) {
            ret += a;
        }
        return ret;
    }

    public double getDouble() {
        double ret;
        ret = (double) b / (double) c;
        if (a != 0) {
            ret += a;
        }
        return ret;
    }

    FractionState getState() {
        if (c == 0) {
            final int signum = Long.signum(b);
            switch (signum) {
            case -1:
                return FractionState.NEGATIVE_INFINITY;
            case 1:
                return FractionState.POSITIVE_INFINITY;
            case 0:
                return FractionState.NAN;
            default:
                break;
            }
        }
        return FractionState.NUMBER;
    }

    int signum() {
        switch (getState()) {
        case NUMBER:
            // means c is not 0
            if (a != 0) {
                return Long.signum(a);
            } else {
                final int signumB = Long.signum(b);
                final int signumC = Long.signum(c);
                if (signumB != 0) {
                    return signumB * signumC;
                } else {
                    return signumC;
                }
            }
        default:
        case NAN:
            return 0;
        case POSITIVE_INFINITY:
            return 1;
        case NEGATIVE_INFINITY:
            return -1;
        }
    }

    public void add(final Fraction f) {
        long gcd;
        final FractionState state = getState();
        final FractionState fState = f.getState();

        // operation on states
        if (state != FractionState.NUMBER || fState != FractionState.NUMBER) {
            switch (state) {
            case NUMBER:
                // set state from f object
                b = f.b;
                c = f.c;
                break;
            case NAN:
                return;
            case POSITIVE_INFINITY:
                if (fState == FractionState.NEGATIVE_INFINITY || fState == FractionState.NAN) {
                    // set state as NaN
                    b = 0;
                    c = 0;
                }
                break;
            case NEGATIVE_INFINITY:
                if (fState == FractionState.POSITIVE_INFINITY || fState == FractionState.NAN) {
                    // set state as NaN
                    b = 0;
                    c = 0;
                }
                break;
            }
            return;
        }

        // there're regular numbers
        a += f.a;
        if (f.b != 0) {
            if (b != 0) {
                gcd = gcdLong(c, f.c);
                b = b * (f.c / gcd) + f.b * (c / gcd);
                c *= f.c / gcd;
                if ((b >= c) || (-b >= c)) {
                    a += b / c;
                    b %= c;
                }
            } else {
                b = f.b;
                c = f.c;
            }
        }
        if (a > 0 && b < 0) {
            a--;
            b += c;
        } else if (a < 0 && b > 0) {
            a++;
            b -= c;
        }

        gcd = gcdLong(b, c);
        if (gcd > 1) {
            b /= gcd;
            c /= gcd;
        }
    }

    public void sub(final Fraction f) {
        f.a = -f.a;
        f.b = -f.b;
        add(f);
        f.a = -f.a;
        f.b = -f.b;
    }

    public void mul(final Fraction f) {
        final Fraction f2 = new Fraction(a * f.b, f.c);
        final Fraction f3 = new Fraction(b * f.b, c * f.c);
        a *= f.a;
        b *= f.a;
        if (b != 0) {
            if ((b >= c) || (-b >= c)) {
                a += b / c;
                b %= c;
            }
        }
        final long gcd = gcdLong(b, c);
        if (gcd > 1) {
            b /= gcd;
            c /= gcd;
        }
        if (f.b != 0) {
            add(f2);
        }
        add(f3);
    }

    public void div(final Fraction f) {
        // check states
        final FractionState fState = f.getState();
        final FractionState state = getState();
        if (fState != FractionState.NUMBER || state != FractionState.NUMBER) {
            switch (state) {
            case NUMBER:
                switch (f.getState()) {
                case NAN:
                    // set as NaN
                    a = 0;
                    b = 0;
                    c = 0;
                    break;
                case POSITIVE_INFINITY:
                    a = 0;
                    b = signum();
                    c = 0;
                    break;
                case NEGATIVE_INFINITY:
                    a = 0;
                    b = -signum();
                    c = 0;
                    break;
                }
                break;
            case NAN:
                // stay as it is
                break;
            case POSITIVE_INFINITY:
                switch (f.getState()) {
                case NAN:
                case POSITIVE_INFINITY:
                case NEGATIVE_INFINITY:
                    // set as NaN
                    a = 0;
                    b = 0;
                    c = 0;
                    break;
                case NUMBER:
                    // change sign if needed
                    b *= f.signum();
                    break;
                }
                break;
            case NEGATIVE_INFINITY:
                switch (f.getState()) {
                case NAN:
                case POSITIVE_INFINITY:
                case NEGATIVE_INFINITY:
                    // set as NaN
                    a = 0;
                    b = 0;
                    c = 0;
                    break;
                case NUMBER:
                    // change sign if needed
                    b *= f.signum();
                    break;
                }
                break;
            }
            return;
        }

        // regular numbers
        final long tc1;
        final long tc2;
        if (b == 0) {
            tc1 = 1;
        } else {
            tc1 = c;
        }
        if (f.b == 0) {
            tc2 = 1;
        } else {
            tc2 = f.c;
        }
        final Fraction tmp = new Fraction(tc2 * (a * tc1 + b), tc1 * (f.a * tc2 + f.b));
        a = tmp.a;
        b = tmp.b;
        c = tmp.c;
    }

    enum FractionState {
        NUMBER,
        NAN,
        POSITIVE_INFINITY,
        NEGATIVE_INFINITY;
    }
}
