package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAFlat extends CGABlade {
     
    CGAFlat(CGAMultivector m){
        super(m);
    }
    
    public FlatAndDirectionParameters decompose(CGAMultivector probePoint){
        return decomposeFlat(probePoint);
    }
}
