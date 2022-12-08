package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeIPNS extends CGAKVector {
    CGAAttitudeIPNS(CGAMultivector m){
        super(m.impl);
    }
    
    protected CGAAttitudeIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // decomposition
    
    public Vector3d direction(){
        throw new RuntimeException("Implementation only for derviced classes available!");
    }
    
    
    // etc
    
    @Override
    public CGATangentOPNS inverse(){
        throw new RuntimeException("An attitude has no inverse!");
    }
    
    @Override
    public CGAAttitudeIPNS dual(){
        return (CGAAttitudeIPNS) super.dual();
    }
}
