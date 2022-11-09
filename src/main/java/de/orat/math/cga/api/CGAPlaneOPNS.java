package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Direct plane in outer product null space representation (grade 4 multivector), 
 * corresponding to direct plane in Dorst2007.
 * 
 * e1^e2^e3^ni, e1^e2^no^ni, e1^e3^no^ni, e2^e3^no^ni
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPlaneOPNS extends CGAOrientedFiniteFlatOPNS implements iCGAQuadvector {
    
    public CGAPlaneOPNS(CGAMultivector m){
        super(m);
    }
    CGAPlaneOPNS(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create plane in outer product null space representation (grade 4 multivector).
     * 
     * Successfull tested!!!
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 second point in inner product null space representation
     * @param p3 third point in inner product null space representation
     */
    public CGAPlaneOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2, CGARoundPointIPNS p3){
        this(p1.op(p2).op(p3).op(createInf(1d)));
    }
    
    /**
     * Create a dual plane as a mid plane between two given result in outer product
     * null space representation (grade 4 multivector).
     * 
     * @param p1 point 1
     * @param p2 point 2
     */
    public CGAPlaneOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        this(createInf(1d).op((p1.op(p2)).dual()));
    }
    
    /**
     * Create dual plane from a point on the plane an its normal vector (in outer product
     * null space representation).
     * 
     * @param p result on the plane.
     * @param n normal vector.
     */
    public CGAPlaneOPNS(Point3d p, Vector3d n){
        this(create(p,n));
    }
    
    private static CGAMultivector create(Point3d p, Vector3d n){
        CGAMultivector cp = new CGARoundPointIPNS(p);
        CGAMultivector cn = new CGARoundPointIPNS(n);
        return new CGAMultivector(cp.ip(cn.op(createInf(1d))).impl);
    }
    
    @Override
    public CGAPlaneIPNS undual(){
        return new CGAPlaneIPNS(impl.undual());
    }
    /**
     * Plane through p tangent to this.
     * 
     * plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
     * ()=>plane_through_point_tangent_to_x(p,S),    // plane through p tangent to S2
     * 
     * Precedences:
     * - has 1
     * * has 2 (geometric product)
     * << and ^ have 3
     * 
     * @param p
     * @return dual plane tangent to the the plane described by this which includes the given point.
     */
    public CGAPlaneOPNS tangent(CGARoundPointIPNS p){
        // ist nicht nur für die plane, sondern auch für die Kugel gültig. Das 
        // führt zu unerwünschter Code-Dopplung. Wie kann ich das vermeiden?
        // In eine Hilfsklasse auslagern? und dabei weiteres Argument einführen?
        // Was haben Ebene und Kugel gemeinsame und unterscheiden sich von Circle,point,line?
        //TODO
        CGAMultivector m = p.op(CGAMultivector.createInf(1d)).lc(this).gp(p.op(CGAMultivector.createInf(1d)));
        System.out.println("tangent="+m.toString());
        // sollte grade 4 sein ist aber grade 2
        //FIXME
        return new CGAPlaneOPNS(m);
    }
}