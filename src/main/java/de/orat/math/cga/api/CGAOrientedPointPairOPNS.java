package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * A point-pair (0-sphere) in outer product null space representation (grade 2 
 * multivector), corresponding to direct point-pair in Dorst2007.
 * 
 * This corresponds to a sphere in a line, the set of point with an equal distance
 * to the center of the point-pair.
 * 
 * Point pairs are the only rounds for which one can retrieve the points that 
 * constitutes them.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointPairOPNS extends CGAOrientedRoundOPNS implements iCGABivector {
    
    public CGAOrientedPointPairOPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedPointPairOPNS(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create point pair in outer product null space representation 
     * (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGAOrientedPointPairOPNS(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        this(point1.op(point2));
    }
    
    /**
     * Create point pair in outer product null space representation (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGAOrientedPointPairOPNS(Point3d point1, Point3d point2){
        this((new CGARoundPointIPNS(point1)).op(new CGARoundPointIPNS(point2)));
    }
    
    @Override
    public CGAOrientedPointPairIPNS undual(){
        return new CGAOrientedPointPairIPNS(impl.undual());
    }
}
