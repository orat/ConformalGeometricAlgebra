package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import org.jogamp.vecmath.Tuple3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * Normalized homogeneous points, or null-vectors, in the conformal model typically
 * have a weight of 1 (grade 1 multivector). 
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
public class CGARoundPoint extends CGASphere {
    
    public CGARoundPoint(CGAMultivector m){
        super(m);
    }
    CGARoundPoint(iCGAMultivector m){
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
    public CGARoundPoint(Tuple3d p){
        this(create(p, 1d));
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
    public CGARoundPoint(Tuple3d p, double weight){
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
        CGARoundPoint probePoint = new CGARoundPoint(new Point3d(0d,0d,0d));
        return CGAMultivector.squaredWeight(attitude, probePoint);
    }*/
    
    /**
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return localisation
     */
    @Override
    public Point3d localisation(){
        // local blade = weight * ( no + center + 0.5 * ( center .. center ) * ni )
        CGAMultivector result = createOrigin(1d).add(this).add(this.ip(this)).gp(createInf(0.5d));
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
    public CGARoundPoint normalize(){
        CGARoundPoint result = new CGARoundPoint(this.div(createInf(1d).ip(this).gp(-1d)));
        result.isNormalized = true;
        return result;
    }
    
    /**
     * Determine squared distance.
     * 
     * @param p second point to determine the distance to
     * @return squared distance to the given point
     */
    public double distSquare(CGARoundPoint p){
        return -2*(this.normalize()).ip(p.normalize()).scalarPart();
    }
}
