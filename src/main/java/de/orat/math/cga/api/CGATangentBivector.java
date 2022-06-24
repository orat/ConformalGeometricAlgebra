package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentBivector extends CGABlade implements iCGATrivector {
    
    public CGATangentBivector(CGAMultivector m){
        super(m);
    }
    
    public CGATangentBivector createCGATangentBivector(CGABivector B){
        return new CGATangentBivector(createOrigin(1.0).gp(B));
    }
}
