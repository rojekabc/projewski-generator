package pl.projewski.generator.viewdata.swing;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.exceptions.WrongDataGeneratorException;
import pl.projewski.generator.interfaces.GeneratorInterface;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.GeneratorTool;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.exceptions.ReadFileGeneratorException;
import pl.projewski.generator.tools.exceptions.WriteFileGeneratorException;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class ViewRowSwingTable
        extends ViewDataBase
        implements WindowListener {
    public final static int F_START_LINE = 1;
    public final static int F_END_LINE = 2;
    public final static int F_BOUND_COL = 4;
    public final static int F_ROW_LINE = 8;
    public final static int F_TITLE_LINE = 16;
    public final static int F_NAME_COL_LINE = 32;
    public final static int F_NAME_LINE = 64;

    private final static String TITLENAME = "TableName";
    private final static String ROWNAMES = "RowNames";
    private final static String FLAGS = "Flags";
    private final static String TABLELENGTH = "TableLength";
    private final static String DATALENGTH = "DataLength";
    private final static String NAMELENGTH = "NameLength";
    private final static String STRINGBETWEENDATA = "StringBetweenData";
    //	private final static String INPUTDATA = "InputData";
    private final static String NUMOFDATA = "NumOfData";
    private final static String TYPEOFDATA = "TypeOfData"; // na co ma odbywac sie konwersja

    private File dataFile;
    private Vector<Object> vec;

    @Override
    public void initParameters() {
        parameters.put(TITLENAME, null);
        parameters.put(ROWNAMES, new String[0]);
        parameters.put(FLAGS, Integer.valueOf(0));
        parameters.put(TABLELENGTH, Integer.valueOf(60));
        parameters.put(DATALENGTH, Integer.valueOf(8));
        parameters.put(NAMELENGTH, Integer.valueOf(12));
        parameters.put(STRINGBETWEENDATA, " ");
        //		parameters.put(INPUTDATA, null);
        parameters.put(NUMOFDATA, null);
        parameters.put(TYPEOFDATA, null);
    }

    /**
     * M4_GEN_PI_GETALLOWEDCLASS_I
     */
    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(TITLENAME) || param.equals(STRINGBETWEENDATA)) {
            return Arrays.asList(String.class);
        } else if (param.equals(ROWNAMES)) {
            return Arrays.asList(String[].class);
        } else if (param.equals(FLAGS) || param.equals(TABLELENGTH) || param.equals(DATALENGTH)
                || param.equals(NAMELENGTH)) {
            return Arrays.asList(Integer.class);
        }
        //		else if ( param.equals(INPUTDATA) )
        //		{
        //			tmp = new Class[2];
        //			tmp[0] = NumberInterface.class;
        //			tmp[1] = GeneratorInterface.class;
        //		}
        else if (param.equals(TYPEOFDATA)) {
            return Arrays.asList(Class.class, Long.class, Integer.class, Float.class, Double.class);
        } else {
            return ListUtils.EMPTY_LIST;
        }
    }

    @Override
    public Object getView() {
        return null;
    }

    /**
     * M4_GEN_VDI_SHOWVIEW
     */
    @Override
    public void showView() {
        String title = null;
        String[] rownames = new String[0];
        String beedata = ""; // ciag między danymi
        int flags = 0, tLength = 60, dLength = 8, nLength = 12;
        //		String bData = null;
        byte[] formLine = null;
        //		Vector<Object> data = null;
        int numOfData = -1;
        Class<?>[] types = null;

        final JFrame frame;
        final JScrollPane scrDesc;
        final JTextPane paneDescr;
        dataFile = null;
        java.io.PrintStream dataStream = null;
        try {
            dataFile = Mysys.getTempFile();
            dataStream = new java.io.PrintStream(
                    new FileOutputStream(dataFile));
        } catch (final IOException e) {
            //			if ( dataStream != null )
            //				dataStream.close();
            if (dataFile != null) {
                if (!dataFile.delete()) {
                    Mysys.error("Nieudane usuwanie tymczasowego pliku " + dataFile.getName());
                }
            }
            throw new WriteFileGeneratorException(dataFile.getName(), e);
        }
        // Tymczasowy plik do odczytania

        frame = new JFrame("Rezultaty");
        scrDesc = new JScrollPane();
        paneDescr = new JTextPane();
        paneDescr.setEditable(false);
        scrDesc.getViewport().setView(paneDescr);
        //		frame.setContentPane( new javax.swing.JPanel() );
        //		frame.getContentPane().setLayout(new java.awt.GridLayout(1, 1));
        frame.getContentPane().add(scrDesc);
        frame.addWindowListener(this);

        // Przeinicjowanie zmiennych
        //		if ( parameters.get(INPUTDATA) != null )
        //			data = (java.util.Vector)parameters.get( INPUTDATA );
        //		else
        if (vec == null) {
            throw new WrongDataGeneratorException();
        }
        // Test na zgodnosc klas w wektorze
        for (int i = 0; i < vec.size(); i++) {
            final Object value = vec.get(i);
            if (value instanceof NumberInterface || value instanceof GeneratorInterface) {
                continue;
            }
            throw new WrongDataGeneratorException();
        }

        if (parameters.get(TITLENAME) != null) {
            title = (String) parameters.get(TITLENAME);
        }
        if (parameters.get(ROWNAMES) != null) {
            rownames = (String[]) parameters.get(ROWNAMES);
        }
        if (parameters.get(FLAGS) != null) {
            flags = Convert.tryToInt(parameters.get(FLAGS));
        }
        if (parameters.get(TABLELENGTH) != null) {
            tLength = Convert.tryToInt(parameters.get(TABLELENGTH));
        }
        if (parameters.get(DATALENGTH) != null) {
            dLength = Convert.tryToInt(parameters.get(DATALENGTH));
        }
        if (parameters.get(NAMELENGTH) != null) {
            nLength = Convert.tryToInt(parameters.get(NAMELENGTH));
        }
        if (parameters.get(STRINGBETWEENDATA) != null) {
            beedata = (String) parameters.get(STRINGBETWEENDATA);
        }
        if (parameters.get(NUMOFDATA) != null) {
            numOfData = Convert.tryToInt(parameters.get(NUMOFDATA));
        }
        if (parameters.get(TYPEOFDATA) != null) {
            types = (Class[]) parameters.get(TYPEOFDATA);
        }

        // Ustalanie typów używanych do pokazywania
        if (types == null) {
            types = new Class[vec.size()];
            for (int i = 0; i < types.length; i++) {
                types[i] = Long.class;
            }
        }
        if (types.length < vec.size()) {
            final Class<?>[] tmp = new Class[vec.size()];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = Long.class;
            }
            for (int i = 0; i < types.length; i++) {
                tmp[i] = types[i];
            }
            types = tmp;
        }

        // Formowanie linijki długiej do obrysowań
        formLine = new byte[tLength];
        for (int i = 0; i < formLine.length; i++) {
            formLine[i] = '-';
        }

        Map<NumberInterface, NumberReader> readers = null;

        try {

            if ((flags & F_START_LINE) == F_START_LINE) {
                dataStream.println(new String(formLine));
            }
            if (title != null) {
                if ((flags & F_BOUND_COL) == F_BOUND_COL) {
                    dataStream.println("|" + formData(title, tLength - 2, 1) + "|");
                } else {
                    dataStream.println(formData(title, tLength, 1));
                }
            }
            if ((flags & F_TITLE_LINE) == F_TITLE_LINE) {
                dataStream.println(new String(formLine));
            }

            final int nj = vec.size();
            int nx = tLength;

            // ustala najmniejszą możliwą liczbę danych - zabezpieczenie przed
            // wyjściem poza rozmiar tablicy
            for (int i = 0; i < nj; i++) {
                if (vec.get(i) instanceof NumberInterface) {
                    final int tmp = ((NumberInterface) vec.get(i)).getSize();
                    if (numOfData == -1) {
                        numOfData = tmp;
                    } else if (tmp < numOfData) {
                        numOfData = tmp;
                    }
                }
            }

            if (numOfData < 0) {
                throw new WrongDataGeneratorException();
            }

            // Ustalanie nx, czyli ile danych wypadnie w jednym wierszu na podstawie
            // dlugosci roznych dodatkow itp, ale tak, aby nie wyszly poza obrys
            if ((flags & F_BOUND_COL) == F_BOUND_COL) // jeśli będą kolumny
            {
                nx -= 2;
            }
            if (rownames.length > 0) // Jeżeli są nazwy wierszy
            {
                nx -= nLength;
                if ((flags & F_NAME_COL_LINE) == F_NAME_COL_LINE) // kolumna dzieląca
                {
                    nx--;
                }
            }
            nx /= (dLength + beedata.length()); // Wydziel przez dlugość danych+podzial

            String prow;
            int i = 0;
            String tmpNum;
            Object tmpObj;
            readers = new HashMap<>();
            while (i < numOfData) {
                if ((i != 0) && ((flags & F_ROW_LINE) == F_ROW_LINE)) {
                    dataStream.println(new String(formLine));
                }

                for (int j = 0; j < nj; j++) {
                    tmpObj = vec.get(j);
                    prow = "";
                    if ((flags & F_BOUND_COL) == F_BOUND_COL) {
                        prow += "| ";
                    }
                    if ((j < rownames.length) && (rownames[j] != null)) {
                        prow += formData(rownames[j], nLength, 0);
                    }

                    for (int a = 0; (a < nx) && (a + i < numOfData); a++) {
                        if (tmpObj instanceof NumberInterface) {
                            NumberReader reader = readers.get(tmpObj);
                            final NumberInterface numIn = (NumberInterface) tmpObj;
                            if (reader == null) {
                                readers.put(numIn, reader = numIn.getNumberReader());
                            }
                            tmpNum = reader.readAsObject(numIn.getStoreClass()).toString();
                        } else if (tmpObj instanceof GeneratorInterface) {
                            tmpNum = GeneratorTool.generate((GeneratorInterface) tmpObj, types[j]).toString();
                        } else {
                            tmpNum = "ERR";
                        }
                        prow += formData(tmpNum, dLength, 2);
                        if (a != nx - 1) {
                            prow += beedata;
                        }
                    }
                    while (prow.length() < tLength - 1) {
                        prow += ' ';
                    }
                    if ((flags & F_BOUND_COL) == F_BOUND_COL) {
                        prow += "| ";
                    } else {
                        prow += ' ';
                    }
                    dataStream.println(prow);
                }
                i += nx;
            }


            if ((flags & F_END_LINE) == F_END_LINE) {
                dataStream.println(new String(formLine));
            }
        } finally {
            dataStream.flush();
            dataStream.close();
            if (readers != null) {
                final Iterator<NumberReader> kolekcja = readers.values().iterator();
                while (kolekcja.hasNext()) {
                    kolekcja.next().close();
                }
            }
            //			if ( !dataFile.delete() )
            //				Mysys.error("Nie moge usunac danych tymczasowych");
        }

        final URI uri = dataFile.toURI();
        try {
            paneDescr.setPage(uri.toURL());
        } catch (final IOException e) {
            throw new ReadFileGeneratorException(dataFile.getName(), e);
        }
        paneDescr.setCaretPosition(0);

        frame.setBounds(0, 0, 500, 200);
        //		Convert.setCentral( frame, 400, 200 );
        //		Convert.setCentral( frame, 400, 200 );
        //		frame.pack();
        frame.setVisible(true);
        frame.setBounds(0, 0, 500, 200);
        //		Convert.setCentral( frame, 400, 200 );


    }

    // justify: 0 Left   1 Center   2 Right
    private String formData(final String text, final int length, final int justify) {
        final byte[] tmp;
        String out;

        if (text.length() < length) {
            if (justify == 1) {
                tmp = new byte[(length - text.length()) / 2];
            } else {
                tmp = new byte[length - text.length()];
            }
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = ' ';
            }

            switch (justify) {
            case 0:
                out = text + (new String(tmp));
                break;
            case 1:
                out = (new String(tmp)) + text + (new String(tmp));
                break;
            case 2:
                out = (new String(tmp)) + text;
                break;
            default:
                return "";
            }
        } else {
            out = text.substring(0, length);
        }
        while (out.length() < length) {
            out += ' ';
        }
        return out;

    }

    /**
     * M4_GEN_VDI_REFRESHVIEW
     */
    @Override
    public void refreshView() {
    }

    /**
     * M4_GEN_VDI_SETDATA_NSI
     */
    @Override
    public void setData(final NumberInterface data) {
        //		Object obj = parameters.get(INPUTDATA);
        if (vec == null) {
            vec = new Vector<>();
        }
        vec.add(data);
        //		parameters.put(INPUTDATA, vec);
    }

    @Override
    public void windowActivated(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(final WindowEvent e) {
        ((JFrame) e.getSource()).dispose();
        if (dataFile != null) {
            if (!dataFile.delete()) {
                Mysys.error("Problem z usunięciem pliku tymczasowego");
            }
        }
    }

    @Override
    public void windowDeactivated(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(final WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(final WindowEvent e) {
        // TODO Auto-generated method stub

    }
}
