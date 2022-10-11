package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * A trivector describes lines and circles in OPNS representation.
 * 
 * Trivectors are linear combinations of Blades with grade 3.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATrivector extends CGAMultivector implements iCGATrivector {
    
    public CGATrivector(CGAMultivector m){
        super(m.impl);
    }
    
    public CGATrivector(Tuple3d a, Tuple3d b, Tuple3d c){
        this((new CGANormalVector(a)).op(
                (new CGANormalVector(b)).op(
                (new CGANormalVector(c)))));
    }
    
    public CGATrivector(Tuple3d a, Tuple3d b){
        this((new CGANormalVector(a)).op(
                (new CGANormalVector(b)).op(
                (CGAMultivector.createInf(1d)))));
    }
}
