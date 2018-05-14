package pl.projewski.generator.interfaces;

import java.util.List;
import java.util.Set;

/*
 * The interface for object parametrization.
 *
 * Also contains methods to receive basic description of parameter and object.
 */
public interface ParameterInterface {
    /**
     * Get the list of available parameters.
     *
     * @return the list of parameters
     */
    Set<String> listParameters();

    /**
     * Get allowed set of class for parameter.
     *
     * @param param name of parameter
     * @return set of allowed parameters
     */
    List getAllowedClass(String param);

    /**
     * Set parameter value
     *
     * @param param parameter name
     * @param value parameter value
     */
    void setParameter(String param, Object value);

    /**
     * Get value of parameter.
     *
     * @param param the parameter name
     * @return get the value of parameter
     */
    Object getParameter(String param);

    /**
     * Get the description of parameter.
     *
     * @param param the parameter name
     * @return the description
     */
    String getParameterDescription(String param);

    /**
     * Get the description of object.
     *
     * @return the description
     */
    String getDescription();
}
