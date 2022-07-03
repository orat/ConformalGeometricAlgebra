package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

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
    
    public CGAVectorE3(Tuple3d t){
        this(createE3(t));
    }
}
