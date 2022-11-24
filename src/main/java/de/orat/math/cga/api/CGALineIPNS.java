package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Generates the motor algebra. 
 * 
 * e1^e2, e1^e3, e2^e3, e1^ni, e2^ni, e3^ni
 * 
 * There are many ways of finding a line: e.g. 
 * the central axis l of a circle σ can be found by contraction with infinity: 
 * 
 * ∞⌋σ = l.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGALineIPNS extends CGAOrientedFiniteFlatIPNS implements iCGABivector {
    
    public CGALineIPNS(CGAMultivector m){
        super(m);
    }
    CGALineIPNS(iCGAMultivector m){
        super(m);
    }
    /**
     * Create line in inner product null space representation (grade 2 multivector).
     * 
     * Be careful: This corresponds to a dual line in Dorst2007.
     * 
     * @param plane1 plane1 in inner product null space representation
     * @param plane2 plane2 in inner product null space representation
     */
    public CGALineIPNS(CGAPlaneIPNS plane1, CGAPlaneIPNS plane2){
        this(plane1.op(plane2));
    }
    
    public CGALineIPNS(CGAOrientedPointPairIPNS pointPair){
        this(pointPair.op(createInf(1d)));
    }
     
    public CGALineIPNS(CGARoundPointIPNS point, CGAAttitudeVectorOPNS direction){
        this(point.op(direction));
    }
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param c location
     * @param direction normalized direction
     * @param weight 
     * @throws IllegalArgumentException if direction is (0,0,0)
     */
    public CGALineIPNS(Point3d c, Vector3d direction, double weight){
        // FIXME
        // ungeklärt, was passiert wenn c=(0,0,0)--> das sollte möglich sein
        // local blade = weight * ( normal + ( center ^ normal ) * ni ) * i
        this(createE3(direction).add(createE3(c).op(createE3(direction)).gp(createInf(1d))).
                gp(createE3Pseudoscalar()).gp(weight));
    }
    
    public CGALineIPNS(CGABivector B, double d){
        this(B.add(createInf(d)));
    }
    
    /**
     * 
     * @param a together with b to create a bivector: a^b
     * @param b together with a to create a bivector: a^b
     * @param d 
     */
    public CGALineIPNS(Vector3d a, Vector3d b, double d){
        this((CGABivector) createE3(a).op(createE3(b)), d);
    }
    
    
    @Override
    public CGALineOPNS undual(){
        return new CGALineOPNS(impl.undual());
    }
    
    /**
     * Is this 3-vector representing a line.
     * 
     * Lines Classification in the Conformal Space R{n+1,1}
     * Lilian Aveneau and Ángel F. Tenorio
     * 
     * @return true if this object represents a line.
     */
    /*public boolean isLine(){
        //TODO
        // 1. is 3-blade ist ja bereits klar
        // 2. op(einf) = 0;
        // 3. Richtung ungleich 0, d.h. einf leftcontraction this != 0
        return true;
    }*/
    
    /**
     * Determine the weight (without the sign) without a probe point and without
     * determination of the attitude.
     * 
     * @return weight (without sign) > 0
     * 
     * FIXME für CGALineIPNS = (-2*e1^e2 - 2*e1^e3 - 2*e1^ei + 2*e2^ei + 2*e3^ei)
     * ergibt sich hier 0, warum?
     */
    private double weight2(){
        // following Parkin2013 CGAUtilMath lua implementation
        // local weight = ( #( ( no .. ( blade ^ ni ) ) * i ) ):tonumber()
        return (createOrigin(1d).ip(this.op(createInf(1d)))).
                gp(createE3Pseudoscalar()).norm();
    }
    /*@Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }*/
    
    /**
     * @return attitude
     */
    /*@Override
    protected CGAAttitudeVectorOPNS attitudeIntern(){
        // Infinity da weight == 0 und die Komponenten damit durch 0 geteilt werden
        // attitudeIntern = (Infinity*e2 - Infinity*eo^e1^e2 - Infinity*e3 + Infinity*eo^e1^e3 + Infinity*eo^e2^e3 + NaN*e1^e2^ei + NaN*e1^e3^ei + NaN*e2^e3^ei)
        //The given multivector is not of grade 2: Infinity*e2 - Infinity*eo^e1^e2 - Infinity*e3 + Infinity*eo^e1^e3 + Infinity*eo^e2^e3 + NaN*e1^e2^ei + NaN*e1^e3^ei + NaN*e2^e3^ei
        //FIXME
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight
	// local normal = ( no .. ( blade ^ ni ) ) * i
        CGAMultivector result =  
                createOrigin(1d).ip(this.gp(1d/weight()).op(createInf(1d))).gp(createE3Pseudoscalar()).compress();
        System.out.println(result.toString("attitudeIntern"));
        return new CGAAttitudeVectorOPNS(result);
    }*/
    
    /**
     * Determine a point on the line which has the closest distance to the origin.
     * 
      * 
     * TODO
     * möglicherweise geht das für alle flats?
     */
    @Override
    public Point3d location(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = ( no .. blade ) * normal * i
        return new Point3d((createOrigin(1d).ip(this.gp(1d/weight2())).
                gp(attitudeIntern())).gp(createE3Pseudoscalar()).extractE3ToPoint3d());
    }
    
    // unklar, ob das hier so gut aufgehoben ist, vermutlich klappt nämlich extract
    // nicht bei Ebenen und Kugeln da ich dann vermutlich einen Bivector bekomme
    //  nach Abspaltung von einf
    // dann kann ich den spezifischen extract-Methode in die spezifischen Attitude classen verschieben?
    // siehe OPNS impl
    //TODO
    @Override
    public Vector3d attitude(){
        return (new CGAAttitudeVectorOPNS(attitudeIntern())).direction(); // aus OPNS impl
        ///CGAAttitude result = attitudeIntern();
        //System.out.println("attitude_cga="+result.toString());
        //return result.extractAttitudeFromEeinfRepresentation();
    }
    
    public Vector3d attitudeIntern2(){
        // implementation following Spencer
        CGAE3Vector result = CGAMultivector.createOrigin(1d).ip(this.div(weight2()).
                op(CGAMultivector.createInf(1d))).gp(CGAMultivector.createE3Pseudoscalar());
        System.out.println(result.toString("attitueIntern2 (CGALineIPNS)"));
        return result.direction();
    }
    
}
