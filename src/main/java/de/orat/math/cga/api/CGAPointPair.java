package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * P-pair in inner product null space representation 
 * (grade 3 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPointPair extends CGARound implements iCGATrivector  {
    
    public CGAPointPair(CGAMultivector m){
        super(m);
    }
    CGAPointPair(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create point-pair in inner product null space representation 
     * (grade 3 multivector).
     * 
     * @param sphere1
     * @param sphere2
     * @param sphere3
     */
    public CGAPointPair(CGASphere sphere1, CGASphere sphere2, CGASphere sphere3){
        this(sphere1.op(sphere2).op(sphere3));
    }
    
    @Override
    public CGADualPointPair dual(){
        return new CGADualPointPair(impl.dual());
    }
}
