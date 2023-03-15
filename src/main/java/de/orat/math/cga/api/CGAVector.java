package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVector extends CGAKVector implements iCGAVector {
    
    public CGAVector(CGAMultivector m) {
        super(m);
    }
    
    public CGAQuadvector dual(){
        return new CGAQuadvector(super.dual().compress());
    }
    public CGAQuadvector undual(){
        return new CGAQuadvector(super.undual().compress());
    }
}
