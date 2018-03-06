/**
 *
 */
package pl.projewski.generator.viewdata.text;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.exceptions.ViewDataException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.labordata.TestSpeed;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;

import java.util.List;

/**
 * @author projewski
 */
public class PrintTestSpeed extends ViewDataBase {

    NumberInterface data;

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return dataSource instanceof TestSpeed;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ViewDataInterface#getView()
     */
    @Override
    public Object getView() throws ViewDataException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ViewDataInterface#refreshView()
     */
    @Override
    public void refreshView() throws ViewDataException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ViewDataInterface#setData(pk.ie.proj.interfaces.NumberInterface)
     */
    @Override
    public void setData(final NumberInterface inputData) throws ViewDataException {
        data = inputData;
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ViewDataInterface#showView()
     */
    @Override
    public void showView() throws ViewDataException {
        NumberReader reader = null;
        ParameterInterface src = null;
        try {
            reader = data.getNumberReader();
            src = data.getDataSource();
            if (src == null) {
                throw new ViewDataException(ViewDataException.UNKNOWN_ERROR); // TODO
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
        } catch (final NumberStoreException e) {
            throw new ViewDataException(e);
        } catch (final NumberFormatException e) {
            throw new ViewDataException(e);
        } catch (final ClassCastException e) {
            throw new ViewDataException(e);
        } catch (final ParameterException e) {
            throw new ViewDataException(e);
        } finally {
            Mysys.closeQuiet(reader);
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
    public List<Class<?>> getAllowedClass(final String arg0) throws ParameterException {
        return ListUtils.EMPTY_LIST;
    }

}
