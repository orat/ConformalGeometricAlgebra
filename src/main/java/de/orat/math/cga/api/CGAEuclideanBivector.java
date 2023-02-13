package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

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
    public CGAEuclideanBivector(Vector3d v1, Vector3d v2){
        super((new CGAEuclideanVector(v1)).op(new CGAEuclideanVector(v2)));
    }
}
