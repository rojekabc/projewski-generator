package pl.projewski.generator.viewdata.text;

import pl.projewski.generator.abstracts.ViewDataBase;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.distribution.ChiSquare;
import pl.projewski.generator.exceptions.WrongParameterGeneratorException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.interfaces.ParameterInterface;
import pl.projewski.generator.labordata.TestChiSquare;
import pl.projewski.generator.tools.Convert;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.VectorDouble;

import java.util.Arrays;
import java.util.List;

/**
 * @author projewski
 */
public class PrintTestChiSquare extends ViewDataBase {

    public static final String SECTIONS = "Sekcje";
    private NumberInterface data;

    @Override
    public boolean canViewData(final ParameterInterface dataSource) {
        return dataSource instanceof TestChiSquare;
    }

    @Override
    public Object getView() {
        return null;
    }

    @Override
    public void refreshView() {
    }

    @Override
    public void setData(final NumberInterface sourceData) {
        data = sourceData;
    }

    private void checkParameters() {
        final Object obj = parameters.get(SECTIONS);
        if (obj != null) {
            final VectorDouble sections = (VectorDouble) obj;
            int i;
            for (i = 0; i < sections.size(); i++) {
                final double num = sections.get(i);
                if ((num < 0.0) && (num > 1.0)) {
                    throw new WrongParameterGeneratorException(SECTIONS);
                }
            }
        } else {
            throw new WrongParameterGeneratorException(SECTIONS);
        }
    }

    /* (non-Javadoc)
     * @see pk.ie.proj.interfaces.ViewDataInterface#showView()
     */
    @Override
    public void showView() {
        final int v; // liczba stopni swobody
        double sections[]; // okreslone sekcje ocen
        final double[] up; // gorna polowka oceny
        final double[] down; // dolna polowka oceny

        checkParameters();
        int i;
        // Pobierz sekcjeiso-8859-2
        sections = ((VectorDouble) parameters.get(SECTIONS)).toArray();
        if (sections == null) {
            sections = new double[0];
        }
        // sortowanie
        Arrays.sort(sections);

        // Pobierz liczbe stopni swobody
        v = Convert.tryToInt(data.getDataSource().getParameter(TestChiSquare.V));
        // Ustal dystrybuante dla chiSquare
        final ChiSquare chidist = new ChiSquare();
        chidist.setParameter(ChiSquare.V, v);
        // ustalenie wartosci dla polowek badania wartosci dla danego stopnia swobody
        up = new double[sections.length];
        down = new double[sections.length];
        for (i = 0; i < sections.length; i++) {
            down[i] = chidist.getInverse(sections[i]);
            up[i] = chidist.getInverse(1 - sections[i]);
        }
        // Wypisz informacje
        Mysys.println("Widok: " + this.toString());
        Mysys.println("Test: " + data.getDataSource().toString());
        // Teraz sprawdzanie danych wzgl�dem obu po��wek
        // Powinna by� wypisywana najgorsza znaleziona
        final NumberReader reader = data.getNumberReader();
        Mysys.println("Test Wynik     Sekcja");
        int j = 0;
        W1:
        while (reader.hasNext()) {
            j++;
            final double value = reader.readDouble();
            String strFormated = String.format(" %02d  %7.4f", j, value);
            for (i = 0; i < sections.length; i++) {
                if (value < down[i]) {
                    strFormated += String.format("   %.3f (dolna)", sections[i]);
                    Mysys.println(strFormated);
                    continue W1;
                }
                if (value > up[i]) {
                    strFormated += String.format("   %.3f (górna)", sections[i]);
                    Mysys.println(strFormated);
                    continue W1;
                }
            }
            Mysys.println(strFormated + "   -------");
        }

    }

    @Override
    public void initParameters() {
        parameters.put(SECTIONS, null);

    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        return Arrays.asList(VectorDouble.class);
    }

}
