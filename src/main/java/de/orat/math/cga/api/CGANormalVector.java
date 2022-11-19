package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A normal vector of a plane with starts on the plane.
 * 
 * Localisation symmetry: plane
 * 
 * Translated form: p·lc(u.op(einf)) = u.add(p.lc(u).gp(einf))
 *  p·(u ∧ e∞), and can shift on a localized plane
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGANormalVector extends CGAKVector implements iCGAVector {
    
    public CGANormalVector(CGAMultivector m){
        super(m);
        // TODO test dass e0, einf nicht vorhanden ist!!!
    }
    
    public CGANormalVector(Point3d p, Vector3d t){
        this(create(p,t));
    }
    
    private static CGAMultivector create(Point3d p, Vector3d t){
        // IPNS ist vermutlich richtig, da das ja auch bei CGALine etc auch
        // so ist...
        return (new CGARoundPointIPNS(p)).lc(createE3(t).op(CGAMultivector.createInf(1d)));
    }
    /**
     * Squared norm/size.
     * 
     * @return squared size
     */
    public double squaredSize(){
        return extractE3ToVector3d().lengthSquared();
    }
}
