package pk.ie.proj.generproj;
import javax.swing.*;

import pk.ie.proj.generproj.layout.AlaNullSizedLayout;

import java.awt.event.*;
import java.awt.*;

class MyFileChooserDialog extends JDialog
	implements ActionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4445013033811027639L;
	private static final int ID_TITLE=190;
	private static final int ID_OK=191;
	private static final int ID_CANCEL=192;
	
	javax.swing.JLabel labDir;
	javax.swing.JList lisPlik;
	javax.swing.JScrollPane scrListPlik;
	javax.swing.JButton buttOk;
	javax.swing.JButton buttCancel;
	java.io.File selectedFile = null;
	String selExt = null;
	Object lastSel = null;
	private void budujObiekty() {
		labDir = new javax.swing.JLabel();
		labDir.setText( "" );
		labDir.setBounds(25, 20, 230, 30);
		labDir.setBackground( new Color( 0xffcccccc ) );
		labDir.setForeground( new Color( 0xff666699 ) );
		labDir.setEnabled(true);
		labDir.setVisible(true);
		labDir.setRequestFocusEnabled(true);
		labDir.setFont( labDir.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(labDir);
		scrListPlik = new javax.swing.JScrollPane();
		lisPlik = new javax.swing.JList();
		scrListPlik.setBounds(25, 55, 230, 230);
		lisPlik.setBackground( new Color( 0xffffffff ) );
		lisPlik.setForeground( new Color( 0xff000000 ) );
		lisPlik.setEnabled(true);
		lisPlik.setVisible(true);
		lisPlik.setRequestFocusEnabled(true);
		lisPlik.addMouseListener( this );
		scrListPlik.getViewport().setView(lisPlik);
		getContentPane().add(scrListPlik);
		buttOk = new javax.swing.JButton();
		buttOk.setText(GenerProj.getDescription(ID_OK));
		buttOk.setBounds(25, 300, 110, 30);
		buttOk.setBackground( new Color( 0xffcccccc ) );
		buttOk.setForeground( new Color( 0xff000000 ) );
		buttOk.setEnabled(true);
		buttOk.setVisible(true);
		buttOk.setRequestFocusEnabled(true);
		buttOk.setFont( buttOk.getFont().deriveFont( Font.PLAIN ) );
		buttOk.addActionListener( this );
		getContentPane().add(buttOk);
		buttCancel = new javax.swing.JButton();
		buttCancel.setText(GenerProj.getDescription(ID_CANCEL));
		buttCancel.setBounds(145, 300, 110, 30);
		buttCancel.setBackground( new Color( 0xffcccccc ) );
		buttCancel.setForeground( new Color( 0xff000000 ) );
		buttCancel.setEnabled(true);
		buttCancel.setVisible(true);
		buttCancel.setRequestFocusEnabled(true);
		buttCancel.setFont( buttCancel.getFont().deriveFont( Font.PLAIN ) );
		buttCancel.addActionListener( this );
		getContentPane().add(buttCancel);
	}

	protected void budujListe( String path, String extension )
	{
		java.io.File chPath = new java.io.File( path );
		java.util.Vector<String> data = new java.util.Vector<String>();
		if ( chPath.isDirectory() )
		{
			java.io.File [] lista = chPath.listFiles( new DirectoryFilter() );
			for ( int i=0; i < lista.length; i++ )
				data.add( "[ " + lista[i].getName() + " ]");
			lista = chPath.listFiles( new ExtensionFilter(extension) );
			for ( int i=0; i < lista.length; i++ )
				data.add( lista[i].getName() );
		}
		lisPlik.setListData( data );
	}

	public MyFileChooserDialog( JFrame parent, String extension ) {
		super( parent, GenerProj.getDescription(ID_TITLE), true );
		selExt = extension;
		setBounds(260, 75, 282, 373);
		getContentPane().setLayout(new AlaNullSizedLayout(282, 353));
		budujObiekty();
		budujListe( System.getProperty("user.dir"), selExt );
		pack();
		setVisible(true);
	}
	
	public MyFileChooserDialog( JDialog parent, String extension ) {
		super( parent, GenerProj.getDescription(ID_TITLE), true );
		selExt = extension;
		setBounds(260, 75, 282, 373);
		getContentPane().setLayout(new AlaNullSizedLayout(282, 353));
		budujObiekty();
		budujListe( System.getProperty("user.dir"), selExt );
		pack();
		setVisible(true);
	}

	// podaj wybrany plik przez uzytkownika
	// jezeli nic nie wybral zwroci null
	public java.io.File getSelectedFile()
	{
		return selectedFile;
	}

	private void checkSelection()
	{
			if ( lastSel == null )
				return;
			String selname = lastSel.toString();
			if ( selname.endsWith(" ]") && selname.startsWith("[ ") )
				selname = selname.substring(2).substring(0, selname.length() - 4);

			java.io.File plikm = new java.io.File( System.getProperty("user.dir") );
			java.io.File pliks = new java.io.File( labDir.getText() );
			pliks = new java.io.File( pliks, selname );
			plikm = new java.io.File( plikm, pliks.getPath() );

			if ( plikm.isDirectory() )
			{
				labDir.setText( pliks.getPath() );
				budujListe( plikm.getPath(), selExt );
				lastSel = null;
			}	else {
				selectedFile = pliks;
				setVisible(false);
			}
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == buttOk )
			checkSelection();
		else if ( o == buttCancel )
			setVisible(false);
	}

	public void mouseClicked( MouseEvent e )
	{
		if ( lastSel == lisPlik.getSelectedValue() )
			checkSelection();
		lastSel = lisPlik.getSelectedValue();
	}

	public void mouseEntered( MouseEvent e )
	{
	}

	public void mouseExited( MouseEvent e )
	{
	}

	public void mousePressed( MouseEvent e )
	{
	}

	public void mouseReleased( MouseEvent e )
	{
	}

}
