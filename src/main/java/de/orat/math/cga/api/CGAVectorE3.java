package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Multivector which conains only the blades e1,e2 and e3 (element of grade 1).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVectorE3 extends CGABlade implements iCGAVector {
    
    
    public CGAVectorE3(CGAMultivector m){
        super(m);
        // TODO test dass e0, einf nicht vorhanden ist!!!
    }
    
    public static CGAVectorE3 createCGAVectorE3(Vector3d t){
        return new CGAVectorE3(createE3(t));
    }
}
