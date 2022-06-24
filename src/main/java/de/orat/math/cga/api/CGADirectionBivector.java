package de.orat.math.cga.api;

/**
 * Direction Bivector of grad 3.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionBivector extends CGABlade implements iCGATrivector {
    
    public CGADirectionBivector(CGAMultivector m){
        super(m);
    }
    
    public CGADirectionBivector(CGABivector B){
        this(B.gp(createInf(1.0)));
    }
}
