package pl.projewski.generator.viewdata.text;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.exceptions.WrongDataGeneratorException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.labordata.TestSpeed;
import pl.projewski.generator.tools.Convert;

import java.util.List;

/**
 * @author projewski
 */
public class PrintTestSpeed extends ViewDataBase {

    private NumberInterface data;

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return dataSource instanceof TestSpeed;
    }

    @Override
    public Object getView() {
        return null;
    }

    @Override
    public void refreshView() {
    }

    @Override
    public void setData(final NumberInterface inputData) {
        data = inputData;
    }

    @Override
    public void showView() {
        final ParameterInterface src;
        try (final NumberReader reader = data.getNumberReader()) {
            src = data.getDataSource();
            if (src == null) {
                throw new WrongDataGeneratorException();
            }
            final int testNum = Convert.tryToInt(src.getParameter(TestSpeed.NUMBEROFTESTS));
            if ((Boolean) src.getParameter(TestSpeed.TESTINTEGER)) {
                int i = testNum;
                System.out.println("Test of nextInt()");
                while (i-- > 0) {
                    System.out.print("" + reader.readLong() + "ms ");
                }
                System.out.println();
            }
            if ((Boolean) src.getParameter(TestSpeed.TESTLONG)) {
                int i = testNum;
                System.out.println("Test of nextLong()");
                while (i-- > 0) {
                    System.out.print("" + reader.readLong() + "ms ");
                }
                System.out.println();
            }
            if ((Boolean) src.getParameter(TestSpeed.TESTFLOAT)) {
                int i = testNum;
                System.out.println("Test of nextFloat()");
                while (i-- > 0) {
                    System.out.print("" + reader.readLong() + "ms ");
                }
                System.out.println();
            }
            if ((Boolean) src.getParameter(TestSpeed.TESTDOUBLE)) {
                int i = testNum;
                System.out.println("Test of nextDouble()");
                while (i-- > 0) {
                    System.out.print("" + reader.readLong() + "ms ");
                }
                System.out.println();
            }
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.abstracts.ParameterAbstract#initParameters()
     */
    @Override
    public void initParameters() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
     */
    @Override
    public List<Class<?>> getAllowedClass(final String arg0) {
        return ListUtils.EMPTY_LIST;
    }

}
