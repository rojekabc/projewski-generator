package pl.projewski.generator.common;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The main problem is comparing two doubles from almost same operation, but calculated in a little different way
 * (another order of operations). So to compare two double values, it is used subtraction operation and check on
 * established level of tolerance.
 */
public class FractionTest {
    final List<Pair<Fraction, Fraction>> operations = Arrays.asList(
            new ImmutablePair<>(new Fraction(1, 2), new Fraction(1, 2)),
            new ImmutablePair<>(new Fraction(1, 2), new Fraction(2)),
            new ImmutablePair<>(new Fraction(2), new Fraction(1, 2)),
            new ImmutablePair<>(new Fraction(1, 1, 2), new Fraction(-1, 2)),
            new ImmutablePair<>(new Fraction(3, 1, 3), new Fraction(2, 1, 6)),
            new ImmutablePair<>(new Fraction(-3, -1, 3), new Fraction(2, 1, 6)),

            new ImmutablePair<>(Fraction.NAN, new Fraction(0, 1, 2)),
            new ImmutablePair<>(Fraction.NAN, new Fraction(0, -1, 2)),
            new ImmutablePair<>(Fraction.NAN, new Fraction(0)),
            new ImmutablePair<>(Fraction.NAN, Fraction.POSITIVE_INFINITY),
            new ImmutablePair<>(Fraction.NAN, Fraction.NEGATIVE_INFINITY),
            new ImmutablePair<>(Fraction.NAN, Fraction.NAN),

            new ImmutablePair<>(Fraction.NEGATIVE_INFINITY, new Fraction(0, 1, 2)),
            new ImmutablePair<>(Fraction.NEGATIVE_INFINITY, new Fraction(0, -1, 2)),
            new ImmutablePair<>(Fraction.NEGATIVE_INFINITY, new Fraction(0)),
            new ImmutablePair<>(Fraction.NEGATIVE_INFINITY, Fraction.NEGATIVE_INFINITY),
            new ImmutablePair<>(Fraction.NEGATIVE_INFINITY, Fraction.POSITIVE_INFINITY),
            new ImmutablePair<>(Fraction.NEGATIVE_INFINITY, Fraction.NAN),

            new ImmutablePair<>(Fraction.POSITIVE_INFINITY, new Fraction(0, 1, 2)),
            new ImmutablePair<>(Fraction.POSITIVE_INFINITY, new Fraction(0, -1, 2)),
            new ImmutablePair<>(Fraction.POSITIVE_INFINITY, new Fraction(0)),
            new ImmutablePair<>(Fraction.POSITIVE_INFINITY, Fraction.NEGATIVE_INFINITY),
            new ImmutablePair<>(Fraction.POSITIVE_INFINITY, Fraction.POSITIVE_INFINITY),
            new ImmutablePair<>(Fraction.POSITIVE_INFINITY, Fraction.NAN),

            new ImmutablePair<>(new Fraction(0), new Fraction(0, 1, 2)),
            new ImmutablePair<>(new Fraction(0), new Fraction(0, -1, 2)),
            new ImmutablePair<>(new Fraction(0), new Fraction(0)),
            new ImmutablePair<>(new Fraction(0), Fraction.NEGATIVE_INFINITY),
            new ImmutablePair<>(new Fraction(0), Fraction.POSITIVE_INFINITY),
            new ImmutablePair<>(new Fraction(0), Fraction.NAN),

            new ImmutablePair<>(new Fraction(0, 3, 2), new Fraction(0, 1, 2)),
            new ImmutablePair<>(new Fraction(0, 3, 2), new Fraction(0, -1, 2)),
            new ImmutablePair<>(new Fraction(0, 3, 2), new Fraction(0)),
            new ImmutablePair<>(new Fraction(0, 3, 2), Fraction.NEGATIVE_INFINITY),
            new ImmutablePair<>(new Fraction(0, 3, 2), Fraction.POSITIVE_INFINITY),
            new ImmutablePair<>(new Fraction(0, 3, 2), Fraction.NAN),

            new ImmutablePair<>(new Fraction(0, -3, 2), new Fraction(0, 1, 2)),
            new ImmutablePair<>(new Fraction(0, -3, 2), new Fraction(0, -1, 2)),
            new ImmutablePair<>(new Fraction(0, -3, 2), new Fraction(0)),
            new ImmutablePair<>(new Fraction(0, -3, 2), Fraction.NEGATIVE_INFINITY),
            new ImmutablePair<>(new Fraction(0, -3, 2), Fraction.POSITIVE_INFINITY),
            new ImmutablePair<>(new Fraction(0, -3, 2), Fraction.NAN)
    );

