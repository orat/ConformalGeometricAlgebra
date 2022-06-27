package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentBivector extends CGATangent implements iCGATrivector {
    
    public CGATangentBivector(CGAMultivector m){
        super(m);
    }
    
    public CGATangentBivector(CGABivector B){
        this(createOrigin(1.0).gp(B));
    }
    
    public CGATangentBivector(Point3d p, Vector3d u){
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
    private static CGATangentBivector createTangentBivector(Point3d p, Vector3d u){
        CGAMultivector cp = new CGAPoint(p);
        return new CGATangentBivector(cp.ip(cp.op(new CGAPoint(u)).op(createInf(1d))));
    }
    
}
