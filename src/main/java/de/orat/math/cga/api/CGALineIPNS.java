package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Lines in IPNS representation (grade 2). 
 * 
 * Lines generates the motor algebra. They can be interpreted as flattended 
 * circles passing through infinity.
 * 
 * e1^e2, e1^e3, e2^e3, e1^ni, e2^ni, e3^ni
 * 
 * There are many ways of finding a line: e.g. 
 * the central axis l of a circle σ can be found by contraction with infinity: 
 * 
 * ∞⌋σ = l.
 * 
 * TODO
 * CGALineIPNS extends CGAOrientedCircleIPNS with r=inf
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGALineIPNS extends CGAFlatIPNS implements iCGABivector {
    
    public CGALineIPNS(CGAMultivector m){
        super(m);
    }
    CGALineIPNS(iCGAMultivector m){
        super(m);
    }
    
    public CGALineIPNS(double[] values){
        super(values);
    }
    
    // composition
    
    /**
     * Create line in inner product null space representation (grade 2).
     * 
     * Be careful: This corresponds to a dual line in Dorst2007.
     * 
     * @param plane1 plane1 in inner product null space representation
     * @param plane2 plane2 in inner product null space representation
     */
    public CGALineIPNS(CGAPlaneIPNS plane1, CGAPlaneIPNS plane2){
        this(plane1.op(plane2));
    }
    
    public CGALineIPNS(CGAPointPairIPNS pointPair){
        this(pointPair.op(inf));
    }
     
    public CGALineIPNS(CGARoundPointIPNS point, CGAAttitudeVectorOPNS attitude){
        this(point.op(attitude));
    }
    
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param c location
     * @param attitude normalized attitude
     * @param weight 
     * @throws IllegalArgumentException if attitude is (0,0,0)
     */
    public CGALineIPNS(Point3d c, Vector3d attitude, double weight){
        // FIXME
        // ungeklärt, was passiert wenn c=(0,0,0)--> das sollte möglich sein
        // local blade = weight * ( normal + ( center ^ normal ) * ni ) * i
        this(createE3(attitude).add(createE3(c).op(createE3(attitude)).gp(inf)).
                gp(I3).gp(weight));
    }
    /**
     * Create a a line in IPNS representation with weight 1.
     * 
     * @param c position on the line
     * @param attitude direction of the line (normalization is not needed)
     */
    public CGALineIPNS(Point3d c, Vector3d attitude){
        this(c, normalize(attitude), 1d);
    }
    private static Vector3d normalize(Vector3d attitude){
        Vector3d result = attitude;
        attitude.normalize();
        return attitude;
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
    
    
    // etc
    
    @Override
    public CGALineOPNS undual(){
        return new CGALineOPNS(super.undual().compress());//impl.dual().gp(-1));
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
    
    
    // decomposition
    
    /**
     * Determine the weight (without the sign) without a probe point and without
     * determination of the attitude.
     * 
     * @return weight (without sign) > 0
     * 
     * FIXME für CGALineIPNS = (-2*e1^e2 - 2*e1^e3 - 2*e1^ei + 2*e2^ei + 2*e3^ei)
     * ergibt sich hier 0, warum?
     */
    private CGAScalarOPNS weightIntern2(){
        // following Parkin2013 CGAUtilMath.pdf, lua implementation
        // local weight = ( #( ( no .. ( blade ^ ni ) ) * i ) ):tonumber()
        return new CGAScalarOPNS((createOrigin(1d).ip(this.op(inf))).
                gp(createI3()).norm());
    }
    
    /**
     * Determine a point on the line which has the closest distance to the origin.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     */
    /*@Override
    public Point3d location(){
        // local center = ( no .. blade ) * normal * i
        return new Point3d((o.ip(this.div(weightIntern2())).
                gp(attitudeIntern())).gp(createI3()).extractE3ToPoint3d());
    }*/
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(super.attitudeIntern());
    }
    
    /**
     * Determine attitude.
     * 
     * TODO
     * Vorzeichen möglicherweise falsch, 
     * 
     * @Deprecated
     * @return normalized attitude as (E3) 1-vector
     */
    public CGAEuclideanVector attitudeIntern2(){
        // implementation following Spencer, nach Spencer wird durch abs(weight) dividiert
        // damit wird Ergebnis normalisiert aber es geht auch das Vorzeichen verloren.
        //TODO
        // besser nicht durch abs(weight) dividieren
        CGAEuclideanVector result = new CGAEuclideanVector(o.ip(this/*.div(weightIntern2())*/.
                op(inf)).gp(CGAMultivector.createI3()));
        System.out.println(result.toString("attitueIntern2 (CGALineIPNS, Spencer)"));
        return result;
        //return result.direction();
    }
    
    public CGALineIPNS normalize(){
        return new CGALineIPNS(super.normalize().compress());
    }
}
