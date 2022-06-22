package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAQuadvector extends CGAMultivector {
    public CGAQuadvector(CGAMultivector m){
        super(m.impl);
        if (m.grade() != 4) throw new IllegalArgumentException("The given multivector is not of grade 4!");
    }
}
