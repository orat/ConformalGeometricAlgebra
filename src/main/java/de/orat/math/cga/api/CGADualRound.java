package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGADualRound extends CGAMultivector {
    
    CGADualRound(CGAMultivector m){
        super(m.impl);
    }
    protected CGADualRound(iCGAMultivector impl){
        super(impl);
    }
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude(dualRound/dualTangent)="+result.toString());
        return result.extractEuclidianVector();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return attitudeFromDualTangentAndDualRound();
    }
    
    /**
     * Squared size or (-)radius squared.
     * 
     * @return squared size
     */
    public double squaredSize(){
        return -CGARound.squaredSize(this);
    }
}
