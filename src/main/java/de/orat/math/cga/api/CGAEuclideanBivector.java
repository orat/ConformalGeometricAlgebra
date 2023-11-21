package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * An euclidean bivector (e12, e13, e23).
 * 
 * TODO implement test-classes for the basisblades.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAEuclideanBivector extends AbstractEuclideanKVector implements iCGABivector {
    
    public CGAEuclideanBivector(CGAMultivector m){
        super(m);
    }
    public CGAEuclideanBivector(CGAEuclideanVector v1, CGAEuclideanVector v2){
        super(v1.op(v2));
    }
    public CGAEuclideanBivector(Tuple3d v1, Tuple3d v2){
        this(new CGAEuclideanVector(v1),new CGAEuclideanVector(v2));
    }
    
    public CGAEuclideanBivector normalize(){
        return new CGAEuclideanBivector(super.normalize());
    }
    
    // entspricht cross product scheint richtig
    public CGAEuclideanVector euclideanDual(){
        return new CGAEuclideanVector(super.euclideanDual());
    }
}
