package pl.projewski.generator.viewdata.tool;

import pl.projewski.generator.exceptions.ParameterException;
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
    public ExtendGraphics e = null;

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
            e.setTransformX(((Boolean) params.getParameter(GraphicPanelParameters.XTRANSFORM)).booleanValue());
            e.setTransformY(((Boolean) params.getParameter(GraphicPanelParameters.YTRANSFORM)).booleanValue());
            e.setMargin(
                    ((Integer) params.getParameter(GraphicPanelParameters.LEFTMARGIN)).intValue(),
                    ((Integer) params.getParameter(GraphicPanelParameters.TOPMARGIN)).intValue(),
                    ((Integer) params.getParameter(GraphicPanelParameters.RIGHTMARGIN)).intValue(),
                    ((Integer) params.getParameter(GraphicPanelParameters.BOTTOMMARGIN)).intValue()
            );
            e.setGraphDataBounds(
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMINX)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMINY)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()
            );
            e.setGraphScreenSize(this.getWidth(), this.getHeight());
            // Os OX
            e.drawLine(g,
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMINX)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()
            );
            // Os OY
            e.drawLine(g,
                    ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMINY)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
                    ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()
            );
            // Tu rysuj strzaleczki
            final double rX = e.getXPixelSize();
            final double rY = e.getYPixelSize();

            if (((Boolean) params.getParameter(GraphicPanelParameters.DRAWOYARROW)).booleanValue()) {
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue() + 12 * rY);
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue() + 12 * rY,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue() - 3 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue() + 3 * rY);
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue() + 12 * rY,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue() + 3 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue() + 3 * rY);
            }
            if (((Boolean) params.getParameter(GraphicPanelParameters.DRAWOXARROW)).booleanValue()) {
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue() + 12 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue());
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue() + 12 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue() + 3 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue() - 3 * rY);
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue() + 12 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),
                        ((Double) params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue() + 3 * rX,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue() + 3 * rY);
            }
            // Rysuj podzialki na osiach w wybranych punktach
            int i;
            VectorDouble vd = null;
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OXSCALVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                e.drawLine(g,
                        val, ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue() - 3 * rY,
                        val, ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue() + 3 * rY
                );
            }
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OYSCALVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                e.drawLine(g,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue() - 3 * rX, val,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue() + 3 * rX, val
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
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue() - strh * rY);
            }
            vd = (VectorDouble) params.getParameter(GraphicPanelParameters.OYPRINTVALUES);
            for (i = 0; i < vd.size(); i++) {
                final double val = vd.get(i);
                final String str = Double.toString(vd.get(i));
                final int strw = getFontMetrics(getFont()).stringWidth(str);
                final int strh = getFontMetrics(getFont()).getHeight();
                e.drawString(g, str,
                        ((Double) params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue() - (strw + 5) * rX,
                        val - ((double) (strh / 4)) * rY);
            }
        } catch (final ParameterException e) {
            e.printStackTrace(); // TODO: Okno dialogowe z opisem błęðdu
        }
        graphPaint(g);
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) throws ParameterException {
        return params.getAllowedClass(param);
    }

    @Override
    public String getDescription() throws ParameterException {
        return "";
    }

    @Override
    public Object getParameter(final String param) throws ParameterException {
        return params.getParameter(param);
    }

    @Override
    public String getParameterDescription(final String param) throws ParameterException {
        return params.getParameterDescription(param);
    }

    @Override
    public Set<String> listParameters() throws ParameterException {
        return params.listParameters();
    }

    @Override
    public void setParameter(final String param, final Object value) throws ParameterException {
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
