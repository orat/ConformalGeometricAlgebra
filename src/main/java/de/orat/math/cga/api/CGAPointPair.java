package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPointPair extends CGAMultivector  {
    
    public CGAPointPair(CGAMultivector m){
        super(m.impl);
    }
     /**
     * Create result pair in inner product null space representation (grade 3 multivector).
     * 
     * @param sphere1
     * @param sphere2
     * @param sphere3
     */
    public CGAPointPair(CGAMultivector sphere1, CGAMultivector sphere2, CGAMultivector sphere3){
        this(sphere1.op(sphere2).op(sphere3));
    }
}
