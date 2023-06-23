package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createEx;
import static de.orat.math.cga.api.CGAMultivector.createEy;
import static de.orat.math.cga.api.CGAMultivector.createEz;
import org.jogamp.vecmath.Vector3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * Planes formed between the Euclidean and Null basis, v ∧ o and v ∧ ∞, which 
 * square to 0. 
 * 
 * Planes are grade 1. Corresponding to dual plane in [Dorst2007].<p>
 * 
 * Planes are flattended spheres.<p>
 * 
 * Given two null points p and q, we can construct the dual plane in between them 
 * by simple substraction: π = p − q : subtracting one normalized point from a
 * nother eliminates the o blade and returns a vector of the form π = n + δ ∞
 * which represents a dual plane with normal n at distance δ from the origin.<p>
 *
 * Planes π = n + δ ∞ are combination of a Euclidean normal vector n plus a
 * weighted infinity ∞ representing the distance from Origin (sometimes called 
 * the Hessian distance). <p>
 *
 * These other kinds of planes enable different kinds of transformations – 
 * namely timelike and lightlike depending upon whether they square to a 
 * positive term, or to zero, respectively.
 * Because of the generality in speaking about spacelike, timelike, and 
 * lightlike planes, and the fact that these are two-dimensional planes within a 
 * higher dimension, we call all of these planes hyperplanes. Since they are a 
 * vector space they can be added together continuously. Also, composites planes 
 * are possible – for instance a ∧ b + v ∧ ∞, which is a combination of a 
 * rotation plane and translation plane, which creates an interpolatable dual 
 * line twist axis.<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPlaneIPNS extends CGAFlatIPNS implements iCGAVector {
    
    public CGAPlaneIPNS(CGAMultivector m){
        super(m.compress());
    }
    CGAPlaneIPNS(iCGAMultivector m){
        super(m);
    }
    public CGAPlaneIPNS(double[] values){
        super(values);
    }
    
    // composition 
    
    /**
     * Create plane in inner product null space representation (grade 1)
     * based on the parameters of the Hesse-Normal-Form Be careful: 
     * This corresponds to dual plane in Dorst2007.
     * 
     * TODO
     * - Ein nicht normalisiertes n liefert anderen Multivektor, aber könnte der
     * nicht die gleiche Ebene representieren?
     * 
     * @param n (normalized) normal vector of the plane
     * @param d distance of the plane to the origin
     * @param weight weight
     */
    public CGAPlaneIPNS(Vector3d n, double d, double weight){
        this(createEx(n.x)
            .add(createEy(n.y))
            .add(createEz(n.z))
            .add(createInf(d)).gp(weight));
    }
    /**
     * Composition of a plane in ipns representation based on its normal vector and 
     * its distance to the origin.
     * 
     * @param n (normalized) normal vector of the plane
     * @param d distance of the plane to the origin
     */
    public CGAPlaneIPNS(Vector3d n, double d){
        this(n,d,1d);
    }
    /**
     * 
     * @param n normalized vector of the plane
     * @param p point laying in the plane
     */
    public CGAPlaneIPNS(Point3d p, Vector3d n){
        this(n,(new Vector3d(p)).dot(n));
    }
   
    /**
     * Composition of a plane based on a point, a normal vector and the weight. 
     * 
     * Notice: The sign of the weight is lost in the decomposition and therefor
     * can not be recovered in decomposition.
     * 
     * looks identical to 
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param P point in the plane
     * @param n normal vector/attitude
     * @param weight weight
     */
    public CGAPlaneIPNS(Point3d P, Vector3d n, double weight){
        // FIXME Dorst2007 p.376 sub() statt add()?
        this((createEx(n.x)
            .add(createEy(n.y))
            .add(createEz(n.z))
            .add(createInf(P.x*n.x+P.y*n.y+P.z*n.z))).gp(weight));
    }
    
    /**
     * Definition of a plane by only a single point laying in the plane. The normal
     * vector of the plane is defined implicit by the direction from the origin to the
     * given point P.
     * 
     * ! 5
     * * 2
     * ^ 3
     * 
     * DualPlane (grade 3) = !(d(P2,no)*ni + (P2^nino*nino).normalized())
     * Hier also ohne den dual operator !
     * P2 ist grade 1
     * 
     * Diese Implementierung ist umständlich, da sie den Point3d erst in einen
     * CGAPoint up-projiziert und dann intern das wieder rückgängig macht etc.
     * 
     * @param P point laying in the plane and defining the normal vector.
     */
    public CGAPlaneIPNS(Point3d P){
        this(new CGARoundPointIPNS(P));
    }
    /**
     * Definition of a plane by only a single point laying in the plane. The normal
     * vector of the plane is defined implicit by the direction from the origin to the
     * given point P.
     * 
     * @param P 
     * 
     * TODO normalize() hat uneindeutiges Vorzeichen, welches brauche ich hier und
     * wie beschaffe ich mir das richtige? Was liefert ganja.js
     * 
     * DualPlane (grade 3) = !(d(P2,no)*ni + (P2^nino*nino).normalized())
     * Hier also ohne den dual operator !
     * P2 ist grade 1
     * 
     * Diese Implementierung ist umständlich, da sie den Point3d erst in einen
     * CGAPoint up-projiziert und dann intern das wieder rückgängig macht etc.
   
     */
    public CGAPlaneIPNS(CGARoundPointIPNS P){
        this(createInf(dist2Origin(P)).add(P.op(I0.negate()).gp(I0.negate()).normalize()));
    }
    
    
    // etc
    
    private static double dist2Origin(CGARoundPointIPNS P){
        return Math.sqrt((new CGARoundPointIPNS(P)).distSquare(new CGARoundPointIPNS(createOrigin(1d))));
    }
    
    // use CGAMultivector.I0 instead
    /*@Deprecated
    private static CGAMultivector createNino(){
        return inf.op(createOrigin(1d));
    }*/
    
    @Override
    public CGAPlaneOPNS undual(){
        //return new CGAPlaneOPNS((new CGAMultivector(impl.dual().gp(-1))).compress());
        return new CGAPlaneOPNS(super.undual().compress());
    }
    
    
    // decomposition 
    
    /**
     * Determine the attitude.
     * 
     * TODO
     * diese Implementierung following Dorst sollte doch auch für line und flat point 
     * gelten, oder?
     * 
     * @return attitude as attitude-bivector in opns representation
     */
    @Override
    public CGAAttitudeBivectorOPNS attitudeIntern(){
        return new CGAAttitudeBivectorOPNS(super.attitudeIntern());
    }  
    
    /**
     * WORKAROUND da super.attitudeIntern() nicht richtig funktioniert für
     * plane obwohl das für line funktioniert
     * 
     * @return 
     */
    public Vector3d attitude(){
        return attitudeIntern2().direction();
    }
    
    /**
     * Determination of the attitude.
     * 
     * @Deprecated
     * @return attitude as (E3) 1-vector
     */
    public CGAEuclideanVector attitudeIntern2(){
        // implementation following
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight
	// local normal = no .. ( blade ^ ni )
        double weight = weight2Intern();
        CGAMultivector result = o.ip(this.gp(1d/weight).op(inf)).compress();
        System.out.println(result.toString("attitudeIntern2(CGAPlaneIPNS, Spencer)"));
        if (weight<=0){
            System.out.println("attitudeIntern2(CGAPlaneIPNS, Spencer) failed because weight="+String.valueOf(weight));
        }
        return new CGAEuclideanVector(result);
    }
    
    /**
     * Determine weight without a probe point and without determination of the
     * attitude.
     * 
     * The sign in lost in composition of the plane and unreoverable.
     * 
     * @return weight wihout sign (always positive, the sign is lost)
     */
    public double weight2Intern(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local weight = ( #( no .. ( blade ^ ni ) ) ):tonumber()
        return createOrigin(1d).ip(this.op(inf)).norm();
    }
    
    /**
     * Determine location.
     * 
     * @return location
     */
    public CGAEuclideanVector locationIntern2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = -( no .. blade ) * normal
        CGAMultivector result = o.ip(this.gp(1d/weight2Intern())).gp(attitudeIntern2()).negate();
        System.out.println(result.toString("locationIntern2 (CGAPlaneIPNS)"));
        //return result.extractE3ToPoint3d();
        return new CGAEuclideanVector(result);
    }
    
    public CGAPlaneIPNS normalize(){
        return new CGAPlaneIPNS(super.normalize().compress());
    }
}
