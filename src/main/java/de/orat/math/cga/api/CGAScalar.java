package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalar extends CGAMultivector {
    
    public CGAScalar(double value){
        super(value);
    }
    public CGAScalar(CGAMultivector m) throws IllegalArgumentException {
        super(m.impl);
        if (!m.isScalar()) throw new IllegalArgumentException("Construction of scalar object from "
                +m.toString()+" failed!");
    }
    
    
    @Override
    public boolean isScalar(){
        return true;
    }
    
    public static CGAScalar atan2(CGAScalar y, CGAScalar x){
        return new CGAScalar(Math.atan2(y.scalarPart(), x.scalarPart()));
    }
}
