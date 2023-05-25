package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * An oriented point is a round of grade 3 in OPNS respresentation.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointOPNS extends CGACircleOPNS {
    
    public CGAOrientedPointOPNS(CGAMultivector m) {
        super(m);
    }
    
    protected CGAOrientedPointOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    /**
     * @param v1 first vector to create a noramlized bivector of the carrier plane
     * @param v2 second vector to create a normalized bivector of the carrier plane
     * @param p location of the oriented point
     */
    public CGAOrientedPointOPNS(Vector3d v1, Vector3d v2, Point3d p){
        super(create(v1,v2,p));
    }
    //TODO
    // not yet tested!!!
    private static CGAMultivector create(Vector3d v1, Vector3d v2, Point3d p){
        // unit orientedCGAOrientedPointOPNS bivector plane
        // Vorzeichen bezüglich I0 überprüft und damit anders als im Video
        CGAEuclideanBivector m = (new CGAEuclideanBivector(v1, v2));//.normalize();
        CGAEuclideanVector x = new CGAEuclideanVector(p);
        return m.op(x).add(m.gp(0.5d*Math.abs(x.sqr().decomposeScalar())).
                sub(x.gp(x.scp(m))).gp(inf)).
                add(m.gp(o)).
                sub(I0.gp(m.ip(x)));
    }
    
    /*public CGAOrientedPointOPNS(Point3d p, Vector3d v){
        super(create2(p,v));
    }*/
    
    
    // etc.
    
    @Override
    public CGAOrientedPointIPNS dual(){
        return new CGAOrientedPointIPNS(impl.dual());
    }
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for generic opns oriented point!");
    }
}
