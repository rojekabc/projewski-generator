package pl.projewski.generator.interfaces;

import pl.projewski.generator.exceptions.ParameterException;

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
     * @throws ParameterException
     */
    Set<String> listParameters() throws ParameterException;

    /**
     * Get allowed set of class for parameter.
     *
     * @param param name of parameter
     * @return set of allowed parameters
     * @throws ParameterException
     */
    List<Class<?>> getAllowedClass(String param) throws ParameterException;

    /**
     * Set parameter value
     *
     * @param param parameter name
     * @param value parameter value
     * @throws ParameterException
     */
    void setParameter(String param, Object value) throws ParameterException;

    /**
     * Get value of parameter.
     *
     * @param param the parameter name
     * @return get the value of parameter
     * @throws ParameterException
     */
    Object getParameter(String param) throws ParameterException;

    /**
     * Get the description of parameter.
     *
     * @param param the parameter name
     * @return the description
     * @throws ParameterException
     */
    String getParameterDescription(String param) throws ParameterException;

    /**
     * Get the description of object.
     *
     * @return the description
     * @throws ParameterException
     */
    String getDescription() throws ParameterException;
}
