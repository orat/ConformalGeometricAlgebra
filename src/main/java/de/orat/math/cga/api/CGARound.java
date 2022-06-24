package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;

/**
 * Rounds are points, circle, spheres and point-pairs.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGARound extends CGABlade  {
    
    CGARound(CGAMultivector m){
        super(m);
    }
    
    public RoundAndTangentParameters decompose(){
        return decomposeRound();
    }
    
    public double decomposeWeight(){
        return CGAMultivector.decomposeWeight(
                determineDirectionFromTangentAndRoundObjectsAsMultivector(), createOrigin(1d));
    }
}
