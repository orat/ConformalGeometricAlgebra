package de.orat.math.cga.api;

/**
 * A scalar is a blade with grade 0.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalar extends CGAKVector implements iCGAScalar {
    
    public CGAScalar(CGAMultivector m) throws IllegalArgumentException {
        super(m);
    }
    
    public CGAScalar(double value){
        super(value);
    }
    
    public static CGAScalar atan2(CGAScalar y, CGAScalar x){
        return new CGAScalar(Math.atan2(y.scalarPart(), x.scalarPart()));
    }
}
