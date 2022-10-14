package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * Point pair (0-sphere) in outer product null space representation (grade 2 
 * multivector), corresponding to direct point-pair in Dorst2007.
 * 
 * Point pairs are the only rounds for which one can retrieve the points that 
 * constituted them.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPointPairOPNS extends CGAOrientedRoundOPNS implements iCGABivector {
    
    public CGAPointPairOPNS(CGAMultivector m){
        super(m);
    }
    CGAPointPairOPNS(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create point pair in outer product null space representation 
     * (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGAPointPairOPNS(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        this(point1.op(point2));
    }
    
    /**
     * Create point pair in outer product null space representation (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGAPointPairOPNS(Point3d point1, Point3d point2){
        this((new CGARoundPointIPNS(point1)).op(new CGARoundPointIPNS(point2)));
    }
    
    @Override
    public CGAPointPairIPNS undual(){
        return new CGAPointPairIPNS(impl.undual());
    }
}