package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualPoint extends CGAMultivector {
    
    public CGADualPoint(CGAMultivector m){
        super(m.impl);
    }
    
    /**
     * Create a conformal result in inner product null space representation (grade 4 multivector).
     * 
     * @param sphere1 first sphere in inner product null space representation
     * @param sphere2 seconds sphere in inner product null space represenation
     * @param sphere3 third sphere in inner product null space representation
     * @param sphere4 forth sphere in inner product null space represenation
     */
    public CGADualPoint(CGAMultivector sphere1, CGAMultivector sphere2, 
                        CGAMultivector sphere3, CGAMultivector sphere4){
        this(sphere1.op(sphere2).op(sphere3).op(sphere4));
    }
    
}
