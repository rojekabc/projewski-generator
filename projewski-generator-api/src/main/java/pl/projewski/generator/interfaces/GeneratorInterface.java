package pl.projewski.generator.interfaces;

import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;

/**
 * General random number generator interface.
 */
public interface GeneratorInterface extends ParameterInterface {

    /**
     * Initialize the random number generator.
     *
     * @throws GeneratorException
     */
    void init() throws GeneratorException;

    /**
     * Reinitialize random number generator. This call allows to back generation from begin state.
     *
     * @throws GeneratorException
     */
    void reinit() throws GeneratorException;

    /**
     * Get next long value from the generator.
     *
     * @return the long value
     * @throws GeneratorException
     */
    long nextLong() throws GeneratorException;

    /**
     * Get next int value from the generator.
     *
     * @return the int value
     * @throws GeneratorException
     */
    int nextInt() throws GeneratorException;

    /**
     * Get the next double value.
     *
     * @return the next double
     * @throws GeneratorException
     */
    double nextDouble() throws GeneratorException;

    /**
     * Get the next float value.
     *
     * @return the next float
     * @throws GeneratorException
     */
    float nextFloat() throws GeneratorException;

    /**
     * Fill the array with values from the generator.
     *
     * @param table the array
     * @throws GeneratorException
     */
    void rawFill(Object table) throws GeneratorException;

    /**
     * Put generated numbers into the writer.
     *
     * @param writer The file writer
     * @param c      the type of generated element
     * @param num    the number of generated elements
     * @throws GeneratorException
     */
    void rawFill(NumberWriter writer, ClassEnumerator c, int num) throws GeneratorException;

};
