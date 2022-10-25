package de.orat.math.cga.api;

/**
 * o ∧ ∞ squares to a positive term.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMinkovskiPlane extends CGAkBlade implements iCGABivector {
    
    public CGAMinkovskiPlane(){
        super(createOrigin(1d).op(createInf(1d)));
    }
}
