/**
 * 
 */
package pl.projewski.generator.generproj;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.tools.Mysys;

/**
 * @author projewski
 *
 */
public class ParameterLine
{
	Component label;
	Component edit;
	int height;
	
	/**
	 * Sprawdzenie, czy w danej tablicy wystepuje okreslona klasa
	 * @param table tablica
	 * @param c klasa
	 * @return true, je�li tak
	 */
	private boolean hasClass(Class<?> [] table, Class<?> c)
	{
		if ( table == null )
			return false;
		for (int i=0; i<table.length; i++)
		{
			if (table[i] == c)
				return true;
		}
		return false;
	}

	/**
	 * Na podstawie podanej klasy zostaje utworzony obiekt, kt�ry pozwoli na edytowanie
	 * zawarto�ci obiektu tej klasy.
	 * 
	 * @param c Lista klas, jaki obiekt obs�uguje
	 * @param initValue warto�� pocz�tkowa
	 * @return Utworzony obiekt
	 */
	private void setComponentByClass( Class<?> [] c, Object initValue )
	{
		if ( hasClass(c, GeneratorInterface.class) )
		{
			JComboBox tmp = new JComboBox();
			Object [] x = GenerProj.listGeneratorTypes();
			String n;
			tmp.setEditable(true);
			for (int j=0; j<x.length; j++)
			{
				n = x[j].toString();
				if ( n.lastIndexOf('.') != -1 )
					n = n.substring(n.lastIndexOf('.')+1);
				tmp.addItem(n);
			}
			if ( initValue != null )
			{
				if ( initValue instanceof GeneratorInterface )
				{
					n = initValue.getClass().getName();
					if ( n.lastIndexOf('.') != -1 )
						n = n.substring(n.lastIndexOf('.')+1);
				}
				else
					n = initValue.toString();
				tmp.setSelectedItem(n);
			}
			else if ( x.length > 0 )
			{
					tmp.setSelectedIndex(0);
			}
			edit = tmp;
			edit.setFont( edit.getFont().deriveFont(java.awt.Font.PLAIN) );
			height = 1;
		}
		// czy jest to pole tekstowe
		else if ( hasClass(c, String.class) )
		{
			if ( initValue != null )
				edit = new JTextArea(initValue.toString());
			else
				edit = new JTextArea();
			edit.setFont( edit.getFont().deriveFont(java.awt.Font.PLAIN) );
			JScrollPane scroll = new JScrollPane(edit);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			edit = scroll;
			height = 3;
		}
		// jakies pozostale typy (integer, double, float, long) 
		else
		{
			if ( initValue != null )
				edit = new javax.swing.JTextField( initValue.toString() );
			else
				edit = new javax.swing.JTextField( );
			edit.setFont( edit.getFont().deriveFont(java.awt.Font.PLAIN) );
			height = 1;
		}
	}

	private void setOpisByClass( Class<?> [] c, Object descr, ActionListener listener )
	{
		// je�eli jest to generator, pozw�l na edycje jego parametr�w
		if ( hasClass(c, GeneratorInterface.class) )
		{
			if ( descr != null )
				label = new javax.swing.JButton( descr.toString() );
			else
				label = new javax.swing.JButton( "UNKNOWN" );
			((javax.swing.JButton)label).addActionListener( listener );
		}
		else
		{
			if ( descr != null )
				label = new javax.swing.JLabel( descr.toString() );
			else
				label = new javax.swing.JLabel( "UNKNOWN" );			
		}
		label.setFont( label.getFont().deriveFont(java.awt.Font.PLAIN) );
	}
	
	private ParameterLine()
	{
	}
	
	public static ParameterLine generateParameterLine(ParameterInterface pi, int paramCnt, ActionListener listener) throws ParameterException
	{
		String [] params = pi.listParameters();
		String paramName = Mysys.encString(params[paramCnt]);
		Object paramValue = pi.getParameter(params[paramCnt]);
		Class<?>[] allowedClasses = pi.getAllowedClass(params[paramCnt]);
		ParameterLine retObj = new ParameterLine();
		retObj.setOpisByClass(allowedClasses, paramName, listener);
		retObj.setComponentByClass(allowedClasses, paramValue);
		return retObj;
	}

}
