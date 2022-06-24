package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Translator rotors are generated by a lightlike Direction Vector d = e1 ∞ + e2 ∞ + e3 ∞
 * with d2 = 0 and weighted bases. 
 * 
 * They can be considered a double reflection in par-
 * allel planes, and can be algorithmically generated as the ratio of two flat points (see
 * 
 * A translator is no blade because it contains blades of grades 0 and 2 both.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATranslator extends CGAMultivector {
    
    public CGATranslator(CGAMultivector m){
        super(m.impl);
        // test auf blades 0,2
    }
    
    public CGATranslator(Vector3d d){
        this((new CGAScalar(1d)).sub(createE3(d).gp(-0.5d).gp(createInf(1d))));
    }
}
