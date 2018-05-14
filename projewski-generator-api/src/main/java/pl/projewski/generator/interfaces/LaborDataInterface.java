package pl.projewski.generator.interfaces;

/**
 * The interface to operate on set of data. It process incoming data, calculates and gives results.
 */
public interface LaborDataInterface extends ParameterInterface {
    /**
     * Set the input data to process.
     *
     * @param data the input data
     */
    void setInputData(NumberInterface data);

    /**
     * Get the calculated data.
     *
     * @param data the container for calculated data
     * @return false, if it's impossible to generate data
     */
    boolean getOutputData(NumberInterface data);
}
