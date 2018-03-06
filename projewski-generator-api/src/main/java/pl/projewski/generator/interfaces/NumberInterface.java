package pl.projewski.generator.interfaces;

import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;

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
     * @throws NumberStoreException
     */
    void setStoreClass(ClassEnumerator c) throws NumberStoreException;

    /**
     * Get the new instance of the number reader.
     *
     * @return the number reader
     * @throws NumberStoreException
     */
    NumberReader getNumberReader() throws NumberStoreException;

    /**
     * Get the instance of the number writer - only one is possible.
     *
     * @return the number writer.
     * @throws NumberStoreException
     */
    NumberWriter getNumberWriter() throws NumberStoreException;

    /**
     * Get the size of stored numbers.
     *
     * @return the size
     * @throws NumberStoreException
     */
    int getSize() throws NumberStoreException;

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
     * @throws NumberStoreException
     */
    ParameterInterface getDataSource() throws NumberStoreException;

    /**
     * Set the source of numbers.
     *
     * @param dataSource the source
     * @throws NumberStoreException
     */
    void setDataSource(ParameterInterface dataSource) throws NumberStoreException;
}
