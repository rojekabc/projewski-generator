package pk.ie.proj.generproj;

import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.generproj.layout.AlaNullSizedLayout;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.tools.Convert;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class GeneratorListFrame extends JFrame
	implements ActionListener, ComponentListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2171416777972875055L;
	private static final int ID_DLG_TITLE = 90;
	private static final int ID_DLG_LOAD = 91;
	private static final int ID_DLG_NEW = 92;
	private static final int ID_DLG_REMOVE = 93;
	private static final int ID_DLG_VIEW = 94;
	private static final int ID_DLG_END = 95;
	private static final int ID_DLG_WARNING = 96;

	javax.swing.JList listGener;
	javax.swing.JButton buttLoad;
	javax.swing.JButton buttNew;
	javax.swing.JButton buttRemove;
	javax.swing.JButton buttLook;
	javax.swing.JButton buttExit;
	javax.swing.JScrollPane scrListGen;

	private void budujObiekty() {
		scrListGen = new javax.swing.JScrollPane();
		listGener = new javax.swing.JList();
		scrListGen.getViewport().setView(listGener);
		scrListGen.setBounds(25, 20, 270, 320);
		listGener.setBackground( new Color( 0xffffffff ) );
		listGener.setForeground( new Color( 0xff000000 ) );
		listGener.setEnabled(true);
		listGener.setVisible(true);
		listGener.setRequestFocusEnabled(true);
		getContentPane().add(scrListGen);
		
		buttLook = new javax.swing.JButton();
		buttLook.setText( GenerProj.getDescription( ID_DLG_VIEW ) );
		buttLook.setBounds(305, 20, 100, 35);
		buttLook.setBackground( new Color( 0xffcccccc ) );
		buttLook.setForeground( new Color( 0xff000000 ) );
		buttLook.setEnabled(true);
		buttLook.setVisible(true);
		buttLook.setRequestFocusEnabled(true);
		buttLook.setFont( buttLook.getFont().deriveFont( Font.PLAIN ) );
		buttLook.addActionListener( this );
		getContentPane().add(buttLook);
		
		buttRemove = new javax.swing.JButton();
		buttRemove.setText( GenerProj.getDescription( ID_DLG_REMOVE ) );
		buttRemove.setBounds(305, 65, 100, 35);
		buttRemove.setBackground( new Color( 0xffcccccc ) );
		buttRemove.setForeground( new Color( 0xff000000 ) );
		buttRemove.setEnabled(true);
		buttRemove.setVisible(true);
		buttRemove.setRequestFocusEnabled(true);
		buttRemove.setFont( buttRemove.getFont().deriveFont( Font.PLAIN ) );
		buttRemove.addActionListener( this );
		getContentPane().add(buttRemove);

		javax.swing.JButton buttTmp = new javax.swing.JButton();
		buttTmp.setText( "" );
		buttTmp.setBounds(305, 105, 100, 4);
		buttTmp.setBackground( new Color( 0xffcccccc ) );
		buttTmp.setForeground( new Color( 0xff000000 ) );
		buttTmp.setEnabled(false);
		buttTmp.setVisible(true);
		buttTmp.setRequestFocusEnabled(false);
		buttTmp.addActionListener( this );
		getContentPane().add(buttTmp);

		buttNew = new javax.swing.JButton();
		buttNew.setText( GenerProj.getDescription( ID_DLG_NEW ) );
		buttNew.setBounds(305, 115, 100, 35);
		buttNew.setBackground( new Color( 0xffcccccc ) );
		buttNew.setForeground( new Color( 0xff000000 ) );
		buttNew.setEnabled(true);
		buttNew.setVisible(true);
		buttNew.setRequestFocusEnabled(true);
		buttNew.setFont( buttNew.getFont().deriveFont( Font.PLAIN ) );
		buttNew.addActionListener( this );
		getContentPane().add(buttNew);

		buttLoad = new javax.swing.JButton();
		buttLoad.setText( GenerProj.getDescription( ID_DLG_LOAD ) );
		buttLoad.setBounds(305, 160, 100, 35);
		buttLoad.setBackground( new Color( 0xffcccccc ) );
		buttLoad.setForeground( new Color( 0xff000000 ) );
		buttLoad.setEnabled(true);
		buttLoad.setVisible(true);
		buttLoad.setRequestFocusEnabled(true);
		buttLoad.setFont( buttLoad.getFont().deriveFont( Font.PLAIN ) );
		buttLoad.addActionListener( this );
		getContentPane().add(buttLoad);

		buttExit = new javax.swing.JButton();
		buttExit.setText( GenerProj.getDescription( ID_DLG_END ) );
		buttExit.setBounds(305, 305, 100, 35);
		buttExit.setBackground( new Color( 0xffcccccc ) );
		buttExit.setForeground( new Color( 0xff000000 ) );
		buttExit.setEnabled(true);
		buttExit.setVisible(true);
		buttExit.setRequestFocusEnabled(true);
		buttExit.setFont( buttExit.getFont().deriveFont( Font.PLAIN ) );
		buttExit.addActionListener( this );
		getContentPane().add(buttExit);
	}

	public GeneratorListFrame() {
		super( GenerProj.getDescription( ID_DLG_TITLE ) );
		/* TO JEST DODATEK, KTÓRY POZWALA NA DZIAŁANIE Z JDK 1.3 */
		/* MOŻLIWE DO ZASTOSOWANIA TYLKO OD JDK 1.3, dla 1.2 można usunąć */
		JPanel pan = new JPanel();
		pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
		pan.getActionMap().put("POMOC", new MyAction( this ));
		setContentPane(pan);
		/* ---------------------------------------------------- */
		getContentPane().setLayout(new AlaNullSizedLayout(427, 364));
		addWindowListener(new EventSimpleFrame());
		addComponentListener( this );
		budujObiekty();
		listGener.setListData( GenerProj.listGenerator() );
		addKeyListener( this );
		Convert.setCentral( this, 427, 384 );
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == buttExit )
			GenerProj.doCloseActualFrame();
		else if ( o == buttNew )
		{
			GenerProj.doSwitchToFrame( new NewGeneratorFrame( null, null ) );
		}
		else if ( o == buttLoad )
		{
			MyFileChooserDialog d = new MyFileChooserDialog( this, "gen" );
			if ( d.getSelectedFile() == null )
				return;
			String filename = d.getSelectedFile().getName();
			if ( filename.endsWith(".gen") )
				filename = filename.substring(0, filename.length()-4);
			String mess = null;
			try {
				GenerProj.addGenerator( filename, null );
				listGener.setListData( GenerProj.listGenerator() );
			} catch ( GeneratorException e ) {
				mess = e.toString(); // TODO: Daj komunikat
			}
			if ( mess != null )
				JOptionPane.showMessageDialog( this, Convert.stringWrap(mess, 30),
						GenerProj.getDescription(ID_DLG_WARNING), JOptionPane.WARNING_MESSAGE );
		}
		else if ( o == buttRemove )
		{
			Object selObj = listGener.getSelectedValue();
			if ( selObj == null )
				return;
			GenerProj.removeGenerator( selObj.toString() );
			listGener.setListData( GenerProj.listGenerator() );
		}
		else if ( o == buttLook )
		{
			Object selObj = listGener.getSelectedValue();
			if ( selObj == null )
				return;
			String mess = null;
			try {
				GeneratorInterface gi = GenerProj.createGenerator( selObj.toString() );
				if ( gi != null )
					GenerProj.doSwitchToFrame( new NewGeneratorFrame( gi,
								selObj.toString() ) );
			} catch ( GeneratorException e ) {
				mess = e.toString();
			}
			if ( mess != null )
				JOptionPane.showMessageDialog( this, Convert.stringWrap(mess, 30),
						GenerProj.getDescription(ID_DLG_WARNING), JOptionPane.WARNING_MESSAGE );
		}
	}

	public void componentHidden( ComponentEvent e ) {}
	public void componentMoved( ComponentEvent e ) {}
	public void componentResized( ComponentEvent e ) {}
	public void componentShown( ComponentEvent e )
	{
		listGener.setListData( GenerProj.listGenerator() );
	}

	public void keyPressed( KeyEvent e )
	{
	}

	public void keyReleased( KeyEvent e )
	{
		if ( e.getKeyCode() == KeyEvent.VK_F1 )
			GenerProj.showHelp( this );
	}

	public void keyTyped( KeyEvent e )
	{
	}
	
}
