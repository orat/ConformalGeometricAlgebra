package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A vector with direction u at point location o (grade?), corresponding to 
 * dual tangent in Dorst2007.
 * 
 * The use of tangent blades is an elegant alternative to represent vertices in
 * a mesh, because they encode both the positional as the tangential information 
 * in a simple primitive element. 
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentVectorIPNS extends CGATangentIPNS implements iCGABivector {
     
    public CGATangentVectorIPNS(CGAMultivector m){
        super(m);
    }
    
    public CGATangentVectorIPNS(Point3d location, Vector3d direction){
        // TODO herausfinden ob Erzeugung im Ursprung + nachträgliche Verschiebung
        // nach location identisch ist zu gleich mit location wedgen
        // vermutlich falsch
        //this(new CGADirectionVectorOPNS(direction).op((new CGARoundPointOPNS(location))));
        this(createCGATangentVectorIPNS(location, direction));
    }
    private static CGAMultivector createCGATangentVectorIPNS(Point3d location, Vector3d direction){
        CGAkBlade u = new CGAAttitudeVectorIPNS(direction); 
        //CGARoundPointIPNS p = new CGARoundPointIPNS(location);
        //return p.op(p.negate().lc(u.gradeInversion().gp(CGAMultivector.createInf(1d))));
        return create(location, u);
    }
    
    @Override
    public CGATangentVectorOPNS undual(){
        return new CGATangentVectorOPNS(super.undual());
    }
    
    /**
     * Creates a tangent vector in the origin.
     * 
     * @param t vector
     * @return tangent vector
     */
    /*public CGATangentVectorIPNS createCGATangentVector (Vector3d t){
        //TODO
        // ist hier geometrisches Produkt überhaupt richtig oder muss hier äußeres 
        // Produkt stehen?
        // muss die Reihenfolge der Faktoren nicht umgedreht sein?
        return new CGATangentVectorIPNS(createOrigin(1.0).gp(new CGAMultivector(t)));
    }*/
    
    // decompose Methoden
}