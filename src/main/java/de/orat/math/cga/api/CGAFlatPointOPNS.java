package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Flat points are null vectors wedged with Infinity: p ∧ ∞. 
 * 
 * The implementation is based on a the outer product of a plane on the left side
 * and a line one the right side. This affects the sign of the weight.
 * 
 * Flat-points are blades with grade 2.
 * 
 * e1^ni, e2^ni, e3^ni, no^ni
 * 
 * Flat points are the result of an intersection between a line and a plane, and 
 * they are useful for describing potential elements within the algebra. 
 * For instance, given a dual line λ and a flat point q not on the line, their 
 * union λ ∧ q defines a dual plane π through q orthogonal to λ. Similarly, the 
 * contraction of a flat point from a direct line q'Λ defines a direct plane Π. 
 * Another example: given a dual circle (a point pair) τ and a flat point q, 
 * their union τ ∧ q defines a dual plane that goes through the axis of the 
 * circle τ and the point q. We can also construct such a relationship with the 
 * contraction product – given a direct circle κ, the contraction with a point 
 * q'κ returns a direct plane that goes through the circle κ and the point q.
 * 
 * The flat-point is the only geometric object in CGA that, when expressed in 
 * direct form, cannot be written as the outer product of (round) points.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAFlatPointOPNS extends CGAOrientedFiniteFlatIPNS implements iCGABivector {
    
    public CGAFlatPointOPNS(CGAMultivector m){
        super(m);
    }
   
    /**
     * @param c location
     * @param weight weight
     * 
     * FIXME ist hier CGARoundPointIPNS richtig?
     */
    public CGAFlatPointOPNS(Point3d c, double weight){
        this(new CGARoundPointIPNS(c, weight));
    }
    
    public CGAFlatPointOPNS(CGARoundPointIPNS p){
        this(p.op(createInf(1d)));
    }
    
    
    
    /**
     * Determines the center of this flat point.
     * 
     * @return location as euclidean point
     */
    @Override
    public Point3d location(){
        
        CGAMultivector o = CGAMultivector.createOrigin(1d);
        CGAMultivector oinf = o.op(CGAMultivector.createInf(1d));
        
        // Dorst2007 drills 14.9.2. nr. 5
        CGAMultivector result = oinf.lc(o.op(this)).div(oinf.lc(this)).negate();
        System.out.println(result.toString("location (CGAFlatPointOPNS)"));
        return result.extractE3ToPoint3d();
    }

    @Override
    public Vector3d attitude() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
