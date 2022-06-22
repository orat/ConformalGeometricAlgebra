package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVector extends CGAMultivector {
    
    public CGAVector(CGAMultivector m){
        super(m.impl);
        if (m.grade() != 1) throw new IllegalArgumentException("The given multivector is not not grade 1!");
    }
}
