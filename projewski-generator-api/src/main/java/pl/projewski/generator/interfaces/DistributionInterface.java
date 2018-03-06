package pl.projewski.generator.interfaces;

import pl.projewski.generator.common.Fraction;

public interface DistributionInterface extends ParameterInterface {
    // wartosci wejsciowe sa z zakresu 0.0 do 1.0 i dystrybuanta powinna
    // byc tak unormowana, ze dla wartosci 0.0 przyjmuje prawdopodobienstwo
    // 0.0, a dla wartosci 1.0 1.0
    // Matlab CDF
    double getPropability(double normvalue);

    Fraction getPropability(Fraction normvalue);

    // wartosci wejsciowe sa z zakresu 0.0 do 1.0 i jest prawdopodobienstwem
    // Matlab Inverse CDF
    double getInverse(double propability);

    // wartosc znormalizowana tak, ze znajduje sie pomiedzy 0.0 a 1.0
    // Matlab PDF
    double getDensity(double normvalue);
}