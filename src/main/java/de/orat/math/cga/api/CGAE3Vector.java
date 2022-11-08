package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * Multivector which contains only the basis-blades e1,e2 and e3 (element of grade 1).
 * 
 * Different to a Vector only e1,e2 and e3 are different to 0.
 * 
 * Localisation symmetry: plane
 * Translated form: p.lc(u.op(einf))
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAE3Vector extends CGAKVector implements iCGAVector {
    
    public CGAE3Vector(CGAMultivector m){
        super(m);
        // TODO test dass e0, einf nicht vorhanden ist!!!
    }
    
    public CGAE3Vector(Tuple3d t){
        this(createE3(t));
    }
    
    public double squaredSize(){
        return extractE3ToVector3d().lengthSquared();
    }
}
