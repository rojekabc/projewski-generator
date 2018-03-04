package pl.projewski.generator.viewdata.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pk.ie.proj.distribution.ChiSquare;
import pk.ie.proj.exceptions.NumberStoreException;
import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.interfaces.NumberInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.labordata.TestChiSquare;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;
import pk.ie.proj.tools.stream.NumberReader;
import pl.projewski.generator.distribution.ChiSquare;
import pl.projewski.generator.labordata.TestChiSquare;


// TODO: UWAGA - Wycofano rozmiar podawany na poczatku, a mowiacy o liczbie V
public class ViewTestChiSquare
	extends ViewDataInterface
{	
	// TODO: Ustalic ten parametr z otrzymanych danych
	int _v = 0;
	NumberInterface _data;
	javax.swing.JFrame _frame;

	public static final String COLS = "Liczba kolumn";

	public void initParameterInterface()
	{
		parameters.put(COLS, Integer.valueOf(3));
	}
	
	public Class <?> [] getAllowedClass(String param)
	{
		return new Class[0];
	}
	
	public boolean canViewData(ParameterInterface dataSource)
	{
		return dataSource instanceof TestChiSquare;
	}

	/** M4_GEN_VDI_SETDATA_NSI  */
	public void setData(NumberInterface data)
		throws ViewDataException
	{
		// sprawdzenie, czy dane podano
		if ( data == null )
			return;
		
		// Ustalenie stopnia swobody
		try 
		{
			ParameterInterface pi = data.getDataSource();
			if ( pi instanceof TestChiSquare )
			{
				TestChiSquare tch = (TestChiSquare)pi;
				_v = Convert.tryToInt( tch.getParameter(TestChiSquare.V) );
			}
			else
				throw new ViewDataException("Dane nie pochodz z klasy " + TestChiSquare.class.getSimpleName());
		} catch (NumberStoreException e) {
			throw new ViewDataException(e);
		} catch (NumberFormatException e) {
			throw new ViewDataException(e);
		} catch (ClassCastException e) {
			throw new ViewDataException(e);
		} catch (ParameterException e) {
			throw new ViewDataException(e);
		}
		
		// Okreslenie danych
		_data = data;
	}
	
	public void showView()
		throws ViewDataException
	{
		_frame = new javax.swing.JFrame("Test Chi Square");
		_frame.getContentPane().add(new ViewTestChiSquarePanel(this));
		_frame.setBounds(0, 0, 320, 320);
		_frame.setVisible(true);
		_frame.setBounds(0, 0, 320, 320);
	}


	public Object getView()
		throws ViewDataException
	{
		return null;
	}

	public void refreshView()
		throws ViewDataException
	{
	}
}

class ViewTestChiSquareDialog
	extends JDialog
	implements ChangeListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String [] itemname =
	{
		"0.1%", "0.5%", "1%", "2.5%", "5%", "10%", "20%", "30%", "40%", "50%"
	};
	static double [] itemvalue =
	{
		0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.2, 0.3, 0.4, 0.5
	};
	JLabel labName;
	JTextField editName;
	JLabel labProc;
	JComboBox comboProc;
	JLabel labKod;
	JPanel labKodKolor;
	JSlider pNum;
	JButton butok;
	JButton butcancel;
	boolean _isOk = false;

	public boolean isOk()
	{
		return _isOk;
	}

	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		if ( o == butok )
		{
			this.setVisible(false);
			_isOk = true;
		}
		else if ( o == butcancel )
		{
			this.setVisible(false);
			_isOk = false;
		}
	}

	public java.awt.Color getCode()
	{
		int val = pNum.getValue();
		return new java.awt.Color(val, val, val);
	}

	public String getName()
	{
		return editName.getText();
	}

	public String getItemName()
	{
		return itemname[comboProc.getSelectedIndex()];
	}

	public double getItemValue()
	{
		return itemvalue[comboProc.getSelectedIndex()];
	}

	public void stateChanged(ChangeEvent e)
	{
		int val = pNum.getValue();
		labKodKolor.setBackground(new java.awt.Color(val, val, val));
	}

	ViewTestChiSquareDialog(JFrame parent)
	{
		super(parent, "View Test Chi Square Dialog", true);
//		super("Ustawienia Progu akceptacji");
		getContentPane().setLayout(null);
		labName = new javax.swing.JLabel("Nazwa");
		labName.setBounds(10, 10, 50, 30);
		editName = new javax.swing.JTextField();
		editName.setBounds(100, 10, 180, 20);
		labProc = new javax.swing.JLabel("Prog");
		labProc.setBounds(10, 40, 50, 30);
		comboProc = new javax.swing.JComboBox();
		comboProc.setBounds(100, 40, 180, 20);
		for ( int i=0; i<itemname.length; i++)
			comboProc.addItem(itemname[i]);
		comboProc.setSelectedIndex(0);
		labKod = new javax.swing.JLabel("Kod");
		labKod.setBounds(10, 70, 50, 30);
		labKodKolor = new javax.swing.JPanel();
		labKodKolor.setBounds(60, 80, 20, 20);
		labKodKolor.setBackground(new java.awt.Color(0, 0, 0));
		butok = new javax.swing.JButton("Ok");
		butok.addActionListener(this);
		butok.setBounds(100, 120, 80, 20);
		butcancel = new javax.swing.JButton("Cancel");
		butcancel.addActionListener(this);
		butcancel.setBounds(200, 120, 80, 20);
		
//		pNum.setMajorTickSpacing(45);
//		pNum.setMinorTickSpacing(5);
		pNum = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
		pNum.setPaintLabels(false);
		pNum.setPaintTicks(false);
		pNum.setBounds(100, 70, 180, 30);
		pNum.addChangeListener(this);
		getContentPane().add(labName);
		getContentPane().add(editName);
		getContentPane().add(labProc);
		getContentPane().add(comboProc);
		getContentPane().add(labKod);
		getContentPane().add(labKodKolor);
		getContentPane().add(pNum);
		getContentPane().add(butok);
		getContentPane().add(butcancel);
		setBounds(0, 0, 300, 180);
		setVisible(true);
	}
}

