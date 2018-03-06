/**
 *
 */
package pl.projewski.generator.generproj;

import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.tools.Mysys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author projewski
 */
public class ParameterLine {
    Component label;
    Component edit;
    int height;

    private ParameterLine() {
    }

    public static ParameterLine generateParameterLine(final ParameterInterface pi, final String param,
                                                      final ActionListener listener) throws
            ParameterException {
        // final String[] params = pi.listParameters();
        final String paramName = Mysys.encString(param);
        final Object paramValue = pi.getParameter(param);
        final List<Class<?>> allowedClasses = pi.getAllowedClass(param);
        final ParameterLine retObj = new ParameterLine();
        retObj.setOpisByClass(allowedClasses, paramName, listener);
        retObj.setComponentByClass(allowedClasses, paramValue);
        return retObj;
    }

    /**
     * Sprawdzenie, czy w danej tablicy wystepuje okreslona klasa
     *
     * @param table tablica
     * @param c     klasa
     * @return true, je�li tak
     */
    private boolean hasClass(final List<Class<?>> table, final Class<?> c) {
        return table == null ? false : table.contains(c);
    }

    /**
     * Na podstawie podanej klasy zostaje utworzony obiekt, kt�ry pozwoli na edytowanie
     * zawarto�ci obiektu tej klasy.
     *
     * @param c         Lista klas, jaki obiekt obs�uguje
     * @param initValue warto�� pocz�tkowa
     * @return Utworzony obiekt
     */
    private void setComponentByClass(final List<Class<?>> c, final Object initValue) {
        if (hasClass(c, GeneratorInterface.class)) {
            final JComboBox tmp = new JComboBox();
            final List<Class<? extends GeneratorInterface>> generatorTypes = GenerProj.listGeneratorTypes();
            String n;
            tmp.setEditable(true);
            for (final Class<? extends GeneratorInterface> generatorType : generatorTypes) {
                n = generatorType.getSimpleName();
                if (n.lastIndexOf('.') != -1) {
                    n = n.substring(n.lastIndexOf('.') + 1);
                }
                tmp.addItem(n);
            }
            if (initValue != null) {
                if (initValue instanceof GeneratorInterface) {
                    n = initValue.getClass().getName();
                    if (n.lastIndexOf('.') != -1) {
                        n = n.substring(n.lastIndexOf('.') + 1);
                    }
                } else {
                    n = initValue.toString();
                }
                tmp.setSelectedItem(n);
            } else if (generatorTypes.size() > 0) {
                tmp.setSelectedIndex(0);
            }
            edit = tmp;
            edit.setFont(edit.getFont().deriveFont(java.awt.Font.PLAIN));
            height = 1;
        }
        // czy jest to pole tekstowe
        else if (hasClass(c, String.class)) {
            if (initValue != null) {
                edit = new JTextArea(initValue.toString());
            } else {
                edit = new JTextArea();
            }
            edit.setFont(edit.getFont().deriveFont(java.awt.Font.PLAIN));
            final JScrollPane scroll = new JScrollPane(edit);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            edit = scroll;
            height = 3;
        }
        // jakies pozostale typy (integer, double, float, long)
        else {
            if (initValue != null) {
                edit = new javax.swing.JTextField(initValue.toString());
            } else {
                edit = new javax.swing.JTextField();
            }
            edit.setFont(edit.getFont().deriveFont(java.awt.Font.PLAIN));
            height = 1;
        }
    }

    private void setOpisByClass(final List<Class<?>> c, final Object descr, final ActionListener listener) {
        // je�eli jest to generator, pozw�l na edycje jego parametr�w
        if (hasClass(c, GeneratorInterface.class)) {
            if (descr != null) {
                label = new javax.swing.JButton(descr.toString());
            } else {
                label = new javax.swing.JButton("UNKNOWN");
            }
            ((javax.swing.JButton) label).addActionListener(listener);
        } else {
            if (descr != null) {
                label = new javax.swing.JLabel(descr.toString());
            } else {
                label = new javax.swing.JLabel("UNKNOWN");
            }
        }
        label.setFont(label.getFont().deriveFont(java.awt.Font.PLAIN));
    }

}
