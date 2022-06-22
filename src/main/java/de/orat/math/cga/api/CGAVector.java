package de.orat.math.cga.api;

/**
 * Was ist das anschaulich?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVector extends CGAMultivector {
    public CGAVector(CGAMultivector m){
        super(m.impl);
    }
}
