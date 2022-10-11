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
 * square to 0. Planes are grade 1.
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
public class CGAPlaneIPNS extends CGAFlatIPNS implements iCGAVector {
    
    public CGAPlaneIPNS(CGAMultivector m){
        super(m);
    }
    CGAPlaneIPNS(iCGAMultivector m){
        super(m);
    }
    
    
    // composition 
    
    /**
     * Create plane in inner product null space representation (grade 1 multivector).
     * 
     * Be careful: This corresponds to dual plane in Dorst2007.
     * 
     * Successful tested!!!
     * 
     * TODO
     * Ein nicht normalisiertes n liefert anderen Multivektor, aber könnte der
     * nicht die gleiche Ebene representieren?
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
     * Composition of a plane based on a point and a normal vector and the weight. 
     * 
     * Notice: The sign of the weight is lost in this decomposition and therefor
     * can not be recovered in decomposition.
     * 
     * looks identical to 
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param P point in the plane
     * @param n normal vector
     * @param weight 
     */
    public CGAPlaneIPNS(Point3d P, Vector3d n, double weight){
        this(createEx(n.x)
            .add(createEy(n.y))
            .add(createEz(n.z))
            .add(createInf(P.x*n.x+P.y*n.y+P.z*n.z)).gp(weight));
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
     * @param P
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
    
    @Override
    public double squaredWeight(){
        return Math.pow(weight(),2);
    }
    /**
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * The sign in lost in composition of the plane and unreoverable.
     * 
     * @return weight wihout sign (aloways positive)
     */
    private double weight(){
        // local weight = ( #( no .. ( blade ^ ni ) ) ):tonumber()
        return Math.abs(createOrigin(1d).ip(this.op(createInf(1d))).scalarPart());
    }
    
    /**
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return attitude/normal/direction
     */
    @Override
    protected CGAMultivector attitudeIntern(){
        //blade = blade / weight
	//local normal = no .. ( blade ^ ni )
        return createOrigin(1d).ip(this.gp(1d/weight()).op(createInf(1d)));
    }
    
    /**
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return localisation
     */
    public Point3d localisation(){
        // local center = -( no .. blade ) * normal
        CGAMultivector result = createOrigin(1d).ip(this.gp(1d/weight())).gp(attitudeIntern()).gp(-1d);
        return result.extractE3ToPoint3d();
    }
    
    
    // others 
    
    @Override
    public CGAPlaneOPNS dual(){
        return new CGAPlaneOPNS(impl.dual());
    }
}
