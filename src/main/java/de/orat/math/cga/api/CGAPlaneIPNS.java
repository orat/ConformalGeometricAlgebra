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
 * square to 0. Planes are grade 1. Corresponding to dual plane in Dorst2007.
 * 
 * Given two null points p and q, we can construct the dual plane in between them 
 * by simple substraction: π = p − q : subtracting one normalized point from a
 * nother eliminates the o blade and returns a vector of the form π = n + δ ∞ #
 * which represents a dual plane with normal n at distance δ from the origin.
 *
 * Planes π = n + δ ∞ are combination of a Euclidean normal vector n plus a
 * weighted infinity ∞ representing the distance from Origin (sometimes called 
 * the Hessian distance). 
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
 * line twist axis.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPlaneIPNS extends CGAOrientedFiniteFlatIPNS implements iCGAVector {
    
    public CGAPlaneIPNS(CGAMultivector m){
        super(m);
    }
    CGAPlaneIPNS(iCGAMultivector m){
        super(m);
    }
    
    
    // composition 
    
    /**
     * Create plane in inner product null space representation (grade 1 multivector)
     * based on the parameters of the Hesse-Normal-Form of a plane.
     * 
     * Be careful: This corresponds to dual plane in Dorst2007.
     * 
     * Successful tested!!!
     * 
     * TODO
     * - Ein nicht normalisiertes n liefert anderen Multivektor, aber könnte der
     * nicht die gleiche Ebene representieren?
     * - weight hinzufügen
     * 
     * @param n (normalized) normal vector of the plane
     * @param d distance of the plane to the origin
     */
    public CGAPlaneIPNS(Vector3d n, double d){
        this(createEx(n.x)
            .add(createEy(n.y))
            .add(createEz(n.z))
            .add(createInf(d)));
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
        // Dorst2007 p.376 sub() statt add()
        this((createEx(n.x)
            .sub(createEy(n.y))
            .sub(createEz(n.z))
            .sub(createInf(P.x*n.x+P.y*n.y+P.z*n.z))).gp(weight));
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
     */
    public CGAPlaneIPNS(CGARoundPointIPNS P){
        this(createInf(dist2Origin(P)).add(P.op(createNino()).gp(createNino()).normalize()));
    }
    
    
    private static double dist2Origin(CGARoundPointIPNS P){
        return Math.sqrt((new CGARoundPointIPNS(P)).distSquare(new CGARoundPointIPNS(createOrigin(1d))));
    }
    private static CGAMultivector createNino(){
        return createInf(1d).op(createOrigin(1d));
    }
    
    
    // decomposition 
    
    /**
     * Determine the attitude.
     * 
     * @return attitude
     */
    @Override
    public CGAAttitudeBivectorOPNS attitudeIntern(){
        // Sign of all coordinates change according to errato to the book Dorst2007
        // mir scheint hier wird von weight==1 ausgegangen. Das Vorzeichen könnte
        // vermutlich verschwinden, wenn ich die beiden Operanden vertausche
        CGAMultivector result = createInf(1d).op(this).undual().negate().compress();
        System.out.println(result.toString("attitudeIntern (CGAPlaneIPNS)"));
        // IPNS plane = (1.0*e3 + 2.0*ei)
        // attitudeIntern(CGAOrientedFiniteFlatIPNS) = - 0.9999999999999996*e1^e2^ei
        // die bestimmte attitude ist hier grade 3
        // jetzt bekomme ich
        // attitude_cga=-0.9999999999999996*e1^e2^ei
        return new CGAAttitudeBivectorOPNS(result);
    }  
    @Override
    public Vector3d attitude(){
        Vector3d result = (new CGAAttitudeBivectorOPNS(attitudeIntern())).direction();
        result.normalize();
        return result;
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
        return createOrigin(1d).ip(this.op(createInf(1d))).norm();
    }
    
    /**
     * Determination of the attitude.
     * 
     * @return attitude/normal/direction
     */
    public CGAE3Vector attitudeIntern2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight
	// local normal = no .. ( blade ^ ni )
        double weight = weight2Intern();
        CGAMultivector result = createOrigin(1d).ip(this.gp(1d/weight).op(createInf(1d))).compress();
        // attitudeIntern(CGAPlaneIPNS) = (1.0*e3)
        System.out.println(result.toString("attitudeIntern(CGAPlaneIPNS)"));
        if (weight<=0){
            System.out.println("attitudeIntern(CGAPlaneIPNS) failed because weight="+String.valueOf(weight));
        }
        return new CGAE3Vector(result);
    }
    
     
    /**
     * Determine location.
     * 
     * @return location
     */
    public CGAE3Vector locationIntern2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = -( no .. blade ) * normal
        CGAMultivector result = createOrigin(1d).ip(this.gp(1d/weight2Intern())).gp(attitudeIntern2()).negate();
        System.out.println(result.toString("locationIntern2 (CGAPlaneIPNS)"));
        //return result.extractE3ToPoint3d();
        return new CGAE3Vector(result);
    }
    
    
    // others 
    
    @Override
    public CGAPlaneOPNS undual(){
        return new CGAPlaneOPNS(impl.dual().gp(-1));
    }
}
