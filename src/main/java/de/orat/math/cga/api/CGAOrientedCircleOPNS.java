package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * Circle (1-sphere) in outer product null space representation (grade 3), corresponding to direct 
 * circle in Dorst2007.
 * 
 * e2^e3^ni, e3^e1^ni, e1^e2^ni, no^e3^ni, no^e1^ni, no^e2^ni, no^e2^e3, 
 * no^e1^e3, no^e1^e2, e1^e2^e3
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedCircleOPNS extends CGAOrientedFiniteRoundOPNS implements iCGATrivector {
    
    public CGAOrientedCircleOPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedCircleOPNS(iCGAMultivector m){
        super(m);
    }
    /**
     * Create direct circle in outer product null space representation 
     * (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     */
    public CGAOrientedCircleOPNS(CGARoundPointIPNS point1, CGARoundPointIPNS point2, CGARoundPointIPNS point3){
        this(point1.op(point2).op(point3));
    }
    
    /**
     * Create circle in outer product null space representation (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     */
    public CGAOrientedCircleOPNS(Point3d point1, Point3d point2, Point3d point3){
         this((new CGARoundPointIPNS(point1)).op((new CGARoundPointIPNS(point2))).op((new CGARoundPointIPNS(point3))));
    }
    public CGAOrientedCircleOPNS(Point3d point1, double weight1, Point3d point2, double weight2, Point3d point3, double weight3){
         this((new CGARoundPointIPNS(point1, weight1)).op((new CGARoundPointIPNS(point2, weight2))).op((new CGARoundPointIPNS(point3, weight3))));
    }
   
    @Override
    public CGAOrientedCircleIPNS undual(){
        return new CGAOrientedCircleIPNS(impl.undual());
    }
}
