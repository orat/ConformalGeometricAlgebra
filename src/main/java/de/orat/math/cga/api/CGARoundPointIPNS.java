package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import org.jogamp.vecmath.Tuple3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * A round point in inner product null space representation (grade 1 multivector), 
 * corresponding to dual round point in Dorst2007. 
 * 
 * Also called tangent scalar (finite point).
 * 
 * Normalized homogeneous points, or null-vectors, in the conformal model typically
 * have a weight of 1.
 * 
 * In CGA a point can be represented as a round or a flat. The round point is a 
 * sphere with radius 0. The round point is a blade with grade 1.
 *
 * The round point is used more often in geometric expressions than the flat 
 * point, since it has nice perpendicularity properties. For example, given 
 * the round points P, Q and R, it can be used for calculating the CGA-object 
 * that is perpendicular to those three points: P ∧ Q ∧ R. This is the
 * circle passing through P, Q and R.
 *
 * They can also be considered as spheres with zero radius. By
 * adding to or subtracting from the weight of the ∞ basis, we can create imaginary or
 * real dual spheres of the from σ = p ± δ ∞ where p is the homogenous center point
 * and δ is the radius of the sphere: by adding δ we create imaginary spheres with a
 * negative squared radius. Finding this squared radius is as simple as squaring the
 * dual sphere: σ 2 = r 2 . What exactly an imaginary sphere is varies from application to
 * application.
 * 
 * Null vectors, or points, in the conformal model have the unique property of hav-
 * ing a zero dot product with themselves: p · p = 0. This interesting result is part of a
 * more general useful trait: the dot product between any two normalized points rep-
 * resents the squared Euclidean distance between them: p · q = δ 2 .
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARoundPointIPNS extends CGASphereIPNS {
    
    public CGARoundPointIPNS(CGAMultivector m){
        super(m);
    }
    CGARoundPointIPNS(iCGAMultivector m){
        super(m);
    }
    
    
    // composition
    
    /**
     * A conformal point (grade 1 multivector) by up-projecting an 
     * euclidian vector into a conformal vector.
     * 
     * Inner and outer product null space representation is identical?.<p>
     * 
     * Successfull tested!!
     * !
     * @param p euclidian normalized point
     */
    public CGARoundPointIPNS(Tuple3d p){
        this(create(p, 1d));
        //FIXME
        // oder besser super(p,0d);
        isNormalized = true;
    }
    
    /**
     * A conformal point (grade 1 multivector) by up-projecting an 
     * euclidian vector into a conformal vector.
     * 
     * Inner and outer product null space representation is identical?.<p>
     * 
     * @param p euclidian point
     * @param weight
     */
    public CGARoundPointIPNS(Tuple3d p, double weight){
        this(create(p, weight));
        if (weight == 1d) isNormalized=true;
    }
    
    /**
     * Create point with given weight.
     * 
     * implementation looks indentical to
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param p euclidian point/vector
     * @param weight
     * @return normalized point
     */
    private static CGAMultivector create(Tuple3d p, double weight){
        // old version
        CGAMultivector result = (createOrigin(1d)
                .add(createEx(p.x))
                .add(createEy(p.y))
                .add(createEz(p.z))
                .add(createInf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)))).gp(weight);
        return result;
        //CGAVectorE3 x = new CGAVectorE3(p);
        //return x.add((new CGAMultivector(0.5)).gp(x.gp(x)).gp(createInf(1d))).add(createOrigin(1d));
    }
   
   
    
    // decomposition
    
   
    /*public double squaredWeight(){
        CGAMultivector attitude = determineDirectionFromTangentAndRoundObjectsAsMultivector();
        CGARoundPointIPNS probePoint = new CGARoundPointIPNS(new Point3d(0d,0d,0d));
        return CGAMultivector.squaredWeight(attitude, probePoint);
    }*/
    
    /**
     * Decompose location.
     * 
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * 
     * blade = blade / weight
     * local center = no_ni .. ( blade ^ no_ni )
     * 
     * @return location
     */
    @Override
    public Point3d location(){
        // das ist doch der Code für up-projection und nicht für decomposition
        // local blade = weight * ( no + center + 0.5 * ( center .. center ) * ni )
        //FIXME
        //CGAMultivector result = createOrigin(1d).add(this).add(this.ip(this)).gp(createInf(0.5d));
        
        // local weight = -blade .. ni
        double weight = this.negate().ip(CGAMultivector.createInf(1d)).scalarPart();
        System.out.println("CGARoundPointIPNS.location weight = "+String.valueOf(weight));
        CGAMultivector result = this.gp(1d/weight);
        // blade = blade / weight
        //local center = no_ni .. ( blade ^ no_ni )
        //CGAMultivector no_inf = createOrigin(1d).op(createInf(1d));
        //CGAMultivector result = no_inf.ip(this.gp(1d/weight).op(no_inf));
        System.out.println("CGARoundPointIPNS.location decompose = "+this.toString());
        return result.extractE3ToPoint3d();
    }
    
    
    /**
     * Normalized round points can be multiplied by scalar factor and 
     * then represent the same point. Sometimes a ’unique’ or default 
     * representation is required for calculations. 
     * 
     * Therefore the point is normalized by the formula P0 :=P/(-∞·P). 
     * This sets the e0 -factor (weight) of the point to 1.
     * 
     * @return normalized point
     */
    @Override
    public CGARoundPointIPNS normalize(){
        CGARoundPointIPNS result = new CGARoundPointIPNS(this.div(createInf(1d).ip(this).gp(-1d)));
        result.isNormalized = true;
        return result;
    }
    
    /**
     * Determine squared distance.
     * 
     * ganja.js example dual planes/spheres:
     * The distance between two points.
     * var d = (a,b)=>((a<<b).Length*2)**.5;
     * TODO Wie passt das mit der aktuellen Implementierung zusammen?
     * - Was bedeutet .Length? vermutlich scalarPart()?
     * - Warum muss hier keine Vorzeichen korrigiert werden. Das wird vermutlich
     *   irgendwie mit der method Length() gemacht. 
     * - Vielleicht eine eigene length() method einführen
     * 
     * get Length (){ 
     *   return options.over?Math.sqrt(Math.abs(this.Mul(this.Conjugate).s.s)):Math.sqrt(Math.abs(this.Mul(this.Conjugate).s)); 
     * };
     * 
     * @param p second point to determine the distance to
     * @return squared distance to the given point
     */
    public double distSquare(CGARoundPointIPNS p){
        return -2*(this.normalize()).ip(p.normalize()).scalarPart();
    }
}
