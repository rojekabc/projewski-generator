package pl.projewski.generator.abstracts;

import lombok.ToString;
import pl.projewski.generator.exceptions.NotAllowedTypeGeneratorException;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.messages.DescriptionManager;

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
    public Set<String> listParameters() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    @Override
    public void setParameter(final String param, final Object value) {
        // check, that parameter is on the list of allowed class
        final List<Class<?>> allowed = getAllowedClass(param);
        if (allowed == null) {
            throw new NotAllowedTypeGeneratorException(param, value.getClass());
        }
        boolean isAllowed = false;
        for (final Class<?> clazz : allowed) {
            if (clazz.isInstance(value)) {
                isAllowed = true;
                break;
            }
        }
        if (!isAllowed) {
            throw new NotAllowedTypeGeneratorException(param, value.getClass());
        }
        parameters.put(param, value);
    }

    @Override
    public Object getParameter(final String param) {
        return this.parameters.get(param);
    }

    @Override
    public String getParameterDescription(final String param) {
        return DescriptionManager.getMessage(getClass().getName() + "." + param, "Parameter " + param);
    }

    @Override
    public String getDescription() {
        return DescriptionManager.getMessage(getClass().getName(), "Element of type " + getClass().getSimpleName());
    }
}
