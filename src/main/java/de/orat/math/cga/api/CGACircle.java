package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircle extends CGABivector {
    
    public CGACircle(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create circle in inner product null space represenation (grade 2 multivector).
     * 
     * @param sphere1
     * @param sphere2
     */
    public CGACircle(CGASphere sphere1, CGASphere sphere2){
        this(sphere1.op(sphere2));
    }
}
