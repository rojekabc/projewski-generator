/**
 *
 */
package tests;

import pl.projewski.generator.exceptions.ParameterException;
import pl.projewski.generator.generator.*;
import pl.projewski.generator.interfaces.GeneratorInterface;

import java.util.Set;

/**
 * @author maq
 * <p>
 * Sprawdzenie funckjonowania opisow do elementow
 */
public class Test6 {

    public static void opisGeneratora(final GeneratorInterface generator) {
        try {
            final Set<String> params = generator.listParameters();
            int i;
            String desc;

            desc = generator.getDescription();
            if (desc == null) {
                desc = "";
            }
            System.out.println("Generator " + generator.getClass().getSimpleName() + " : " + desc);
            for (final String param : params) {
                desc = generator.getParameterDescription(param);
                System.out.print("Parametr [" + param + "]: ");
                if (desc != null) {
                    System.out.println(desc);
                } else {
                    System.out.println();
                }
            }
        } catch (final ParameterException e) {
            e.printStackTrace();
        }
        System.out.println("");
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        opisGeneratora(new GeneratorGausHastings());
        opisGeneratora(new GeneratorJavaRandom());
        opisGeneratora(new GeneratorLCG());
        opisGeneratora(new GeneratorSimConst());
        opisGeneratora(new GeneratorSystemTime());
    }

}
