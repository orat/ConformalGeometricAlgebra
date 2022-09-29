package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Dual sphere (inner product null space representation) as a multivector 
 * of grade 4.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualSphere extends CGADualRound implements iCGAQuadvector {
    
    public CGADualSphere(CGAMultivector m){
        super(m);
    }
    CGADualSphere(iCGAMultivector impl){
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
    public CGADualSphere(Point3d o, Point3d p){
        this((new CGARoundPoint(p)).ip(createInf(1d).op((new CGARoundPoint(o)))));
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
    public CGADualSphere(CGARoundPoint p1, CGARoundPoint p2, 
                         CGARoundPoint p3, CGARoundPoint p4){
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
    public CGADualSphere(Point3d p1, Point3d p2, Point3d p3, Point3d p4){
        this((new CGARoundPoint(p1)).op((new CGARoundPoint(p2))).op((new CGARoundPoint(p3))).op((new CGARoundPoint(p4))));
    }
    
    @Override
    public CGASphere undual(){
        return new CGASphere(impl.undual());
    }
    
    /**
     * Create a plane tangent to this dual sphere which includes a given point.
     * 
     * plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
     * ()=>plane_through_point_tangent_to_x(p,S),    // plane through p tangent to S2
     * 
     * Precedences:
     * - has 1
     * * has 2
     * << and ^ have 3
     * 
     * @param p
     * @return 
     */
    public CGADualPlane tangent(CGARoundPoint p){
        // ist nicht nur für die plane, sondern auch für die Kugel gültig. Das 
        // führt zu unerwünschter Code-Dopplung. Wie kann ich das vermeiden?
        // In eine Hilfsklasse auslagern? und dabei weiteres Argument einführen?
        // Was haben Ebene und Kugel gemeinsame und unterscheiden sich von Circle,point,line?
        //TODO
        CGAMultivector m = p.op(CGAMultivector.createInf(1d)).lc(this.gp(p.op(CGAMultivector.createInf(1d))));
        System.out.println("tangent="+m.toString());
        // tangent=0.0724999999999999*e1^e3 statt grade 4
        return new CGADualPlane(m);
    }
}
