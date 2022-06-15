package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalar extends CGAMultivector {
    
    public CGAScalar(CGAMultivector m) throws IllegalArgumentException {
        super(m.impl);
        if (!m.isScalar()) throw new IllegalArgumentException("Construction of scalar object from "
                +m.toString()+" failed!");
    }
    @Override
    public boolean isScalar(){
        return true;
    }
}
