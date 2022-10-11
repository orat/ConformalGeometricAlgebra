package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Bivectors describe points or pairs of points.
 * 
 * Bivectors are timelike.
 * 
 *  TODO
 * abschaffen, oder alle spezifischen Bivectoren davon erben lassen
 * CGAPointPairOPNS extends CGARoundOPNS implements iCGABivector
 * so kann CGAPointPairOPNS nicht von CGABivector erben
 * --> CGABivector wieder abschaffen und den Code als default Methods in iCGABivector verschieben
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
        this((new CGANormalVector(a)).op(
             (new CGANormalVector(b))));
    }
}
