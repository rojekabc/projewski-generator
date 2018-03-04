package pl.projewski.generator.generproj;
// KLASA TYLKO JAKO DODATEK DLA JDK 1.3 i wyzszych
public class MyAction extends javax.swing.AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4925636220514394239L;
	Object obj_help;
	MyAction( Object obj )
	{
		obj_help = obj;
	}
	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		GenerProj.showHelp( obj_help );
	}
}
