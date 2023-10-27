package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeIPNS extends CGAKVector implements iCGAAttitude {
    
    public CGAAttitudeIPNS(CGAMultivector m){
        super(m.impl);
    }
    
    protected CGAAttitudeIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    public static boolean is(CGAMultivector m){
        if (!inf.op(m).isNull()) return false;
        return inf.lc(m).isNull();
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
    public CGAAttitudeOPNS undual(){
        return new CGAAttitudeOPNS(super.undual().compress());
    }
    @Override
    public CGAKVector dual(){
        throw new RuntimeException("The given multivector is ipns-type - dual() is not allowed! Use undual() instead!");
    }
}
