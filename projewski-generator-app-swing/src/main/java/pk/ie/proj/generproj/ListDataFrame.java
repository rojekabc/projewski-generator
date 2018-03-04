package pk.ie.proj.generproj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import pk.ie.proj.generproj.layout.AlaNullSizedLayout;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.interfaces.ViewDataListener;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.Mysys;

class ListDataFrame extends JFrame
	implements ActionListener, KeyListener, ViewDataListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4750492184453133033L;
	private static final int ID_DLG_TITLE = 110;
	private static final int ID_DLG_VIEW = 111;
	private static final int ID_DLG_REMOVE = 112;
	private static final int ID_DLG_END = 113;
	private static final int ID_DLG_WARNING = 114;

	javax.swing.JList listData;
	javax.swing.JButton buttShow;
	javax.swing.JButton buttRemove;
	javax.swing.JButton buttExit;
	javax.swing.JComboBox comViewType;
	javax.swing.JButton butViewSel;
	javax.swing.JScrollPane scrListData;
	ViewDataInterface view = null;

	private void budujObiekty() {
		scrListData = new javax.swing.JScrollPane();
		listData = new javax.swing.JList();
		scrListData.getViewport().setView( listData );
		scrListData.setBounds(25, 65, 270, 275);
		listData.setBackground( new Color( 0xffffffff ) );
		listData.setForeground( new Color( 0xff000000 ) );
		listData.setEnabled(true);
		listData.setVisible(true);
		listData.setRequestFocusEnabled(true);
		getContentPane().add(scrListData);
		buttShow = new javax.swing.JButton();
		buttShow.setText( GenerProj.getDescription( ID_DLG_VIEW ) );
		buttShow.setBounds(305, 20, 100, 35);
		buttShow.setBackground( new Color( 0xffcccccc ) );
		buttShow.setForeground( new Color( 0xff000000 ) );
		buttShow.setEnabled(true);
		buttShow.setVisible(true);
		buttShow.setRequestFocusEnabled(true);
		buttShow.setFont( buttShow.getFont().deriveFont( Font.PLAIN ) );
		buttShow.addActionListener( this );
		getContentPane().add(buttShow);
		buttRemove = new javax.swing.JButton();
		buttRemove.setText( GenerProj.getDescription(ID_DLG_REMOVE) );
		buttRemove.setBounds(305, 65, 100, 35);
		buttRemove.setBackground( new Color( 0xffcccccc ) );
		buttRemove.setForeground( new Color( 0xff000000 ) );
		buttRemove.setEnabled(true);
		buttRemove.setVisible(true);
		buttRemove.setRequestFocusEnabled(true);
		buttRemove.setFont( buttRemove.getFont().deriveFont( Font.PLAIN ) );
		buttRemove.addActionListener( this );
		getContentPane().add(buttRemove);
		buttExit = new javax.swing.JButton();
		buttExit.setText( GenerProj.getDescription(ID_DLG_END) );
		buttExit.setBounds(305, 305, 100, 35);
		buttExit.setBackground( new Color( 0xffcccccc ) );
		buttExit.setForeground( new Color( 0xff000000 ) );
		buttExit.setEnabled(true);
		buttExit.setVisible(true);
		buttExit.addActionListener( this );
		buttExit.setFont( buttExit.getFont().deriveFont( Font.PLAIN ) );
		buttExit.setRequestFocusEnabled(true);
		getContentPane().add(buttExit);
		comViewType = new javax.swing.JComboBox( GenerProj.listView() );
		comViewType.setBounds(25, 20, 220, 35);
		comViewType.setBackground( new Color( 0xffcccccc ) );
		comViewType.setForeground( new Color( 0xff000000 ) );
		comViewType.setEnabled(true);
		comViewType.setVisible(true);
		comViewType.setRequestFocusEnabled(true);
		comViewType.setFont( comViewType.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(comViewType);
		butViewSel = new javax.swing.JButton();
		butViewSel.setText("...");
		butViewSel.setBounds(250, 20, 44, 35);
		butViewSel.setBackground( new Color( 0xffcccccc ) );
		butViewSel.setForeground( new Color( 0xff000000 ) );
		butViewSel.setEnabled(true);
		butViewSel.setVisible(true);
		butViewSel.setRequestFocusEnabled(true);
		butViewSel.setFont( butViewSel.getFont().deriveFont( Font.PLAIN ) );
		butViewSel.addActionListener( this );
		getContentPane().add(butViewSel);
	}

	public ListDataFrame() {
		super( GenerProj.getDescription(ID_DLG_TITLE) );
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
		budujObiekty();
		listData.setListData( GenerProj.listGeneratedData() );
		addKeyListener( this );
		Convert.setCentral( this, 427, 384 );
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == buttExit )
		{
			GenerProj.doCloseActualFrame();
		}
		else if ( o == buttShow )
		{
			ClassItem classItem = (ClassItem) comViewType.getSelectedItem();
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
			Object selObj = listData.getSelectedValue();
			if ( selObj == null )
				return;
			GeneratedData gd = (GeneratedData)selObj;
			try {
				if ( !gd.readFile() )
				{
					Mysys.error("Brak pliku z danymi");
					return;
				}
				view.setData(gd);
				view.addViewDataListener(this);
				view.showView( );
			} catch ( Exception e ) {
				// TODO: Komunikat błędu z przyciskiem OK
				Mysys.error(e.toString());
			}
			
			return;
		}
		else if ( o == buttRemove )
		{
			Object selObj = listData.getSelectedValue();
			if ( selObj == null )
				return;
			GenerProj.removeGeneratedData( selObj );
			listData.setListData( GenerProj.listGeneratedData() );
		}
		else if ( o == butViewSel )
		{
			MyFileChooserDialog d = new MyFileChooserDialog( this, "class" );
			String mess = null;
			try {
				if ( d.getSelectedFile() == null ) // nacisnięcie Rezygnuj
					return;
				ClassItem newItem = GenerProj.addView( d.getSelectedFile().getPath() );
				if ( newItem != null )
					comViewType.addItem( newItem );
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
						GenerProj.getDescription(ID_DLG_WARNING), JOptionPane.WARNING_MESSAGE );
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

	public void onMouseEvent(Frame frame, ParameterInterface pi, MouseEvent event) {
		if (( event.getID() == MouseEvent.MOUSE_RELEASED ) && (event.getModifiers() == InputEvent.BUTTON3_MASK ))
		{
			ParameterDialog.doParameterDialog(frame, pi, view);
		}
	}
}
