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
public class CGAPlane extends CGAFlat implements iCGAVector {
    
    public CGAPlane(CGAMultivector m){
        super(m);
    }
    CGAPlane(iCGAMultivector m){
        super(m);
    }
    
    
    // composition 
    
    /**
     * Create plane in inner product null space representation (grade 1 multivector).
     * 
     * Be careful: This corresponds to dual plane in Dorst2007.
     * 
     * @param n normal vector of the plane
     * @param d distance of the plane to the origin
     */
    public CGAPlane(Vector3d n, double d){
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
     * @param o point in the plane
     * @param n normal vector
     * @param weight 
     */
    public CGAPlane(Point3d o, Vector3d n, double weight){
        this(createEx(n.x)
            .add(createEy(n.y))
            .add(createEz(n.z))
            .add(createInf(o.x*n.x+o.y*n.y+o.z*n.z)).gp(weight));
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
    public CGADualPlane dual(){
        return new CGADualPlane(impl.dual());
    }
}
