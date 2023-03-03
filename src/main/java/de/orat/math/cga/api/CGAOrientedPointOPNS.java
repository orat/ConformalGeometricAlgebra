package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
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
        // unit oriented bivector plane
        // Vorzeichen bezüglich I0 überprüft und damit anders als im Video
        CGAEuclideanBivector iq = (new CGAEuclideanBivector(v1, v2)).normalize();
        CGAEuclideanVector q = new CGAEuclideanVector(p);
        return iq.op(q).add(q.sqr().gp(0.5d).gp(iq).sub(q.gp(q.scp(iq))).gp(inf)).add(iq.gp(o)).sub(iq.ip(q.gp(I0)));
    }
    @Override
    public CGAOrientedPointIPNS dual(){
        return new CGAOrientedPointIPNS(impl.dual());
    }
    
}
