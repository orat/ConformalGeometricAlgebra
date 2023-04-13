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
        super(create(p,v));
    }
    // Vorzeichen wegen I0 ist überprüft, Formel daher anders als im Video
    // stimmt mir meinem IAS paper überein
    // @CGA("nn∧x+(0.5x²nn-x (x⋅nn))εᵢ+nnε₀-(x⋅nn)E₀")
    private static CGAMultivector create(Point3d p, Vector3d v){
        CGAEuclideanVector nq = new CGAEuclideanVector(v);
        CGAEuclideanVector q = new CGAEuclideanVector(p);
        CGAMultivector result =  nq.op(q).add(q.sqr().gp(0.5d).gp(nq).sub(q.gp(q.ip(nq)))).gp(inf).
                add(nq.gp(o)).sub(q.ip(nq).gp(I0));
        // orientedPoint = (-0.9999999999999998*eo^e3 - 2.9999999999999987*eo^ei - 
        // 2.9999999999999996*e1^ei - 5.999999999999999*e2^ei - 1.9999999999999996*e3^ei 
        // - 0.9999999999999998*e1^e3^ei - 1.9999999999999996*e2^e3^ei)
        // die beiden 3-blades am Ende sind hier falsch
        //FIXME
        System.out.println(result.toString("orientedPoint"));
        return result;
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
