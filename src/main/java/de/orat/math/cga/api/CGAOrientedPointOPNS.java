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
        CGAEuclideanBivector iq = (new CGAEuclideanBivector(v1, v2)).normalize();
        CGAEuclideanVector q = new CGAEuclideanVector(p);
        return iq.op(q).add(q.sqr().gp(0.5d).gp(iq).sub(q.gp(q.scp(iq))).gp(inf)).add(iq.gp(o)).sub(iq.ip(q.gp(I0)));
    }
    
    public CGAOrientedPointOPNS(Point3d p, Vector3d v){
        super(create(p,v));
    }
    /**
     * Creates an oriented point in opns representation.
     * 
     * Implementation following [Hildenbrand2011].<p>
     * 
     * TODO
     * ungetested
     * 
     * @param c location
     * @param n orientation
     * @return oriented point in opns representation
     */
    private static CGAMultivector create(Point3d c, Vector3d n){
        Vector3d cn = new Vector3d(); cn.cross(new Vector3d(c),n);
        CGAEuclideanVector nc = new CGAEuclideanVector(n);
        CGAEuclideanVector cc = new CGAEuclideanVector(c);
        Vector3d cv = new Vector3d(c);
        return (new CGAEuclideanVector(cn)).gp(I3).
                add(nc.op(o)).
                add(inf.op(o).gp(cv.dot(n))).
                add(nc.gp(0.5*cv.lengthSquared()).sub(cc.gp(n.dot(cv))).op(inf));
    }
    
    
    // etc.
    
    @Override
    public CGAOrientedPointIPNS dual(){
        return new CGAOrientedPointIPNS(impl.dual());
    }
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for generic opns oriented point!");
    }
}
