package pl.projewski.generator.abstracts;

import lombok.ToString;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.ParameterInterface;

import java.util.*;

/**
 * Base implementation of parameter interface.
 */
@ToString
public abstract class AbstractParameter implements ParameterInterface {
    protected final Map<String, Object> parameters;

    protected AbstractParameter() {
        parameters = new LinkedHashMap<>();
        initParameters();
    }

    /**
     * Initialize parameters of object.
     */
    protected abstract void initParameters();

    // TODO: REMOVE
    protected abstract String getTypeName();

    @Override
    public Set<String> listParameters() throws ParameterException {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    @Override
    public void setParameter(final String param, final Object value) throws ParameterException {
        // check, that parameter is on the list of allowed class
        final List<Class<?>> allowed = getAllowedClass(param);
        if (allowed == null) {
            throw new ParameterException(ParameterException.PARAMETER_CLASS_ERROR, param);
        }
        allowed.stream().filter(value.getClass()::equals).findFirst()
                .orElseThrow(() -> new ParameterException(ParameterException.PARAMETER_CLASS_ERROR, param));
        // set parameter
        parameters.put(param, value);
    }

    @Override
    public Object getParameter(final String param) throws ParameterException {
        return this.parameters.get(param);
    }

    @Override
    public String getParameterDescription(final String param) throws ParameterException {
        final String classname = this.getClass().getSimpleName();
        return "PARAMETER DESCRIPTION";
        // return Mysys.getDescription(classname, param, this.getTypeName(), null);
    }

    @Override
    public String getDescription() throws ParameterException {
        final String classname = this.getClass().getSimpleName();
        return "DESCRIPTION";
        // return Mysys.getDescription(classname, classname, this.getTypeName(), null);
    }
}
