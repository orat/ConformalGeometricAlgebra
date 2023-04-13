package de.orat.math.cga.api;

/**
 * A scalar is the (basis-) blade with grade 0 (e0).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalarOPNS extends CGAKVector implements iCGAScalarOPNS {
    
    public CGAScalarOPNS(CGAMultivector m) throws IllegalArgumentException {
        super(m);
    }
    public CGAScalarOPNS(double value){
         super(defaultInstance.impl.createScalar(value));
    }
    
    // etc.
    
    public static CGAScalarOPNS atan2(CGAScalarOPNS y, CGAScalarOPNS x){
        return new CGAScalarOPNS(Math.atan2(y.decomposeScalar(), x.decomposeScalar()));
    }
    
    public CGAScalarOPNS sqrt(){
        return new CGAScalarOPNS(Math.sqrt(this.decomposeScalar()));
    }
    
    public double value(){
        return decomposeScalar();
    }
    
    public CGAScalarIPNS dual(){
        return new CGAScalarIPNS(super.dual());
    }
}
