package pl.projewski.generator.generproj;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.interfaces.*;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.GeneratedData;
import pl.projewski.generator.tools.GeneratorStoreFile;
import pl.projewski.generator.tools.Mysys;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@Slf4j
public class GenerProj
        extends DefaultHandler {
    //	private String actualParam=null; // aktualnie obrabiany parametr pliku konfiguracyjnego z XMLa
//	private String actName=null, actTick=null;
//	private String rdBuf=null;
    private static final String configurationFile = "generproj.cnf";
    // Do systemu otwierania okien i zamykania, gdzie jedno jest zawsze aktualne
    // a wyjscie nastepuje, gdy juz zostanie zamkniete okno glowne
    private static final java.util.Vector<JFrame> storeFrame = new java.util.Vector<>();
    // tablica nazw plikow z zapamietanymi generatorami
    private static final java.util.Vector<String> vecGeneratorFile = new java.util.Vector<>();
    // tablica zbiorow danych
    private static final java.util.Vector<GeneratedData> vecGeneratedData = new java.util.Vector<>();
    private static JFrame actualFrame = null;
    // ciag okreslajacy miejsce z ustawieniami jezykowymi
    private static String langFolder = "docpl";
    private static HelpFrame helpframe = null;

    public static List<Class<? extends GeneratorInterface>> listGeneratorTypes() {
        final ServiceLoader<PackageInterface> serviceLoader = ServiceLoader.load(PackageInterface.class);
        final Iterator<PackageInterface> iterator = serviceLoader.iterator();
        final List<Class<? extends GeneratorInterface>> result = new ArrayList<>();
        while (iterator.hasNext()) {
            final PackageInterface pack = iterator.next();
            result.addAll(Arrays.asList(pack.listGenerator()));
        }
        return result;
    }

    public static String getLangFolder() {
        return langFolder;
    }

    // ustawienie katalogu j�zykowego, r�wnocze�nie sprawdza istnienie
    // plik�w koniecznych do funkcjonowania
    // Tam s� helpy i plik nazw //TODO: plik nazw
    public static boolean setLangFolder(final String folderName) {
        if (folderName == null) {
            return false;
        }
        java.io.File plik = new java.io.File(folderName);
        if (!plik.exists()) {
            try {
                final URL resource = GenerProj.class.getResource("/" + folderName);
                if (resource != null) {
                    plik = new File(resource.toURI());
                }
            } catch (final URISyntaxException ignore) {
            }
            if (!plik.exists()) {
                return false;
            }
        }
        if (!plik.isDirectory()) {
            return false;
        }
        langFolder = folderName;
        return true;
    }

    public static boolean addGeneratedData(final GeneratedData gdt) {
        if (vecGeneratedData.contains(gdt)) {
            return true;
        }
        vecGeneratedData.add(gdt);
        return true;
    }

    public static void removeGeneratedData(final Object gdt) {
        final String filename = ((GeneratedData) gdt).getFileName();
        final java.io.File f;
        if (filename.endsWith(".gdt")) {
            f = new File(filename);
        } else {
            f = new File(filename + ".gdt");
        }
        if (!f.delete()) {
            Mysys.error("Nieudane usuwanie pliku " + f.getName());
        }
        vecGeneratedData.remove(gdt);
    }

    public static Object[] listGeneratedData() {
        return vecGeneratedData.toArray();
    }

    public static GeneratorInterface createGenerator(final String filename)
            throws GeneratorException {
        if (filename.endsWith(".gen")) {
            return GeneratorStoreFile.loadGeneratorFromFile(filename);
        } else {
            return GeneratorStoreFile.loadGeneratorFromFile(filename + ".gen");
        }
    }

    private static <T> T tryCreateInstance(final Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("Cannot instance " + clazz.getName(), e);
            return null;
        }
    }

    // wylistuj dostepne widoki, ale z uwzglednieniem zrodla danych
    // Odpytuje wtedy dostepne widoki, czy sa w stanie przedstawia dane zrodlo danych
    // i jezeli tak, to wtedy dodaje je do listy
    public static List<Class<? extends ViewDataInterface>> listView(final ParameterInterface dataSource) {
        final List<Class<? extends ViewDataInterface>> result = new ArrayList<>();
        listView().stream()
                .map(GenerProj::tryCreateInstance) // create instance
                .filter(Objects::nonNull) // non null - created
                .filter(viewData -> viewData.canViewData(dataSource)) // can view
                .forEach(viewData -> result.add(viewData.getClass())); // append result
        return result;
    }

    public static List<Class<? extends ViewDataInterface>> listView() {
        final List<Class<? extends ViewDataInterface>> result = new ArrayList<>();
        final ServiceLoader<PackageInterface> serviceLoader = ServiceLoader.load(PackageInterface.class);
        final Iterator<PackageInterface> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            final PackageInterface pack = iterator.next();
            Arrays.stream(pack.listViewData()).forEach(result::add);
        }
        return result;
    }

    public static List<Class<? extends LaborDataInterface>> listTest() {
        final List<Class<? extends LaborDataInterface>> result = new ArrayList<>();
        final ServiceLoader<PackageInterface> serviceLoader = ServiceLoader.load(PackageInterface.class);
        final Iterator<PackageInterface> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            final PackageInterface pack = iterator.next();
            Arrays.stream(pack.listLaborData()).forEach(result::add);
        }
        return result;
    }

    // nazwa bez rozszerzenia / generator - zapisanie do pliku i dodanie do
    // zbiorow - jezeli gi jest jako null, to znaczy, ze istnieje zalozenie, iz
    // dany plik istnieje i nalezy wykonac probe odczytywania pliku
    public static boolean addGenerator(final String filename, GeneratorInterface gi)
            throws GeneratorException {
        if (gi == null) {
            gi = createGenerator(filename); // na niepowodzeniu bedzie null
        } else {
            if (filename.endsWith(".gen")) {
                GeneratorStoreFile.saveGeneratorToFile(filename, gi);
            } else {
                GeneratorStoreFile.saveGeneratorToFile(filename + ".gen", gi);
            }
        }
        if (vecGeneratorFile.contains(filename)) {
            return true;
        }
        if (gi == null) {
            return false;
        }
        vecGeneratorFile.add(filename);
        return true;
    }

    // usuni�cie ze zbior�w generator'a - usuwa r�wnie� wygenerowany plik !
    // z generatorem
    public static void removeGenerator(final String filename) {
        final java.io.File f;
        if (filename.endsWith(".gen")) {
            f = new File(filename);
        } else {
            f = new File(filename + ".gen");
        }
        if (!f.delete()) {
            Mysys.error("Nieudane usuwanie pliku " + f.getName());
        }
        vecGeneratorFile.remove(filename);
    }

    public static Vector<String> listGenerator() {
        return vecGeneratorFile;
    }

    // wyj�cie z zapisaniem aktualnych ustawie�
    protected static void doSystemExit() {
        doSaveConfiguration();
        System.exit(0);
    }

    protected static void doCopyOfConfigurationFile() {
        final java.io.File plik = new java.io.File(configurationFile);
        final java.io.File pliktmp = new java.io.File(configurationFile + ".sav");
        if (pliktmp.exists()) {
            System.out.println("Plik z kopi� zapsaow� ju� istnieje. Praca wstrzymana.");
            System.exit(0);
        }
        if (!plik.renameTo(pliktmp)) {
            System.out.println("Nie mog� utowrzy� kopii zapasowej pliku konfiguracji.");
            System.out.println("Wstrzymanie pracy programu.");
            System.exit(0);
        }
    }

    protected static void doLoadConfiguration() {
        try {
            final DefaultHandler han = new GenerProj();
            final SAXParserFactory fac = SAXParserFactory.newInstance();
            final SAXParser par = fac.newSAXParser();
            final java.io.File plik = new java.io.File(configurationFile);
            if (!plik.exists()) {
                doSaveConfiguration();
            }
            par.parse(new java.io.File(configurationFile), han);
        } catch (final java.io.IOException e) {
            System.out.println("B��d wczytywania pliku konfiguracyjnego.");
            System.out.println(e.toString());
        } catch (final SAXException e) {
            System.out.println("B��d parsera XML na pliku konfiguracyjnym");
            System.out.println("Pr�ba utworzenia kopii pliku konfiguracyjnego");
            doCopyOfConfigurationFile();
        } catch (final javax.xml.parsers.ParserConfigurationException e) {
            System.out.println("B��d konfiguracji parsera XML");
            System.out.println("Pr�ba utworzenia kopii pliku konfiguracyjnego");
            doCopyOfConfigurationFile();
        }
    }

    protected static void doSaveConfiguration() {
        try {
            final java.io.PrintStream savefile = new java.io.PrintStream(
                    new java.io.FileOutputStream(configurationFile));
//			Object [] vecGeneratorType = listGeneratorTypes();
            savefile.println("<?xml version='1.0' encoding='Latin1'?>");
            // zapisz wektor z klasami generator�w
            savefile.println("<GenerProjConfiguration>");
            for (int i = 0; i < vecGeneratorFile.size(); i++) {
                savefile.println("<GeneratorFile file=\"" +
                        vecGeneratorFile.get(i) + "\" />");
            }
            for (int i = 0; i < vecGeneratedData.size(); i++) {
                final GeneratedData gdt = (GeneratedData) vecGeneratedData.get(i);
                savefile.println("<GeneratedData name=\"" + gdt.getName()
                        + "\" tick=\"" + gdt.getDataTick() + "\" />");
            }
            savefile.println("</GenerProjConfiguration>");
            savefile.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void doSwitchToFrame(final JFrame frame) {
        if (frame != null) {
            storeFrame.add(frame);
        }
        if (storeFrame.size() == 0) {
            doSystemExit();
        }
        if (actualFrame != null) {
            actualFrame.setVisible(false);
        }
        actualFrame = (JFrame) storeFrame.lastElement();
        actualFrame.pack();
        actualFrame.setVisible(true);
    }

    public static void doCloseActualFrame() {
        if (actualFrame == null) {
            doSystemExit();
        }
        actualFrame.setVisible(false);
        actualFrame.dispose();
        if (storeFrame.size() == 0) {
            doSystemExit();
        }
        storeFrame.remove(actualFrame);
        if (storeFrame.size() == 0) {
            doSystemExit();
        }
        actualFrame = (JFrame) storeFrame.lastElement();
        actualFrame.setVisible(true);
    }

    public static void main(final String[] args) {
        try {
            final LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
            if (installedLookAndFeels != null) {
                for (final LookAndFeelInfo lookAndFeelInfo : installedLookAndFeels) {
                    System.out.println(lookAndFeelInfo.getClassName());
                }
            }
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

            final String osname = System.getProperty("os.name");
            if (osname.equals("Windows XP")) {
                UIManager.setLookAndFeel("com.stefankrause.xplookandfeel.XPLookAndFeel");
            } else if (osname.equals("Linux")) {
//		javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
//		javax.swing.UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                javax.swing.UIManager.setLookAndFeel("com.digitprop.tonic.TonicLookAndFeel");
            } else {
                System.out.println("Nieznany system operacyjny: " + osname);
            }
        } catch (final Exception e) {
            System.out.println(e.toString());
        }
        doLoadConfiguration();
        if (!setLangFolder(langFolder)) {
            System.out.println("Cannot use correctly language folder " + langFolder);
            System.exit(0);
        }
        doSwitchToFrame(new MainFrame());
    }

    /**
     * Metoda wywo�ywana po uzyskaniu zawarto�ci znacznika XML
     */
/*	public void characters( char[] buf, int offset, int len )
		throws SAXException
	{
	}
*/
    // polacz nazwe bazowa z nazwa klasy, ktora moze byc z kropkami
    // i ostatecznie dodaj nazwe klasy z rozszerzeniem .htm
    private static java.io.File concat(final String basefolder, String classname) {
        int i = 0;
        java.io.File x = new java.io.File(basefolder);
        i = classname.indexOf('.');
        while (i != -1) {
            x = new java.io.File(x, classname.substring(0, i));
            classname = classname.substring(i + 1);
            i = classname.indexOf('.');
        }
        x = new java.io.File(x, classname + ".htm");
        return x;
    }

    public static boolean isHelpFile(final String name) {
        final java.io.File helpfile = concat(langFolder, name);
        if ((!helpfile.exists()) || (!helpfile.isFile())) {
            System.out.println("No helpfile: " + helpfile.toString());
            return false;
        }
        return true;
    }

    public static void showHelp(final String name) {
        final java.io.File helpfile = concat(langFolder, name);
        if (!isHelpFile(name)) {
            System.out.println("Niedost�pny plik pomocy " + helpfile.getPath());
            return;
        }
        if (helpframe == null) {
            helpframe = new HelpFrame(helpfile.getAbsolutePath(), 10, 10, 450, 300);
        } else {
            helpframe.setNewHelp(helpfile.getAbsolutePath());
        }
    }

    public static void showHelp(final ParameterInterface pi) {
        final String name = pi.getClass().getName();
        if (isHelpFile(name)) {
            showHelp(name);
        } else {
            if (helpframe == null) {
                helpframe = new HelpFrame(pi, 10, 10, 450, 300);
            } else {
                helpframe.setNewHelp(pi);
            }
        }
    }

    /**
     * Metoda wywolywana przy zamknieciu analizowania znacznika XML
     */
/*	public void endElement( String uriname, String sname, String qname )
	{
	}
*/
    public static void showHelp(final Object obj) {
        final String name = obj.getClass().getName();
        showHelp(name);
    }

    public static String getDescription(final int index) {
        try {
            return Mysys.getDescription(GenerProj.class.getSimpleName(), "" + index, "generproj", null);
        } catch (final Exception e) {
            Mysys.println(e.toString());
            return "";
        }
    }

    /**
     * Metoda wywo�ywana przy rozpocz�ciu analizy dokumentu XML
     */
    @Override
    public void startDocument()
            throws SAXException {
//		rdBuf = new String("");
    }

    /**
     * Metoda wywo�ywana przy zako�czeniu analizy dokumentu XML
     */
    @Override
    public void endDocument()
            throws SAXException {
    }

    /**
     * Metoda wywo�ywana przy rozpocz�ciu analizowania znacznika XML
     */
    @Override
    public void startElement(final String uriname, final String sname, final String qname,
                             final Attributes attr)
            throws SAXException {
        try {
            String name = sname;
            if (name.equals("")) {
                name = qname;
            }

            if (name.equals("GeneratedData")) {
                final long l = Convert.tryToLong(attr.getValue("tick"));
                final GeneratedData gdt = new GeneratedData(attr.getValue("name"), l);
                addGeneratedData(gdt);
            } else if (name.equals("GeneratorFile")) {
                if (addGenerator(attr.getValue("file"), null) == false) {
                    Mysys.println("Nieudane �adowanie generatora " + attr.getValue("file"));
                }
            }
        } catch (final Exception e) {
            System.out.println("startElement " + e.toString());
        }
    }
}
