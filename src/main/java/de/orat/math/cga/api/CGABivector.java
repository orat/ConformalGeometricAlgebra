package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGABivector extends CGAMultivector {
    
    public CGABivector(CGAMultivector m){
        super(m.impl);
        if (m.grade() != 2) throw new IllegalArgumentException("The given multivector is not of grade 2!");
    }
}
