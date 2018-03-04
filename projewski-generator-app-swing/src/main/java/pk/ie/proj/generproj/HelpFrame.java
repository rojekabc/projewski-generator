package pk.ie.proj.generproj;

import java.io.IOException;
import java.net.MalformedURLException;

import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.tools.Mysys;

public class HelpFrame extends javax.swing.JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2533243976077240221L;
	private static final int ID_DLG_ERROR_OPENING = 100;
	javax.swing.JTextPane pane = null;

	public HelpFrame( String strContext, String strTitle,
			int x, int y, int w, int h )
	{
		javax.swing.JScrollPane scrpane = new javax.swing.JScrollPane();
		pane = new javax.swing.JTextPane();
		scrpane.getViewport().setView( pane );
		pane.setContentType("text/html");
		pane.setEditable( false );
		setTitle( strTitle );
		setContentPane( scrpane );
		setBounds( x, y, w, h );
		if ( !setNewHelp( strContext, strTitle ) )
			return;
		setBounds( x, y, w, h );
		return;
	}

	public HelpFrame( ParameterInterface pi, int x, int y, int w, int h )
	{
		javax.swing.JScrollPane scrpane = new javax.swing.JScrollPane();
		pane = new javax.swing.JTextPane();
		scrpane.getViewport().setView( pane );
		pane.setContentType("text/html");
		pane.setEditable( false );
		setContentPane( scrpane );
		setBounds( x, y, w, h );
		if ( !setNewHelp( pi ) )
			return;
		setBounds( x, y, w, h );
		return;
	}

	public HelpFrame( String helpfile, int x, int y, int w, int h )
	{
		if ( helpfile == null )
			return;
		javax.swing.JScrollPane scrpane = new javax.swing.JScrollPane();
		pane = new javax.swing.JTextPane();
		scrpane.getViewport().setView( pane );
		pane.setContentType("text/html");
		pane.setEditable( false );
		setContentPane( scrpane );
		pane.setCaretPosition(0);
		setBounds( x, y, w, h );
		if ( !setNewHelp( helpfile ) )
			return;
		setBounds( x, y, w, h );
		return;
	}

	public boolean setNewHelp( String helpfile )
	{
		setTitle( helpfile );
		try {
			java.io.File file = new java.io.File( helpfile );
			java.net.URL url = file.toURL();
			pane.setPage( url );
			pane.setCaretPosition(0);
		} catch (MalformedURLException e) {
			Mysys.println(GenerProj.getDescription(ID_DLG_ERROR_OPENING)+helpfile);
			return false;
		} catch (IOException e) {
			Mysys.println(GenerProj.getDescription(ID_DLG_ERROR_OPENING)+helpfile);
			return false;
		}
		setVisible(true);
		return true;
	}

	public boolean setNewHelp( ParameterInterface pi )
	{
			String descript;
				
			descript = "<HTML>\n";
			descript += "<HEAD>\n";
			descript += "<META http-equiv=Content-Type content=\"text/html; charset=";
			descript += Mysys.getEncoding();
			descript += "\">";
			descript = "<BODY>\n";
			descript += "<div align=center bgcolor=#00EEEE><BIG>";
			descript += pi.getClass().getName();
			descript += "</BIG></div>\n";
			try {
				descript += pi.getDescription() + "<BR>\n";
			} catch ( ParameterException e ) {
				Mysys.println( e.toString() );
			}
			String [] param = null;
			try {
				param = pi.listParameters();
			} catch ( ParameterException e ) {
				Mysys.println( e.toString() );
				return false;
			}
			for ( int i=0; i<param.length; i++ )
			{
				descript +="<div align=center bgcolor=#00CCCC>Parametr <B>";
				descript += param[i];
				descript += "</B></div>\n";
				try {
					descript += pi.getParameterDescription( param[i] ) + "<BR>\n";
				} catch ( ParameterException e ) {
				Mysys.println( e.toString() );
				}
			}
			descript += "</BODY>\n</HTML>\n";
		
		pane.setText( descript );
		pane.setCaretPosition(0);
		setTitle( pi.getClass().getName() );
		setVisible(true);
		return true;
	}

	public boolean setNewHelp( String strContext, String strTitle )
	{
		if ( strContext == null )
			return false;
		pane.setText( strContext );
		pane.setCaretPosition(0);
		setVisible(true);
		return true;
	}
}
