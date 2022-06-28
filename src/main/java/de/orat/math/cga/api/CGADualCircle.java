package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualCircle extends CGADualRound implements iCGATrivector {
    
    public CGADualCircle(CGAMultivector m){
        super(m);
    }
    CGADualCircle(iCGAMultivector m){
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
    public CGADualCircle(CGARoundPoint point1, CGARoundPoint point2, CGARoundPoint point3){
        this(point1.op(point2).op(point3));
    }
    
    /**
     * Create dual circle in outer product null space representation (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     */
    public CGADualCircle(Point3d point1, Point3d point2, Point3d point3){
         this((new CGARoundPoint(point1)).op((new CGARoundPoint(point2))).op((new CGARoundPoint(point3))));
    }
   
    public CGACircle undual(){
        return new CGACircle(impl.undual());
    }
}
