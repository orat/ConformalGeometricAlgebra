package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGABoolean extends CGAScalarOPNS {
    
    public CGABoolean(CGAMultivector m) throws IllegalArgumentException {
        super(m);
    }
    public CGABoolean(boolean value){
        super(create(value));  
    }
    private static double create(boolean value){
        double doubleValue = 1;
        if (!value){
            doubleValue = -1;
        }
        return doubleValue;
    }
}
