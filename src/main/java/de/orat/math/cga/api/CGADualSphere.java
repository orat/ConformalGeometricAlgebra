package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createEinf;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;

/**
 * Dual sphere (inner product null space representation) as a multivector of grade 4.
 * 
 * corresponds to Quadvector
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualSphere extends CGAQuadvector {
    
    public CGADualSphere(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create dual sphere.
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * @param o origin of the sphere
     * @param p result on the sphere
     */
    public CGADualSphere(Point3d o, Point3d p){
        this((new CGAPoint(p)).ip(createEinf(1d).op((new CGAPoint(o)))));
    }
    
    /**
     * Create dual sphere in outer product null space representation (grade 4 multivector).
     * 
     * @param p1 multivector representing a point on the sphere
     * @param p2 multivector representing a point on the sphere
     * @param p3 multivector representing a point on the sphere
     * @param p4 multivector representing a point on the sphere
     */
    public CGADualSphere(CGAPoint p1, CGAPoint p2, 
                         CGAPoint p3, CGAPoint p4){
        this(p1.op(p2).op(p3).op(p4));
    }
    
    /**
     * Create dual sphere in outer product null space represenation (grade 4 multivector).
     * 
     * @param p1 a point on the sphere
     * @param p2 a point on the sphere
     * @param p3 a point on the sphere
     * @param p4 a point on the sphere
     */
    public CGADualSphere(Point3d p1, Point3d p2, Point3d p3, Point3d p4){
        this((new CGAPoint(p1)).op((new CGAPoint(p2))).op((new CGAPoint(p3))).op((new CGAPoint(p4))));
    }
    
    public RoundAndTangentParameters decompose(){
        return decomposeDualRound();
    }
}
