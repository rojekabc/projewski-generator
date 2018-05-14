package pl.projewski.generator.generproj;

import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.tools.Mysys;

import java.io.IOException;
import java.util.Set;

public class HelpFrame extends javax.swing.JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 2533243976077240221L;
    private static final int ID_DLG_ERROR_OPENING = 100;
    private javax.swing.JTextPane pane = null;

    public HelpFrame(final String strContext, final String strTitle,
            final int x, final int y, final int w, final int h) {
        final javax.swing.JScrollPane scrpane = new javax.swing.JScrollPane();
        pane = new javax.swing.JTextPane();
        scrpane.getViewport().setView(pane);
        pane.setContentType("text/html");
        pane.setEditable(false);
        setTitle(strTitle);
        setContentPane(scrpane);
        setBounds(x, y, w, h);
        if (!setNewHelp(strContext, strTitle)) {
            return;
        }
        setBounds(x, y, w, h);
    }

    public HelpFrame(final ParameterInterface pi, final int x, final int y, final int w, final int h) {
        final javax.swing.JScrollPane scrpane = new javax.swing.JScrollPane();
        pane = new javax.swing.JTextPane();
        scrpane.getViewport().setView(pane);
        pane.setContentType("text/html");
        pane.setEditable(false);
        setContentPane(scrpane);
        setBounds(x, y, w, h);
        if (!setNewHelp(pi)) {
            return;
        }
        setBounds(x, y, w, h);
    }

    public HelpFrame(final String helpfile, final int x, final int y, final int w, final int h) {
        if (helpfile == null) {
            return;
        }
        final javax.swing.JScrollPane scrpane = new javax.swing.JScrollPane();
        pane = new javax.swing.JTextPane();
        scrpane.getViewport().setView(pane);
        pane.setContentType("text/html");
        pane.setEditable(false);
        setContentPane(scrpane);
        pane.setCaretPosition(0);
        setBounds(x, y, w, h);
        if (!setNewHelp(helpfile)) {
            return;
        }
        setBounds(x, y, w, h);
    }

    public boolean setNewHelp(final String helpfile) {
        setTitle(helpfile);
        try {
            final java.io.File file = new java.io.File(helpfile);
            final java.net.URL url = file.toURI().toURL();
            pane.setPage(url);
            pane.setCaretPosition(0);
        } catch (final IOException e) {
            Mysys.println(GenerProj.getDescription(ID_DLG_ERROR_OPENING) + helpfile);
            return false;
        }
        setVisible(true);
        return true;
    }

    public boolean setNewHelp(final ParameterInterface pi) {
        final StringBuilder descript;

        descript = new StringBuilder("<HTML>\n");
        descript.append("<HEAD>\n");
        descript.append("<META http-equiv=Content-Type content=\"text/html; charset=");
        descript.append(Mysys.getEncoding());
        descript.append("\">");
        descript.append("<BODY>\n");
        descript.append("<div align=center bgcolor=#00EEEE><BIG>");
        descript.append(pi.getClass().getName());
        descript.append("</BIG></div>\n");
        descript.append(pi.getDescription()).append("<BR>\n");
        final Set<String> params = pi.listParameters();
        for (final String param : params) {
            descript.append("<div align=center bgcolor=#00CCCC>Parametr <B>");
            descript.append(param);
            descript.append("</B></div>\n");
            descript.append(pi.getParameterDescription(param)).append("<BR>\n");
        }
        descript.append("</BODY>\n</HTML>\n");

        pane.setText(descript.toString());
        pane.setCaretPosition(0);
        setTitle(pi.getClass().getName());
        setVisible(true);
        return true;
    }

    public boolean setNewHelp(final String strContext, final String strTitle) {
        if (strContext == null) {
            return false;
        }
        pane.setText(strContext);
        pane.setCaretPosition(0);
        setVisible(true);
        return true;
    }
}
