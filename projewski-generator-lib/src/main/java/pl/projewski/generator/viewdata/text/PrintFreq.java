package pl.projewski.generator.viewdata.text;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.labordata.FindMax;
import pl.projewski.generator.labordata.Frequency;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;

import java.util.Arrays;
import java.util.List;

/**
 * @author projewski
 */
public class PrintFreq extends ViewDataBase {

    public final static String LINESAMMOUNT = "Liczba linii";
    private NumberInterface data;

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return dataSource instanceof Frequency;
    }

    @Override
    public Object getView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void refreshView() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setData(final NumberInterface data) {
        this.data = data;
    }

    @Override
    public void showView() {
        final ParameterInterface source = data.getDataSource();
        final NumberReader reader = data.getNumberReader();
        final FindMax maxLD = new FindMax();
        maxLD.setInputData(data);
        final int max = Convert.tryToInt(maxLD.getMaximum());
        final int numClass = Convert.tryToInt(source.getParameter(Frequency.CLASSAMMOUNT));
        int numLines = Convert.tryToInt(parameters.get(LINESAMMOUNT));
        //			Mysys.debugln( "Odczytano: " + numClass );
        //			Mysys.debugln("Max: " + max);
        final char[][] tabs = new char[numLines][numClass];
        for (int i = 0; i < numClass; i++) {
            for (int j = 0; j < numLines; j++) {
                if (j != 0) {
                    tabs[j][i] = ' ';
                } else {
                    tabs[j][i] = '-';
                }
            }
            //				Mysys.debugln("Pozycja " + pos);
            final int pos = reader.readInt() * (numLines - 1) / max;
            if (pos != 0) {
                tabs[pos][i] = '-';
            } else {
                tabs[pos][i] = '=';
            }
        }

        Mysys.println("Widok: " + this.toString());
        Mysys.println("Test: " + source.toString());

        // TODO: Zaraz numerowania linii powinna by� liczebno��
        System.out.println("  ^");
        while (numLines-- > 0) {
            if (numLines < 10) {
                if (numLines > 0) {
                    System.out.println(" " + numLines + "|" + new String(tabs[numLines]));
                } else {
                    System.out.println(" " + numLines + "*" + new String(tabs[numLines]) + ">");
                }
            } else {
                System.out.println("" + numLines + "|" + new String(tabs[numLines]));
            }
        }
        System.out.println("Maksymalna liczebność: " + max);
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.abstracts.ParameterAbstract#initParameters()
     */
    @Override
    public void initParameters() {
        parameters.put(LINESAMMOUNT, 10);
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ParameterInterface#getAllowedClass(java.lang.String)
     */
    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(LINESAMMOUNT)) {
            return Arrays.asList(Integer.class);
        }
        return ListUtils.EMPTY_LIST;
    }

}
