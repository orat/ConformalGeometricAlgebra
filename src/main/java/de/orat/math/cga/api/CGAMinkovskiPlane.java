package de.orat.math.cga.api;

/**
 * o ∧ ∞ squares to a positive term - Minkovski plane or minkovski bi-vector.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMinkovskiPlane extends CGAKVector implements iCGABivector {
    
    public CGAMinkovskiPlane(){
        super(createOrigin(1d).op(inf));
    }
}
