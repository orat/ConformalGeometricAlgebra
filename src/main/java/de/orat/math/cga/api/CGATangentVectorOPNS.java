package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A vector with direction u at location p (grade 2), corresponding to 
 * direct tangent in Dorst2007.
 * 
 * It squares to 0 since it is like a round with zero radius. It does not contain inf
 * so that inf^X != 0.
 * 
 * Vermutlich enthält das immer die folgenden Komponenten: e10, e20, e30
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
    
    @Override
    public CGATangentVectorIPNS dual(){
        return new CGATangentVectorIPNS(super.dual());
    }
    
    public CGATangentVectorOPNS(Point3d location, Vector3d direction){
        this(createCGATangentVectorOPNS(location, direction));
    }
    // following Dorst2007 page 406
    private static CGAMultivector createCGATangentVectorOPNS(Point3d location, Vector3d direction){
        // das sollte funktionieren, tut es aber nicht
        // The given multivector is not of grade 2: 0
        //CGARoundPointOPNS p = new CGARoundPointOPNS(location);
        
        // warum also hier die IPNS Darstellung?
        //FIXME
        CGARoundPointIPNS p = new CGARoundPointIPNS(location);
        System.out.println("(tangentVector) p="+p.toString());
        
        CGAKVector u = CGAMultivector.createE3(direction);
        System.out.println("(tangentVector) u="+u.toString());
        // tangent vector nach Dorst Tutorial 2008 page 17
        // p · (p ∧ B ∧ n∞) (grade 3)
        //CGAMultivector result =  p.ip(p.op(u).op(CGAMultivector.createInf(1d)));
        // hier kommt immer 0 raus
        
        // following Dorst2007 page 406 or Fernandes2009 (supplementary material B)
        CGAMultivector result = p.op(p.negate().lc(u.gradeInversion().gp(CGAMultivector.createInf(1d))));
        
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