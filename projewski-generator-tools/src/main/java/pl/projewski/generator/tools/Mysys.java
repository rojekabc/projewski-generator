package pl.projewski.generator.tools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

/**
 * Zapewnia istnienie konfiguracji własnego systemu z następującymi
 * możliwościami:
 * - ustwaienie kodowania wyjściowego na stdout i udostępnienie metod
 * przekierunkowywujących na owo wyjście
 * - Ustwaianie podstawowych ustawień pakietu
 * kodowanie plików xml'a
 * nazwy plików językowych z opisem kodów błędów i innych komunikatów
 */
public class Mysys extends DefaultHandler {
    private static Mysys factoredMysys = null;
    private java.io.Writer out;
    private java.io.Writer err;
    private String encoding = "iso8859-2"; // setNum = 1
    private String langFolderName = "lang";
    private String langName = "pl";
    private int packageSize = 8000; // setNum = 4
    private boolean isDebug = false;
    private java.io.File tempFolder = null;
    private java.io.File langFolder = null;

    private Mysys() {
        initConfiguration();
    }

    private static Mysys factorMysys() {
        if (factoredMysys == null) {
            factoredMysys = new Mysys();
        }
        return factoredMysys;
    }

    public static String getDescription(final String classname, final String descname, final String typename,
            final String[] args) {
        File descriptionFile = null;
        final Mysys mysys = factorMysys();
        if (mysys.langFolder == null) {
            Mysys.error("[WARN] Mysys: nie określono folderu napisów");
            return "";
        }
        descriptionFile = new File(
                mysys.langFolder.toString() + File.separator +
                        typename + File.separator +
                        classname + ".properties");
        if (!descriptionFile.exists()) {
            Mysys.error("[WARN] Nie odnaleziono pliku napisow: " + descriptionFile.toString());
            return "";
        }
        if (!descriptionFile.isFile()) {
            Mysys.error("[WARN] Pozycja nie jest plikiem napisow: " + descriptionFile.toString());
            return "";
        }
        try {
            final PropertyResourceBundle prop = new PropertyResourceBundle(new FileInputStream(descriptionFile));
            String description = prop.getString(descname);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    description = description.replace("$" + i, args[i]);
                }
            }
            return encString(description);
        } catch (final IOException | MissingResourceException e) {
            Mysys.println(e.toString());
        }
        return "";
    }

    public static String encString(final String inStr) {
        final Mysys mysys = factorMysys();
        if (inStr == null) {
            return null;
        }
        try {
            return new String(inStr.getBytes("iso8859-1"), mysys.encoding);
        } catch (final java.io.UnsupportedEncodingException e) {
            System.out.println(e.toString());
            return inStr;
        }
    }

    public static String getEncoding() {
        final Mysys mysys = factorMysys();
        return mysys.encoding;
    }

    /*	public static String getErrCodeFile()
        {
            if (out == null)
                initConfiguration();
            return errcodefile;
        }

        public static String getDlgCodeFile()
        {
            if (out == null)
                initConfiguration();
            return dlgcodefile;
        }
    */
    public static int getPackageSize() {
        final Mysys mysys = factorMysys();
        return mysys.packageSize;
    }

    public static void error(Throwable t) {
        final Mysys mysys = factorMysys();
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        final StackTraceElement last = stack[3];
        try {
            mysys.err.write("ERROR [ " + last.toString() + " ]\n");
            while (t != null) {
                mysys.err.write(t.toString() + "\n");
                mysys.err.flush();
                t = t.getCause();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void error(final String s) {
        final Mysys mysys = factorMysys();
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        try {
            final StackTraceElement last = stack[2];
            mysys.err.write("ERROR [ " + last.toString() + " ] " + s + "\n");
            mysys.err.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public static void debugln(final String s) {
        try {
            final Mysys mysys = factorMysys();
            if (mysys.isDebug) {
                final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                final StackTraceElement last = stack[3];
                println("DEBUG [ " + last.toString() + " ]" + s);
            }
        } catch (final Exception e) {
            System.err.println("Mysys.java: " + e.toString());
        }
    }

    public static void debug(final String s) {
        try {
            final Mysys mysys = factorMysys();
            if (mysys.isDebug) {
                print(s);
            }
        } catch (final Exception e) {
            System.err.println("Mysys.java: " + e.toString());
        }
    }

    public static void print(final String s) {
        try {
            final Mysys mysys = factorMysys();
            mysys.out.write(s);
            mysys.out.flush();
        } catch (final Exception e) {
            System.err.println("Niedozwolony błąd !!!");
            System.err.println(e.toString());
            System.err.println("Niedozwolony błąd !!!");
        }
    }

    public static void println() {
        try {
            final Mysys mysys = factorMysys();
            mysys.out.write('\n');
            mysys.out.flush();
        } catch (final Exception e) {
            System.err.println("Niedozwolony błąd !!!");
            System.err.println(e.toString());
            System.err.println("Niedozwolony błąd !!!");
        }
    }

    public static void println(final String s) {
        try {
            final Mysys mysys = factorMysys();
            mysys.out.write(s);
            mysys.out.write('\n');
            mysys.out.flush();
        } catch (final Exception e) {
            System.err.println("Niedozwolony błąd !!!");
            System.err.println(e.toString());
            System.err.println("Niedozwolony błąd !!!");
        }
    }

    public static File getTempFile() throws IOException {
        final Mysys mysys = factorMysys();
        if (!mysys.tempFolder.exists()) {
            mysys.tempFolder.mkdir();
        }
        if (mysys.tempFolder != null) {
            return File.createTempFile("genprj_", ".tmp", mysys.tempFolder);
        } else {
            return File.createTempFile("genprj_", ".tmp");
        }
    }

    private void writeDefaultConfiguration(final java.io.File file) {
        try {
            final java.io.PrintStream savefile = new java.io.PrintStream(
                    new java.io.FileOutputStream(file));
            savefile.println("<?xml version='1.0' encoding='" + encoding + "'?>");
            savefile.println("<Configuration"
                    + " encoding=\"" + encoding + "\""
                    + " langFolder=\"" + langFolderName + "\""
                    + " language=\"" + langName + "\""
                    + " packageSize=\"" + packageSize + "\""
                    + " debug=\"" + isDebug + "\""
                    + " />");
            savefile.close();
        } catch (final Exception e) {
            System.out.println(e.toString());
        }
    }

    // odczytuje podstawowy plik konfiguracyjny i inicjuje zmienne systemowe
    private void initConfiguration() {
        try {
            java.io.File plik = new java.io.File("mysys.cnf");
            if (!plik.exists()) {
                final URL resource = this.getClass().getResource("/mysys.cnf");
                if (resource != null) {
                    plik = new File(resource.toURI());
                }
                if (!plik.exists()) {
                    writeDefaultConfiguration(plik);
                }
            }
            final DefaultHandler han = this;
            final SAXParserFactory fac = SAXParserFactory.newInstance();
            final SAXParser par = fac.newSAXParser();
            par.parse(plik, han);
            out = new java.io.OutputStreamWriter(System.out, encoding);
            err = new java.io.OutputStreamWriter(System.err, encoding);
        } catch (final Exception e) {
            System.out.println("Mysys: " + e.toString());
        }
    }

    @Override
    public void startDocument()
            throws SAXException {
        System.out.print("Czytanie pliku konfiguracyjnego ...");
        //			isConfig = false;
        //			rdBuf = new String("");
    }

    @Override
    public void endDocument()
            throws SAXException {
        //			rdBuf = null;
        System.out.println("Ok.");
    }

    @Override
    public void startElement(final String uri, final String sn, final String qn, final Attributes att)
            throws SAXException {
        try {
            String name = sn;
            if (name.equals("")) {
                name = qn;
            }
            if (name.equals("Configuration")) {
                encoding = att.getValue("encoding");
                langFolderName = att.getValue("langFolder");
                langName = att.getValue("language");
                packageSize = Integer.parseInt(att.getValue("packageSize"));
                isDebug = att.getValue("debug").equals("true");
                if (att.getValue("tmpFolder") != null) {
                    tempFolder = new File(att.getValue("tmpFolder"));
                }

                if ((langFolderName != null) && (langName != null)) {
                    langFolder = new File(
                            langFolderName
                                    + File.separator
                                    + langName
                    );
                    if (!langFolder.exists()) {
                        final URL resource = Mysys.class.getResource("/" + langFolderName + "/" + langName);
                        if (resource != null) {
                            langFolder = new File(resource.toURI());
                        }
                    }
                    if (langFolder.exists() == false) {
                        System.out.println("[WARN] Nie odnaleziono folderu z napisami: " + langFolder.toString());
                        langFolder = null;
                        return;
                    }
                    if (langFolder.isDirectory() == false) {
                        System.out.println("[WARN] Podana pozycja wskazuje plik: " + langFolder.toString());
                        langFolder = null;
                        return;
                    }
                }
            }
        } catch (final Exception e) {
            System.out.println("Mysys.startElement: " + e.toString());
        }
    }

    @Override
    public void endElement(final String uri, final String sn, final String qn)
            throws SAXException {
    }

    @Override
    public void characters(final char[] buf, final int off, final int len)
            throws SAXException {
    }

}
