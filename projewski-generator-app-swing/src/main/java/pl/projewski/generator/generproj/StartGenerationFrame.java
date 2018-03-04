package pl.projewski.generator.generproj;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.generproj.layout.AlaNullSizedLayout;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.stream.NumberWriter;

class StartGenerationFrame extends JFrame
	implements ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7440482810497027750L;
	private static final int ID_TITLE = 140;
	private static final int ID_GENNAME = 141;
	private static final int ID_GENCOUNT = 142;
//	private static final int ID_GENPACK = 143;
	private static final int ID_GENTYPE = 144;
	private static final int ID_START = 145;
	private static final int ID_END = 146;
	private static final int ID_GENFUN = 147;
	private static final int ID_FUN_INIT = 148;
	private static final int ID_FUN_REINIT = 149;
	private static final int ID_FUN_NONE = 150;
	private static final int ID_WARNING = 151;
	private static final int ID_WARN_1 = 152;
	private static final int ID_WARN_2 = 153;
//	private static final int ID_WARN_3 = 154;
//	private static final int ID_WARN_4 = 155;
//	private static final int ID_WARN_5 = 156;
	private static final int ID_GEN_OK = 157;
	
	javax.swing.JLabel JLabel0;
	javax.swing.JTextField texName;
	javax.swing.JCheckBox chkWrite;
	javax.swing.JLabel JLabel2;
//	javax.swing.JLabel JLabel3;
	javax.swing.JLabel JLabel4;
	javax.swing.JTextField texNum;
