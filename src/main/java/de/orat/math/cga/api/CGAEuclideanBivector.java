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
    public CGAEuclideanBivector(Tuple3d v1, Tuple3d v2){
        super((new CGAEuclideanVector(v1)).op(new CGAEuclideanVector(v2)));
    }
    
    public CGAEuclideanBivector normalize(){
        return new CGAEuclideanBivector(this.normalize());
    }
}
