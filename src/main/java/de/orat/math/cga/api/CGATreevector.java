package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATreevector extends CGAMultivector {
    public CGATreevector(CGAMultivector m){
        super(m.impl);
        if (m.grade() != 3) throw new IllegalArgumentException("The given multivector m is not of grade 3!");
    }
}
