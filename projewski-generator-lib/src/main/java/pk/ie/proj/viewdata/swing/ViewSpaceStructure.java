package pk.ie.proj.viewdata.swing;

import pk.ie.proj.enumeration.ClassEnumerator;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.labordata.FindMax;
import pk.ie.proj.labordata.FindMin;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pk.ie.proj.viewdata.tool.GraphicPanel;
import pk.ie.proj.viewdata.tool.GraphicPanelParameters;

public class ViewSpaceStructure
	extends ViewDataInterface
{
	javax.swing.JFrame frame = null;
	FindMax _max = null;
	FindMin _min = null;
	NumberInterface _data = null;
	double datamin, datamax, datadelta;
	boolean digitalData=false;

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
		return dataSource instanceof GeneratorInterface;
	}

	/** M4_GEN_VDI_SHOWVIEW  */
	public void showView()
		throws ViewDataException
	{
		if ( (_min == null) || (_max == null) )
			return;
		try
		{
			datamin = Convert.tryToDouble(_min.getMinimum());
			datamax = Convert.tryToDouble(_max.getMaximum());
			ClassEnumerator cl = _data.getStoreClass();
			if ( cl == ClassEnumerator.INTEGER )
				datadelta = Math.abs(datamax - datamin)/(_data.getSize());
			else if ( cl == ClassEnumerator.LONG )
				datadelta = Math.abs(datamax - datamin)/(_data.getSize());
			else if ( cl == ClassEnumerator.FLOAT )
				datadelta = Math.abs(datamax - datamin)/(_data.getSize());
			else if ( cl == ClassEnumerator.DOUBLE )
				datadelta = Math.abs(datamax - datamin)/(_data.getSize());
			if ( (cl == ClassEnumerator.INTEGER) || (cl == ClassEnumerator.LONG) )
				digitalData = true;
			else
				digitalData = false;
			if ( frame == null )
			{
				frame = new javax.swing.JFrame("Widok struktury przestrzennej");
				frame.getContentPane().add( new ViewSpaceStructurePanel( this ) );
			}
			frame.setBounds(0, 0, 320, 320);
			frame.setVisible(true);
			frame.setBounds(0, 0, 320, 320);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.out.println( e.toString() );
		}
	}
	
	/** M4_GEN_VDI_REFRESHVIEW  */
	public void refreshView()
		throws ViewDataException
	{
	}
	/** M4_GEN_VDI_SETDATA_NSI  */
	public void setData(NumberInterface data)
		throws ViewDataException
	{
		try
		{
			_max = new FindMax();
			_min = new FindMin();
			_max.setInputData( data );
			_min.setInputData( data );
			_data = data;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return;
		}
	}

	public Class <?> [] getAllowedClass(String arg0) throws ParameterException {
		return new Class[0];
	}

}

class ViewSpaceStructurePanel
	extends GraphicPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ViewSpaceStructure _vs_;

	public ViewSpaceStructurePanel(ViewSpaceStructure vs)
	{
		_vs_ = vs;
		setBackground( java.awt.Color.white );
		setForeground( java.awt.Color.black );
		
		try {
			this.setParameter(GraphicPanelParameters.DATAMINX, new Double(_vs_.datamin));
			this.setParameter(GraphicPanelParameters.DATAMAXX, new Double(_vs_.datamax));
			this.setParameter(GraphicPanelParameters.DATAMINY, new Double(_vs_.datamin));
			this.setParameter(GraphicPanelParameters.DATAMAXY, new Double(_vs_.datamax));
		} catch (ParameterException e) {
			Mysys.debugln("ViewSpaceStructure::ViewSpaceStructure");
		}
	}

	public void graphPaint(java.awt.Graphics g)
	{
		if ( _vs_._data == null )
			return; // TODO: exception

		try {
		// Teraz nanieś dane
//			ClassEnumerator cl = _vs_._data.getStoreClass();
			NumberReader reader = _vs_._data.getNumberReader();
			g.setColor( java.awt.Color.blue );
			
			double prev = reader.readDouble();
			double act;
			while ( reader.hasNext() )
			{
				act = reader.readDouble();
				e.drawCircle(g, prev, act, 1);
				prev = act;
			}
		} catch ( Exception e )
		{
			Mysys.println(e.toString() );
		}
	}

	public ViewDataInterface getViewDataInterface() {
		return this._vs_;
	}
}
