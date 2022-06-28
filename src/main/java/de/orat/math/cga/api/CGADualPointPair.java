package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * Dual point pair in outer product null space (grade 2 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualPointPair extends CGADualRound implements iCGABivector {
    
    public CGADualPointPair(CGAMultivector m){
        super(m);
    }
    CGADualPointPair(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create dual point pair in outer product null space representation 
     * (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGADualPointPair(CGARoundPoint point1, CGARoundPoint point2){
        this(point1.op(point2));
    }
    
    /**
     * Create dual point pair in outer product null space representation (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGADualPointPair(Point3d point1, Point3d point2){
        this((new CGARoundPoint(point1)).op(new CGARoundPoint(point2)));
    }
    
    public CGAPointPair undual(){
        return new CGAPointPair(impl.undual());
    }
}
