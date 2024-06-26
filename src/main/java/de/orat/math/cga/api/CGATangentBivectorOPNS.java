package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Describes what a sphere and a plane have in common at their point of intersection
 * (grade 3).
 * 
 * e1^e2^e3, e2^e3^ni, e3^e1^ni, e1^e2^ni, no^e3^ni, no^e1^ni, no^e2^ni, 
 * no^e2^e3, no^e1^e3, no^e1^e2
 * 
 * This is an infinitesimal circle in a well defined plane.
 * 
 * Gibt es einen Zusammenhang mit flat-points?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentBivectorOPNS extends CGATangentOPNS implements iCGATrivector {
    
    public CGATangentBivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    public CGATangentBivectorOPNS(CGABivector B){
        this(createOrigin(1.0).gp(B));
    }
    
    public CGATangentBivectorOPNS(Point3d p, Vector3d u){
        this(createTangentBivector(p,u));
    }
    /**
     * Create tangent vector which includes a result and a direction in inner product null space 
     * representation.
     * 
     * @param p result 
     * @param u direction of the tangent
     * @return bivector representing a tangend vector
     */
    private static CGATangentBivectorOPNS createTangentBivector(Point3d p, Vector3d u){
        CGAMultivector cp = new CGARoundPointIPNS(p);
        return new CGATangentBivectorOPNS(cp.ip(cp.op(new CGARoundPointIPNS(u)).op(inf)));
    }
    
    public CGATangentBivectorIPNS dual(){
        return new CGATangentBivectorIPNS(super.dual());
    }
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for generic opns tangent bivector!");
    }
}