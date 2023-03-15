package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * A round point in outer product null space representation (grade 4 multivector), 
 * corresponding to direct round point in [Dorst2007].
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARoundPointOPNS extends CGARoundOPNS implements iCGAQuadvector {
    
    public CGARoundPointOPNS(CGAMultivector m){
        super(m.compress());
    }
    
    CGARoundPointOPNS(iCGAMultivector m){
        super(m);
    }
    
    
    // composition
    
    /**
     * Create a conformal round point in outer product null space representation 
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
    
    
    // etc
    
    @Override
    public CGARoundPointIPNS dual(){
        return new CGARoundPointIPNS(impl.dual());
    }
}
