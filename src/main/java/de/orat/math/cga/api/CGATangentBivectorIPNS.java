package de.orat.math.cga.api;

/**
 * grade 2?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentBivectorIPNS extends CGATangentIPNS implements iCGABivector {
    
    public CGATangentBivectorIPNS(CGAMultivector m) {
        super(m);
    }
    
    public CGATangentBivectorOPNS undual(){
        return new CGATangentBivectorOPNS(super.undual());
    }
}
