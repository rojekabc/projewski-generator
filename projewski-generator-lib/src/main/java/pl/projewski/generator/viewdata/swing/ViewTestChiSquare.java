package pl.projewski.generator.viewdata.swing;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.distribution.ChiSquare;
import pl.projewski.generator.exceptions.WrongDataGeneratorException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.labordata.TestChiSquare;
import pl.projewski.generator.tools.Convert;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


// TODO: UWAGA - Wycofano rozmiar podawany na poczatku, a mowiacy o liczbie V
public class ViewTestChiSquare
        extends ViewDataBase {
    public static final String COLS = "Liczba kolumn";
    // TODO: Ustalic ten parametr z otrzymanych danych
    int _v = 0;
    NumberInterface data;
    javax.swing.JFrame frame;

    @Override
    public void initParameters() {
        parameters.put(COLS, 3);
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return dataSource instanceof TestChiSquare;
    }

    /**
     * M4_GEN_VDI_SETDATA_NSI
     */
    @Override
    public void setData(final NumberInterface data) {
        // sprawdzenie, czy dane podano
        if (data == null) {
            return;
        }

        // Ustalenie stopnia swobody
        final ParameterInterface pi = data.getDataSource();
        if (pi instanceof TestChiSquare) {
            final TestChiSquare tch = (TestChiSquare) pi;
            _v = Convert.tryToInt(tch.getParameter(TestChiSquare.V));
        } else {
            // Data should created by TestChiSquare
            throw new WrongDataGeneratorException();
        }

        // Okreslenie danych
        this.data = data;
    }

    @Override
    public void showView() {
        frame = new javax.swing.JFrame("Test Chi Square");
        frame.getContentPane().add(new ViewTestChiSquarePanel(this));
        frame.setBounds(0, 0, 320, 320);
        frame.setVisible(true);
        frame.setBounds(0, 0, 320, 320);
    }


    @Override
    public Object getView() {
        return null;
    }

    @Override
    public void refreshView() {
    }
}

class ViewTestChiSquareDialog
        extends JDialog
        implements ChangeListener, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String[] itemname =
            {
                    "0.1%", "0.5%", "1%", "2.5%", "5%", "10%", "20%", "30%", "40%", "50%"
            };
    private static final double[] itemvalue =
            {
                    0.001, 0.005, 0.01, 0.025, 0.05, 0.1, 0.2, 0.3, 0.4, 0.5
            };
    private final JLabel labName;
    private final JTextField editName;
    private final JLabel labProc;
    private final JComboBox comboProc;
    private final JLabel labKod;
    private final JPanel labKodKolor;
    private final JSlider pNum;
    private final JButton butok;
    private final JButton butcancel;
    private boolean isOk = false;

    ViewTestChiSquareDialog(final JFrame parent) {
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
        for (final String anItemname : itemname) {
            comboProc.addItem(anItemname);
        }
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

    boolean isOk() {
        return isOk;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Object o = e.getSource();
        if (o == butok) {
            this.setVisible(false);
            isOk = true;
        } else if (o == butcancel) {
            this.setVisible(false);
            isOk = false;
        }
    }

    java.awt.Color getCode() {
        final int val = pNum.getValue();
        return new java.awt.Color(val, val, val);
    }

    @Override
    public String getName() {
        return editName.getText();
    }

    String getItemName() {
        return itemname[comboProc.getSelectedIndex()];
    }

    double getItemValue() {
        return itemvalue[comboProc.getSelectedIndex()];
    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        final int val = pNum.getValue();
        labKodKolor.setBackground(new java.awt.Color(val, val, val));
    }
}

