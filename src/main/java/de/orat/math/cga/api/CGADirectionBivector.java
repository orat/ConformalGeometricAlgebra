package de.orat.math.cga.api;

/**
 * Direction Bivector of grad 3.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionBivector extends CGABlade implements iCGABivector {
    
    public CGADirectionBivector(CGAMultivector B){
        super(B.gp(createInf(1.0)));
    }
}
