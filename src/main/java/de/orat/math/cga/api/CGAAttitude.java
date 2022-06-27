package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAAttitude extends CGAMultivector {
    
    CGAAttitude(CGAMultivector m){
        super(m.impl);
    }
    
    public Vector3d attitude(){
        CGAMultivector attitude = attitudeIntern();
        return attitude.extractEuclidianVector();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        System.out.println("attitude="+toString());
        return this;
    }
    
    @Override
    public CGATangent inverse(){
        throw new RuntimeException("An attitude has no inverse!");
    }
}