    @Test
    void testDiv() {
        operations.forEach(pair -> {
            final Fraction left = pair.getLeft();
            final Fraction right = pair.getRight();
            final double expected = left.getDouble() / right.getDouble();
            final String errorMessage = "Error for sample " + left + " divide by " + right;
            left.div(right);
            if (left.getState() == Fraction.FractionState.NUMBER) {
                assertTrue(Math.abs(expected - left.getDouble()) < 0.0000000001, errorMessage);
            } else {
                assertEquals(expected, left.getDouble(), errorMessage);
            }
        });

    }

    @Test
    void testMul() {
        operations.forEach(pair -> {
            final Fraction left = pair.getLeft();
            final Fraction right = pair.getRight();
            final double expected = left.getDouble() * right.getDouble();
            final String errorMessage = "Error for sample " + left + " multiple by " + right;
            left.mul(right);
            if (left.getState() == Fraction.FractionState.NUMBER) {
                assertTrue(Math.abs(expected - left.getDouble()) < 0.0000000001, errorMessage);
            } else {
                assertEquals(expected, left.getDouble(), errorMessage);
            }
        });
    }

    @Test
    void testSub() {
        operations.forEach(pair -> {
            final Fraction left = pair.getLeft();
            final Fraction right = pair.getRight();
            final double expected = left.getDouble() - right.getDouble();
            final String errorMessage = "Error for sample " + left + " sub " + right;
            left.sub(right);
            if (left.getState() == Fraction.FractionState.NUMBER) {
                assertTrue(Math.abs(expected - left.getDouble()) < 0.0000000001, errorMessage);
            } else {
                assertEquals(expected, left.getDouble(), errorMessage);
            }
        });
    }

    @Test
    void testAdd() {
        operations.forEach(pair -> {
            final Fraction left = pair.getLeft();
            final Fraction right = pair.getRight();
            final double expected = left.getDouble() + right.getDouble();
            final String errorMessage = "Error for sample " + left + " add " + right;
            left.add(right);
            if (left.getState() == Fraction.FractionState.NUMBER) {
                assertTrue(Math.abs(expected - left.getDouble()) < 0.0000000001, errorMessage);
            } else {
                assertEquals(expected, left.getDouble(), errorMessage);
            }
        });
    }

    @Test
    void testConstruct_a_and_b_to_c() {
        final long[] a = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, 1, 1, 1, 0, 0, 0};
        final long[] b = {1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0, -1, -1, 1, -1, -1, 1, 0, 1, -1};
        final long[] c = {2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10, -2, 2, -2, -2, 2, -2, 0, 0, 0};

        assertEquals(b.length, c.length);
        assertEquals(b.length, a.length);

        for (int i = 0; i < b.length; i++) {
            double expected = (double) b[i] / (double) c[i];
            expected += a[i];
            final Fraction f = new Fraction(a[i], b[i], c[i]);
            assertEquals(expected, f.getDouble(), "Error for sample " + i + " for " + a[i] + "+" + b[i] + "/" + c[i]);
        }
    }

    @Test
    void testConstruct_b_to_c() {
        final long[] b = {1, 1, 1, 1, 1, 2, 9, 2, 1, 9, 1, 18, 21, 22, 1, 0, -1, -1, 1, -1, 0};
        final long[] c = {2, 3, 4, 5, 6, 4, 12, 3, 11, 18, 101, 9, 9, 9, 0, 10, -2, 2, -2, 0, 0};

        assertEquals(b.length, c.length);

        for (int i = 0; i < b.length; i++) {
            final double expected = (double) b[i] / (double) c[i];
            final Fraction f = new Fraction(b[i], c[i]);
            assertEquals(expected, f.getDouble(), "Error for sample " + i + " for " + b[i] + "/" + c[i]);
        }
    }
}
