package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;

/**
 * Round point in outer product null space representation (grade 4 multivector), 
 * corresponding to direct round point in Dorst2007.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARoundPointOPNS extends CGASphereOPNS {
    
    public CGARoundPointOPNS(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create a conformal result in outer product null space representation 
     * (grade 4 multivector).
     * 
     * @param sphere1 first sphere in inner product null space representation
     * @param sphere2 seconds sphere in inner product null space represenation
     * @param sphere3 third sphere in inner product null space representation
     * @param sphere4 forth sphere in inner product null space represenation
     */
    public CGARoundPointOPNS(CGASphereIPNS sphere1, CGASphereIPNS sphere2, 
                        CGASphereIPNS sphere3, CGASphereIPNS sphere4){
        this(sphere1.op(sphere2).op(sphere3).op(sphere4));
    }
    public CGARoundPointOPNS(Point3d p){
        this((new CGARoundPointIPNS(p)).dual());
    }
    public CGARoundPointOPNS(Point3d p, double weight){
        this((new CGARoundPointIPNS(p, weight)).dual());
    }
}
