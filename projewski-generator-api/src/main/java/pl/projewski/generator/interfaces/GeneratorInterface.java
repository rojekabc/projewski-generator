package pl.projewski.generator.interfaces;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;

/**
 * General random number generator interface.
 */
public interface GeneratorInterface extends ParameterInterface {

    /**
     * Initialize the random number generator.
     */
    void init();

    /**
     * Reinitialize random number generator. This call allows to back generation from begin state.
     */
    void reinit();

    /**
     * Get next long value from the generator.
     *
     * @return the long value
     */
    long nextLong();

    /**
     * Get next int value from the generator.
     *
     * @return the int value
     */
    int nextInt();

    /**
     * Get the next double value.
     *
     * @return the next double
     */
    double nextDouble();

    /**
     * Get the next float value.
     *
     * @return the next float
     */
    float nextFloat();

    /**
     * Fill the array with values from the generator.
     *
     * @param table the array
     */
    void rawFill(Object table);

    /**
     * Put generated numbers into the writer.
     *
     * @param writer The file writer
     * @param c      the type of generated element
     * @param num    the number of generated elements
     */
    void rawFill(NumberWriter writer, ClassEnumerator c, int num);

}
