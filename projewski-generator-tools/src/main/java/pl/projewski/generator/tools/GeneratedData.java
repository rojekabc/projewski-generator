package pl.projewski.generator.tools;

import org.xml.sax.helpers.DefaultHandler;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.NumberStoreException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.tools.parser.GeneratedDataParser;
import pl.projewski.generator.tools.stream.SeparatorStreamReader;
import pl.projewski.generator.tools.stream.SeparatorStreamWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class GeneratedData implements NumberInterface
//extends DefaultHandler
{
    // jezeli plik danych juz istnieje to bedzie nastepowalo dopisywanie do
    // jego konca nowych paczek danych
    private long datumTick = 0; // czas generacji
    private String name = null; // nazwa (z niej powstaja nazwy plikow)
    private ParameterInterface source = null;
    private int dataCount = 0;
    private ClassEnumerator classType = null;

    private NumberWriter writer = null;

    private java.io.PrintStream infoStream = null;

    public GeneratedData(final String generationName) {
        this(generationName, System.currentTimeMillis(), null);
    }

    public GeneratedData() {
        this(null, System.currentTimeMillis(), null);
    }

    // zalozenie obiektu
    public GeneratedData(final long dataTick) {
        this(null, dataTick, null);
    }

    // zalozenie obiektu
    public GeneratedData(final String generationName, final long dataTick) {
        this(generationName, dataTick, null);
    }

    public GeneratedData(final String generationName, final ParameterInterface src) {
        this(generationName, System.currentTimeMillis(), src);
    }

    public GeneratedData(final String generationName, final long dataTick, final ParameterInterface src) {
        if ((generationName != null) && (generationName.trim().length() != 0)) {
            name = generationName.trim();
        }
        datumTick = dataTick;
        source = src;
// PROPONOWANE MARKOWANIE
//		java.io.File plik = new java.io.File( name + ".gdt" );
//		if ( plik.exists() )
//		{
//			readFile(plik);
//		}
    }

    public static GeneratedData createTemporary() throws IOException {
        final File tmp = Mysys.getTempFile();
        final GeneratedData gdt = new GeneratedData(tmp.getAbsolutePath());
        gdt.setDatumTick(System.currentTimeMillis());
        if (!tmp.delete()) {
            Mysys.encString("Nieudane usuwanie tymczasowego pliku " + tmp.getName());
        }
        return gdt;
    }

    public boolean delete() {
        boolean ret = true;
        File f = new File(getFileName());
        if (f.exists()) {
            ret &= f.delete();
        }
        f = new File(getDataFilename());
        if (f.exists()) {
            ret &= f.delete();
        }
        return ret;
    }

    public void setDatumTick(final long datumTick) {
        this.datumTick = datumTick;
    }

    protected boolean readFile(final java.io.File plik) throws NumberStoreException {
        try {
            final DefaultHandler han = new GeneratedDataParser(this);
            final SAXParserFactory fac = SAXParserFactory.newInstance();
            final SAXParser par = fac.newSAXParser();
            par.parse(plik, han);
            Mysys.debugln("Read file");
            return true;
        } catch (final Exception e) {
            throw new NumberStoreException(e);
        }
    }

    public boolean readFile() throws NumberStoreException {
        final File f = new File(getFileName());
        if (f.exists()) {
            return readFile(new File(getFileName()));
        }
        return false;
    }

    public boolean wrtieFile() throws FileNotFoundException {
        datumTick = System.currentTimeMillis();
        infoStream = new java.io.PrintStream(new java.io.FileOutputStream(this.getFileName()));
        infoStream.println("<?xml version='1.0' encoding='Latin1'?>");
        infoStream.println("<GeneratedData>");
        final java.util.Date date = new java.util.Date(datumTick);
        final java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();
        infoStream.println("<DateTime tick='" + datumTick + "' date='" + df.format(date) + "' />");

        String classtypename = null;
        if (classType == ClassEnumerator.INTEGER) {
            classtypename = "Int";
        } else if (classType == ClassEnumerator.LONG) {
            classtypename = "Long";
        } else if (classType == ClassEnumerator.FLOAT) {
            classtypename = "Float";
        } else if (classType == ClassEnumerator.DOUBLE) {
            classtypename = "Double";
        } else {
            return false;
        }

        if (source != null) {
            ParameterStoreFile.writeParameterInterface(infoStream, source, "Source");
        }
        infoStream.println("<Pack count='"
                + this.dataCount + "' file='"
                + this.getDataFilename() + "' classtype='"
                + classtypename + "'/>");

        infoStream.println("</GeneratedData>");
        infoStream.close();
        return true;
    }

    // zwraca nazwe pliku, jaki jest uzywany do przechowywania/pobierania danych
    public String getFileName() {
        return name + ".gdt";
    }

    public String getDataFilename() {
        return name + ".dat";
    }

    public long getDataTick() {
        return datumTick;
    }

    public String getName() {
        return name;
    }

    // toString
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof GeneratedData)) {
            return false;
        }
        return name.equals(o.toString());
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        } else {
            return 0;
        }
    }

    @Override
    public NumberReader getNumberReader() throws NumberStoreException {
        try {
            return new SeparatorStreamReader(new FileInputStream(this.getDataFilename()));
        } catch (final FileNotFoundException e) {
            throw new NumberStoreException(e);
        }
    }

    @Override
    public NumberWriter getNumberWriter() throws NumberStoreException {
        if (writer == null) {
            try {
                writer = new SeparatorStreamWriter(new FileOutputStream(this.getDataFilename()));
            } catch (final FileNotFoundException e) {
                throw new NumberStoreException(e);
            }
        }
        return writer;
    }

    @Override
    public int getSize() {
        return this.dataCount;
    }

    @Override
    public void setSize(final int size) {
        this.dataCount = size;
    }

    @Override
    public ClassEnumerator getStoreClass() {
        return this.classType;
    }

    @Override
    public void setStoreClass(final ClassEnumerator c) {
        this.classType = c;
    }

    @Override
    public ParameterInterface getDataSource() throws NumberStoreException {
        return source;
    }

    @Override
    public void setDataSource(final ParameterInterface source) {
        this.source = source;
    }

}
