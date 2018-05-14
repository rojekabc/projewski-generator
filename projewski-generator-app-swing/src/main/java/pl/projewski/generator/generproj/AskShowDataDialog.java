package pl.projewski.generator.generproj;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.generator.generproj.layout.AlaNullSizedLayout;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.interfaces.ViewDataListener;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.Mysys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@Slf4j
class AskShowDataDialog extends JFrame
        implements KeyListener, ActionListener, ViewDataListener, WindowListener {
    /**
     *
     */
    private static final long serialVersionUID = 5756729542764897849L;
    private static final int ID_TITLE = 180;
    private static final int ID_VIEW = 181;
    private static final int ID_OK = 182;
    private static final int ID_WARNING = 183;

    javax.swing.JLabel labDesc;
    javax.swing.JComboBox comView;
    javax.swing.JButton butSelView;
    javax.swing.JButton butView;
    javax.swing.JButton butOk;
    GeneratedData actGdt;
    ViewDataInterface view;
    boolean remGenDataOnClose;

    public AskShowDataDialog(final JFrame parent, final String desc, final GeneratedData gdt) {
        super(GenerProj.getDescription(ID_TITLE));
        //super( parent, GenerProj.getDescription(ID_TITLE), true );

        this.remGenDataOnClose = false;

        final JPanel pan = new JPanel();
        pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
        pan.getActionMap().put("POMOC", new MyAction(this));
        setContentPane(pan);

        getContentPane().setLayout(new AlaNullSizedLayout(270, 120));
        actGdt = gdt;
        budujObiekty();
        if (desc != null) {
            labDesc.setFont(labDesc.getFont().deriveFont(java.awt.Font.PLAIN));
            labDesc.setText(desc);
        }
        comView.setMaximumRowCount(3);
        addKeyListener(this);
        this.addWindowListener(this);
        Convert.setCentral(this, 270, 120);
        pack();
        setVisible(true);
    }

    private void budujObiekty() {
        labDesc = new javax.swing.JLabel();
        labDesc.setText("");
        labDesc.setBounds(10, 10, 255, 25);
        labDesc.setBackground(new Color(0xffcccccc));
        labDesc.setForeground(new Color(0xff666699));
        labDesc.setEnabled(true);
        labDesc.setVisible(true);
        labDesc.setRequestFocusEnabled(true);
        labDesc.setFont(labDesc.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(labDesc);

        // ustal ródło danych
        ParameterInterface dataSource = null;
        dataSource = actGdt.getDataSource();
        comView = new javax.swing.JComboBox();
        GenerProj.listView(dataSource).stream().map(ClassItem::new).forEach(comView::addItem);
        comView.setBounds(10, 40, 215, 25);
        comView.setBackground(new Color(0xffcccccc));
        comView.setForeground(new Color(0xff000000));
        comView.setEnabled(true);
        comView.setVisible(true);
        comView.setRequestFocusEnabled(true);
        comView.setFont(comView.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(comView);
        butSelView = new javax.swing.JButton();
        butSelView.setText("...");
        butSelView.setBounds(225, 40, 35, 25);
        butSelView.setBackground(new Color(0xffcccccc));
        butSelView.setForeground(new Color(0xff000000));
        butSelView.setEnabled(true);
        butSelView.setVisible(true);
        butSelView.setRequestFocusEnabled(true);
        butSelView.addActionListener(this);
        butSelView.setFont(butSelView.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(butSelView);
        butView = new javax.swing.JButton();
        butView.setText(GenerProj.getDescription(ID_VIEW));
        butView.setBounds(10, 75, 130, 25);
        butView.setBackground(new Color(0xffcccccc));
        butView.setForeground(new Color(0xff000000));
        butView.setEnabled(true);
        butView.setVisible(true);
        butView.setRequestFocusEnabled(true);
        butView.setFont(butView.getFont().deriveFont(Font.PLAIN));
        butView.addActionListener(this);
        getContentPane().add(butView);
        butOk = new javax.swing.JButton();
        butOk.setText(GenerProj.getDescription(ID_OK));
        butOk.setBounds(140, 75, 120, 25);
        butOk.setBackground(new Color(0xffcccccc));
        butOk.setForeground(new Color(0xff000000));
        butOk.setEnabled(true);
        butOk.setVisible(true);
        butOk.setRequestFocusEnabled(true);
        butOk.setFont(butOk.getFont().deriveFont(Font.PLAIN));
        butOk.addActionListener(this);
        getContentPane().add(butOk);
    }

    public void setRemoveGeneratedData(final boolean b) {
        this.remGenDataOnClose = b;
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        final Object o = evt.getSource();
        if (o == butOk) {
            windowClosing(null);
        } else if (o == butSelView) {
            log.warn("Functionality disabled - not in use anymore");
        } else if (o == butView) {
            final ClassItem classItem = (ClassItem) comView.getSelectedItem();
            if (classItem == null) {
                return;
            }
            view = null;
            try {
                view = (ViewDataInterface) classItem.getClassItem().newInstance();
            } catch (final Exception e) {
                e.printStackTrace();
                Mysys.println("Error not should be here");
                return;
            }
            try {
                view.setData(actGdt);
                Mysys.debugln("I want to add ViewDataListenet");
                view.addViewDataListener(this);
                view.showView();
            } catch (final Exception e) {
                e.printStackTrace();
                Mysys.println(e.toString());
            }
            return;
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
    public void onMouseEvent(final Frame frame, final ParameterInterface pi, final MouseEvent event) {
        if ((event.getID() == MouseEvent.MOUSE_RELEASED) && (event.getModifiers() == InputEvent.BUTTON3_MASK)) {
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
    @Override
    public void windowActivated(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(final WindowEvent e) {
        GenerProj.doCloseActualFrame();
        if (this.remGenDataOnClose) {
            if (!this.actGdt.delete()) {
                Mysys.error("Cannot remove temporary file");
            }
        }
    }

    @Override
    public void windowDeactivated(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

}
