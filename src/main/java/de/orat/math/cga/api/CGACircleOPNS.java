package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * Circle (1-sphere) in outer product null space representation (grade 3), corresponding to direct 
 * circle in Dorst2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircleOPNS extends CGAOrientedRoundOPNS implements iCGATrivector {
    
    public CGACircleOPNS(CGAMultivector m){
        super(m);
    }
    CGACircleOPNS(iCGAMultivector m){
        super(m);
    }
    /**
     * Create dual circle in outer product null space representation 
     * (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     */
    public CGACircleOPNS(CGARoundPointIPNS point1, CGARoundPointIPNS point2, CGARoundPointIPNS point3){
        this(point1.op(point2).op(point3));
    }
    
    /**
     * Create dual circle in outer product null space representation (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     */
    public CGACircleOPNS(Point3d point1, Point3d point2, Point3d point3){
         this((new CGARoundPointIPNS(point1)).op((new CGARoundPointIPNS(point2))).op((new CGARoundPointIPNS(point3))));
    }
   
    @Override
    public CGACircleIPNS undual(){
        return new CGACircleIPNS(impl.undual());
    }
}
