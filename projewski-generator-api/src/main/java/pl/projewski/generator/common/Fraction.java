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
    private long _a, _b, _c;

    public Fraction(final Fraction f) {
        _a = f._a;
        _b = f._b;
        _c = f._c;
    }

    public Fraction(long b, long c) {
        final long gcd;
        if (c < 0) {
            b = -b;
            c = -c;
        }
        if (b == 0) {
            _b = b;
            _c = 0;
            return;
        }
        if (b >= c || -b >= c) {
            _a = b / c;
            if (b >= 0) {
                b %= c;
            } else {
                b = -b;
                b %= c;
                b = -b;
            }
        } else {
            _a = 0;
        }
        gcd = gcdLong(b, c);
        if (gcd > 1) {
            b /= gcd;
            c /= gcd;
        }
        _b = b;
        _c = c;
    }

    public Fraction(final long a, long b, long c) {
        final long gcd;
        _a = a;
        if (c < 0) {
            b = -b;
            c = -c;
        }
        if (b == 0) {
            _b = b;
            _c = 0;
            return;
        }
        if (b >= c || -b >= c) {
            _a += b / c;
            if (b >= 0) {
                b %= c;
            } else {
                b = -b;
                b %= c;
                b = -b;
            }
        }
        gcd = gcdLong(b, c);
        if (gcd > 1) {
            b /= gcd;
            c /= gcd;
        }
        _b = b;
        _c = c;
    }

    private long gcdLong(long a, long b) {
        if ((a == 0) || (b == 0)) {
            return 0; // TODO: Exception
        }
        if (a < 0) {
            a = -a;
        }
        if (b < 0) {
            b = -b;
        }
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

    public String getString() {
        String ret = "";
        if (_a != 0) {
            ret += _a;
        }
        if (_b != 0) {
            if (_b > 0) {
                if (_a != 0) {
                    ret += "+" + _b + "/" + _c;
                } else {
                    ret += _b + "/" + _c;
                }
            } else {
                ret += _b + "/" + _c;
            }

        }
        return ret;
    }

    @Override
    public String toString() {
        return getString();
    }

    public float getFloat() {
        float ret;
        ret = _a;
        if (_b != 0 || _c != 0) {
            ret += (float) _b / (float) _c;
        }
        return ret;
    }

    public double getDouble() {
        double ret;
        ret = _a;
        if (_b != 0 || _c != 0) {
            ret += (double) _b / (double) _c;
        }
        return ret;
    }

    public void add(final Fraction f) {
        long gcd;
        _a += f._a;
        if (f._b != 0) {
            if (_b != 0) {
                gcd = gcdLong(_c, f._c);
                _b = _b * (f._c / gcd) + f._b * (_c / gcd);
                _c *= f._c / gcd;
                if ((_b >= _c) || (-_b >= _c)) {
                    _a += _b / _c;
                    _b %= _c;
                }
            } else {
                _b = f._b;
                _c = f._c;
            }
        }
        if (_a > 0 && _b < 0) {
            _a--;
            _b += _c;
        } else if (_a < 0 && _b > 0) {
            _a++;
            _b -= _c;
        }

        gcd = gcdLong(_b, _c);
        if (gcd > 1) {
            _b /= gcd;
            _c /= gcd;
        }
    }

    public void sub(final Fraction f) {
        f._a = -f._a;
        f._b = -f._b;
        add(f);
        f._a = -f._a;
        f._b = -f._b;
    }

    public void mul(final Fraction f) {
        final Fraction f2 = new Fraction(_a * f._b, f._c);
        final Fraction f3 = new Fraction(_b * f._b, _c * f._c);
        _a *= f._a;
        _b *= f._a;
        if (_b != 0) {
            if ((_b >= _c) || (-_b >= _c)) {
                _a += _b / _c;
                _b %= _c;
            }
        }
        final long gcd = gcdLong(_b, _c);
        if (gcd > 1) {
            _b /= gcd;
            _c /= gcd;
        }
        if (f._b != 0) {
            add(f2);
        }
        add(f3);
    }

    public void div(final Fraction f) {
        long tc1;
        final long tc2;
        if (f._a == 0 && f._b == 0 && f._c == 0) {
            tc1 = 1;
            tc1 /= 0;
        }
        if (_b == 0) {
            tc1 = 1;
        } else {
            tc1 = _c;
        }
        if (f._b == 0) {
            tc2 = 1;
        } else {
            tc2 = f._c;
        }
        final Fraction tmp = new Fraction(tc2 * (_a * tc1 + _b), tc1 * (f._a * tc2 + f._b));
        _a = tmp._a;
        _b = tmp._b;
        _c = tmp._c;
    }
}
