package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Forque, Wrench, Screw-force.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAForqueIPNS extends CGAMultivector {
    
    public CGAForqueIPNS(double[] values) {
        super(values);
    }
    public CGAForqueIPNS(CGAMultivector m){
        super(m.impl);
    }
    CGAForqueIPNS(iCGAMultivector m){
        super(m);
    }
}