class ViewTestChiSquarePanel
        extends javax.swing.JPanel
        implements java.awt.event.MouseListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final ViewTestChiSquare viewTestChiSquare;

    private final javax.swing.JLabel zakresButton;
    private final java.util.Vector<Object> vecProg = new java.util.Vector<>();


    ViewTestChiSquarePanel(final ViewTestChiSquare vtcs) {
        setLayout(null);
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.black);
        zakresButton = new javax.swing.JLabel("Wynik          Próg    Kod");
        zakresButton.setBackground(java.awt.Color.white);
        zakresButton.setForeground(java.awt.Color.black);
        zakresButton.setBounds(180, 10, 120, 20);
        zakresButton.addMouseListener(this);
        add(zakresButton);
        viewTestChiSquare = vtcs;
    }

    @Override
    public void mouseClicked(final java.awt.event.MouseEvent e) {
        final ViewTestChiSquareDialog dialog;
        dialog = new ViewTestChiSquareDialog(viewTestChiSquare.frame);
        if (dialog.isOk()) {
            vecProg.add(dialog.getName());
            vecProg.add(dialog.getItemName());
            vecProg.add(new Double(dialog.getItemValue()));
            vecProg.add(dialog.getCode());
            repaint();
        }
    }

    @Override
    public void mouseEntered(final java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseExited(final java.awt.event.MouseEvent e) {
    }

    @Override
    public void mousePressed(final java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseReleased(final java.awt.event.MouseEvent e) {
    }

    /*	public void actionPerformed(java.awt.event.ActionEvent e)
        {
            if ( e.getObject() == zakresButton )
            {
            }
        }*/
    @Override
    public void paintComponent(final java.awt.Graphics g) {
        try (final NumberReader reader = viewTestChiSquare.data.getNumberReader()) {
            super.paintComponent(g);
            int i;
            final double[] src = new double[vecProg.size() / 2];

            /* rysowanie pola informaczjnego */
            g.drawLine(160, 30, 300, 30);
            for (i = 0; i < vecProg.size(); i += 4) {
                final java.awt.Color kolor = (Color) vecProg.get(i + 3);
                g.setColor(java.awt.Color.black);
                g.drawString(((String) vecProg.get(i)), 160, 50 + (i / 4) * 20);
                g.drawString(((String) vecProg.get(i + 1)), 250, 50 + (i / 4) * 20);
                // wypelnia dwie polowki tablicy - prawdopodobienstwami p i 1-p
                src[i / 4] = (Double) vecProg.get(i + 2);
                src[src.length - (i / 4) - 1] = 1.0 - (Double) vecProg.get(i + 2);
                g.setColor(kolor);
                g.fillOval(280, 40 + (i / 4) * 20, 15, 15);
                if (kolor.getRed() > 250) {
                    g.setColor(java.awt.Color.black);
                    g.drawOval(280, 40 + (i / 4) * 20, 14, 14);
                }
            }
            final ChiSquare chidist = new ChiSquare();
            chidist.setParameter(ChiSquare.V, viewTestChiSquare._v);
            // pobiera wartosci graniczne chi square dla podanych prawdopodobienstw
            for (i = 0; i < src.length; i++) {
                src[i] = chidist.getInverse(src[i]);
            }
            /* rysowanie kwadratow */
            int x, y, j;
            final int kols = Convert.tryToInt(viewTestChiSquare.getParameter(ViewTestChiSquare.COLS));
            double tmp;
            x = 10;
            y = 10;
            i = 1; // liczba danych

            while (reader.hasNext()) {
                tmp = reader.readDouble();
                g.setColor(java.awt.Color.black);
                g.drawRect(x, y, 25, 25);
                int pos = -1;
                // poszukuj w progach wartosci najmniejszej, ponizej ktorej moze byc
                // sprawdzana wartosc
                for (j = 0; j < src.length / 2; j++) {
                    if (tmp < src[j]) {
                        if (pos == -1) {
                            pos = j;
                        } else if (src[pos] > src[j]) {
                            pos = j;
                        }
                    }
                }
                if (pos == -1) {
                    // poszukuj w progach wartosci najwiekszej, jaka przekroczy
                    // sprawdzana wartosc
                    for (j = src.length / 2; j < src.length; j++) {
                        if (tmp > src[j]) {
                            if (pos == -1) {
                                pos = j;
                            } else if (src[pos] < src[j]) {
                                pos = j;
                            }
                        }
                    }
                    if (pos != -1) {
                        pos = src.length - pos - 1; // przelicz na pierwsza polowke pozycje
                    }
                }
                if (pos != -1) {
                    // rysuj odpowiedni kod
                    final java.awt.Color kolor = (Color) vecProg.get(pos * 4 + 3);
                    g.setColor(kolor);
                    g.fillOval(x + 5, y + 5, 15, 15);
                    if (kolor.getRed() > 250) {
                        g.setColor(java.awt.Color.black);
                        g.drawOval(x + 5, y + 5, 14, 14);
                    }
                }
                x += 25;
                if (((i) % kols) == 0) {
                    x = 10;
                    y += 25;
                }
                i++;
            }
        }
    }

}
