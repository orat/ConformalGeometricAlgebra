package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createEinf;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualPlane extends CGAMultivector {
    
    public CGADualPlane(CGAMultivector p){
        super(p.impl);
    }
    
    /**
     * Create plane in outer product null space representation (grade 4 multivector).
     * 
     * @param p1 first result in inner product null space representation
     * @param p2 second result in inner product null space representation
     * @param p3 third result in inner product null space representation
     */
    public CGADualPlane(CGAMultivector p1, CGAMultivector p2, CGAMultivector p3){
        this(p1.op(p2).op(p3).op(createEinf(1d)));
    }
    /**
     * Create a dual plane as a mid plane between two given result in outer product
     * null space representation (grade 4 multivector).
     * 
     * @param p1 point 1
     * @param p2 point 2
     */
    public CGADualPlane(CGAMultivector p1, CGAMultivector p2){
        this(createEinf(1d).op((p1.op(p2)).dual()));
    }
    /**
     * Create dual plane from a result on the plane an its normal vector (in outer product
     * null space representation).
     * 
     * @param p result on the plane.
     * @param n normal vector.
     */
    public CGADualPlane(Point3d p, Vector3d n){
        this(create(p,n));
    }
    
    private static CGAMultivector create(Point3d p, Vector3d n){
        CGAMultivector cp = new CGAPoint(p);
        CGAMultivector cn = new CGAPoint(n);
        return new CGAMultivector(cp.ip(cn.op(createEinf(1d))).impl);
    }
}
