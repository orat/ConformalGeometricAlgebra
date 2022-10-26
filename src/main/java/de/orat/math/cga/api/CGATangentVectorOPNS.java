package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A vector with direction u at point location o (grade 2), corresponding to 
 * direct tangent in Dorst2007.
 * 
 * TODO
 * was ist mit normal vector (grade 1)?
 * mir fehlt dann nocht CGATangentVectorIPNS class
 * 
 * The use of tangent blades is an elegant alternative to represent vertices in
 * a mesh, because they encode both the positional as the tangential information 
 * in a simple primitive element. 
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentVectorOPNS extends CGATangentOPNS implements iCGABivector {
     
    public CGATangentVectorOPNS(CGAMultivector m){
        super(m);
    }
    
    public CGATangentVectorIPNS dual(){
        return new CGATangentVectorIPNS(super.dual());
    }
    
    public CGATangentVectorOPNS(Point3d location, Vector3d direction){
        // TODO herausfinden ob Erzeugung im Ursprung + nachträgliche Verschiebung
        // nach location identisch ist zu gleich mit location wedgen
        // vermutlich falsch
        //this(new CGADirectionVectorOPNS(direction).op((new CGARoundPointOPNS(location))));
        this(createCGATangentVectorOPNS(location, direction));
    }
    // following Dorst2007 page 406
    private static CGAMultivector createCGATangentVectorOPNS(Point3d location, Vector3d direction){
        CGARoundPointOPNS p = new CGARoundPointOPNS(location);
        System.out.println("(tangentVector) p="+p.toString());
        // das scheint mir die Rechenreihenfolge zu ändern, es wird dann B ∧ n∞
        // statt p ∧ B gerechnet?
        //CGAkBlade u = new CGADirectionVectorOPNS(direction); 
        CGAkBlade u = CGAMultivector.createE3(direction);
        System.out.println("(tangentVector) u="+u.toString());
        // p · (p ∧ B ∧ n∞) 
        CGAMultivector result =  p.ip(p.op(u).op(CGAMultivector.createInf(1d)));
        // hier kommt immer 0 raus
        //FIXME
        System.out.println("tangentVector="+result.toString());
        return result;
    }
    
    /**
     * Creates a tangent vector in the origin.
     * 
     * @param t vector
     * @return tangent vector
     */
    public CGATangentVectorOPNS createCGATangentVector (Vector3d t){
        //TODO
        // ist hier geometrisches Produkt überhaupt richtig oder muss hier äußeres 
        // Produkt stehen?
        // muss die Reihenfolge der Faktoren nicht umgedreht sein?
        return new CGATangentVectorOPNS(createOrigin(1.0).gp(new CGAMultivector(t)));
    }
    
    // decompose Methoden
}