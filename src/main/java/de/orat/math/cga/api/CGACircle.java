package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;

/**
 *
 * Circle in inner product null space represenation (grade 2 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircle extends CGARound implements iCGABivector {
    
    public CGACircle(CGAMultivector m){
        super(m);
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
}
