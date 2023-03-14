package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointIPNS extends CGACircleIPNS {

    public CGAOrientedPointIPNS(CGAMultivector m) {
        super(m);
    }
    protected CGAOrientedPointIPNS(iCGAMultivector impl){
        super(impl);
    }
    public CGAOrientedPointIPNS(Point3d p, Vector3d v){
        super(create(v,p));
    }
    // Vorzeichen wegen I0 ist überprüft, Formel daher anders als im Video
    private static CGAMultivector create(Vector3d v, Point3d p){
        CGAEuclideanVector nq = new CGAEuclideanVector(v);
        CGAEuclideanVector q = new CGAEuclideanVector(p);
        return nq.op(q).add(q.sqr().gp(0.5d).gp(nq).sub(q.gp(q.scp(nq)))).gp(inf).
                add(nq.gp(o)).sub(q.ip(nq).gp(I0));
    }
    
    public static boolean typeof(CGAMultivector m){
        boolean result = CGARoundIPNS.typeof(m);
        //TODO weitere Tests
        return result;
    }
    
    @Override
    public CGAOrientedPointOPNS dual(){
        return new CGAOrientedPointOPNS(impl.dual());
    }
}
