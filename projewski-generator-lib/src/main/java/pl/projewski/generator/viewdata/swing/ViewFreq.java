package pl.projewski.generator.viewdata.swing;

import java.awt.Graphics;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.labordata.FindMax;
import pk.ie.proj.labordata.Frequency;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.viewdata.tool.GraphicPanel;
import pk.ie.proj.viewdata.tool.GraphicPanelParameters;
import pl.projewski.generator.labordata.FindMax;
import pl.projewski.generator.labordata.Frequency;

public class ViewFreq
	extends ViewDataInterface
{
	javax.swing.JFrame frame = null;
	NumberInterface _freq = null;
	boolean digitalData = false; // czy dane sa dyskretne

	double maxy, miny;
	double datamin, datamax, datadelta;

	public void initParameterInterface()
	{
	}


	/** M4_GEN_VDI_GETVIEW  */
	public Object getView()
		throws ViewDataException
	{
		return null;
	}
	
	public boolean canViewData(ParameterInterface dataSource)
	{
		return dataSource instanceof Frequency;
	}	

	/** M4_GEN_VDI_SHOWVIEW  */
	public void showView()
		throws ViewDataException
	{
		if ( _freq == null )
			return; // TODO:
		
		try
		{
			FindMax maxint;

			maxint = new FindMax();			
			maxint.setInputData(_freq);
			maxy = Convert.tryToDouble(maxint.getMaximum());
			miny = 0.0;

			if ( frame == null )
			{
				frame = new javax.swing.JFrame(Mysys.encString("Rozklad czestotliwosci"));
				frame.getContentPane().add( new ViewFreqPanel(this) );
			}
		}
		catch ( Exception e )
		{
			Mysys.println(e.toString());
			e.printStackTrace();
		}

		frame.setBounds(0, 0, 330, 330);
		frame.setVisible(true);
		frame.setBounds(0, 0, 330, 330);
	}
	
	/** M4_GEN_VDI_REFRESHVIEW  */
	public void refreshView()
		throws ViewDataException
	{
		Mysys.debugln("repaint");
		if ( this.frame != null )
			this.frame.repaint();
	}
	
	/** M4_GEN_PI_GETALLOWEDCLASS_I */
	public Class<?> [] getAllowedClass(String param) {
			return new Class <?> [] {};
	}
	
	@Override
	public void setData(NumberInterface data) throws ViewDataException {
		ParameterInterface dataSource = null;

		// pobierz dane zrodlowe
		try
		{
			dataSource = data.getDataSource();
			if ( dataSource == null )
			{
				Mysys.error("Brak zrodla danych");
				return; // TODO
			}
			if ( !canViewData(dataSource) )
			{
				Mysys.error("Nieodpowiednie zrodlo danych");
				return; // TODO:
			}
		} catch (Exception e) {
			throw new ViewDataException(e);
		}
		
		
		try {
			datamin = Convert.tryToDouble( dataSource.getParameter(Frequency.MINIMUM) );
			datamax = Convert.tryToDouble( dataSource.getParameter(Frequency.MAXIMUM) );
			_freq = data;
						
			datadelta = Math.abs(datamax - datamin)/_freq.getSize();
		} catch (Exception e) {
			throw new ViewDataException(e);
		}
		
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

	private ViewFreq _vf_;

	public ViewFreqPanel(ViewFreq vf)
	{
		_vf_ = vf;
		setBackground( java.awt.Color.white );
		setForeground( java.awt.Color.black );
		try {
			this.setParameter(GraphicPanelParameters.DATAMINX, new Double(_vf_.datamin));
			this.setParameter(GraphicPanelParameters.DATAMAXX, new Double(_vf_.datamax));
			this.setParameter(GraphicPanelParameters.DATAMINY, new Double(_vf_.miny));
			this.setParameter(GraphicPanelParameters.DATAMAXY, new Double(_vf_.maxy));
		} catch (ParameterException e) {
			Mysys.debugln("VieFreqPanel::ViewFreqPanel");
		}
	}

	public void graphPaint(Graphics g) {
		Mysys.debugln("GraphPaint ViewFreq");

		if ( _vf_._freq == null )
			return;
		NumberReader is = null;
		try {
			double rX = e.getXPixelSize();
			// Teraz nanies dane
			ClassEnumerator cl = _vf_._freq.getStoreClass();
			if ( cl == ClassEnumerator.INTEGER ) // Inna nie powinna byc
			{
				int tmp;
				int i = 0;
				is = _vf_._freq.getNumberReader();
				// Nanos punkty
				
				while ( is.hasNext() )
				{
					tmp = is.readInt();
					g.setColor( java.awt.Color.blue );
					if ( _vf_.digitalData ) // Punkt
					{
						if ( _vf_.datadelta >= 4*rX ) // ustalenie rozmiaru kulek punktow
							e.drawCircle(g, _vf_.datamin+i*_vf_.datadelta,(double)tmp, 2);
						else
							e.drawCircle(g, _vf_.datamin+i*_vf_.datadelta,(double)tmp, 1);
					}
					else // Linia nad obszarem wystepowania
					{
						e.drawLine(g, _vf_.datamin+i*_vf_.datadelta, (double)tmp,
								_vf_.datamin+(i+1)*_vf_.datadelta, (double)tmp );
					}
					i++;
				} // while
			}
			else
				Mysys.println("Niedozwolone typ danych freq"); // TODO:
		} catch ( Exception e )	{
			Mysys.println(e.toString() );
			e.printStackTrace();
		} finally {
			Mysys.debugln("Closing input stream for vieFreq");
			Mysys.closeQuiet(is);
		}
	}

	public ViewDataInterface getViewDataInterface() {
		return this._vf_;
	}	
}
