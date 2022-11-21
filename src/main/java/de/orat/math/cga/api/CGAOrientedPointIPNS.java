package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * 
 * FIXME
 * brauche ich das überhaupt noch? Ich hab doch auch RoundPointIPNS sollte das
 * nicht das gleiche sein? Nein, das ist nicht das gleiche. 
 * denn ein oriented Point im Ursprung ist n^e0. Das ist also ein neues objekt.
 * Unklar ob es dafür auch eine Duale Form gibt.
 * 
 * Anschaulich ist das ein circle mit radius=0
 * 
 * Sollte ich dann nicht direkt von CircleIPNS erben?
 * Bisher habe ich das noch gar nicht. Bisher habe ich nur CircleOPNS?
 * 
 * 
 * Welchen Grad hat so ein Objekt überhaupt?
 * 
 * Conformal geometric objects with focus on oriented points.
 * D. Hildenbrandt, P. Charrier
 * 9th international conference on clifford algebra and their applications in 
 * mathematical physics 2011.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointIPNS extends CGAKVector implements iCGATrivector {
    
    public CGAOrientedPointIPNS(CGAMultivector m){
        super(m);
    }
    protected CGAOrientedPointIPNS(iCGAMultivector impl){
        super(impl);
    }
    /*public CGAOrientedPointIPNS(Point3d c, Vector3d nc){
    
    }*/
}
