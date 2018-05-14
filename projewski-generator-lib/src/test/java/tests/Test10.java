package tests;

import pl.projewski.generator.exceptions.GeneratorException;
import pl.projewski.generator.tools.stream.SeparatorStreamReader;
import pl.projewski.generator.tools.stream.SeparatorStreamWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author projewski
 * <p>
 * Test na SeparatorStreamReader/Writer
 */
public class Test10 {

    public static void main(final String[] args) {
        int i = 100000;
        final SeparatorStreamReader reader;
        final SeparatorStreamWriter writer;
        try {
            writer = new SeparatorStreamWriter(new FileOutputStream("test.bin"));
            final Random random = new Random();
            while (i > 0) {
                writer.write(random.nextInt());
                i--;
            }
            writer.close();
            reader = new SeparatorStreamReader(new FileInputStream("test.bin"));
            while (reader.hasNext()) {
                reader.readInt();
            }
            System.out.println();
            reader.close();

        } catch (final GeneratorException | IOException e) {
            e.printStackTrace();
        }
    }

}
