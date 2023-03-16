package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVector extends CGAKVector implements iCGAVector {
    
    public CGAVector(CGAMultivector m) {
        super(m);
    }
    public CGAVector(Tuple3d v, double infinity, double origin){
        this(create(v, infinity, origin));
    }
    
    private static CGAMultivector create(Tuple3d v, double infinity, double origin){
        CGAMultivector result = null;
        if (origin != 0d){
            result = o;
        }
        if (v!= null){
            if (result == null){
                result = CGAMultivector.createE3(v);
            } else {
                result = result.op(CGAMultivector.createE3(v));
            }
        }
        if (infinity != 0d){
            if (result == null){
                result = inf;
            } else {
                result = result.op(inf);
            }
        }
        return result;
    }
    
    public CGAQuadvector dual(){
        return new CGAQuadvector(super.dual().compress());
    }
    public CGAQuadvector undual(){
        return new CGAQuadvector(super.undual().compress());
    }
}
