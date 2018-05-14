package pl.projewski.generator.viewdata.swing;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.FindMax;
import pl.projewski.generator.labordata.FindMin;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.viewdata.tool.GraphicPanel;
import pl.projewski.generator.viewdata.tool.GraphicPanelParameters;

import java.util.List;

public class ViewSpaceStructure
        extends ViewDataBase {
    javax.swing.JFrame frame = null;
    FindMax _max = null;
    FindMin _min = null;
    NumberInterface _data = null;
    double datamin, datamax, datadelta;
    boolean digitalData = false;

    @Override
    public void initParameters() {
    }


    /**
     * M4_GEN_VDI_GETVIEW
     */
    @Override
    public Object getView() {
        return null;
    }

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return dataSource instanceof GeneratorInterface;
    }

    /**
     * M4_GEN_VDI_SHOWVIEW
     */
    @Override
    public void showView() {
        if ((_min == null) || (_max == null)) {
            return;
        }
        try {
            datamin = Convert.tryToDouble(_min.getMinimum());
            datamax = Convert.tryToDouble(_max.getMaximum());
            final ClassEnumerator cl = _data.getStoreClass();
            if (cl == ClassEnumerator.INTEGER) {
                datadelta = Math.abs(datamax - datamin) / (_data.getSize());
            } else if (cl == ClassEnumerator.LONG) {
                datadelta = Math.abs(datamax - datamin) / (_data.getSize());
            } else if (cl == ClassEnumerator.FLOAT) {
                datadelta = Math.abs(datamax - datamin) / (_data.getSize());
            } else if (cl == ClassEnumerator.DOUBLE) {
                datadelta = Math.abs(datamax - datamin) / (_data.getSize());
            }
            if ((cl == ClassEnumerator.INTEGER) || (cl == ClassEnumerator.LONG)) {
                digitalData = true;
            } else {
                digitalData = false;
            }
            if (frame == null) {
                frame = new javax.swing.JFrame("Widok struktury przestrzennej");
                frame.getContentPane().add(new ViewSpaceStructurePanel(this));
            }
            frame.setBounds(0, 0, 320, 320);
            frame.setVisible(true);
            frame.setBounds(0, 0, 320, 320);
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    /**
     * M4_GEN_VDI_REFRESHVIEW
     */
    @Override
    public void refreshView() {
    }

    /**
     * M4_GEN_VDI_SETDATA_NSI
     */
    @Override
    public void setData(final NumberInterface data) {
        try {
            _max = new FindMax();
            _min = new FindMin();
            _max.setInputData(data);
            _min.setInputData(data);
            _data = data;
        } catch (final Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public List<Class<?>> getAllowedClass(final String arg0) {
        return ListUtils.EMPTY_LIST;
    }

}

class ViewSpaceStructurePanel
        extends GraphicPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ViewSpaceStructure _vs_;

    public ViewSpaceStructurePanel(final ViewSpaceStructure vs) {
        _vs_ = vs;
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.black);

        this.setParameter(GraphicPanelParameters.DATAMINX, new Double(_vs_.datamin));
        this.setParameter(GraphicPanelParameters.DATAMAXX, new Double(_vs_.datamax));
        this.setParameter(GraphicPanelParameters.DATAMINY, new Double(_vs_.datamin));
        this.setParameter(GraphicPanelParameters.DATAMAXY, new Double(_vs_.datamax));
    }

    @Override
    public void graphPaint(final java.awt.Graphics g) {
        if (_vs_._data == null) {
            return; // TODO: exception
        }

        try {
            // Teraz nanieś dane
            //			ClassEnumerator cl = _vs_.data.getStoreClass();
            final NumberReader reader = _vs_._data.getNumberReader();
            g.setColor(java.awt.Color.blue);

            double prev = reader.readDouble();
            double act;
            while (reader.hasNext()) {
                act = reader.readDouble();
                e.drawCircle(g, prev, act, 1);
                prev = act;
            }
        } catch (final Exception e) {
            Mysys.println(e.toString());
        }
    }

    @Override
    public ViewDataInterface getViewDataInterface() {
        return this._vs_;
    }
}
