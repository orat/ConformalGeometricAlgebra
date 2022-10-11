package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * Multivector which conains only the blades e1,e2 and e3 (element of grade 1).
 * 
 * Localisation symmetry: plane
 * Translated form: p.lc(u.op(einf))
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGANormalVector extends CGABlade implements iCGAVector {
    
    public CGANormalVector(CGAMultivector m){
        super(m);
        // TODO test dass e0, einf nicht vorhanden ist!!!
    }
    
    public CGANormalVector(Tuple3d t){
        this(createE3(t));
    }
    
    public double squaredSize(){
        return extractE3ToVector3d().lengthSquared();
    }
}
