package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
  * (Dual) plane in outer product null space representation (grade 4 multivector).
 * 
 * TODO gehört der folgende Text und die impl der Methode nicht nach CGAPlane?
 * Given two null points p and q, we can construct the dual plane in between them 
 * by simple substraction: π = p − q : subtracting one normalized point from a
 * nother eliminates the o blade and returns a vector of the form π = n + δ ∞ #
 * which represents a dual plane with normal n at distance δ from the origin.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualPlane extends CGADualFlat implements iCGAQuadvector {
    
    public CGADualPlane(CGAMultivector m){
        super(m);
    }
    CGADualPlane(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create plane in outer product null space representation (grade 4 multivector).
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 second point in inner product null space representation
     * @param p3 third point in inner product null space representation
     */
    public CGADualPlane(CGARoundPoint p1, CGARoundPoint p2, CGARoundPoint p3){
        this(p1.op(p2).op(p3).op(createInf(1d)));
    }
    
    /**
     * Create a dual plane as a mid plane between two given result in outer product
     * null space representation (grade 4 multivector).
     * 
     * @param p1 point 1
     * @param p2 point 2
     */
    public CGADualPlane(CGARoundPoint p1, CGARoundPoint p2){
        this(createInf(1d).op((p1.op(p2)).dual()));
    }
    
    /**
     * Create dual plane from a point on the plane an its normal vector (in outer product
     * null space representation).
     * 
     * @param p result on the plane.
     * @param n normal vector.
     */
    public CGADualPlane(Point3d p, Vector3d n){
        this(create(p,n));
    }
    
    private static CGAMultivector create(Point3d p, Vector3d n){
        CGAMultivector cp = new CGARoundPoint(p);
        CGAMultivector cn = new CGARoundPoint(n);
        return new CGAMultivector(cp.ip(cn.op(createInf(1d))).impl);
    }
    
    @Override
    public CGAPlane undual(){
        return new CGAPlane(impl.undual());
    }
}