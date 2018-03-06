/**
 *
 */
package pl.projewski.generator.generproj;

import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.exceptions.ViewDataException;
import pl.projewski.generator.generproj.layout.AlaNullLayout;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.interfaces.ViewDataInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * @author projewski
 */
public class ParameterDialog extends JDialog implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final JButton dialogButtOk;
    private final ParameterInterface inPi;
    private final ParameterParamPack paramPack;
    private final ViewDataInterface view;

    ParameterDialog(final Frame frame, final ParameterInterface pi, final ViewDataInterface vi) {
        super(frame, true);
        dialogButtOk = new JButton("Ok");
        dialogButtOk.setBounds(400, 10, 80, 25);
        dialogButtOk.addActionListener(this);
        //JPanel panel = new ParameterParamPack(pi);
        // JPanel panel = new JPanel();
        paramPack = new ParameterParamPack(pi);
        setBounds(10, 10, 500, 300);
        setContentPane(new JPanel());
        getContentPane().setLayout(new AlaNullLayout());
        paramPack.setLayout(new AlaNullLayout());
        //panel.setLayout(new AlaNullLayout());

        //paramPack = new ParameterParamPack(panel, pi);

        final JScrollPane scrPanParam = new javax.swing.JScrollPane(paramPack);
        //scrPanParam.getViewport().setView(panel);
        scrPanParam.setBounds(10, 10, 380, 250);

        getContentPane().add(scrPanParam);
        getContentPane().add(dialogButtOk);
        inPi = pi;
        view = vi;
    }

    static void doParameterDialog(final Frame frame, final ParameterInterface pi, final ViewDataInterface vi) {
        final ParameterDialog pd = new ParameterDialog(frame, pi, vi);
        pd.setVisible(true);
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        final Object o = evt.getSource();

        if (o == this.dialogButtOk) {
            paramPack.getParameterInterface(inPi);
//			Object[] paramValue;
            final Set<String> paramName;
            try {
//				paramValue = inPi.listValues();
                paramName = inPi.listParameters();
            } catch (final ParameterException e) {
                Mysys.error(e);
                return;
            }

            for (final String param : paramName) {
                try {
                    if (inPi.getParameter(param) == null) {
                        JOptionPane.showMessageDialog(this,
                                Convert.stringWrap("Niepoprawnie ustawiona wartosc parametru " + param, 30),
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (final ParameterException e) {
                    Mysys.error(e);
                    return;
                }
            }

            try {
                view.refreshView();
            } catch (final ViewDataException e) {
                Mysys.error(e);
            }

            setVisible(false);
            return;
        }

    }

}
