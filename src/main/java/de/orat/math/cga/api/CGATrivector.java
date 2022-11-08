package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * A trivector describe lines and circles in OPNS representation.
 * 
 * Trivectors are linear combinations of blades with grade 3 (e123).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATrivector extends CGAMultivector implements iCGATrivector {
    
    public CGATrivector(CGAMultivector m){
        super(m.impl);
    }
    
    public CGATrivector(Tuple3d a, Tuple3d b, Tuple3d c){
        this((new CGAE3Vector(a)).op(
                (new CGAE3Vector(b)).op(
                (new CGAE3Vector(c)))));
    }
    
    public CGATrivector(Tuple3d a, Tuple3d b){
        this((new CGAE3Vector(a)).op(
                (new CGAE3Vector(b)).op(
                (CGAMultivector.createInf(1d)))));
    }
}
