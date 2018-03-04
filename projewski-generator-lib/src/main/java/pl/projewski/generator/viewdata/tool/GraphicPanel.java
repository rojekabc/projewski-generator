package pl.projewski.generator.viewdata.tool;

import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.VectorDouble;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public abstract class GraphicPanel extends JPanel implements ParameterInterface, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5447544404924441879L;
	private GraphicPanelParameters params = new GraphicPanelParameters();
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
	
	public ExtendGraphics e = null;
	abstract public void graphPaint(Graphics g);
	abstract public ViewDataInterface getViewDataInterface();
	
	public GraphicPanel()
	{
		e = new ExtendGraphics();
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
//		if (setsDialog != null)
//			return;
		Mysys.debugln("Paint component");
		try {
			// Ustawienia
			e.setTransformX(((Boolean)params.getParameter(GraphicPanelParameters.XTRANSFORM)).booleanValue());
			e.setTransformY(((Boolean)params.getParameter(GraphicPanelParameters.YTRANSFORM)).booleanValue());
			e.setMargin(
				((Integer)params.getParameter(GraphicPanelParameters.LEFTMARGIN)).intValue(),
				((Integer)params.getParameter(GraphicPanelParameters.TOPMARGIN)).intValue(),
				((Integer)params.getParameter(GraphicPanelParameters.RIGHTMARGIN)).intValue(),
				((Integer)params.getParameter(GraphicPanelParameters.BOTTOMMARGIN)).intValue()
					);
			e.setGraphDataBounds(
				((Double)params.getParameter(GraphicPanelParameters.DATAMINX)).doubleValue(),
				((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue(),
				((Double)params.getParameter(GraphicPanelParameters.DATAMINY)).doubleValue(),
				((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()
					);
			e.setGraphScreenSize(this.getWidth(), this.getHeight());
			// Os OX
			e.drawLine(g,
					((Double)params.getParameter(GraphicPanelParameters.DATAMINX)).doubleValue(),
					((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),
					((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue(),				
					((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()
					);
			// Os OY
			e.drawLine(g,
					((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
					((Double)params.getParameter(GraphicPanelParameters.DATAMINY)).doubleValue(),
					((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
					((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()				
					);
			// Tu rysuj strzaleczki
			double rX = e.getXPixelSize();
			double rY = e.getYPixelSize();
	
			if ( ((Boolean)params.getParameter(GraphicPanelParameters.DRAWOYARROW)).booleanValue() )
			{
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue(),				
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()+12*rY);				
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()+12*rY,				
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue()-3*rX,
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()+3*rY);				
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue(),
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()+12*rY,				
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue()+3*rX,
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXY)).doubleValue()+3*rY);
			}
			if ( ((Boolean)params.getParameter(GraphicPanelParameters.DRAWOXARROW)).booleanValue() )
			{
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue(),
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),				
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue()+12*rX,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue());				
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue()+12*rX,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),				
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue()+3*rX,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()-3*rY);				
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue()+12*rX,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue(),				
						((Double)params.getParameter(GraphicPanelParameters.DATAMAXX)).doubleValue()+3*rX,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()+3*rY);
			}
			// Rysuj podzialki na osiach w wybranych punktach
			int i;
			VectorDouble vd = null;
			vd = (VectorDouble)params.getParameter(GraphicPanelParameters.OXSCALVALUES);
			for (i=0; i<vd.size(); i++)
			{
				double val = vd.get(i);
				e.drawLine(g,
						val, ((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()-3*rY,				
						val, ((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()+3*rY			
						);
			}
			vd = (VectorDouble)params.getParameter(GraphicPanelParameters.OYSCALVALUES);
			for (i=0; i<vd.size(); i++)
			{
				double val = vd.get(i);
				e.drawLine(g,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue()-3*rX, val,				
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue()+3*rX, val			
						);			
			}
			// Teraz umieszczaj napisy we wskazanych punktach dla podzialki
			vd = (VectorDouble)params.getParameter(GraphicPanelParameters.OXPRINTVALUES);
			for (i=0; i<vd.size(); i++)
			{
				double val = vd.get(i);
				String str = Double.toString(vd.get(i));
				int strw = getFontMetrics( getFont() ).stringWidth(str);
				int strh = getFontMetrics( getFont() ).getHeight();
				e.drawString(g, str,
						val-((double)strw/2)*rX,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOX)).doubleValue()-strh*rY);
			}
			vd = (VectorDouble)params.getParameter(GraphicPanelParameters.OYPRINTVALUES);
			for (i=0; i<vd.size(); i++)
			{
				double val = vd.get(i);
				String str = Double.toString(vd.get(i));
				int strw = getFontMetrics( getFont() ).stringWidth(str);
				int strh = getFontMetrics( getFont() ).getHeight();
				e.drawString(g, str,
						((Double)params.getParameter(GraphicPanelParameters.DRAWOY)).doubleValue()-(strw+5)*rX,
						val-((double)(strh/4))*rY);
			}
		} catch (ParameterException e) {
			e.printStackTrace(); // TODO: Okno dialogowe z opisem błęðdu
		}
		graphPaint(g);
	}

	public Class<?> [] getAllowedClass(String param) throws ParameterException {
		return params.getAllowedClass(param);
	}

	public String getDescription() throws ParameterException {
		return "";
	}

	public Object getParameter(String param) throws ParameterException {
		return params.getParameter(param);
	}

	public String getParameterDescription(String param) throws ParameterException {
		return params.getParameterDescription(param);
	}

	public String[] listParameters() throws ParameterException {
		return params.listParameters();
	}

	public void setParameter(String param, Object value) throws ParameterException {
		params.setParameter(param, value);
	}
	
	public Frame getFrame()
	{
		Component x = this;
		while ( (x = x.getParent()) != null )
		{
			if ( x instanceof Frame )
			{
				return (Frame)x;
			}
		}
		return null;
	}

	public void mouseReleased(MouseEvent arg0)
	{
		getViewDataInterface().sendMouseEventToListeners(this.getFrame(), this, arg0);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
