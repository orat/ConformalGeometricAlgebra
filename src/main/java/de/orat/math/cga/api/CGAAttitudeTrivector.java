package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude trivector.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeTrivector extends AbstractCGAAttitude implements iCGATrivector {
    
    public CGAAttitudeTrivector(CGAMultivector m){
        super(m);
    }
    
    public CGAAttitudeTrivector(Vector3d a, Vector3d b, Vector3d c){
        this((new CGATrivector(a,b,c)).gp(createInf(1.0)));
    }
}
