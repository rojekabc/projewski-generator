package pl.projewski.generator.generproj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.generproj.layout.AlaNullSizedLayout;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.interfaces.ViewDataListener;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.Mysys;
import pl.projewski.generator.generproj.layout.AlaNullSizedLayout;

class AskShowDataDialog extends JFrame
	implements KeyListener, ActionListener, ViewDataListener, WindowListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5756729542764897849L;
	private static final int ID_TITLE=180;
	private static final int ID_VIEW=181;
	private static final int ID_OK=182;
	private static final int ID_WARNING=183;

	javax.swing.JLabel labDesc;
	javax.swing.JComboBox comView;
	javax.swing.JButton butSelView;
	javax.swing.JButton butView;
	javax.swing.JButton butOk;
	GeneratedData actGdt;
	ViewDataInterface view;
	boolean remGenDataOnClose;
	
	private void budujObiekty() {
		labDesc = new javax.swing.JLabel();
		labDesc.setText("");
		labDesc.setBounds(10, 10, 255, 25);
		labDesc.setBackground( new Color( 0xffcccccc ) );
		labDesc.setForeground( new Color( 0xff666699 ) );
		labDesc.setEnabled(true);
		labDesc.setVisible(true);
		labDesc.setRequestFocusEnabled(true);
		labDesc.setFont( labDesc.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(labDesc);
		
		// ustal ródło danych
		ParameterInterface dataSource = null;
		try {
			dataSource = actGdt.getDataSource();
		} catch (NumberStoreException e) {
			dataSource = null;
		}
		comView = new javax.swing.JComboBox( GenerProj.listView(dataSource) );
		comView.setBounds(10, 40, 215, 25);
		comView.setBackground( new Color( 0xffcccccc ) );
		comView.setForeground( new Color( 0xff000000 ) );
		comView.setEnabled(true);
		comView.setVisible(true);
		comView.setRequestFocusEnabled(true);
		comView.setFont( comView.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(comView);
		butSelView = new javax.swing.JButton();
		butSelView.setText("...");
		butSelView.setBounds(225, 40, 35, 25);
		butSelView.setBackground( new Color( 0xffcccccc ) );
		butSelView.setForeground( new Color( 0xff000000 ) );
		butSelView.setEnabled(true);
		butSelView.setVisible(true);
		butSelView.setRequestFocusEnabled(true);
		butSelView.addActionListener( this );
		butSelView.setFont( butSelView.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(butSelView);
		butView = new javax.swing.JButton();
		butView.setText( GenerProj.getDescription(ID_VIEW) );
		butView.setBounds(10, 75, 130, 25);
		butView.setBackground( new Color( 0xffcccccc ) );
		butView.setForeground( new Color( 0xff000000 ) );
		butView.setEnabled(true);
		butView.setVisible(true);
		butView.setRequestFocusEnabled(true);
		butView.setFont( butView.getFont().deriveFont( Font.PLAIN ) );
		butView.addActionListener( this );
		getContentPane().add(butView);
		butOk = new javax.swing.JButton();
		butOk.setText(GenerProj.getDescription(ID_OK));
		butOk.setBounds(140, 75, 120, 25);
		butOk.setBackground( new Color( 0xffcccccc ) );
		butOk.setForeground( new Color( 0xff000000 ) );
		butOk.setEnabled(true);
		butOk.setVisible(true);
		butOk.setRequestFocusEnabled(true);
		butOk.setFont( butOk.getFont().deriveFont( Font.PLAIN ) );
		butOk.addActionListener( this );
		getContentPane().add(butOk);
	}

	public AskShowDataDialog(JFrame parent, String desc, GeneratedData gdt)
	{
		super( GenerProj.getDescription(ID_TITLE) );
		//super( parent, GenerProj.getDescription(ID_TITLE), true );
		
		this.remGenDataOnClose = false;
		
		JPanel pan = new JPanel();
		pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
		pan.getActionMap().put("POMOC", new MyAction( this ));
		setContentPane(pan);

		getContentPane().setLayout(new AlaNullSizedLayout(270, 120));
		actGdt = gdt;
		budujObiekty();
		if ( desc != null )
		{
			labDesc.setFont( labDesc.getFont().deriveFont( java.awt.Font.PLAIN ) );
			labDesc.setText(desc);
		}
		comView.setMaximumRowCount(3);
		addKeyListener( this );
		this.addWindowListener(this);
		Convert.setCentral(this, 270, 120);
		pack();
		setVisible(true);
	}
	
	public void setRemoveGeneratedData(boolean b)
	{
		this.remGenDataOnClose = b;
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == butOk )
		{
			windowClosing(null);
		}
		else if ( o == butSelView )
		{
			MyFileChooserDialog d = new MyFileChooserDialog( this, "class" );
			String mess = null;
			try {
				if ( d.getSelectedFile() == null ) // nacisnięcie Rezygnuj
					return;
				ClassItem newItem = GenerProj.addView( d.getSelectedFile().getPath() );
				if ( newItem != null )
				{
					comView.addItem( newItem );
					comView.setSelectedItem( newItem );
				}
			} catch ( ClassCastException e ) { // Nie można zrzutować na labordata
				mess = e.toString();
			} catch ( ClassNotFoundException e ) { // Nie odnaleziono klasy
				mess = e.toString();
/*			} catch ( InstantiationException e ) { // Nie można stworzyć instancji
				mess = e.toString();
			} catch ( IllegalAccessException e ) { // Nie można uzyskać dostępu konstr
				mess = e.toString();
*/			}
			if ( mess != null )
				JOptionPane.showMessageDialog( this, Convert.stringWrap(mess, 30),
						GenerProj.getDescription(ID_WARNING),
						JOptionPane.WARNING_MESSAGE );
			return;
		} else if ( o == butView ) {
			ClassItem classItem = (ClassItem) comView.getSelectedItem();
			if ( classItem == null )
				return;
			view = null;
			try {
				view = (ViewDataInterface) classItem.getClassItem().newInstance();
			} catch ( Exception e ) {
				e.printStackTrace();
				Mysys.println("Error not should be here");
				return;
			}
			try {
				view.setData(actGdt);
				Mysys.debugln("I want to add ViewDataListenet");
				view.addViewDataListener(this);
				view.showView( );
			} catch ( Exception e ) {
				e.printStackTrace();
				Mysys.println( e.toString() );
			}
			return;
		}
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

	public void onMouseEvent(Frame frame, ParameterInterface pi, MouseEvent event)
	{
		if (( event.getID() == MouseEvent.MOUSE_RELEASED ) && (event.getModifiers() == InputEvent.BUTTON3_MASK ))
		{
			ParameterDialog.doParameterDialog(frame, pi, view);
		}
	}

	// --- Component Listener Methods ---
/*	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}
*/
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		GenerProj.doCloseActualFrame();
		if ( this.remGenDataOnClose )
		{
			if ( !this.actGdt.delete() )
				Mysys.error("Cannot remove temporary file");
		}
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