class ViewTestChiSquarePanel
	extends javax.swing.JPanel
	implements java.awt.event.MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ViewTestChiSquare _vtcs;

	javax.swing.JLabel zakresButton;
	java.util.Vector<Object> vecProg = new java.util.Vector<Object>();

	
	public ViewTestChiSquarePanel(ViewTestChiSquare vtcs)
	{
		setLayout(null);
		setBackground( java.awt.Color.white );
		setForeground( java.awt.Color.black );
		zakresButton = new javax.swing.JLabel("Wynik          Próg    Kod");
		zakresButton.setBackground( java.awt.Color.white );
		zakresButton.setForeground( java.awt.Color.black );
		zakresButton.setBounds(180, 10, 120, 20);
		zakresButton.addMouseListener(this);
		add(zakresButton);
		_vtcs = vtcs;
	}
	public void mouseClicked(java.awt.event.MouseEvent e)
	{
		ViewTestChiSquareDialog dialog;
		dialog = new ViewTestChiSquareDialog(_vtcs._frame);
		if ( dialog.isOk() )
		{
			vecProg.add(dialog.getName());
			vecProg.add(dialog.getItemName());
			vecProg.add(new Double(dialog.getItemValue()));
			vecProg.add(dialog.getCode());
			repaint();
		}
	}
	public void mouseEntered(java.awt.event.MouseEvent e)
	{
	}
	public void mouseExited(java.awt.event.MouseEvent e)
	{
	}
	public void mousePressed(java.awt.event.MouseEvent e)
	{
	}
	public void mouseReleased(java.awt.event.MouseEvent e)
	{
	}
/*	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		if ( e.getObject() == zakresButton )
		{
		}
	}*/
	public void paintComponent(java.awt.Graphics g)
	{
		NumberReader reader = null;
		try
		{
			super.paintComponent(g);
			int i;
			double [] src = new double[vecProg.size()/2];
			
			reader = _vtcs._data.getNumberReader();
			/* rysowanie pola informaczjnego */
			g.drawLine(160, 30, 300, 30);
			for ( i=0; i<vecProg.size(); i+=4 )
			{
				java.awt.Color kolor = (Color)vecProg.get(i+3);
				g.setColor(java.awt.Color.black);
				g.drawString(((String)vecProg.get(i)), 160, 50 + (i/4)*20);
				g.drawString(((String)vecProg.get(i+1)), 250, 50 + (i/4)*20);
				// wypelnia dwie polowki tablicy - prawdopodobienstwami p i 1-p
				src[i/4] = ((Double)vecProg.get(i+2)).doubleValue();
				src[src.length-(i/4)-1] = 1.0-((Double)vecProg.get(i+2)).doubleValue();
				g.setColor(kolor);
				g.fillOval(280, 40 + (i/4)*20, 15, 15);
				if ( kolor.getRed() > 250 )
				{
					g.setColor(java.awt.Color.black);
					g.drawOval(280, 40 + (i/4)*20, 14, 14);
				}
			}
			ChiSquare chidist = new ChiSquare();
			chidist.setParameter(ChiSquare.V, Integer.valueOf(_vtcs._v));
			// pobiera wartosci graniczne chi square dla podanych prawdopodobienstw
			for ( i=0; i<src.length; i++ )
				src[i] = chidist.getInverse(src[i]);
			/* rysowanie kwadratow */
			int x, y,j;
			int kols = Convert.tryToInt(_vtcs.getParameter(ViewTestChiSquare.COLS));
			double tmp;
			x = 10;
			y = 10;
			i = 1; // liczba danych
			
			while ( reader.hasNext() )
			{
				tmp = reader.readDouble();
				g.setColor( java.awt.Color.black );
				g.drawRect(x, y, 25, 25);
				int pos=-1;
				// poszukuj w progach wartosci najmniejszej, ponizej ktorej moze byc
				// sprawdzana wartosc
				for ( j=0; j<src.length/2; j++ )
				{
					if ( tmp < src[j] )
					{
						if ( pos==-1 )
							pos = j;
						else if ( src[pos] > src[j] )
							pos = j;
					}
				}
				if ( pos == -1 )
				{
					// poszukuj w progach wartosci najwiekszej, jaka przekroczy
					// sprawdzana wartosc
					for ( j=src.length/2; j<src.length; j++ )
					{
						if ( tmp > src[j] )
						{
							if ( pos==-1 )
								pos = j;
							else if ( src[pos] < src[j] )
								pos = j;
						}
					}
					if ( pos != -1 )
						pos = src.length-pos-1; // przelicz na pierwsza polowke pozycje
				}
				if ( pos != -1 )
				{
					// rysuj odpowiedni kod
					java.awt.Color kolor = (Color)vecProg.get(pos*4+3);
					g.setColor(kolor);
					g.fillOval(x+5, y+5, 15, 15);
					if ( kolor.getRed() > 250 )
					{
						g.setColor(java.awt.Color.black);
						g.drawOval(x+5, y+5, 14, 14);
					}
				}
				x += 25;
				if ( ((i)%kols) == 0 )
				{
					x = 10;
					y += 25;
				}
				i++;
			}
		} catch (ParameterException e) {
			Mysys.error(e);
		} catch (NumberStoreException e) {
			Mysys.error(e);
		} finally {
			Mysys.closeQuiet(reader);
		}
	}
	
}
