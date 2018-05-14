package pl.projewski.generator.viewdata.tool;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.VectorDouble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Set;

public abstract class GraphicPanel extends JPanel implements ParameterInterface, MouseListener {
    /**
     *
     */
    private static final long serialVersionUID = -5447544404924441879L;
    /*
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

        protected String [] paramName =
        {
            "Y Transformation",
            "X Transformation",
            "Left Margin",
            "Top Margin",
            "Right Margin",
            "Bottom Margin",
            "Data X Minimum",
            "Data X Maximum",
            "Data Y Minimum",
            "Data Y Maximum",
            "Draw OX",
            "Draw OY",
            "Draw OX Arrow",
            "Draw OY Arrow",
            "OX Scal Values",
            "OY Scal Values",
            "OX Print Values",
            "OY Print Values"
        };
        protected Class<?> [] paramClass =
        {
            Boolean.class,
            Boolean.class,
            Integer.class,
            Integer.class,
            Integer.class,
            Integer.class,
            Double.class,
            Double.class,
            Double.class,
            Double.class,
            Double.class,
            Double.class,
            Boolean.class,
            Boolean.class,
            VectorDouble.class,
            VectorDouble.class,
            VectorDouble.class,
            VectorDouble.class
        };
        protected Object [] paramValue =
        {
            new Boolean(true),
            new Boolean(false),
            new Integer(15),
            new Integer(15),
            new Integer(15),
            new Integer(15),
            new Double(-1.0),
            new Double(1.0),
            new Double(-1.0),
            new Double(1.0),
            new Double(0.0),
            new Double(0.0),
            new Boolean(true),
            new Boolean(true),
            new VectorDouble(),
            new VectorDouble(),
            new VectorDouble(),
            new VectorDouble()
        };
    */
    //	private JDialog setsDialog = null;
    //	ParameterParamPack paramPack = null;
    private final GraphicPanelParameters params = new GraphicPanelParameters();
    public ExtendGraphics e;

    public GraphicPanel() {
        e = new ExtendGraphics();
        this.addMouseListener(this);
    }

    abstract public void graphPaint(Graphics g);

    abstract public ViewDataInterface getViewDataInterface();

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        //		if (setsDialog != null)
        //			return;
        Mysys.debugln("Paint component");
        try {
            // Ustawienia
            e.setTransformX((Boolean) params.getParameter(GraphicPanelParameters.XTRANSFORM));
            e.setTransformY((Boolean) params.getParameter(GraphicPanelParameters.YTRANSFORM));
            e.setMargin(
                    (Integer) params.getParameter(GraphicPanelParameters.LEFTMARGIN),
                    (Integer) params.getParameter(GraphicPanelParameters.TOPMARGIN),
                    (Integer) params.getParameter(GraphicPanelParameters.RIGHTMARGIN),
                    (Integer) params.getParameter(GraphicPanelParameters.BOTTOMMARGIN)
            );
            e.setGraphDataBounds(
                    (Double) params.getParameter(GraphicPanelParameters.DATAMINX),
                    (Double) params.getParameter(GraphicPanelParameters.DATAMAXX),
                    (Double) params.getParameter(GraphicPanelParameters.DATAMINY),
                    (Double) params.getParameter(GraphicPanelParameters.DATAMAXY)
            );
            e.setGraphScreenSize(this.getWidth(), this.getHeight());
            // Os OX
            e.drawLine(g,
                    (Double) params.getParameter(GraphicPanelParameters.DATAMINX),
                    (Double) params.getParameter(GraphicPanelParameters.DRAWOX),
                    (Double) params.getParameter(GraphicPanelParameters.DATAMAXX),
                    (Double) params.getParameter(GraphicPanelParameters.DRAWOX)
            );
            // Os OY
            e.drawLine(g,
                    (Double) params.getParameter(GraphicPanelParameters.DRAWOY),
                    (Double) params.getParameter(GraphicPanelParameters.DATAMINY),
                    (Double) params.getParameter(GraphicPanelParameters.DRAWOY),
                    (Double) params.getParameter(GraphicPanelParameters.DATAMAXY)
            );
            // Tu rysuj strzaleczki
            final double rX = e.getXPixelSize();
            final double rY = e.getYPixelSize();

            if ((Boolean) params.getParameter(GraphicPanelParameters.DRAWOYARROW)) {
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXY),
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXY) + 12 * rY);
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXY) + 12 * rY,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY) - 3 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXY) + 3 * rY);
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXY) + 12 * rY,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY) + 3 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXY) + 3 * rY);
            }
            if ((Boolean) params.getParameter(GraphicPanelParameters.DRAWOXARROW)) {
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXX),
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXX) + 12 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX));
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXX) + 12 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXX) + 3 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX) - 3 * rY);
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXX) + 12 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX),
                        (Double) params.getParameter(GraphicPanelParameters.DATAMAXX) + 3 * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX) + 3 * rY);
            }
            // Rysuj podzialki na osiach w wybranych punktach
            int i;
            VectorDouble vd = null;
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OXSCALVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                e.drawLine(g,
                        val, (Double) params.getParameter(GraphicPanelParameters.DRAWOX) - 3 * rY,
                        val, (Double) params.getParameter(GraphicPanelParameters.DRAWOX) + 3 * rY
                );
            }
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OYSCALVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                e.drawLine(g,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY) - 3 * rX, val,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY) + 3 * rX, val
                );
            }
            // Teraz umieszczaj napisy we wskazanych punktach dla podzialki
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OXPRINTVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                final String str = Double.toString(vd.get(i));
                final int strw = getFontMetrics(getFont()).stringWidth(str);
                final int strh = getFontMetrics(getFont()).getHeight();
                e.drawString(g, str,
                        val - ((double) strw / 2) * rX,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOX) - strh * rY);
            }
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OYPRINTVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                final String str = Double.toString(vd.get(i));
                final int strw = getFontMetrics(getFont()).stringWidth(str);
                final int strh = getFontMetrics(getFont()).getHeight();
                e.drawString(g, str,
                        (Double) params.getParameter(GraphicPanelParameters.DRAWOY) - (strw + 5) * rX,
                        val - ((double) (strh / 4)) * rY);
            }
        } catch (final GeneratorException e) {
            e.printStackTrace(); // TODO: Okno dialogowe z opisem błęðdu
        }
        graphPaint(g);
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return params.getAllowedClass(param);
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Object getParameter(final String param) {
        return params.getParameter(param);
    }

    @Override
    public String getParameterDescription(final String param) {
        return params.getParameterDescription(param);
    }

    @Override
    public Set<String> listParameters() {
        return params.listParameters();
    }

    @Override
    public void setParameter(final String param, final Object value) {
        params.setParameter(param, value);
    }

    public Frame getFrame() {
        Component x = this;
        while ((x = x.getParent()) != null) {
            if (x instanceof Frame) {
                return (Frame) x;
            }
        }
        return null;
    }

    @Override
    public void mouseReleased(final MouseEvent arg0) {
        getViewDataInterface().sendMouseEventToListeners(this.getFrame(), this, arg0);
    }

    @Override
    public void mouseClicked(final MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(final MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(final MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(final MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}
