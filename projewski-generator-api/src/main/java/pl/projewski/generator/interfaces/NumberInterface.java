package pl.projewski.generator.interfaces;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;

/**
 * The interface to manage numbers store of different types.
 */
public interface NumberInterface {
    /**
     * Get the type of numbers.
     *
     * @return the type of numbers
     */
    ClassEnumerator getStoreClass();

    /**
     * Set type of numbers, which will be hold.
     *
     * @param c the type of numbers
     */
    void setStoreClass(ClassEnumerator c);

    /**
     * Get the new instance of the number reader.
     *
     * @return the number reader
     */
    NumberReader getNumberReader();

    /**
     * Get the instance of the number writer - only one is possible.
     *
     * @return the number writer.
     */
    NumberWriter getNumberWriter();

    /**
     * Get the size of stored numbers.
     *
     * @return the size
     */
    int getSize();

    /**
     * Set the size of stored numbers.
     *
     * @param size the size
     */
    void setSize(int size);

    /**
     * Get the source of numbers. In most case it means, which object generate those numbers.
     *
     * @return the source
     */
    ParameterInterface getDataSource();

    /**
     * Set the source of numbers.
     *
     * @param dataSource the source
     */
    void setDataSource(ParameterInterface dataSource);
}
