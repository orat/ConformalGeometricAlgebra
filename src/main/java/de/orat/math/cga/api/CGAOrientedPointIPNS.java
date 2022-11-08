package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * 
 * FIXME
 * brauche ich das überhaupt noch?
 * 
 * Conformal geometric objects with focus on oriented points.
 * D. Hildenbrandt, P. Charrier
 * 9th international conference on clifford algebra and their applications in 
 * mathematical physics 2011.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointIPNS extends CGAKVector implements iCGATrivector {
    
    public CGAOrientedPointIPNS(CGAMultivector m){
        super(m);
    }
    protected CGAOrientedPointIPNS(iCGAMultivector impl){
        super(impl);
    }
}
