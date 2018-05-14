package pl.projewski.generator.tools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.tools.exceptions.ReadFileGeneratorException;
import pl.projewski.generator.tools.exceptions.WriteFileGeneratorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GeneratorStoreFile
        extends DefaultHandler {
    private String readBuf = null;
    private ParameterStoreFile buildStructure = null;
    //	private static java.util.Vector buildStructure = new java.util.Vector();

    /**
     * M4_GENERATOR_STORE_FILE_FUN_LOAD_GENERATOR_S
     */
    public static GeneratorInterface loadGeneratorFromFile(final String filename)
            throws GeneratorException {
        try {
            final GeneratorStoreFile han = new GeneratorStoreFile();
            final SAXParserFactory fac = SAXParserFactory.newInstance();
            final SAXParser par = fac.newSAXParser();
            final File file = new File(filename);
            par.parse(file, han);
            final GeneratorInterface giRet = (GeneratorInterface) han.buildStructure.getLastObject();
            han.buildStructure.closeObject();
            return giRet;
        } catch (final SAXException | ParserConfigurationException | IOException e) {
            throw new ReadFileGeneratorException(filename, null);
        }
    }

    /**
     * M4_GENERATOR_STORE_FILE_FUN_SAVE_GENERATOR_TO_FILE_S_GI
     */
    public static void saveGeneratorToFile(final String filename,
            final GeneratorInterface gen)
            throws GeneratorException {
        try {
            final java.io.File f = new java.io.File(filename);
            final PrintStream savefile = new PrintStream(new FileOutputStream(filename));
            savefile.println("<?xml version='1.0' encoding='Latin1' ?>");
            ParameterStoreFile.writeParameterInterface(savefile, gen, "generator");

            savefile.close();
        } catch (final java.io.IOException e) {
            throw new WriteFileGeneratorException(filename, e);
        }
    }

    /**
     * Metoda wywoływana przy rozpoczęciu analizy dokumentu XML
     */
    @Override
    public void startDocument() {
        buildStructure = new ParameterStoreFile();
        readBuf = "";
    }

    /**
     * Metoda wywoływana przy zakończeniu analizy dokumentu XML
     */
    @Override
    public void endDocument() {
    }

    /**
     * Metoda wywoływana przy rozpoczęciu analizowania znacznika XML
     */
    @Override
    public void startElement(final String uriname, final String sname, final String qname,
            final Attributes attr) {
        try {
            String name = sname;
            readBuf = "";
            if (name.equals("")) {
                name = qname;
            }
            if (name.equals("parameter") || name.equals("generator")) {
                String clName = null, parName = null;
                for (int i = 0; i < attr.getLength(); i++) {
                    if (attr.getQName(i).equals("class")) {
                        clName = attr.getValue(i);
                    } else if (attr.getQName(i).equals("name")) {
                        parName = attr.getValue(i);
                    } else {
                        System.out.println("Nieznany atrybut generatora " + attr.getQName(i));
                    }
                }
                Mysys.debugln("Open object " + parName + " of " + clName);
                buildStructure.openObject(parName, clName);
            }
        } catch (final Exception e) {
            System.out.println("GeneratorStoreFile.startElement " + e.toString());
        }
    }

    /**
     * Metoda wywoływana przy zamknięciu analizowania znacznika XML
     */
    @Override
    public void endElement(final String uriname, final String sname, final String qname) {
        try {
            String name = sname;
            if (name.equals("")) {
                name = qname;
            }
            if (name.equals("parameter")) {
                readBuf = readBuf.trim();
                buildStructure.setObjectValue(readBuf);
                readBuf = "";
                Mysys.debugln("Close object ");
                buildStructure.closeObject();
            } else if (name.equals("generator")) {
                readBuf = "";
            }
        } catch (final Exception e) {
            System.out.println("endElement " + e.toString());
        }
    }

    /**
     * Metoda wywoływana po uzyskaniu zawartości znacznika XML
     */
    @Override
    public void characters(final char[] buf, final int offset, final int len) {
        final String s = new String(buf, offset, len);
        readBuf += s;
    }
}
