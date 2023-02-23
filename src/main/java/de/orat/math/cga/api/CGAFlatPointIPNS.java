package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Flat points (grade 3). 
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
    public CGAFlatPointIPNS(Point3d c){
        this(create(c, 1d));
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
        return (new CGAScalarOPNS(1d)).sub(createE3(c).op(inf)).gp(createI3()).gp(weight);
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
        return -createOrigin(1d).ip(this.op(inf)).gp(createI3()).decomposeScalar();
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
    public CGAEuclideanVector locationIntern5(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // It must be non-zero and of grade 3.
        // CGAUtil.lua l.263
        // blade = blade / weight
        // i = e1 ^ e2 ^ e3
	// local center = ( no .. blade ) * i
        CGAMultivector result = (o.ip(this.gp(1d/weight2()))).gp(createI3()).compress();
        // CGAFlatPointIPNS.location = (NaN*e1^e3 - Infinity*eo^e1^e2^e3 + NaN*e1^e2^e3^ei)
        //FIXME stimmt irgendwie gar nicht!
        System.out.println(result.toString("locationInter5 (CGAFlatPointIPNS)"));
        return new CGAEuclideanVector(result);
    }
   
    
    // etc
    
    // The attitude of a flat point contains only the component inf. oder bezieht sich 
    // dies auf die OPNS Darstellung?
    //FIXME
    public Vector3d attitude(){
        return new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
    public CGAFlatPointOPNS undual(){
        return new CGAFlatPointOPNS(super.undual().compress());
    }
}
