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
class ListDataFrame extends JFrame
        implements ActionListener, KeyListener, ViewDataListener {
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

    public ListDataFrame() {
        super(GenerProj.getDescription(ID_DLG_TITLE));
        /* TO JEST DODATEK, KTÓRY POZWALA NA DZIAŁANIE Z JDK 1.3 */
        /* MOŻLIWE DO ZASTOSOWANIA TYLKO OD JDK 1.3, dla 1.2 można usunąć */
        final JPanel pan = new JPanel();
        pan.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "POMOC");
        pan.getActionMap().put("POMOC", new MyAction(this));
        setContentPane(pan);
        /* ---------------------------------------------------- */
        getContentPane().setLayout(new AlaNullSizedLayout(427, 364));
        addWindowListener(new EventSimpleFrame());
        budujObiekty();
        listData.setListData(GenerProj.listGeneratedData());
        addKeyListener(this);
        Convert.setCentral(this, 427, 384);
    }

    private void budujObiekty() {
        scrListData = new javax.swing.JScrollPane();
        listData = new javax.swing.JList();
        scrListData.getViewport().setView(listData);
        scrListData.setBounds(25, 65, 270, 275);
        listData.setBackground(new Color(0xffffffff));
        listData.setForeground(new Color(0xff000000));
        listData.setEnabled(true);
        listData.setVisible(true);
        listData.setRequestFocusEnabled(true);
        getContentPane().add(scrListData);
        buttShow = new javax.swing.JButton();
        buttShow.setText(GenerProj.getDescription(ID_DLG_VIEW));
        buttShow.setBounds(305, 20, 100, 35);
        buttShow.setBackground(new Color(0xffcccccc));
        buttShow.setForeground(new Color(0xff000000));
        buttShow.setEnabled(true);
        buttShow.setVisible(true);
        buttShow.setRequestFocusEnabled(true);
        buttShow.setFont(buttShow.getFont().deriveFont(Font.PLAIN));
        buttShow.addActionListener(this);
        getContentPane().add(buttShow);
        buttRemove = new javax.swing.JButton();
        buttRemove.setText(GenerProj.getDescription(ID_DLG_REMOVE));
        buttRemove.setBounds(305, 65, 100, 35);
        buttRemove.setBackground(new Color(0xffcccccc));
        buttRemove.setForeground(new Color(0xff000000));
        buttRemove.setEnabled(true);
        buttRemove.setVisible(true);
        buttRemove.setRequestFocusEnabled(true);
        buttRemove.setFont(buttRemove.getFont().deriveFont(Font.PLAIN));
        buttRemove.addActionListener(this);
        getContentPane().add(buttRemove);
        buttExit = new javax.swing.JButton();
        buttExit.setText(GenerProj.getDescription(ID_DLG_END));
        buttExit.setBounds(305, 305, 100, 35);
        buttExit.setBackground(new Color(0xffcccccc));
        buttExit.setForeground(new Color(0xff000000));
        buttExit.setEnabled(true);
        buttExit.setVisible(true);
        buttExit.addActionListener(this);
        buttExit.setFont(buttExit.getFont().deriveFont(Font.PLAIN));
        buttExit.setRequestFocusEnabled(true);
        getContentPane().add(buttExit);
        comViewType = new javax.swing.JComboBox();
        GenerProj.listView().stream().map(ClassItem::new).forEach(comViewType::addItem);
        comViewType.setBounds(25, 20, 220, 35);
        comViewType.setBackground(new Color(0xffcccccc));
        comViewType.setForeground(new Color(0xff000000));
        comViewType.setEnabled(true);
        comViewType.setVisible(true);
        comViewType.setRequestFocusEnabled(true);
        comViewType.setFont(comViewType.getFont().deriveFont(Font.PLAIN));
        getContentPane().add(comViewType);
        butViewSel = new javax.swing.JButton();
        butViewSel.setText("...");
        butViewSel.setBounds(250, 20, 44, 35);
        butViewSel.setBackground(new Color(0xffcccccc));
        butViewSel.setForeground(new Color(0xff000000));
        butViewSel.setEnabled(true);
        butViewSel.setVisible(true);
        butViewSel.setRequestFocusEnabled(true);
        butViewSel.setFont(butViewSel.getFont().deriveFont(Font.PLAIN));
        butViewSel.addActionListener(this);
        getContentPane().add(butViewSel);
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        final Object o = evt.getSource();
        if (o == buttExit) {
            GenerProj.doCloseActualFrame();
        } else if (o == buttShow) {
            final ClassItem classItem = (ClassItem) comViewType.getSelectedItem();
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
            final Object selObj = listData.getSelectedValue();
            if (selObj == null) {
                return;
            }
            final GeneratedData gd = (GeneratedData) selObj;
            try {
                if (!gd.readFile()) {
                    Mysys.error("Brak pliku z danymi");
                    return;
                }
                view.setData(gd);
                view.addViewDataListener(this);
                view.showView();
            } catch (final Exception e) {
                // TODO: Komunikat błędu z przyciskiem OK
                Mysys.error(e.toString());
            }

            return;
        } else if (o == buttRemove) {
            final Object selObj = listData.getSelectedValue();
            if (selObj == null) {
                return;
            }
            GenerProj.removeGeneratedData(selObj);
            listData.setListData(GenerProj.listGeneratedData());
        } else if (o == butViewSel) {
            log.warn("Functionality disabled - not in use anymore");
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
}
