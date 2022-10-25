package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A direction vector (grade 2).
 *
 * It is rotation covariant but translation invariant.
 * 
 * Corresponding to Dorst2007 this is the direct representation, also called
 * OPNS representation. 
 * 
 * TODO
  * ist das wirklich grade 2?
  * könnte ich nicht eine klasse directFlat bauen und davon erben?
  * ist das auch ein null-vektor?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionVectorOPNS extends CGAkBlade implements iCGABivector {
     
    public CGADirectionVectorOPNS(CGAMultivector m){
        super(m);
    }
    
    public CGADirectionVectorOPNS (Vector3d t){
        //FIXME 
        // brauche ich hier nicht das äußere Produkt statt das geometrischen?
        // also .op statt .gp?
        this((new CGAMultivector(t)).gp(createInf(1.0)));
    }
}
