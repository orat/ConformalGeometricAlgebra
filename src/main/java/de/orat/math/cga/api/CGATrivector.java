package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A trivector describes lines and circles.
 * 
 * TODO
 * ähnliche Probleme wie mit CGABivector
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATrivector extends CGAMultivector implements iCGATrivector {
    
    public CGATrivector(CGAMultivector m){
        super(m.impl);
    }
    
    public CGATrivector(Vector3d a, Vector3d b, Vector3d c){
        this((new CGANormalVector(a)).op(
                (new CGANormalVector(b)).op(
                (new CGANormalVector(c)))));
    }
}
