package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A vector with direction u at location p (grade 2), corresponding to 
 * direct tangent in Dorst2007.
 * 
 * no^e1,    no^e2,    no^e3,    e1^e2,    e2^e3,    e3^e1,    e1^ni,    e2^ni,    
 * e3^ni,    no^ni
 * 
 * It squares to 0 since it is like a round with zero radius. It does not contain inf
 * so that inf^X != 0.
 * 
 * Vermutlich enthält das immer die folgenden Komponenten: e10, e20, e30, scheint
 * leider nicht zu stimmen, vielleicht nur für Tangenten im Ursprung
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
    
    public CGATangentVectorOPNS(Point3d location, Vector3d direction){
        this(create(location, direction));
    }
    
    @Override
    public CGATangentVectorIPNS dual(){
        return new CGATangentVectorIPNS(super.dual());
    }
    
    // following Dorst2007 page 406
    /*private static CGAMultivector createCGATangentVectorOPNS(Point3d location, Vector3d direction){
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
    }*/
    
    /**
     * Create tangent vector in OPNS representation (direct form in Dorst2007).
     * 
     * @param location euclidean point
     * @param u direction (euclidean) vector
     * @return tangent vector (in OPNS representation)
     */
    protected static CGAMultivector create(Point3d location, Vector3d u){
        
        // FIXME generic version failed
        //return create(location, CGAMultivector.createE3(u));
        
        // Warum hier IPNS? ist bei line etc. auch so
        CGARoundPointIPNS p = new CGARoundPointIPNS(location);
        // following Dorst2007 page 452 (specialized form: u from euclidean vector)
        return p.op(p.lc(CGAMultivector.createE3(u).gp(inf)));
    }
    
    /**
     * Creates a tangent vector in the origin.
     * 
     * @param t euclidean vector
     * @return tangent vector located in the origin
     */
    public static CGATangentVectorOPNS createCGATangentVector (Vector3d t){
        //TODO
        // ist hier geometrisches Produkt überhaupt richtig oder muss hier äußeres 
        // Produkt stehen?
        // muss die Reihenfolge der Faktoren nicht umgedreht sein?
        return new CGATangentVectorOPNS(createOrigin(1.0).gp(new CGAMultivector(t)));
    }
    
    
    // decomposeMotor Methoden
    
    /*@Override
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }*/
   
    
    /**
     * Determine the location.
     * 
     * @return location as euclidean point
     */
    @Override
    public Point3d location(){
        // CGATangentVectorOPNS.location (flat point) = (eo^ei + e2^ei)
        // The "meet" of the line perpendicularly through "this" with the plane that 
        // contains "this".
        // corresponding to Dorst2007 p. 562
        // hier kommt grade-2 heraus, obwohl nach lua der FlatPoint eigentlich grade-3
        // sein sollte
        CGAFlatPointOPNS result = new CGAFlatPointOPNS(inf.lc(this).lc(this.op(inf)));
        System.out.println(result.toString("CGATangentVectorOPNS.location (flat point)"));
        return result.location();
    }
}