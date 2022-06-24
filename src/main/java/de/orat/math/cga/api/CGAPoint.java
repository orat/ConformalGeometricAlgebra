package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;

/**
 * Normalized homogeneous points, or null-vectors, in the conformal model typically
 * have a weight of 1 (grade 1 multivector). 
 * 
 * They can also be considered as dual spheres with zero radius. By
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
public class CGAPoint extends CGARound implements iCGAVector {
    
    public CGAPoint(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create a conformal point (grade 1 multivector) by up-projecting an euclidian vector
     * into a conformal vector.
     * 
     * Inner and outer product null space representation is identical.<p>
     * 
     * Multiplication of the multivector by double alpha possible.<p>
     * 
     * @param p result
     */
    public CGAPoint(Tuple3d p){
        this(create(p));
    }
    
    private static CGAMultivector create(Tuple3d p){
        // old version
        //return createOrigin(1d)
        //        .add(createEx(p.x))
        //        .add(createEy(p.y))
        //        .add(createEz(p.z))
        //        .add(createInf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)));
        CGAMultivector x = new CGAMultivector(p);
        return x.add((new CGAMultivector(0.5)).gp(x.gp(x)).gp(createInf(1d))).add(createOrigin(1d));
    }
    
    public double distSquare(CGAPoint p){
        return -2*this.ip(p).scalarPart();
    }
   
    
    public double decomposeSquaredWeight(){
        CGAMultivector attitude = determineDirectionFromTangentAndRoundObjectsAsMultivector();
        CGAPoint probePoint = new CGAPoint(new Point3d(0d,0d,0d));
        return CGAMultivector.decomposeSquaredWeight(attitude, probePoint);
    }
}
