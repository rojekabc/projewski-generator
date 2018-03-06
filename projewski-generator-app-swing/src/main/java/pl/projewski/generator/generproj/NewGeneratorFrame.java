package pl.projewski.generator.generproj;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.generproj.layout.AlaNullSizedLayout;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

@Slf4j
class NewGeneratorFrame extends JFrame
        implements ActionListener, KeyListener, ComponentListener {

    /**
     *
     */
    private static final long serialVersionUID = -5982262250856556861L;
    private static final int ID_TITLE = 120;
    private static final int ID_LAB_NAME = 121;
    private static final int ID_TEX_NAME = 122;
    private static final int ID_GENTYPE = 123;
    private static final int ID_START = 124;
    private static final int ID_SAVE = 125;
    private static final int ID_DESC = 126;
    private static final int ID_END = 127;
    private static final int ID_NOTYPE_WARNING = 128;
    private static final int ID_NONAME_WARNING = 129;
    private static final int ID_OVERWRITE_WARNING = 130;
    private static final int ID_SAVE_TITLE = 131;
    private static final int ID_SAVE_DESC = 132;
    private static final int ID_WARNING = 133;
    private static final int ID_ASKSAVE = 134;
    private static final int ID_ASKCHANGE = 135;
    private static final int ID_EDITPARAM = 136;

    javax.swing.JLabel JLabel0;
    javax.swing.JTextField texName;
    javax.swing.JButton butName;
    javax.swing.JLabel JLabel2;
    javax.swing.JComboBox comType;
    javax.swing.JButton butType;
    javax.swing.JPanel panParam;
    javax.swing.JScrollPane scrPanParam;
    javax.swing.JButton buttGen;
    javax.swing.JButton buttSave;
    javax.swing.JButton buttDesc;
    javax.swing.JButton buttExit;
    javax.swing.JScrollPane scrLabDescr;
    javax.swing.JTextPane paneDescr;

    ParameterParamPack paramEl;
    GeneratorInterface inGI;
    CreationInterface inCi = null;
    boolean isSaved = false;
    boolean isChanged = false;

    public NewGeneratorFrame(final GeneratorInterface gi, final String name,
                             final CreationInterface ci, final String setParam) {
        this(gi, name, ci);
        if (setParam != null) {
            buttGen.setVisible(false);
            buttSave.setVisible(false);
            texName.setEditable(false);
            texName.setText(setParam);
            texName.setBackground(new Color(0xffcccccc));
            JLabel0.setText(GenerProj.getDescription(ID_EDITPARAM));
        }
    }

    public NewGeneratorFrame(final GeneratorInterface gi, final String name,
                             final CreationInterface ci) {
        this(gi, name);
        inCi = ci;
    }

    // jezeli zostanie podany interfejs generatora to ustawi parametry na aktualne
    // wartosci
    public NewGeneratorFrame(final GeneratorInterface gi, final String name) {
        super(GenerProj.getDescription(ID_TITLE));
        /* TO JEST DODATEK, KTÓRY POZWALA NA DZIAŁANIE Z JDK 1.3 */
        /* MOŻLIWE DO ZASTOSOWANIA TYLKO OD JDK 1.3, dla 1.2 można usunąć */
        final JPanel pan = new JPanel();
        pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
        pan.getActionMap().put("POMOC", new MyAction(this));
        setContentPane(pan);
        /* ---------------------------------------------------- */
        getContentPane().setLayout(new AlaNullSizedLayout(424, 381));
        addWindowListener(new EventSimpleFrame());
        budujObiekty();
        inGI = gi;
//		paramEl = new ParameterParamPack( panParam, null );
        // dodaje typy generatorów - usunieto z powodu zbednego dodawania do ustawien
/*		try {
			if ( gi != null )
				GenerProj.addGeneratorType( gi.getClass().getName() );
		} catch ( Exception e ) { // TODO: Obsluga
			e.printStackTrace();
		}
*/
        GenerProj.listGeneratorTypes().stream().map(ClassItem::new).forEach(comType::addItem);

        if (gi != null) {
            for (int i = 0; i < comType.getItemCount(); i++) {
                final ClassItem classItem = (ClassItem) comType.getItemAt(i);
                if (classItem.getClassItem() == gi.getClass()) {
                    comType.setSelectedIndex(i);
                    break;
                }
            }
//			comType.setSelectedItem( gi.getClass().getSimpleName() );
            try {
                paneDescr.setText(gi.getDescription());
            } catch (final ParameterException e) {
                Mysys.println(e.toString());
            }
            if (name != null) {
                texName.setText(name);
            }
            isSaved = true;
        } else {
            doSelectionElement();
        }
        // ustawia tekst opisu w zależności od aktualnie wybranego generatora
        // oraz parametry dostepne do ustawienia
        addKeyListener(this);
        addComponentListener(this);
        comType.addActionListener(this);
        Convert.setCentral(this, 424, 381);
    }

    private void budujObiekty() {
        JLabel0 = new javax.swing.JLabel();
        JLabel0.setText(GenerProj.getDescription(ID_LAB_NAME));
        JLabel0.setBounds(20, 20, 130, 30);
        JLabel0.setBackground(new Color(0xffcccccc));
        JLabel0.setForeground(new Color(0xff666699));
        JLabel0.setEnabled(true);
        JLabel0.setVisible(true);
        JLabel0.setRequestFocusEnabled(true);
        JLabel0.setFont(JLabel0.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(JLabel0);

        texName = new javax.swing.JTextField();
        texName.setText(GenerProj.getDescription(ID_TEX_NAME));
        texName.setBounds(160, 20, 210, 30);
        texName.setBackground(new Color(0xffffffff));
        texName.setForeground(new Color(0xff000000));
        texName.setEnabled(true);
        texName.setVisible(true);
        texName.setRequestFocusEnabled(true);
        texName.setFont(texName.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(texName);

        butName = new javax.swing.JButton();
        butName.setText("...");
        butName.setBounds(370, 20, 40, 30);
        butName.setBackground(new Color(0xffcccccc));
        butName.setForeground(new Color(0xff000000));
        butName.setEnabled(true);
        butName.setVisible(true);
        butName.setRequestFocusEnabled(true);
        butName.setFont(butName.getFont().deriveFont(Font.PLAIN));
        butName.addActionListener(this);
        getContentPane().add(butName);

        JLabel2 = new javax.swing.JLabel();
        JLabel2.setText(GenerProj.getDescription(ID_GENTYPE));
        JLabel2.setBounds(20, 60, 130, 30);
        JLabel2.setBackground(new Color(0xffcccccc));
        JLabel2.setForeground(new Color(0xff666699));
        JLabel2.setEnabled(true);
        JLabel2.setVisible(true);
        JLabel2.setRequestFocusEnabled(true);
        JLabel2.setFont(JLabel2.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(JLabel2);

        comType = new javax.swing.JComboBox();
        comType.setBounds(160, 60, 210, 30);
        comType.setBackground(new Color(0xffcccccc));
        comType.setForeground(new Color(0xff000000));
        comType.setEnabled(true);
        comType.setVisible(true);
        comType.setRequestFocusEnabled(true);
        comType.setFont(comType.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(comType);

        butType = new javax.swing.JButton();
        butType.setText("...");
        butType.setBounds(370, 60, 40, 30);
        butType.setBackground(new Color(0xffcccccc));
        butType.setForeground(new Color(0xff000000));
        butType.setEnabled(true);
        butType.setVisible(true);
        butType.setRequestFocusEnabled(true);
        butType.setFont(butType.getFont().deriveFont(Font.PLAIN));
        butType.addActionListener(this);
        getContentPane().add(butType);

        scrLabDescr = new javax.swing.JScrollPane();
        paneDescr = new javax.swing.JTextPane();
        paneDescr.setContentType("text/html");
        paneDescr.setEditable(false);
        paneDescr.setBackground(new Color(0xffcccccc));
        paneDescr.setFont(paneDescr.getFont().deriveFont(Font.PLAIN));
        scrLabDescr.getViewport().setView(paneDescr);
        scrLabDescr.setBounds(20, 100, 390, 65);
        getContentPane().add(scrLabDescr);

        scrPanParam = new javax.swing.JScrollPane();
        paramEl = new ParameterParamPack(null);
        paramEl.setLayout(new AlaNullSizedLayout(360, 140,
                AlaNullSizedLayout.CHANGE_X));

//		panParam = new javax.swing.JPanel();
//		panParam.setLayout(new AlaNullSizedLayout(380, 140,
//					AlaNullSizedLayout.CHANGE_X));
        scrPanParam.setBounds(20, 170, 390, 150);
//		scrPanParam.setViewportView( panParam );
        scrPanParam.setViewportView(paramEl);
        scrPanParam.getViewport().setBackground(new Color(0xffcccccc));
        paramEl.setBackground(new Color(0xffcccccc));
        paramEl.setForeground(new Color(0xff000000));
        paramEl.setEnabled(true);
        paramEl.setVisible(true);
        paramEl.setRequestFocusEnabled(true);
/*		panParam.setBackground( new Color( 0xffcccccc ) );
		panParam.setForeground( new Color( 0xff000000 ) );
		panParam.setEnabled(true);
		panParam.setVisible(true);
		panParam.setRequestFocusEnabled(true);
*/
        getContentPane().add(scrPanParam);

        buttGen = new javax.swing.JButton();
        buttGen.setText(GenerProj.getDescription(ID_START));
        buttGen.setBounds(20, 330, 90, 30);
        buttGen.setBackground(new Color(0xffcccccc));
        buttGen.setForeground(new Color(0xff000000));
        buttGen.setEnabled(true);
        buttGen.setVisible(true);
        buttGen.setRequestFocusEnabled(true);
        buttGen.setFont(buttGen.getFont().deriveFont(Font.PLAIN));
        buttGen.addActionListener(this);
        getContentPane().add(buttGen);

        buttSave = new javax.swing.JButton();
        buttSave.setText(GenerProj.getDescription(ID_SAVE));
        buttSave.setBounds(115, 330, 90, 30);
        buttSave.setBackground(new Color(0xffcccccc));
        buttSave.setForeground(new Color(0xff000000));
        buttSave.setEnabled(true);
        buttSave.setVisible(true);
        buttSave.setRequestFocusEnabled(true);
        buttSave.setFont(buttSave.getFont().deriveFont(Font.PLAIN));
        buttSave.addActionListener(this);
        getContentPane().add(buttSave);

        buttDesc = new javax.swing.JButton();
        buttDesc.setText(GenerProj.getDescription(ID_DESC));
        buttDesc.setBounds(210, 330, 90, 30);
        buttDesc.setBackground(new Color(0xffcccccc));
        buttDesc.setForeground(new Color(0xff000000));
        buttDesc.setEnabled(true);
        buttDesc.setVisible(true);
        buttDesc.setRequestFocusEnabled(true);
        buttDesc.setFont(buttDesc.getFont().deriveFont(Font.PLAIN));
        buttDesc.addActionListener(this);
        getContentPane().add(buttDesc);

        buttExit = new javax.swing.JButton();
        buttExit.setText(GenerProj.getDescription(ID_END));
        buttExit.setBounds(320, 330, 90, 30);
        buttExit.setBackground(new Color(0xffcccccc));
        buttExit.setForeground(new Color(0xff000000));
        buttExit.setEnabled(true);
        buttExit.setVisible(true);
        buttExit.setRequestFocusEnabled(true);
        buttExit.setFont(buttExit.getFont().deriveFont(Font.PLAIN));
        buttExit.addActionListener(this);
        getContentPane().add(buttExit);
    }

/*	protected void setActualGenerator( GeneratorInterface gi, String name )
	{
		paramEl.setActiveParameter( gi );
	}*/

    protected void doSelectionElement() {
        try {
            final ClassItem sel = (ClassItem) comType.getSelectedItem();
            if (sel != null) {
                if ((inGI == null) || (inGI.getClass() != sel.getClassItem())) {
                    final Object o = sel.getClassItem().newInstance();
                    final GeneratorInterface gi = (GeneratorInterface) o;
                    inGI = gi;
                }

                paneDescr.setText(inGI.getDescription());
                paramEl.setActiveParameter(inGI);
                paintAll(getGraphics());
            }
        } catch (final Exception e) {
            System.out.println("NewGeneratorFrame().<init>: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Na podstawie parametrów zgromadzonych w oknie paramEl jest budowany
     * generator.
     *
     * @return Zwraca interfejs do obiektu zbudowanego generatora. Jeżeli budowanie
     * zakończy się niepowodzeniem zostanie wyświetlony najpierw komunikat błędu,
     * a następnie zostanie zwrócona wartość null.
     */
    private GeneratorInterface buildGenerator() {
        try {
            final ClassItem sel = (ClassItem) comType.getSelectedItem();
            if (sel == null) {
                return null;
            }
            final GeneratorInterface gi = (GeneratorInterface) sel.getClassItem().newInstance();
            final Set<String> params = gi.listParameters();
            int paramPos = 0;
            for (final String param : params) {
                final Object value = paramEl.getParameterValue(paramPos);
                if (value != null) {
                    gi.setParameter(param, value);
                } else {
                    JOptionPane.showMessageDialog(this,
                            Convert.stringWrap(
                                    "Niepoprawnie wprowadzony parametr \"" + param + "\"", 30),
                            GenerProj.getDescription(ID_WARNING),
                            JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                paramPos++;
            }
            return gi;
        } catch (final Exception e) { // TODO: Lepsze wyłapywanie
            e.printStackTrace();
        }
        return null;
    }

    // zwraca true, jeśli wykonano nadpisanie lub nowozapisanie, false jeżeli nie
    // wykonano nadpisania
    private boolean saveGenerator() {
        // sprawdź, czy wybrano jakiś typ generatora
        final Object sel = comType.getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(this,
                    Convert.stringWrap(GenerProj.getDescription(ID_NOTYPE_WARNING), 30),
                    GenerProj.getDescription(ID_WARNING),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        // sprawdzenie czy jest nazwa
        final String s = texName.getText().trim();
        if ((s == null) || (s.length() == 0)) {
            JOptionPane.showMessageDialog(this,
                    Convert.stringWrap(GenerProj.getDescription(ID_NONAME_WARNING), 30),
                    GenerProj.getDescription(ID_WARNING),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        // Do gromadzenia komunikatów błędów
        String errStr = null;
        GeneratorInterface gi = null;
        try {
            // próba dodania
            gi = buildGenerator();
            if (gi != null) {
                GenerProj.addGenerator(s, gi);
            } else {
                return false;
            }
        } catch (final GeneratorException e) {
            // czy okazało się, że już istnieje
            if (e.getCode() == GeneratorException.FILE_ALREADY_EXISTS) {
                final int response = javax.swing.JOptionPane.showConfirmDialog(this,
                        Convert.stringWrap(e.toString() + "\n"
                                + GenerProj.getDescription(ID_OVERWRITE_WARNING), 30),
                        GenerProj.getDescription(ID_WARNING),
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                if (response == javax.swing.JOptionPane.YES_OPTION) {
                    // wykonaj ponowną próbe nadpisania po wykasowaniu z zasobów
                    try {
                        GenerProj.removeGenerator(s);
                        GenerProj.addGenerator(s, gi);
                    } catch (final GeneratorException e2) {
                        errStr = e2.toString();
                    }
                } else {
                    return false; // Naciśnięto przycisk NO
                }
            } else {
                errStr = e.toString();
            }
        } // try - catch
        if (errStr != null) {
            JOptionPane.showMessageDialog(this, Convert.stringWrap(errStr, 30),
                    GenerProj.getDescription(ID_WARNING), JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            JOptionPane.showMessageDialog(this,
                    Convert.stringWrap(GenerProj.getDescription(ID_SAVE_DESC), 30),
                    GenerProj.getDescription(ID_SAVE_TITLE),
                    JOptionPane.INFORMATION_MESSAGE);
            isSaved = true;
            return true;
        }
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        final Object o = evt.getSource();
        if (o == buttExit) {
            if (inGI != null) {
                paramEl.getParameterInterface(inGI);
            }
            if (!isSaved) // nie zapisano, czy to zrobić
            {
                final int response = javax.swing.JOptionPane.showConfirmDialog(this,
                        Convert.stringWrap(GenerProj.getDescription(ID_ASKSAVE), 30),
                        GenerProj.getDescription(ID_WARNING),
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                if (response == javax.swing.JOptionPane.YES_OPTION) {
                    if (!saveGenerator()) {
                        return;
                    }
                }
            } else if (isChanged) // zostal zmieniony, czy zachować
            {
                final int response = javax.swing.JOptionPane.showConfirmDialog(this,
                        Convert.stringWrap(GenerProj.getDescription(ID_ASKCHANGE), 30),
                        GenerProj.getDescription(ID_WARNING),
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                if (response == javax.swing.JOptionPane.YES_OPTION) {
                    saveGenerator();
                }
            }
            if (inCi != null) {
                inCi.setCreatedObject(inGI);
            }
            GenerProj.doCloseActualFrame();
        } else if (o == buttGen) {
            final Object sel = comType.getSelectedItem();
            if (sel == null) {
                JOptionPane.showMessageDialog(this,
                        Convert.stringWrap(GenerProj.getDescription(ID_NOTYPE_WARNING), 30),
                        GenerProj.getDescription(ID_WARNING),
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            inGI = buildGenerator();
            if (inGI == null) {
                return;
            }
            GenerProj.doSwitchToFrame(new StartGenerationFrame(inGI,
                    texName.getText()));
        } else if (o == comType) {
            doSelectionElement();
        } else if (o == buttSave) {
            saveGenerator();
        } else if (o == butType) {
            log.warn("Functionality disabled - not longer in use");
        } else if (o == buttDesc) {
            final ClassItem classItem = (ClassItem) comType.getSelectedItem();
            if (classItem == null) {
                return;
            }
            final String className = classItem.getClassItem().getName();

            if (GenerProj.isHelpFile(className)) {
                GenerProj.showHelp(className);
            } else { // Try show help from description from ParameterInterface
                try {
                    final GeneratorInterface gi = (GeneratorInterface) classItem.getClassItem().newInstance();
                    GenerProj.showHelp(gi);
                } catch (final Exception e) {
                }
                return;
            }
        } else if (o == butName) {
            final MyFileChooserDialog d = new MyFileChooserDialog(this, "gen");
            if (d.getSelectedFile() == null) {
                return;
            }
            String filename = d.getSelectedFile().getName();
            if (filename.endsWith(".gen")) {
                filename = filename.substring(0, filename.length() - 4);
            }
            String mess = null;
            try {
                GenerProj.addGenerator(filename, null);
                inGI = GenerProj.createGenerator(filename);

                for (int i = 0; i < comType.getItemCount(); i++) {
                    final ClassItem classItem = (ClassItem) comType.getItemAt(i);
                    if (classItem.getClassItem() == inGI.getClass()) {
                        comType.setSelectedIndex(i);
                        break;
                    }
                }
                //comType.setSelectedItem( inGI.getClass().getName() );
            } catch (final GeneratorException e) {
                mess = e.toString(); // TODO: Daj komunikat
            }
            if (mess != null) {
                JOptionPane.showMessageDialog(this, Convert.stringWrap(mess, 30),
                        GenerProj.getDescription(ID_WARNING), JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            GenerProj.showHelp(this);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void componentHidden(final ComponentEvent e) {
    }

    @Override
    public void componentMoved(final ComponentEvent e) {
    }

    @Override
    public void componentResized(final ComponentEvent e) {
    }

    @Override
    public void componentShown(final ComponentEvent e) {
        paramEl.setActiveParameter(inGI);
        paintAll(getGraphics());
    }
}
