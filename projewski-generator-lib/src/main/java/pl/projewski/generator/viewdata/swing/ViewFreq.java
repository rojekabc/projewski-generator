package pl.projewski.generator.viewdata.swing;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.labordata.FindMax;
import pl.projewski.generator.labordata.Frequency;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.viewdata.tool.GraphicPanel;
import pl.projewski.generator.viewdata.tool.GraphicPanelParameters;

import java.awt.*;
import java.util.List;

public class ViewFreq
        extends ViewDataBase {
    NumberInterface freq = null;
    boolean digitalData = false; // czy dane sa dyskretne
    double maxy, miny;
    double datamin, datamax, datadelta;
    private javax.swing.JFrame frame = null;

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
        return dataSource instanceof Frequency;
    }

    /**
     * M4_GEN_VDI_SHOWVIEW
     */
    @Override
    public void showView() {
        if (freq == null) {
            return; // TODO:
        }

        try {
            final FindMax maxint;

            maxint = new FindMax();
            maxint.setInputData(freq);
            maxy = Convert.tryToDouble(maxint.getMaximum());
            miny = 0.0;

            if (frame == null) {
                frame = new javax.swing.JFrame(Mysys.encString("Rozklad czestotliwosci"));
                frame.getContentPane().add(new ViewFreqPanel(this));
            }
        } catch (final Exception e) {
            Mysys.println(e.toString());
            e.printStackTrace();
        }

        frame.setBounds(0, 0, 330, 330);
        frame.setVisible(true);
        frame.setBounds(0, 0, 330, 330);
    }

    /**
     * M4_GEN_VDI_REFRESHVIEW
     */
    @Override
    public void refreshView() {
        Mysys.debugln("repaint");
        if (this.frame != null) {
            this.frame.repaint();
        }
    }

    /**
     * M4_GEN_PI_GETALLOWEDCLASS_I
     */
    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public void setData(final NumberInterface data) {
        // pobierz dane zrodlowe
        final ParameterInterface dataSource = data.getDataSource();
        if (dataSource == null) {
            Mysys.error("Brak zrodla danych");
            return; // TODO
        }
        if (!canViewData(dataSource)) {
            Mysys.error("Nieodpowiednie zrodlo danych");
            return; // TODO:
        }

        datamin = Convert.tryToDouble(dataSource.getParameter(Frequency.MINIMUM));
        datamax = Convert.tryToDouble(dataSource.getParameter(Frequency.MAXIMUM));
        freq = data;

        datadelta = Math.abs(datamax - datamin) / freq.getSize();

    }
}

class ViewFreqPanel
        extends GraphicPanel
        //extends javax.swing.JPanel
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final ViewFreq _vf_;

    public ViewFreqPanel(final ViewFreq vf) {
        _vf_ = vf;
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.black);
        this.setParameter(GraphicPanelParameters.DATAMINX, _vf_.datamin);
        this.setParameter(GraphicPanelParameters.DATAMAXX, _vf_.datamax);
        this.setParameter(GraphicPanelParameters.DATAMINY, _vf_.miny);
        this.setParameter(GraphicPanelParameters.DATAMAXY, _vf_.maxy);
    }

    @Override
    public void graphPaint(final Graphics g) {
        Mysys.debugln("GraphPaint ViewFreq");

        if (_vf_.freq == null) {
            return;
        }
        try (final NumberReader is = _vf_.freq.getNumberReader()) {
            final double rX = e.getXPixelSize();
            // Teraz nanies dane
            final ClassEnumerator cl = _vf_.freq.getStoreClass();
            if (cl == ClassEnumerator.INTEGER) // Inna nie powinna byc
            {
                int tmp;
                int i = 0;
                // Nanos punkty

                while (is.hasNext()) {
                    tmp = is.readInt();
                    g.setColor(java.awt.Color.blue);
                    if (_vf_.digitalData) // Punkt
                    {
                        if (_vf_.datadelta >= 4 * rX) // ustalenie rozmiaru kulek punktow
                        {
                            e.drawCircle(g, _vf_.datamin + i * _vf_.datadelta, (double) tmp, 2);
                        } else {
                            e.drawCircle(g, _vf_.datamin + i * _vf_.datadelta, (double) tmp, 1);
                        }
                    } else // Linia nad obszarem wystepowania
                    {
                        e.drawLine(g, _vf_.datamin + i * _vf_.datadelta, (double) tmp,
                                _vf_.datamin + (i + 1) * _vf_.datadelta, (double) tmp);
                    }
                    i++;
                } // while
            } else {
                Mysys.println("Niedozwolone typ danych freq"); // TODO:
            }
        }
    }

    @Override
    public ViewDataInterface getViewDataInterface() {
        return this._vf_;
    }
}
