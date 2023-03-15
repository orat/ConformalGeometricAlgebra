package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATrivector extends CGAKVector implements iCGATrivector {
    
    public CGATrivector(CGAMultivector m) {
        super(m);
    }
    
    public CGATrivector(Tuple3d a, Tuple3d b, Tuple3d c){
        this((new CGAEuclideanVector(a)).op(
             (new CGAEuclideanVector(b)).op((new CGAEuclideanVector(b)))));
    }
    
    public CGATrivector(Tuple3d a, Tuple3d b){
        this((new CGAEuclideanVector(a)).op(
             (new CGAEuclideanVector(b)).op(inf)));
    }
    
    public CGABivector dual(){
        return new CGABivector(super.dual().compress());
    }
    public CGABivector undual(){
        return new CGABivector(super.undual().compress());
    }
}
