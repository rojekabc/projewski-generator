package pl.projewski.generator.labordata;

import org.apache.commons.collections.ListUtils;
import pl.projewski.generator.abstracts.DistributionBase;
import pl.projewski.generator.abstracts.LaborDataBase;
import pl.projewski.generator.common.Fraction;
import pl.projewski.generator.common.NumberReader;
import pl.projewski.generator.common.NumberWriter;
import pl.projewski.generator.distribution.Uniform;
import pl.projewski.generator.enumeration.ClassEnumerator;
import pl.projewski.generator.exceptions.MissingDataGeneratorException;
import pl.projewski.generator.interfaces.NumberInterface;
import pl.projewski.generator.tools.Mysys;
import pl.projewski.generator.tools.NumberStoreOne;

import java.util.Arrays;
import java.util.List;

// Obliczanie wartości ChiSquare. TO jeszcze nie test !
// TODO: Refactor variable names
public class ChiSquare extends LaborDataBase {
    // maksymalna dozwolona liczba grup (lub tez maksymalna liczba v=k-1 stopni swobody)
    public static final int MAX_AMMOUNT = 100;
    public final static String DISTRIBUTION = "rozkład oczekiwany";
    //	Class _cl = null;
    int amount = 0;
    long probeAmount = 0;
    Fraction chisquare = new Fraction(0, 0);

    @Override
    public void initParameters() {
        // Dane testowane z danych wejściowych, które stanowią już
        // obliczoną tablicę częstotliowści występowania
        // lub pobieranie z generatora danych i liczenie częstości występowań.
        parameters.put(DISTRIBUTION, new Uniform());
    }

    @Override
    public List<Class<?>> getAllowedClass(final String param) {
        if (param.equals(DISTRIBUTION)) {
            return Arrays.asList(DistributionBase.class, NumberInterface.class);
        }
        return ListUtils.EMPTY_LIST;
    }

    @Override
    public boolean getOutputData(final NumberInterface data) {
        // ustaw wynik
        data.setStoreClass(ClassEnumerator.DOUBLE);
        data.setSize(1);
        try (final NumberWriter writer = data.getNumberWriter()) {
            writer.write(chisquare.getDouble());
        }
        return true;
    }

    public double getChiSquare() {
        return chisquare.getDouble();
    }

    @Override
    public void setInputData(final NumberInterface data) {
        final ClassEnumerator icl;

        probeAmount = 0;
        amount = 0;
        chisquare = new Fraction(0, 0);

        icl = data.getStoreClass();
        if ((icl != ClassEnumerator.INTEGER) && (icl != ClassEnumerator.LONG)) {
            // TODO: false. akceptuje tylko tablice int i long
            Mysys.println("Tablica innego typu niż int lub long");
            return;
        }

        // Zliczanie danych i obliczanie ogolnej sumy
        long tmp;
        try (final NumberReader reader = data.getNumberReader()) {
            while (reader.hasNext()) {
                tmp = reader.readLong();
                amount++;
                probeAmount += tmp;
            }
        }

        int i;

        final Object distribution = parameters.get(DISTRIBUTION);
        //		System.out.println("Get Output Data");
        if (distribution == null) {
            // TODO:
            System.out.println("No distribution");
            return;
        }
        if (amount < 2) {
            // TODO:
            // Nie pozwala na otrzymanie liczby stopni swobody mniejszej niz 1
            System.out.println("Group ammount to low");
            return;
        }
        if (amount > MAX_AMMOUNT) {
            // TODO:
            // Nie pozwalaj na obliczenia powyżej liczebnosci grup 100
            System.out.println("Group ammoun to high");
            return;
        }

        if (data.getSize() != amount) {
            throw new MissingDataGeneratorException(amount, data.getSize());
        }

        try (final NumberReader reader = data.getNumberReader()) {
            if (distribution instanceof DistributionBase) {
                final DistributionBase di;
                di = (DistributionBase) distribution;
                for (i = 0; i < amount; i++) {
                    final Fraction statammount = di.getPropability(new Fraction(i + 1, amount));
                    Mysys.debugln("Stat all: " + statammount.getDouble());
                    statammount.sub(di.getPropability(new Fraction(i, amount)));
                    Mysys.debugln("Stat propab: " + statammount.getDouble());
                    Mysys.debugln("Probe Ammount " + probeAmount);
                    statammount.mul(new Fraction(probeAmount, 1));
                    Mysys.debugln("After mul: " + statammount.getDouble());
                    Fraction diff = null;
                    diff = new Fraction(reader.readLong(), 1);
                    Mysys.debugln(
                            "Realammount is " + diff.getDouble() + " Statammount is " + statammount.getDouble());
                    diff.sub(statammount);
                    diff.mul(new Fraction(diff));
                    diff.div(statammount);
                    chisquare.add(diff);
                    //				chisquare += diff*diff/statammount;
                    Mysys.debugln("Now chisquare is " + chisquare.getDouble());
                }
            } else if (distribution instanceof NumberStoreOne) {
                final NumberStoreOne dist = (NumberStoreOne) distribution;
                if (dist.getStoreClass() != ClassEnumerator.INTEGER) {
                    // TODO:
                    // nieodpowiedni typ
                    System.out.println("Wrong class distribution");
                    return;
                }
                if (dist.getSize() != amount) {
                    // TODO:
                    // niezgodna licznosc grup
                    System.out.println("Wrong group ammount to distribution ammount");
                    return;
                }
                final int[] statammount = dist.getTInt();
                for (i = 0; i < amount; i++) {
                    final long realammount = reader.readLong();
                    final long diff = realammount - statammount[i];
                    chisquare.add(new Fraction(diff * diff, statammount[i]));
                }
            } else {
                // TODO:
                System.out.println("Unknown distribution class");
                return;
            }
            return;
        }

    }
}
