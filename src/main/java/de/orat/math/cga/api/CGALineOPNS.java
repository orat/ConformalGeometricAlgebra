package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Line in outer product null space representation (grade 3 multivector),
 * corresponding to direct line in Dorst2007.
 * 
 * e1^e2^ni, e1^e3^ni, e2^e3^ni, e1^no^ni, e2^no^ni, e3^no^ni
 * 
 * Lines are directly generated by wedging a point pair with ∞, or wedging a point
 * with a direction vector. 
 *
 * A line in CGA is represented by a 3 dimensional blade. The internal 
 * representation corresponds with the so-called Plücker coordinates.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGALineOPNS extends CGAOrientedFiniteFlatOPNS implements iCGATrivector {
    
    public CGALineOPNS(CGAMultivector m){
        super(m.compress());
    }
    CGALineOPNS(iCGAMultivector m){
        super(m);
    }
    /**
     * Create line in outer product null space representation (grade 3 multivector).
     * 
     * Be careful: This corresponds to a line in Dorst2007 but to a dual line in
     * Hildenbrand2013.
     * 
     * Hint: The direction of the line is from p2 to p1.
     * 
     * Successfull tested!!!
     * 
     * @param p1 first point on the line
     * @param p2 second point on the line or direction of the line
     * (tri-vector: (e12inf, e13inf, e23inf, e10inf, e20inf, e30inf = tri-vector))
     */
    public CGALineOPNS(Point3d p1, Tuple3d p2){
        this((new CGARoundPointIPNS(p1)), (new CGARoundPointIPNS(p2)));
    }
    /**
     * 
     * Hint: The direction of the line is from p2 to p1.
     * 
     * @param p1
     * @param weight1
     * @param p2
     * @param weight2 
     */
    public CGALineOPNS(Point3d p1, double weight1, Tuple3d p2, double weight2){
        this((new CGARoundPointIPNS(p1, weight1)), (new CGARoundPointIPNS(p2, weight2)));
    }
    
    /**
     * Create line in outer product null space representation 
     * (grade 3 multivector).
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 seconds point in inner product null space representation
     * 
     * Be careful: The representation is called dual in Hildenbrand2013 but direkt
     * in Dorst2007.
     */
    public CGALineOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        this(p1.op(p2).op(inf));
    }
    
    @Override
    public CGALineIPNS dual(){
        return new CGALineIPNS(new CGALineIPNS(impl.dual()).compress());
    }
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(super.attitudeIntern());
    }
    
    public CGAE3Vector attitudeIntern2(){
        // nach Spencer
        CGAE3Vector result = this.dual().attitudeIntern2();
        System.out.println(result.toString("attitudeIntern2 (CGALineOPNS, via dual, Spencer"));
        return result;
    }
    /**
     * Determine the normalized attitude of the corresponding geometric object.
     * 
     * @return noramlized attitude (its sign is the orientation corresponding to
     * the euclidean pseudoscalar)
     */
    @Override
    public Vector3d attitude(){
        Vector3d result = attitudeIntern().direction();
        result.normalize();
        return result;
    }
}
