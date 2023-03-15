package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.inf;
import org.jogamp.vecmath.Tuple3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAQuadvector extends CGAKVector implements iCGAQuadvector {
    
    public CGAQuadvector(CGAMultivector m) {
        super(m);
    }
    
    public CGAQuadvector(Tuple3d a, Tuple3d b, Tuple3d c, Tuple3d d){
        this((new CGAEuclideanVector(a)).op(
             (new CGAEuclideanVector(b)).op((new CGAEuclideanVector(c))).op((new CGAEuclideanVector(b)))));
    }
    
    public CGAQuadvector(Tuple3d a, Tuple3d b, Tuple3d c){
        this((new CGAEuclideanVector(a)).op(
             (new CGAEuclideanVector(b)).op((new CGAEuclideanVector(c))).op(inf)));
    }
    
    public CGAVector dual(){
        return new CGAVector(super.dual().compress());
    }
    public CGAVector undual(){
        return new CGAVector(super.undual().compress());
    }
}
