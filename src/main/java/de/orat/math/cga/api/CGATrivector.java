package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATrivector extends CGAMultivector implements iCGATrivector {
    
    public CGATrivector(CGAMultivector m){
        super(m.impl);
    }
    
    public CGATrivector(Vector3d a, Vector3d b, Vector3d c){
        this((new CGAVectorE3(a)).op(
                (new CGAVectorE3(b)).op(
                (new CGAVectorE3(c)))));
    }
}
