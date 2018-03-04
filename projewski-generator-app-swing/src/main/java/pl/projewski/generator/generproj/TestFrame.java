package pl.projewski.generator.generproj;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pk.ie.proj.generproj.layout.AlaNullLayout;
import pk.ie.proj.generproj.layout.AlaNullSizedLayout;
import pk.ie.proj.interfaces.LaborDataInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.Mysys;
import pl.projewski.generator.generproj.layout.AlaNullLayout;
import pl.projewski.generator.generproj.layout.AlaNullSizedLayout;

class TestFrame extends JFrame
	implements ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4369225664988060380L;
	private static final int ID_TITLE = 160;
	private static final int ID_TYPE = 161;
	private static final int ID_INDATA = 162;
	private static final int ID_OUTDATA = 163;
	private static final int ID_PARAM = 164;
	private static final int ID_START = 165;
	private static final int ID_DESC = 166;
	private static final int ID_END = 167;
	private static final int ID_WARNING = 168;
	private static final int ID_WARN_1 = 169;
	private static final int ID_WARN_2 = 170;
	private static final int ID_WARN_OK = 171;

	javax.swing.JLabel labTestType;
	javax.swing.JLabel labInData;
	javax.swing.JLabel labOutData;
	javax.swing.JComboBox comTestType;
	javax.swing.JComboBox comInData;
	javax.swing.JCheckBox chkOutData;
	javax.swing.JTextField texOutData;
	javax.swing.JButton butTestType;
	javax.swing.JLabel labParam;
	//javax.swing.JPanel panParam;
	javax.swing.JScrollPane scrPanParam;
	javax.swing.JButton butOk;
	javax.swing.JButton butExit;
	javax.swing.JButton butDesc;
	ParameterParamPack paramEl;

	private void budujObiekty() {
		labTestType = new javax.swing.JLabel();
		labTestType.setText( GenerProj.getDescription( ID_TYPE ) );
		labTestType.setBounds(15, 15, 120, 25);
		labTestType.setBackground( new Color( 0xffcccccc ) );
		labTestType.setForeground( new Color( 0xff666699 ) );
		labTestType.setEnabled(true);
		labTestType.setVisible(true);
		labTestType.setRequestFocusEnabled(true);
		labTestType.setFont( labTestType.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(labTestType);
		labInData = new javax.swing.JLabel();
		labInData.setText(GenerProj.getDescription( ID_INDATA ));
		labInData.setBounds(15, 45, 120, 25);
		labInData.setBackground( new Color( 0xffcccccc ) );
		labInData.setForeground( new Color( 0xff666699 ) );
		labInData.setEnabled(true);
		labInData.setVisible(true);
		labInData.setRequestFocusEnabled(true);
		labInData.setFont( labInData.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(labInData);
		labOutData = new javax.swing.JLabel();
		labOutData.setText(GenerProj.getDescription( ID_OUTDATA ));
		labOutData.setBounds(15, 75, 120, 25);
		labOutData.setBackground( new Color( 0xffcccccc ) );
		labOutData.setForeground( new Color( 0xff666699 ) );
		labOutData.setEnabled(true);
		labOutData.setVisible(true);
		labOutData.setRequestFocusEnabled(true);
		labOutData.setFont( labOutData.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(labOutData);
		comTestType = new javax.swing.JComboBox( GenerProj.listTest() );
		comTestType.setBounds(140, 15, 190, 25);
		comTestType.setBackground( new Color( 0xffcccccc ) );
		comTestType.setForeground( new Color( 0xff000000 ) );
		comTestType.setEnabled(true);
		comTestType.setVisible(true);
		comTestType.setRequestFocusEnabled(true);
		comTestType.setFont( comTestType.getFont().deriveFont( Font.PLAIN ) );
		comTestType.addActionListener( this );
		getContentPane().add(comTestType);
		comInData = new javax.swing.JComboBox( GenerProj.listGeneratedData() );
		comInData.setBounds(140, 45, 190, 25);
		comInData.setBackground( new Color( 0xffcccccc ) );
		comInData.setForeground( new Color( 0xff000000 ) );
		comInData.setEnabled(true);
		comInData.setVisible(true);
		comInData.setRequestFocusEnabled(true);
		comInData.setFont( comInData.getFont().deriveFont( Font.PLAIN ) );
		comInData.addActionListener( this );
		getContentPane().add(comInData);
		texOutData = new javax.swing.JTextField();
		texOutData.setText("");
		texOutData.setBounds(140, 75, 190, 25);
		texOutData.setBackground( new Color( 0xffffffff ) );
		texOutData.setForeground( new Color( 0xff000000 ) );
		texOutData.setEnabled(true);
		texOutData.setVisible(true);
		texOutData.setRequestFocusEnabled(true);
		texOutData.setFont( texOutData.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(texOutData);
		chkOutData = new javax.swing.JCheckBox();
		chkOutData.setBounds(115, 75, 20, 25);
		chkOutData.setEnabled(true);
		chkOutData.setVisible(true);
		chkOutData.setRequestFocusEnabled(true);
		chkOutData.setSelected(true);
		chkOutData.addActionListener(this);
		getContentPane().add(chkOutData);
		butTestType = new javax.swing.JButton();
		butTestType.setText("...");
		butTestType.setBounds(335, 15, 30, 25);
		butTestType.setBackground( new Color( 0xffcccccc ) );
		butTestType.setForeground( new Color( 0xff000000 ) );
		butTestType.setEnabled(true);
		butTestType.setVisible(true);
		butTestType.setRequestFocusEnabled(true);
		butTestType.setFont( butTestType.getFont().deriveFont( Font.PLAIN ) );
		butTestType.addActionListener( this );
		getContentPane().add(butTestType);
		labParam = new javax.swing.JLabel();
		labParam.setText(GenerProj.getDescription( ID_PARAM ));
		labParam.setBounds(15, 115, 350, 25);
		labParam.setBackground( new Color( 0xffcccccc ) );
		labParam.setForeground( new Color( 0xff996666 ) );
		labParam.setEnabled(true);
		labParam.setVisible(true);
		labParam.setRequestFocusEnabled(true);
		labParam.setFont( labParam.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(labParam);
		scrPanParam = new javax.swing.JScrollPane();
		paramEl = new ParameterParamPack( null );
		//panParam = new javax.swing.JPanel();
		paramEl.setLayout(new AlaNullLayout());
		scrPanParam.getViewport().setView(paramEl);
		scrPanParam.setBounds(15, 145, 355, 170);
		paramEl.setBackground( new Color( 0xffcccccc ) );
		paramEl.setForeground( new Color( 0xff000000 ) );
		paramEl.setEnabled(true);
		paramEl.setVisible(true);
		paramEl.setRequestFocusEnabled(true);
		getContentPane().add(scrPanParam);
		butOk = new javax.swing.JButton();
		butOk.setText(GenerProj.getDescription( ID_START ));
		butOk.setBounds(15, 330, 100, 30);
		butOk.setBackground( new Color( 0xffcccccc ) );
		butOk.setForeground( new Color( 0xff000000 ) );
		butOk.setEnabled(true);
		butOk.setVisible(true);
		butOk.setRequestFocusEnabled(true);
		butOk.setFont( butOk.getFont().deriveFont( Font.PLAIN ) );
		butOk.addActionListener( this );
		getContentPane().add(butOk);
		butExit = new javax.swing.JButton();
		butExit.setText(GenerProj.getDescription( ID_END ));
		butExit.setBounds(270, 330, 100, 30);
		butExit.setBackground( new Color( 0xffcccccc ) );
		butExit.setForeground( new Color( 0xff000000 ) );
		butExit.setEnabled(true);
		butExit.setVisible(true);
		butExit.setRequestFocusEnabled(true);
		butExit.setFont( butExit.getFont().deriveFont( Font.PLAIN ) );
		butExit.addActionListener( this );
		getContentPane().add(butExit);
		butDesc = new javax.swing.JButton();
		butDesc.setText(GenerProj.getDescription( ID_DESC ));
		butDesc.setBounds(122, 330, 100, 30);
		butDesc.setBackground( new Color( 0xffcccccc ) );
		butDesc.setForeground( new Color( 0xff000000 ) );
		butDesc.setEnabled(true);
		butDesc.setVisible(true);
		butDesc.setRequestFocusEnabled(true);
		butDesc.setFont( butDesc.getFont().deriveFont( Font.PLAIN ) );
		butDesc.addActionListener( this );
		getContentPane().add(butDesc);
	}

	protected void autocreateOutName()
	{
		// Okreslenie podstawowej nazwy wynikowej
		String namOfOut="";
		if ( comInData.getSelectedItem() != null )
		{
			try {
				namOfOut = ((GeneratedData)comInData.getSelectedItem()).getName();
			} catch ( Exception e ) {
				e.printStackTrace();
				System.out.println("Tego bledu nie powinno byc !!");
			}
		}
		if ( namOfOut.length() != 0 )
			namOfOut += "_";
		if ( comTestType.getSelectedItem() != null )
		{
				namOfOut += comTestType.getSelectedItem().toString();
		}
		texOutData.setText( namOfOut );
	}

	public TestFrame() {
		super(GenerProj.getDescription( ID_TITLE ));
		getContentPane().setLayout(new AlaNullSizedLayout(379,377));
		addWindowListener(new EventSimpleFrame());
		addKeyListener( this );
		budujObiekty();
		//paramEl = new ParameterParamPack( panParam, null );
		
		ClassItem classItem = (ClassItem) comTestType.getSelectedItem();
		if ( classItem != null )
		{
			try {
				paramEl.setActiveParameter(	(ParameterInterface) classItem.getClassItem().newInstance() );
			} catch ( Exception e ) {
				e.printStackTrace();
				System.out.println("Tego bledu nie powinno byc !!");
			}
		}

		autocreateOutName();
		
		Convert.setCentral( this, 379, 377 );
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == butExit )	{
			GenerProj.doCloseActualFrame();
		} else if ( o == butTestType ) {
			MyFileChooserDialog d = new MyFileChooserDialog( this, "class" );
			String mess = null;
			try {
				if ( d.getSelectedFile() == null ) // nacisni�cie Rezygnuj
					return;
				ClassItem newItem = GenerProj.addTest( d.getSelectedFile().getPath() );
				if ( newItem != null )
					comTestType.addItem( newItem );
			} catch ( ClassCastException e ) { // Nie mo�na zrzutowa� na labordata
				mess = e.toString();
			} catch ( ClassNotFoundException e ) { // Nie odnaleziono klasy
				mess = e.toString();
/*			} catch ( InstantiationException e ) { // Nie mo�na stworzy� instancji
				mess = e.toString();
			} catch ( IllegalAccessException e ) { // Nie mo�na uzyska� dost�pu konstr
				mess = e.toString();
*/			}
			if ( mess != null )
				JOptionPane.showMessageDialog( this, Convert.stringWrap(mess, 30),
						GenerProj.getDescription( ID_WARNING ),
						JOptionPane.WARNING_MESSAGE );
		} else if ( o == comTestType ) {
			ClassItem classItem = (ClassItem) comTestType.getSelectedItem();
			if ( classItem != null )
			{
				try {
					paramEl.setActiveParameter( (ParameterInterface) classItem.getClassItem().newInstance() );
					paramEl.repaint();
					paintAll( getGraphics() );
				} catch ( Exception e ) {
					e.printStackTrace();
					System.out.println("Tego bledu nie powinno byc !!");
				}
			}
			autocreateOutName();
		} else if ( o == comInData ) {
			autocreateOutName();
		} else if ( o == butDesc ) {
			if ( comTestType.getSelectedItem() == null )
				return;
			
			Class<?> klasa = ((ClassItem) comTestType.getSelectedItem()).getClassItem(); 
			String name = klasa.getName();
			
			if ( GenerProj.isHelpFile( name ) )
				GenerProj.showHelp( name );
			else { // Try show help from description from ParameterInterface
				try {
					LaborDataInterface ldi = (LaborDataInterface) klasa.newInstance();
					GenerProj.showHelp( ldi );
				} catch ( Exception e ) {
				}
				return;
			}
		} else if ( o == butOk ) {
			String msg = "";
			if ( comTestType.getSelectedItem() == null )
				msg += GenerProj.getDescription( ID_WARN_1 );
			if ( comInData.getSelectedItem() == null )
				msg += GenerProj.getDescription( ID_WARN_2 );
			if ( msg.length() != 0 )
			{
				JOptionPane.showMessageDialog( this, Convert.stringWrap( msg, 30 ),
					GenerProj.getDescription( ID_WARNING ),
					JOptionPane.WARNING_MESSAGE );
				return;
			}
			if ( texOutData.getText().trim().length() == 0 )
				autocreateOutName();
			//String name = comTestType.getSelectedItem().toString();
			LaborDataInterface ldi = null;
			GeneratedData gd = null;
			try {
				ldi = (LaborDataInterface) ((ClassItem)comTestType.getSelectedItem()).getClassItem().newInstance();
				paramEl.getParameterInterface( ldi );
				gd = (GeneratedData)comInData.getSelectedItem();
			} catch ( Exception e ) {
				e.printStackTrace();
				JOptionPane.showMessageDialog( this,
						Convert.stringWrap( e.toString(), 30 ),
					"BLAD PROGRAMU", JOptionPane.ERROR_MESSAGE );
				return;
			}
//			int cnt = gd.countDataInFile();
			GeneratedData outgd = null;
			String name = texOutData.getText(); // ustalenie nazwy
			// tworzenie danych wynikowych
			if ( chkOutData.isSelected() )
				outgd = new GeneratedData( name ); // utworzenie pliku danych
			else
			{
				try {
					outgd = GeneratedData.createTemporary();
				} catch (IOException e) {
					Mysys.error("Cannot create tempotary file: " + e.toString());
					return;
				}
			}
			outgd.setDataSource(ldi);
			try {
				gd.readFile();
				ldi.setInputData(gd);
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this,
						Convert.stringWrap( e.toString(), 30 ),
						GenerProj.getDescription( ID_WARNING ),
						JOptionPane.WARNING_MESSAGE );
					return;
			}
			
			try {
				ldi.getOutputData(outgd);
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( this,
						Convert.stringWrap( e.toString(), 30 ),
					GenerProj.getDescription( ID_WARNING ),
					JOptionPane.WARNING_MESSAGE );
				return;
			}
			
			AskShowDataDialog askShow = new AskShowDataDialog(this, GenerProj.getDescription( ID_WARN_OK ), outgd); 
			if ( chkOutData.isSelected() )
				GenerProj.addGeneratedData( outgd ); // dodanie ich do projektu
			else
				askShow.setRemoveGeneratedData(true);
				
			GenerProj.doSwitchToFrame(askShow);			
		}
		else if ( o == chkOutData )
		{
			texOutData.setEnabled( chkOutData.isSelected() );
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
	
}
