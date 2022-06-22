package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircle extends CGAMultivector {
    
    /**
     * Create circle in inner product null space represenation (grade 2 multivector).
     * 
     * @param sphere1
     * @param sphere2
     */
    public CGACircle(CGAMultivector sphere1, CGAMultivector sphere2){
        super(sphere1.op(sphere2).impl);
    }
}
