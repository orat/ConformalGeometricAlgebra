package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualCircle extends CGATreevector {
    
    public CGADualCircle(CGAMultivector m){
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
    public CGADualCircle(CGAPoint point1, CGAPoint point2, CGAPoint point3){
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
         this((new CGAPoint(point1)).op((new CGAPoint(point2))).op((new CGAPoint(point3))));
    }
}
