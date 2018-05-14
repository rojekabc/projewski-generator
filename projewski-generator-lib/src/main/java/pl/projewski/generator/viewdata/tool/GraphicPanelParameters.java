package pl.projewski.generator.viewdata.tool;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.AbstractParameter;
import pl.projewski.generator.tools.VectorDouble;

import java.util.Collections;
import java.util.List;

/**
 * @author projewski
 */
public class GraphicPanelParameters extends AbstractParameter {

    public static final String XTRANSFORM = "X Transformation";
    public static final String YTRANSFORM = "Y Transformation";
    public static final String LEFTMARGIN = "Left Margin";
    public static final String TOPMARGIN = "Top Margin";
    public static final String RIGHTMARGIN = "Right Margin";
    public static final String BOTTOMMARGIN = "Bottom Margin";
    public static final String DATAMINX = "Data X Minimum";
    public static final String DATAMAXX = "Data X Maximum";
    public static final String DATAMINY = "Data Y Minimum";
    public static final String DATAMAXY = "Data Y Maximum";
    public static final String DRAWOX = "Draw OX";
    public static final String DRAWOY = "Draw OY";
    public static final String DRAWOXARROW = "Draw OX Arrow";
    public static final String DRAWOYARROW = "Draw OY Arrow";
    public static final String OXSCALVALUES = "OX Scal Values";
    public static final String OYSCALVALUES = "OY Scal Values";
    public static final String OXPRINTVALUES = "OX Print Values";
    public static final String OYPRINTVALUES = "OY Print Values";

    /* (non-Javadoc)
     * @see pk.ie.proj.abstracts.ParameterAbstract#getTypeName()
     */
    @Override
    public String getTypeName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.abstracts.ParameterAbstract#initParameters()
     */
    @Override
    public void initParameters() {
        parameters.put(XTRANSFORM, Boolean.FALSE);
        parameters.put(YTRANSFORM, Boolean.TRUE);
        parameters.put(LEFTMARGIN, 15);
        parameters.put(TOPMARGIN, 15);
        parameters.put(RIGHTMARGIN, 15);
        parameters.put(BOTTOMMARGIN, 15);
        parameters.put(DATAMINX, -1.0);
        parameters.put(DATAMAXX, 1.0);
        parameters.put(DATAMINY, -1.0);
        parameters.put(DATAMAXY, 1.0);
        parameters.put(DRAWOX, 0.0);
        parameters.put(DRAWOY, 0.0);
        parameters.put(DRAWOXARROW, Boolean.TRUE);
        parameters.put(DRAWOYARROW, Boolean.TRUE);
        parameters.put(OXSCALVALUES, new VectorDouble());
        parameters.put(OYSCALVALUES, new VectorDouble());
        parameters.put(OXPRINTVALUES, new VectorDouble());
        parameters.put(OYPRINTVALUES, new VectorDouble());
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
     */
    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(XTRANSFORM)
                || param.equals(YTRANSFORM)
                || param.equals(DRAWOXARROW)
                || param.equals(DRAWOYARROW)) {
            return Collections.singletonList(Boolean.class);
        } else if (param.equals(LEFTMARGIN)
                || param.equals(TOPMARGIN)
                || param.equals(RIGHTMARGIN)
                || param.equals(BOTTOMMARGIN)) {
            return Collections.singletonList(Integer.class);
        } else if (param.equals(DATAMINX)
                || param.equals(DATAMAXX)
                || param.equals(DATAMAXY)
                || param.equals(DATAMINY)
                || param.equals(DRAWOX)
                || param.equals(DRAWOY)) {
            return Collections.singletonList(Double.class);
        } else if (param.equals(OXSCALVALUES)
                || param.equals(OYSCALVALUES)
                || param.equals(OXPRINTVALUES)
                || param.equals(OYPRINTVALUES)) {
            return Collections.singletonList(VectorDouble.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

}
