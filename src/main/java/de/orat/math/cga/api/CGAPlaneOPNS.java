package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Plane in outer product null space representation (grade 4 multivector), 
 * corresponding to direct plane in [Dorst2007].
 * 
 * e1^e2^e3^ni, e1^e2^no^ni, e1^e3^no^ni, e2^e3^no^ni
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPlaneOPNS extends CGAFlatOPNS implements iCGAQuadvector {
    
    public CGAPlaneOPNS(CGAMultivector m){
        super(m);
    }
    
    CGAPlaneOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    public CGAPlaneOPNS(double[] values){
        super(values);
    }
    
    /**
     * Create plane in outer product null space representation (grade 4 multivector).
     * 
     * TODO
     * herausfinden zu was für einer Ausrichtung des Normalenvektors dies führen soll.
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 second point in inner product null space representation
     * @param p3 third point in inner product null space representation
     */
    public CGAPlaneOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2, CGARoundPointIPNS p3){
        this(p1.op(p2).op(p3).op(inf));
    }
    
    /**
     * Create plane in outer product null space representation (grade 4 multivector).
     * 
     * TODO
     * herausfinden zu was für einer Ausrichtung des Normalenvektors dies führen soll.
     * 
     * @param p1
     * @param p2
     * @param p3 
     */
    public CGAPlaneOPNS(Point3d p1, Point3d p2, Point3d p3){
        this(create(p1,p2,p3));
    }
    
    /**
     * following [Bayro-Corrochano2005].
     * 
     * TODO
     * herausfinden zu was für einer Ausrichtung des Normalenvektors dies führen soll.<p>
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return up(p3).op(p1).op(p2).op(inf)
     */
    private static CGAMultivector create(Point3d p1, Point3d p2, Point3d p3){
        CGAEuclideanVector p1c = new CGAEuclideanVector(p1);
        CGAEuclideanVector p2c = new CGAEuclideanVector(p2);
        CGAEuclideanVector p3c = new CGAEuclideanVector(p3);
        return p3c.op(p1c).op(p2c).op(inf).add((p3c.sub(p1c).op(p2c.sub(p1c))).gp(inf.op(o)));
    }
    
    /**
     * Create a dual plane as a mid plane between two given result in outer product
     * null space representation (grade 4 multivector).
     * 
     * TODO
     * unklares Vorzeichen des Normalenvektors der Ebene
     * 
     * @param p1 point 1
     * @param p2 point 2
     */
    public CGAPlaneOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        //FIXME inf wirklich zu beginn?
        this(inf.op((p1.op(p2)).dual()));
    }
    
    /**
     * Create dual plane from a point on the plane an its normal vector (in outer product
     * null space representation).
     * 
     * FIXME stimmt die create()-methode so überhaupt????
     * 
     * @param p result on the plane.
     * @param n normal vector.
     */
    public CGAPlaneOPNS(Point3d p, Vector3d n){
        this(create(p,n));
    }
    private static CGAMultivector create(Point3d p, Vector3d n){
        //TODO vermutlich falsch
        //CGAMultivector cp = new CGARoundPointIPNS(p);
        //CGAMultivector cn = new CGARoundPointIPNS(n);
        //return new CGAMultivector(cp.ip(cn.op(inf)).impl);
        return (new CGAPlaneIPNS(p, n)).undual();
    }
    
    
    // etc
    
    @Override
    public CGAPlaneIPNS dual(){
        return new CGAPlaneIPNS(new CGAPlaneIPNS(impl.dual()).compress());
    }
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for opns plane!");
    }
    
    public CGAPlaneOPNS normalize(){
        return new CGAPlaneOPNS(super.normalize().compress());
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
        CGAMultivector m = p.op(inf).lc(this).gp(p.op(inf));
        System.out.println("tangent="+m.toString());
        // sollte grade 4 sein ist aber grade 2
        //FIXME
        return new CGAPlaneOPNS(m);
    }
    
    /**
     * not tested
     * following Perwass p. 178
     * @return 
     */
    public CGAPlaneIPNS midPlane(){
        return new CGAPlaneIPNS(this.ip(inf));
    }
    
    
    // attitude
    
    /**
     * WORKAROUND da super.attitudeIntern() nicht richtig funktioniert für
     * "plane-opns", obwohl das für "line-opns" funktioniert!
     * 
     * Führt zu gleichem Ergebnis, wie die CGAFlatOPNS implementation in den tests,
     * aber zu einer um 90grad gedrehten Ebenen bei der IK.
     * 
     * @return euclidean direction
     */
    /*public Vector3d attitude(){
        Vector3d result = (new CGAPlaneIPNS(dual().negate().compress())).attitude();
        System.out.println(toString("attitude (CGAPlaneOPNS via dual(), Spencer))"));
        return result;
    }*/
    
    /**
     * Determine the attitude as attitude bivector.
     * 
     * @return attitude as attitude-bivector in opns representation
     */
    @Override
    public CGAAttitudeBivectorOPNS attitudeIntern(){
        return new CGAAttitudeBivectorOPNS(super.attitudeIntern());
    } 
}