package de.orat.math.cga.test;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class TestUtils {
    
    public TestUtils() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        double prefactor = 2.0;
        String[] baseVectors = new String[]{"1", "2", "3"};
        System.out.println(prefactor+Arrays.toString(baseVectors));
    }
}
