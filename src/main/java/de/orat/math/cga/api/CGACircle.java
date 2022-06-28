package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 *
 * Circle in inner product null space represenation (grade 2 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircle extends CGAFlat implements iCGABivector {
    
    public CGACircle(CGAMultivector m){
        super(m);
    }
    protected CGACircle(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create a circle by intersection of two spheres.
     * 
     * @param sphere1 first sphere
     * @param sphere2 second sphere
     */
    public CGACircle(CGASphere sphere1, CGASphere sphere2){
        this(sphere1.op(sphere2));
    }
    @Override
    public CGADualCircle dual(){
        return new CGADualCircle(impl.dual());
    }
}
