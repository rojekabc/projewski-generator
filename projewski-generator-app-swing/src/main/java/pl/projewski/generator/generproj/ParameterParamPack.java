package pl.projewski.generator.generproj;

import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParameterParamPack extends JPanel
        implements java.awt.event.ActionListener, CreationInterface {
    public static final int BASE_DIALOG_CLASS_INTEGER = 1;
    public static final int BASE_DIALOG_CLASS_FLOAT = 2;
    public static final int BASE_DIALOG_CLASS_DOUBLE = 3;
    public static final int BASE_DIALOG_CLASS_LONG = 4;
    public static final int BASE_DIALOG_CLASS_GENERATOR = 5;
    public static final int BASE_DIALOG_CLASS_UNKNOWN = 6;
    /**
     *
     */
    private static final long serialVersionUID = -846711574944937271L;
    private static final int colFirstX = 10;
    private static final int colSecondX = 100;
    private static final int colThirdX = 360;

    //	protected java.awt.Container container = null;
    protected java.util.Vector<Component> vecDesc = new java.util.Vector<>();
    protected java.util.Vector<Component> vecVal = new java.util.Vector<>();
    protected ParameterInterface inPI;
    protected String setParameter;
    protected Dimension dimension;

    // Jesli podano gi, to zainicjuje wartosciami aktualnego generatora
    public ParameterParamPack(final ParameterInterface pi) {
//		container = c;
        setActiveParameter(pi);
    }

    public void setActiveParameter(final ParameterInterface pi) {
        int height = 0;
        inPI = pi;
        if (pi == null) {
            return;
        }
        try {
            removeAll();
            final Set<String> params = pi.listParameters();

            for (final String param : params) {
                final ParameterLine pl = ParameterLine.generateParameterLine(pi, param, this);
                vecDesc.add(pl.label);
                vecVal.add(pl.edit);
                pl.label.setBounds(colFirstX, height * 25, colSecondX - colFirstX - 5, 20);
                pl.edit.setBounds(colSecondX, height * 25, colThirdX - colSecondX - 5, (pl.height - 1) * 25 + 20);
//				System.out.println("y " + (height*25) + " height " + ((pl.height-1) * 25 + 20));
                this.add(pl.label);
                this.add(pl.edit);
                height += pl.height;
            }
            dimension = new Dimension(colThirdX, (height - 1) * 25 + 20);
//			System.out.println("NOW: " + ((height-1)*25 + 20));
//			super.setSize(super.getWidth(), (height-1)*25 + 20);
//				addParam( Mysys.encString( param[i].toString() ),
//						pi.getAllowedClass(param[i]), pi.getParameter(param[i]) );
        } catch (final ParameterException e) {
            // TODO: Reaction
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        super.removeAll();
        vecDesc.clear();
//		vecType.clear();
        vecVal.clear();
    }

    @Override
    public void actionPerformed(final java.awt.event.ActionEvent e) {
        final Object o = e.getSource();
        // zdarzenia należące do wyboru typu
/*		for ( int i=0; i<vecType.size(); i++ )
		{
			if ( o == vecType.get( i ) )
			{
				// ustal wybraną klasę
				javax.swing.JComboBox cb = (javax.swing.JComboBox)o;
				Class cl = (Class)((MyComboBoxItem)cb.getSelectedItem()).getItem( 1 );
				// pobierz wartość ustawioną dla generatora
				Object paramValue;
				try {
					paramValue = inPI.getParameter( i );
				} catch ( Exception ex ) {
					paramValue = null;
				}
				// Ustal element z wartością parametru. Jeżeli okaże się, że klasa
				// wybrana przez użytkownika, jest tą samą klasą, którą jest parametr
				// generatora to nadaj mu wartość, która jest mu przypisana
				java.awt.Component war;
				if ( ( paramValue != null) && (cl.isInstance( paramValue )) )
					war = getComponentByClass(	cl, paramValue );
				else
					war = getComponentByClass(	cl, null );
				// ustal opis - nazwę parametru oraz obiekt, jaki ma być umieszczony
				// w kolumience z opisem parametrów
				java.awt.Component opis;
				try {
					opis = getOpisByClass( cl, (String)inPI.listParameters()[i] );
				} catch ( ParameterException ex ) {
					opis = new javax.swing.JLabel( "ERROR" );
					ex.printStackTrace();
				}
				// usuń stare obiekty z kontenera
				container.remove( (java.awt.Component)vecDesc.get( i ) );
				container.remove( (java.awt.Component)vecVal.get( i ) );
				// ustaw wektory
				vecDesc.set( i, opis );
				vecVal.set( i, war );
				// ustaw obiekty dodawane na nowo
				opis.setBounds( colFirstX, i*25, colSecondX - colFirstX - 5, 20 );
				war.setBounds( colSecondX, i*25, colThirdX - colSecondX - 5, 20 );
				// dodaj nowe obiekty do kontenera
				container.add( opis );
				container.add( war );
				// odrysuj kontener
				container.paintAll( container.getGraphics() );
				return;
			}
		}*/
        // zdarzenia należące do elementów opisu
        for (int i = 0; i < vecDesc.size(); i++) {
            if (o == vecDesc.get(i)) {
                final Object oOut = getParameterValue(i);
                GeneratorInterface gi = null;
                if (!(oOut instanceof GeneratorInterface)) {
                    return;
                }
                if (oOut != null) {
                    gi = (GeneratorInterface) oOut;
                }

                if (gi != null) {
                    getParameterInterface(inPI);
                    setParameter = getParameterName(vecDesc.get(i));
                    GenerProj.doSwitchToFrame(new NewGeneratorFrame(gi,
                            "", this, ((javax.swing.JButton) o).getText()));
                }
            }
        }
    }

    public String getParameterName(final Component component) {
        if (component instanceof JButton) {
            return ((JButton) component).getText();
        } else if (component instanceof JLabel) {
            return ((JLabel) component).getText();
        } else {
            return "";
        }

    }

    // zaladowanie do podanego interfejsu parametrow zmagazynowanych
    // w ustawieniach formularza
    // zwraca true, jeżeli wykryto dokonanie zmiany
    public void getParameterInterface(final ParameterInterface pi) {
        if (pi == null) {
            System.out.println("PI IS NULL - ERROR IN PROGRAMMING !!!");
            return;
        }
//		if ( inPI.getClass() != pi.getClass() ) //TODO: message
//		{
//			System.out.println("ERROR IN PROGRAMING");
//			return;
//		}
        try {
            for (int i = 0; i < vecVal.size(); i++) {
                pi.setParameter(getParameterName(vecDesc.get(i)), getParameterValue(i));
            }
        } catch (final ParameterException ex) {
            ex.printStackTrace();
        }
        return;
    }

    private String getStringFromComponent(final Component c) {
        if (c instanceof JTextField) {
            return ((JTextField) c).getText().trim();
        } else if (c instanceof JComboBox) {
            return ((JComboBox) c).getSelectedItem().toString().trim();
        } else if (c instanceof JTextArea) {
            return ((JTextArea) c).getText();
        } else if (c instanceof JScrollPane) {
            return getStringFromComponent(((JScrollPane) c).getViewport().getView());
        } else {
            Mysys.error("Unknown class: " + c.getClass().getName());
        }
        return null;

    }

    private int getSelectionFromComponent(final Component c) {
        if (c instanceof JComboBox) {
            return ((JComboBox) c).getSelectedIndex();
        }
        return -1;
    }

    public Object getParameterValue(final int i) {
        Object ret = null;

        if (i >= vecVal.size()) {
            return ret;
        }

        //javax.swing.JComboBox cb = (javax.swing.JComboBox)vecType.get( i );
        //MyComboBoxItem cbi = (MyComboBoxItem)cb.getSelectedItem();
        //Class cl = (Class)cbi.getItem( 1 );
        List<Class<?>> cl = new ArrayList<>();
        try {
            cl = inPI.getAllowedClass(getParameterName(vecDesc.get(i)));
        } catch (final ParameterException e1) {
            Mysys.error("Not allowed excpetion. ParameterParamPack.java:parameterValue");
        }

//		try {
        int j;
        for (final Class<?> clazz : cl) {
            final Component c = vecVal.get(i);
            final int sel = getSelectionFromComponent(c);
            final String getstr = getStringFromComponent(c);

            if (clazz == GeneratorInterface.class) {
                try {
                    GeneratorInterface gi = null;
                    if (sel == -1) {
                        continue;
                    }
                    String n;
                    final String paramName = getParameterName(vecDesc.get(i));
                    if (inPI.getParameter(paramName) != null) {
                        n = inPI.getParameter(paramName).getClass().getName();
                    } else {
                        n = "";
                    }
                    if (n.lastIndexOf('.') != -1) {
                        n = n.substring(n.lastIndexOf('.') + 1);
                    }
                    // Sprawdz, czy zmienil sie co do poprzednika typ Generatora
                    if (n.equals(getstr)) {
                        gi = (GeneratorInterface) inPI.getParameter(paramName);
                    } else {
                        gi = (GeneratorInterface) GenerProj.listGeneratorTypes().get(sel).newInstance();
                    }
                    ret = gi;
                } catch (final Exception e) {
                    e.printStackTrace();
                    continue;
                }
                break;
            } else {
                try {
                    ret = Convert.tryToClass(getstr, clazz);
                    break;
                } catch (final Exception e) {
                    continue;
                }
            }
        }

        if (ret == null) {
            Mysys.error("Nieudana konwersja parametru " + vecDesc.get(i) + " do dostepnych typow");
        }
//		} catch ( Exception e ) { // TODO: Komunikaty
//			e.printStackTrace();
//		}
        return ret;
    }

    @Override
    public void setCreatedObject(final Object o) {
        try {
            inPI.setParameter(setParameter, o);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (dimension == null) {
            return super.getPreferredSize();
        } else {
            return dimension;
        }
    }

};
