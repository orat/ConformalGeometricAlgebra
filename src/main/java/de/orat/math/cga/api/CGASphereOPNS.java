package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Direct (or ordinary) sphere in outer product null space representation as a multivector 
 * of grade 4, corresponding to direct sphere in Dorst2007.
 * 
 * e1^e2^e3^ni, e1^e2^no^ni, e1^e3^no^ni, e2^e3^no^ni, e1^e2^e3^no
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGASphereOPNS extends CGARoundOPNS implements iCGAQuadvector {
    
    public CGASphereOPNS(CGAMultivector m){
        super(m);
    }
    
    CGASphereOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    /**
     * Create dual sphere.
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * @param o origin of the sphere
     * @param p point on the sphere
     */
    public CGASphereOPNS(Point3d o, Point3d p){
        this((new CGARoundPointIPNS(p)).ip(inf.op((new CGARoundPointIPNS(o)))));
    }
    
    public CGASphereOPNS(Point3d o, double r){
        this(new CGASphereIPNS(o,r).undual());
    }
    
    public CGASphereOPNS(Point3d o, double r, double weight){
        this(new CGASphereIPNS(o,r, weight).undual());
    }
    
    /**
     * Create dual sphere in outer product null space representation 
     * (grade 4 multivector).
     * 
     * Successfull tested!!!
     * 
     * @param p1 multivector representing a point on the sphere
     * @param p2 multivector representing a point on the sphere
     * @param p3 multivector representing a point on the sphere
     * @param p4 multivector representing a point on the sphere
     */
    public CGASphereOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2, 
                         CGARoundPointIPNS p3, CGARoundPointIPNS p4){
        this(p1.op(p2).op(p3).op(p4));
    }
    
    /**
     * Create dual sphere in outer product null space represenation (grade 4 multivector).
     * 
     * @param p1 a point on the sphere
     * @param p2 a point on the sphere
     * @param p3 a point on the sphere
     * @param p4 a point on the sphere
     */
    public CGASphereOPNS(Point3d p1, Point3d p2, Point3d p3, Point3d p4){
        this((new CGARoundPointIPNS(p1)).op((new CGARoundPointIPNS(p2))).op((new CGARoundPointIPNS(p3))).op((new CGARoundPointIPNS(p4))));
    }
    
    // ungetested
    CGASphereOPNS(Point3d c, CGAEuclideanTrivector ev, double r){
        super(create(c, ev, r).impl);
    }
    
    
    // etc.
    
    @Override
    public CGASphereIPNS dual(){
        return new CGASphereIPNS(impl.dual());
    }
    
    /**
     * Create a plane tangent to this dual sphere which includes a given point.
     * 
     * plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
     * ()=>plane_through_point_tangent_to_x(p,S),    // plane through p tangent to S
     * 
     * Precedences:
     * - has 1
     * * has 2 (geometric product)
     * << (inner product) and ^ (outer product) have 3
     * 
     * @param p point
     * @return dual plane tanget to the sphere through the given point p
     */
    public CGAPlaneOPNS tangent(CGARoundPointIPNS p){
        // ist nicht nur für die plane, sondern auch für die Kugel gültig. Das 
        // führt zu unerwünschter Code-Dopplung. Wie kann ich das vermeiden?
        // In eine Hilfsklasse auslagern? und dabei weiteres Argument einführen?
        // Was haben Ebene und Kugel gemeinsame und unterscheiden sich von Circle,point,line?
        //TODO
        CGAMultivector m = p.op(CGAMultivector.inf).lc(this).gp(p.op(inf));
        System.out.println("tangent="+m.toString());
        // tangent=-0.69999*eo^e3 - 0.349997*e2^e3 - 0.69993*e3^ei - 0.49967*eo^e1^e3^ei + 1.39999988*eo^e2^e3^ei + 0.249983*e1^e2^e3^ei
        // woher kommen die grade2 Komponenten? Die sind vermutlich falsch?
        //FIXME CGAPlaneOPNS muss grade 4 sein
        return new CGAPlaneOPNS(m);
    }
}
