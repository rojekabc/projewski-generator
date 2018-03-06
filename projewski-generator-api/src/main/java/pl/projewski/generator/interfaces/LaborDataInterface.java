package pl.projewski.generator.interfaces;

import pl.projewski.generator.exceptions.LaborDataException;

/**
 * The interface to operate on set of data. It process incoming data, calculates and gives results.
 */
public interface LaborDataInterface extends ParameterInterface {
    /**
     * Set the input data to process.
     *
     * @param data the input data
     * @throws LaborDataException
     */
    void setInputData(NumberInterface data) throws LaborDataException;

    /**
     * Get the calculated data.
     *
     * @param data the container for calculated data
     * @return false, if it's impossible to generate data
     * @throws LaborDataException
     */
    boolean getOutputData(NumberInterface data) throws LaborDataException;
}
