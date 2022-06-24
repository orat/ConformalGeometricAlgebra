package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGABivector extends CGABlade implements iCGABivector {
    
    public CGABivector(CGAMultivector m){
        super(m);
    }
    
    public CGABivector(Vector3d a, Vector3d b){
        // eigentlich will ich hier einen euclidischen Bivector B erzeugen. Unklar
        // ob das so überhaupt richtig ist, ist der dann hier eingebettet in CGA?
        //FIXME
        this(CGAVectorE3.createCGAVectorE3(a).op(
             CGAVectorE3.createCGAVectorE3(b)));
    }
}
