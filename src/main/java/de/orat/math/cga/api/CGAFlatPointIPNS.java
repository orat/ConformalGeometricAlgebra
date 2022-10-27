package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import org.jogamp.vecmath.Point3d;

/**
 * Flat points are null vectors wedged with Infinity: p ∧ ∞. 
 * 
 * Flat points are blades with grade 2. 
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
 * A flat point
 * 
 * The flat-point is the only geometric object in CGA that, when expressed in 
 * direct form, cannot be written as the outer product of (round) points.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAFlatPointIPNS extends CGAOrientedFiniteFlatIPNS implements iCGABivector {
    
    public CGAFlatPointIPNS(CGAMultivector m){
        super(m);
    }
   
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * FIXME 
     * ist das wirklich IPNS?
     * 
     * @param c location
     * @param weight weight
     */
    public CGAFlatPointIPNS(Point3d c, double weight){
        // local blade = weight * ( 1 - center ^ ni ) * i
        this((new CGAScalar(1d)).sub(createE3(c).op(createInf(1d))).gp(createPseudoscalar()).gp(weight));
    }
    
    public CGAFlatPointIPNS(CGARoundPointIPNS p){
        this(p.op(createInf(1d)));
    }
    
    /**
     * Determination the weight from this flat point.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     */
    private double weight(){
        // local weight = -( no .. ( blade ^ ni ) ) * i
        return createOrigin(1d).ip(this.op(createInf(1d))).gp(createE3Pseudoscalar()).scalarPart();
    }
    /**
     * Determination the squared weight from this flat point.Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return squared weight
     */
    @Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }
    /**
     * Determines the center of this flat point.
     * 
     * Determine a point on the line which has the closest distance to the origin.Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return location
     */
    @Override
    public Point3d location(){
        // blade = blade / weight
	// local center = ( no .. blade ) * i
        CGAMultivector result = (createOrigin(1d).ip(this.gp(1d/weight()))).gp(createE3Pseudoscalar());
        return result.extractE3ToPoint3d();
    }
}
