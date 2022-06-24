package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalar extends CGABlade implements iCGAScalar {
    
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
