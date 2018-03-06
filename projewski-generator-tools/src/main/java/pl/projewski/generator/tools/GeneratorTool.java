package pl.projewski.generator.tools;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;

public class GeneratorTool {
    public static <T> T generate(final GeneratorInterface gi, final Class<T> type) throws GeneratorException {
        if (type == Long.class) {
            return (T) Long.valueOf(gi.nextLong());
        } else if (type == Double.TYPE) {
            return (T) Double.valueOf(gi.nextDouble());
        } else if (type == Integer.TYPE) {
            return (T) Integer.valueOf(gi.nextInt());
        } else if (type == Float.TYPE) {
            return (T) Float.valueOf(gi.nextFloat());
        } else {
            return null;
        }
    }
}