//	javax.swing.JTextField texPack;
	javax.swing.JComboBox comType;
	javax.swing.JButton buttStart;
	javax.swing.JButton buttExit;
	javax.swing.JLabel JLabel10;
	javax.swing.JButton JButton11;
	GeneratorInterface inGI = null;
	
	private void budujObiekty() {
		JLabel0 = new javax.swing.JLabel();
		JLabel0.setText( GenerProj.getDescription( ID_GENNAME ) );
		JLabel0.setBounds(25, 20, 170, 30);
		JLabel0.setBackground( new Color( 0xffcccccc ) );
		JLabel0.setForeground( new Color( 0xff666699 ) );
		JLabel0.setEnabled(true);
		JLabel0.setVisible(true);
		JLabel0.setRequestFocusEnabled(true);
		JLabel0.setFont( JLabel0.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(JLabel0);
		texName = new javax.swing.JTextField();
		texName.setText("");
		texName.setBounds(190, 15, 145, 30);
		texName.setBackground( new Color( 0xffffffff ) );
		texName.setForeground( new Color( 0xff000000 ) );
		texName.setEnabled(true);
		texName.setVisible(true);
		texName.setRequestFocusEnabled(true);
		texName.setFont( texName.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(texName);
		chkWrite = new javax.swing.JCheckBox("");
		chkWrite.addActionListener( this );
		chkWrite.setBounds(160, 15, 20, 30);
		chkWrite.setSelected(true);
		getContentPane().add(chkWrite);
		JLabel2 = new javax.swing.JLabel();
		JLabel2.setText(GenerProj.getDescription( ID_GENCOUNT ));
		JLabel2.setBounds(25, 55, 160, 30);
		JLabel2.setBackground( new Color( 0xffcccccc ) );
		JLabel2.setForeground( new Color( 0xff666699 ) );
		JLabel2.setEnabled(true);
		JLabel2.setVisible(true);
		JLabel2.setRequestFocusEnabled(true);
		JLabel2.setFont( JLabel2.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(JLabel2);
//		JLabel3 = new javax.swing.JLabel();
//		JLabel3.setText(GenerProj.getDescription( ID_GENPACK ));
//		JLabel3.setBounds(25, 95, 155, 30);
//		JLabel3.setBackground( new Color( 0xffcccccc ) );
//		JLabel3.setForeground( new Color( 0xff666699 ) );
//		JLabel3.setEnabled(true);
//		JLabel3.setVisible(true);
//		JLabel3.setRequestFocusEnabled(true);
//		JLabel3.setFont( JLabel3.getFont().deriveFont( Font.PLAIN ) );
//		getContentPane().add(JLabel3);
		JLabel4 = new javax.swing.JLabel();
		JLabel4.setText(GenerProj.getDescription( ID_GENTYPE ));
		JLabel4.setBounds(25, 135, 140, 30);
		JLabel4.setBackground( new Color( 0xffcccccc ) );
		JLabel4.setForeground( new Color( 0xff666699 ) );
		JLabel4.setEnabled(true);
		JLabel4.setVisible(true);
		JLabel4.setRequestFocusEnabled(true);
		JLabel4.setFont( JLabel4.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(JLabel4);
		texNum = new javax.swing.JTextField();
		texNum.setText("");
		texNum.setBounds(190, 55, 145, 30);
		texNum.setBackground( new Color( 0xffffffff ) );
		texNum.setForeground( new Color( 0xff000000 ) );
		texNum.setEnabled(true);
		texNum.setVisible(true);
		texNum.setRequestFocusEnabled(true);
		texNum.setFont( texNum.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(texNum);
//		texPack = new javax.swing.JTextField();
//		texPack.setText("");
//		texPack.setBounds(190, 95, 145, 30);
//		texPack.setBackground( new Color( 0xffffffff ) );
//		texPack.setForeground( new Color( 0xff000000 ) );
//		texPack.setEnabled(true);
//		texPack.setVisible(true);
//		texPack.setRequestFocusEnabled(true);
//		texPack.setFont( texPack.getFont().deriveFont( Font.PLAIN ) );
//		getContentPane().add(texPack);
		comType = new javax.swing.JComboBox();
		comType.setBounds(190, 135, 145, 30);
		comType.setBackground( new Color( 0xffcccccc ) );
		comType.setForeground( new Color( 0xff000000 ) );
		comType.setEnabled(true);
		comType.setVisible(true);
		comType.setRequestFocusEnabled(true);
		comType.setFont( comType.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(comType);
		buttStart = new javax.swing.JButton();
		buttStart.setText(GenerProj.getDescription( ID_START ));
		buttStart.setBounds(25, 215, 140, 30);
		buttStart.setBackground( new Color( 0xffcccccc ) );
		buttStart.setForeground( new Color( 0xff000000 ) );
		buttStart.setEnabled(true);
		buttStart.setVisible(true);
		buttStart.setRequestFocusEnabled(true);
		buttStart.setFont( buttStart.getFont().deriveFont( Font.PLAIN ) );
		buttStart.addActionListener( this );
		getContentPane().add(buttStart);
		buttExit = new javax.swing.JButton();
		buttExit.setText(GenerProj.getDescription( ID_END ));
		buttExit.setBounds(190, 215, 145, 30);
		buttExit.setBackground( new Color( 0xffcccccc ) );
		buttExit.setForeground( new Color( 0xff000000 ) );
		buttExit.setEnabled(true);
		buttExit.setVisible(true);
		buttExit.setRequestFocusEnabled(true);
		buttExit.setFont( buttExit.getFont().deriveFont( Font.PLAIN ) );
		buttExit.addActionListener( this );
		getContentPane().add(buttExit);
		JLabel10 = new javax.swing.JLabel();
		JLabel10.setText(GenerProj.getDescription( ID_GENFUN ));
		JLabel10.setBounds(25, 170, 140, 35);
		JLabel10.setBackground( new Color( 0xffcccccc ) );
		JLabel10.setForeground( new Color( 0xff666699 ) );
		JLabel10.setEnabled(true);
		JLabel10.setVisible(true);
		JLabel10.setRequestFocusEnabled(true);
		JLabel10.setFont( JLabel10.getFont().deriveFont( Font.PLAIN ) );
		getContentPane().add(JLabel10);
		JButton11 = new javax.swing.JButton();
		JButton11.setText(GenerProj.getDescription( ID_FUN_INIT ));
		JButton11.setBounds(190, 175, 145, 30);
		JButton11.setBackground( new Color( 0xffcccccc ) );
		JButton11.setForeground( new Color( 0xff000000 ) );
		JButton11.setEnabled(true);
		JButton11.setVisible(true);
		JButton11.setRequestFocusEnabled(true);
		JButton11.setFont( JButton11.getFont().deriveFont( Font.PLAIN ) );
		JButton11.addActionListener( this );
		getContentPane().add(JButton11);
		
	}

	public StartGenerationFrame( GeneratorInterface gi, String name ) {
		super( GenerProj.getDescription( ID_TITLE ) );
		/* TO JEST DODATEK, KTÓRY POZWALA NA DZIAŁANIE Z JDK 1.3 */
		/* MOŻLIWE DO ZASTOSOWANIA TYLKO OD JDK 1.3, dla 1.2 można usunąć */
		JPanel pan = new JPanel();
		pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
		pan.getActionMap().put("POMOC", new MyAction( this ));
		setContentPane(pan);
		/* ---------------------------------------------------- */
		getContentPane().setLayout(new AlaNullSizedLayout(363, 261));
		addWindowListener(new EventSimpleFrame());
		budujObiekty();
		if ( name != null )
			texName.setText( name );
		inGI = gi;
		comType.addItem( new ClassItem(Integer.class) );
		comType.addItem( new ClassItem(Float.class) );
		comType.addItem( new ClassItem(Long.class) );
		comType.addItem( new ClassItem(Double.class) );
/*		comType.addItem( new MyComboBoxItem(
				Mysys.getDescription(
						ParameterParamPack.class.getSimpleName(),
						"" + ParameterParamPack.BASE_DIALOG_CLASS_INTEGER,
						"GenerProj") ));
		comType.addItem( new MyComboBoxItem(
				Mysys.getDescription(
						ParameterParamPack.class.getSimpleName(),
						"" + ParameterParamPack.BASE_DIALOG_CLASS_FLOAT,
						"GenerProj") ));
		comType.addItem( new MyComboBoxItem(
				Mysys.getDescription(
						ParameterParamPack.class.getSimpleName(),
						"" + ParameterParamPack.BASE_DIALOG_CLASS_LONG,
						"GenerProj") ));
		comType.addItem( new MyComboBoxItem(
				Mysys.getDescription(
						ParameterParamPack.class.getSimpleName(),
						"" + ParameterParamPack.BASE_DIALOG_CLASS_DOUBLE,
						"GenerProj") ));*/
		addKeyListener( this );
		Convert.setCentral( this, 363, 281 );
	}

	public void actionPerformed( ActionEvent evt )
	{
		Object o = evt.getSource();
		if ( o == buttExit )
		{
			GenerProj.doCloseActualFrame();
		}
		else if ( o == JButton11 )
		{
			if ( JButton11.getText().equals(GenerProj.getDescription( ID_FUN_INIT )) )
				JButton11.setText(GenerProj.getDescription( ID_FUN_REINIT ));
			else if ( JButton11.getText().equals(GenerProj.getDescription( ID_FUN_REINIT )) )
				JButton11.setText(GenerProj.getDescription( ID_FUN_NONE ));
			else
				JButton11.setText(GenerProj.getDescription( ID_FUN_INIT ));
		}
		else if ( o == buttStart )
		{
			long data = System.currentTimeMillis();
			int num = 0;
			String errStr = "";
			try {
				num = Convert.tryToInt( texNum.getText() );
			} catch ( NumberFormatException e ) {
				errStr = GenerProj.getDescription( ID_WARN_1 );
			}

			if ( num == 0 )
				errStr = GenerProj.getDescription( ID_WARN_2 );
			if ( num < 0 )
				num = Math.abs( num );
// Pole nie ma obecnie znaczenia
//			try {
//				pack = Convert.tryToInt( texPack.getText() );
//			} catch ( NumberFormatException e ) {
//				errStr = GenerProj.getDescription( ID_WARN_3 );
//			}

//			if ( pack == 0 )
//				pack = num;

//			if ( pack < 0 )
//				pack = Math.abs( pack );

//			if ( pack != 0 && num % pack != 0 )
//				errStr = GenerProj.getDescription( ID_WARN_4 );

//			if ( pack > num )
//				errStr = GenerProj.getDescription( ID_WARN_5 );

			if ( errStr.length() != 0 )
			{
				JOptionPane.showMessageDialog( this, Convert.stringWrap( errStr, 30 ),
					GenerProj.getDescription( ID_WARNING ), JOptionPane.WARNING_MESSAGE );
				return;
			}

			// wyselekcjonowany typ generacji
			ClassItem classItem = (ClassItem) comType.getSelectedItem();
			Class<?> selectedClass = classItem.getClassItem();


			try {
				// Sposób zainicjowania generatora
				if ( JButton11.getText().equals(GenerProj.getDescription( ID_FUN_INIT )) )
					inGI.init();
				else if ( JButton11.getText().equals(GenerProj.getDescription( ID_FUN_REINIT )) )
					inGI.reinit();

				// ustalanie paczki z plikiem generowanych danych
				GeneratedData gdt;
				if ( chkWrite.isSelected() )
					gdt = new GeneratedData( texName.getText(), data, inGI );
				else
					gdt = new GeneratedData( Mysys.getTempFile().getName(), data, inGI );
				
				// Generacje
				NumberWriter writer = gdt.getNumberWriter();
				ClassEnumerator cl = null;
				gdt.setSize(num);
				if ( selectedClass == Integer.class )
					gdt.setStoreClass(cl = ClassEnumerator.INTEGER);
				else if ( selectedClass == Long.class )
					gdt.setStoreClass(cl = ClassEnumerator.LONG);
				else if ( selectedClass == Float.class )
					gdt.setStoreClass(cl = ClassEnumerator.FLOAT);
				else if ( selectedClass == Double.class )
					gdt.setStoreClass(cl = ClassEnumerator.DOUBLE);
				inGI.rawFill(writer, cl, num);
				writer.close();
								
				// dodanie uzyskanych danych do konfiguracji systemu
				AskShowDataDialog askShow = new AskShowDataDialog( this, GenerProj.getDescription( ID_GEN_OK ), gdt );
				if ( chkWrite.isSelected() )
				{
					gdt.wrtieFile();
					GenerProj.addGeneratedData( gdt );
				}
				else
					askShow.setRemoveGeneratedData( true );
				
				// Info o zakończeniu
				GenerProj.doSwitchToFrame(askShow);
			} catch ( GeneratorException e ) {
				JOptionPane.showMessageDialog( this,
					Convert.stringWrap( e.toString(), 30 ),
					GenerProj.getDescription( ID_WARNING ),
					JOptionPane.WARNING_MESSAGE );
				e.printStackTrace();
			} catch ( Exception e ) {
				e.printStackTrace(); // TODO:
				Mysys.println( e.toString() );
			}
		}
		else if ( o == chkWrite )
		{
			texName.setEnabled( chkWrite.isSelected() );
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
