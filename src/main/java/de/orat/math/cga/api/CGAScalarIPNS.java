package de.orat.math.cga.api;

/**
 * A scalar in IPNS representation is the pseudoscalar with grade 5 (e01234).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalarIPNS extends CGAKVector implements iCGAScalarIPNS {
    
    public CGAScalarIPNS(CGAMultivector m) throws IllegalArgumentException {
        super(m);
    }
    
    public CGAScalarIPNS(double value){
        super(createE(value));
    }
    
    public static CGAScalarIPNS atan2(CGAScalarIPNS y, CGAScalarIPNS x){
        return new CGAScalarIPNS(Math.atan2(y.decomposeScalar(), x.decomposeScalar()));
    }
    
    public CGAScalarIPNS sqrt(){
        return new CGAScalarIPNS(Math.sqrt(this.decomposeScalar()));
    }
    
    public double decomposeScalar(){
        return impl.extractCoordinates(5)[0];
    }
    public double value(){
        return decomposeScalar();
    }
    
    /*public CGAKVector dual(){
        throw new RuntimeException("not allowed - used undual() instead!");
    }*/
    public CGAScalarOPNS undual(){
        return new CGAScalarOPNS(super.undual());
    }
}
