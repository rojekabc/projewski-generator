package pl.projewski.generator.generproj;

import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.tools.Mysys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

public class HelpFrame extends javax.swing.JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 2533243976077240221L;
    private static final int ID_DLG_ERROR_OPENING = 100;
    javax.swing.JTextPane pane = null;

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
        return;
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
        return;
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
        return;
    }

    public boolean setNewHelp(final String helpfile) {
        setTitle(helpfile);
        try {
            final java.io.File file = new java.io.File(helpfile);
            final java.net.URL url = file.toURL();
            pane.setPage(url);
            pane.setCaretPosition(0);
        } catch (final MalformedURLException e) {
            Mysys.println(GenerProj.getDescription(ID_DLG_ERROR_OPENING) + helpfile);
            return false;
        } catch (final IOException e) {
            Mysys.println(GenerProj.getDescription(ID_DLG_ERROR_OPENING) + helpfile);
            return false;
        }
        setVisible(true);
        return true;
    }

    public boolean setNewHelp(final ParameterInterface pi) {
        String descript;

        descript = "<HTML>\n";
        descript += "<HEAD>\n";
        descript += "<META http-equiv=Content-Type content=\"text/html; charset=";
        descript += Mysys.getEncoding();
        descript += "\">";
        descript = "<BODY>\n";
        descript += "<div align=center bgcolor=#00EEEE><BIG>";
        descript += pi.getClass().getName();
        descript += "</BIG></div>\n";
        try {
            descript += pi.getDescription() + "<BR>\n";
        } catch (final ParameterException e) {
            Mysys.println(e.toString());
        }
        Set<String> params = null;
        try {
            params = pi.listParameters();
        } catch (final ParameterException e) {
            Mysys.println(e.toString());
            return false;
        }
        for (final String param : params) {
            descript += "<div align=center bgcolor=#00CCCC>Parametr <B>";
            descript += param;
            descript += "</B></div>\n";
            try {
                descript += pi.getParameterDescription(param) + "<BR>\n";
            } catch (final ParameterException e) {
                Mysys.println(e.toString());
            }
        }
        descript += "</BODY>\n</HTML>\n";

        pane.setText(descript);
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
