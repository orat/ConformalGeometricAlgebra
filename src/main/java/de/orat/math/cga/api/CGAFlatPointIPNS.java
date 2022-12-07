package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Flat points are null vectors wedged with Infinity: p ∧ ∞. 
 * 
 * FIXME
 * Es sieht so aus, dass die gesamte Beschreibung für flat-Points in opns Darstellung
 * gilt und der Konstruktor aber ipns erzeugt. Das würde beideuten, dass es bei float
 * points wirklich eine duale representation gibt.
 * Der Konstruktor liefert grade-3 vektoren statt der zu erwartenden grade 2
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
public class CGAFlatPointIPNS extends CGAOrientedFiniteFlatIPNS implements iCGATrivector {
    
    public CGAFlatPointIPNS(CGAMultivector m){
        super(m);
    }
   
    
    // composition
    
    /**
     * @param c location
     * @param weight weight
     */
    public CGAFlatPointIPNS(Point3d c, double weight){
        this(create(c, weight));
    }
    
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * CGAUtil.lua l.153
     *
     * @param c position of the flat point
     * @param weight weight
     * @return flat point
     */
    private static CGAMultivector create(Point3d c, double weight){
        // local blade = weight * ( 1 - center ^ ni ) * i
        return (new CGAScalar(1d)).sub(createE3(c).op(inf)).gp(createI3()).gp(weight);
    }
    
    
    // decomposition
    
    /**
     * Determination the weight from this flat point without the usage of a probe
     * point and without determination of the attitude.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * CGAUtil.lua l.256
     */
    private double weight2(){
        // local weight = -( no .. ( blade ^ ni ) ) * i
        return -createOrigin(1d).ip(this.op(inf)).gp(createI3()).scalarPart();
    }
    
    /**
     * Determines the center of this flat point.
     * 
     * @return location as euclidean point
     */
    @Override
    public Point3d location(){
        // Determine a point on the line which has the closest distance to the origin.
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // It must be non-zero and of grade 3.
        // CGAUtil.lua l.263
        // blade = blade / weight
        // i = e1 ^ e2 ^ e3
	// local center = ( no .. blade ) * i
        // flat point = (-5.5511151231257765E-17*eo^e2 + 0.999999999999999*eo^ei + 0.9999999999999989*e2^ei)
        //CGAMultivector result = (createOrigin(1d).ip(this.gp(1d/weight()))).gp(createI3());
        // CGAFlatPointIPNS.location = (NaN*e1^e3 - Infinity*eo^e1^e2^e3 + NaN*e1^e2^e3^ei)
        //FIXME stimmt irgendwie gar nicht!
        //System.out.println(result.toString("CGAFlatPointIPNS.location"));
        //return result.extractE3ToPoint3d();
        
        // Dorst2007 p. 428 or Dorst Drills p. 45
        // funktioniert gut!!!
        CGAMultivector o = CGAMultivector.createOrigin(1d);
        CGAMultivector oinf = o.op(inf);
        CGAMultivector result = oinf.lc(o.op(this)).div(oinf.lc(this)).negate();
        //System.out.println(result.toString("CGAFlatPointIPNS.location2"));
        return result.extractE3ToPoint3d();
    }

    /**
     * Determines the center of this flat point.
     * 
     * @return location as euclidean point
     */
    public CGAE3Vector locationIntern5(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // It must be non-zero and of grade 3.
        // CGAUtil.lua l.263
        // blade = blade / weight
        // i = e1 ^ e2 ^ e3
	// local center = ( no .. blade ) * i
        // flat point = (-5.5511151231257765E-17*eo^e2 + 0.999999999999999*eo^ei + 0.9999999999999989*e2^ei)
        CGAMultivector result = (createOrigin(1d).ip(this.gp(1d/weight2()))).gp(createI3()).compress();
        // CGAFlatPointIPNS.location = (NaN*e1^e3 - Infinity*eo^e1^e2^e3 + NaN*e1^e2^e3^ei)
        //FIXME stimmt irgendwie gar nicht!
        System.out.println(result.toString("locationInter5 (CGAFlatPointIPNS)"));
        return new CGAE3Vector(result);
    }
   
    @Override
    public CGAAttitudeScalarOPNS attitudeIntern(){
        return new CGAAttitudeScalarOPNS(super.attitudeIntern());
    }
    
    
    // etc
}
