package pl.projewski.generator.generproj;

import pk.ie.proj.generproj.layout.AlaNullSizedLayout;
import pk.ie.proj.tools.Convert;
import pl.projewski.generator.generproj.layout.AlaNullSizedLayout;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class MainFrame extends JFrame
	implements ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7761616523942914095L;
	private static final int ID_DIALOG_BUT_GENERATORS = 80;
	private static final int ID_DIALOG_BUT_TESTS = 81;
	private static final int ID_DIALOG_BUT_DATAS = 82;
	private static final int ID_DIALOG_BUT_END = 83;
	private static final int ID_DIALOG_TITLE = 84;

	javax.swing.JButton buttGenerator;
	javax.swing.JButton buttTest;
	javax.swing.JButton buttData;
	javax.swing.JButton buttExit;
	private void budujObiekty() {
		buttGenerator = new javax.swing.JButton();
		buttGenerator.setText( GenerProj.getDescription(ID_DIALOG_BUT_GENERATORS) );
		buttGenerator.setBounds(25, 20, 215, 35);
		buttGenerator.setBackground( new Color( 0xffcccccc ) );
		buttGenerator.setForeground( new Color( 0xff000000 ) );
		buttGenerator.setEnabled(true);
		buttGenerator.setVisible(true);
		buttGenerator.setRequestFocusEnabled(true);
		buttGenerator.addActionListener( this );
		getContentPane().add(buttGenerator);
		buttTest = new javax.swing.JButton();
		buttTest.setText( GenerProj.getDescription(ID_DIALOG_BUT_TESTS) );
		buttTest.setBounds(25, 65, 215, 35);
		buttTest.setBackground( new Color( 0xffcccccc ) );
		buttTest.setForeground( new Color( 0xff000000 ) );
		buttTest.setEnabled(true);
		buttTest.setVisible(true);
		buttTest.setRequestFocusEnabled(true);
		buttTest.addActionListener( this );
		getContentPane().add(buttTest);
		buttData = new javax.swing.JButton();
		buttData.setText( GenerProj.getDescription(ID_DIALOG_BUT_DATAS) );
		buttData.setBounds(25, 110, 215, 35);
		buttData.setBackground( new Color( 0xffcccccc ) );
		buttData.setForeground( new Color( 0xff000000 ) );
		buttData.setEnabled(true);
		buttData.setVisible(true);
		buttData.setRequestFocusEnabled(true);
		buttData.addActionListener( this );
		getContentPane().add(buttData);
		buttExit = new javax.swing.JButton();
		buttExit.setText( GenerProj.getDescription(ID_DIALOG_BUT_END) );
		buttExit.setBounds(25, 155, 215, 35);
		buttExit.setBackground( new Color( 0xffcccccc ) );
		buttExit.setForeground( new Color( 0xff000000 ) );
		buttExit.setEnabled(true);
		buttExit.setVisible(true);
		buttExit.setRequestFocusEnabled(true);
		buttExit.addActionListener( this );
		getContentPane().add(buttExit);
	}

	public MainFrame()
	{
		super( GenerProj.getDescription(ID_DIALOG_TITLE));

		/* TO JEST DODATEK, KTÓRY POZWALA NA DZIAŁANIE Z JDK 1.3 */
		/* MOŻLIWE DO ZASTOSOWANIA TYLKO OD JDK 1.3, dla 1.2 można usunąć */
		JPanel pan = new JPanel();
		pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
		pan.getActionMap().put("POMOC", new MyAction( this ));
		setContentPane(pan);
		/* ---------------------------------------------------- */
		getContentPane().setLayout(new AlaNullSizedLayout(269, 219));
		addWindowListener(new EventSimpleFrame());
		addKeyListener( this );
		budujObiekty();
		Convert.setCentral( this, 269, 239 );
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == buttExit )
			GenerProj.doCloseActualFrame();
		else if ( o == buttGenerator )
			GenerProj.doSwitchToFrame( new GeneratorListFrame() );
		else if ( o == buttData )
			GenerProj.doSwitchToFrame( new ListDataFrame() );
		else if ( o == buttTest )
			GenerProj.doSwitchToFrame( new TestFrame() );
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
