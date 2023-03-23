package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Line in outer product null space representation (grade 3 multivector),
 * corresponding to direct line in [Dorst2007].
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
public class CGALineOPNS extends CGAFlatOPNS implements iCGATrivector {
    
    public CGALineOPNS(CGAMultivector m){
        super(m.compress());
    }
    CGALineOPNS(iCGAMultivector m){
        super(m);
    }
    
    
    // composition
    
    /**
     * Create normalized line in outer product null space representation 
     * (grade 3 multivector).
     * 
     * Be careful: This corresponds to a line in Dorst2007 but to a dual line in
     * Hildenbrand2013.
     * 
     * The direction of the line is from p2 to p1.
     * 
     * @param p1 first point on the line
     * @param p2 second point on the line
     * 
     * (tri-vector: (e12inf, e13inf, e23inf, e10inf, e20inf, e30inf = tri-vector))
     */
    public CGALineOPNS(Point3d p1, Point3d p2){
        this(create(p1,p2));
        // bisher
        //this(create(new CGARoundPointIPNS(p1),new CGARoundPointIPNS(p2)).normalize());
    }
    
    /**
     * Create (non-normalized) line in outer product null space representation 
     * (grade 3).
     * 
     * Be careful: This corresponds to a line in Dorst2007 but to a dual line in
     * Hildenbrand2013.
     * 
     * Hint: The direction of the line is from p2 to p1.
     * 
     * (tri-vector: (e12inf, e13inf, e23inf, e10inf, e20inf, e30inf = tri-vector))
     * 
     * @param p1
     * @param weight1
     * @param p2
     * @param weight2 
     */
    public CGALineOPNS(Point3d p1, double weight1, Point3d p2, double weight2){
        this(new CGARoundPointIPNS(p1, weight1), 
             new CGARoundPointIPNS(p2, weight2));
    }
    
    /**
     * Create (non normalized) line in outer product null space representation 
     * (grade 3).
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 second point in inner product null space representation
     * 
     * Be careful: The representation is called dual in Hildenbrand2013 but direct
     * in Dorst2007.
     * 
     * Hint: The direction of the line is from p2 to p1.
     */
    public CGALineOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        this(create(p1,p2));
    }
    
    /**
     * Create a non-normalized line in outer product null space representation.
     * 
     * @param p1
     * @param p2
     * @return line as CGA multivector object with direction from p2 to p1
     */
    private static CGAMultivector create(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        return p1.op(p2).op(inf);
    }
    /**
     * Create a normalized line in outer product null space representation.
     * 
     * TODO normalization
     * - reicht es die Länge des Richtungsvektors auf 1 zu bringen, ohne dessen Vorzeichen zu ändern?
     * - funktioniert das normalize wirklich zuverlässig? Da wird ja durch Wurnzel des 
     *   absolut-Wertes des Quadrats geteilt. Da das quadrat negativ sein kann, eliminiert
     *   der absolut werd doch in bestimmten Fällen das Vorzeichen, was aber nötig ist um die Wurzel
     *   zu ziehen. Könnte ich nicht mit einer if-Anweisung vorher das Vorzeichen aufheben?
     *   Wann tritt dieser Fall überhaupt auf?
     * 
     * @param p1
     * @param p2
     * @return 
     */
    private static CGAMultivector create(Point3d p1, Point3d p2){
        return (new CGAAttitudeBivectorOPNS(p1,p2)).add(
                (new CGAEuclideanVector(p1)).sub(new CGAEuclideanVector(p2))/*.normalize()*/.gp(I0));
    }
    
    /**
     * Create a line in outer product null space representation (grade 3 multivector)
     * based on its moment and direction. 
     * 
     * FIXME Normalization?
     * 
     * The moment bivector is the outer product of two points on the line. Different
     * to plücker coordinates this moment representation allows to create also
     * line through the origin. In Euclidean geometry this is not possible due
     * to the not defined cross product.
     * 
     * @param moment
     * @param direction 
     */
    public CGALineOPNS(CGAEuclideanBivector moment, Vector3d direction){
        this(moment.op(inf).add(CGAMultivector.createE3(direction).negate().gp(I0)));
    }
    /**
     * Create a line in outer product null space representation (grade 3)
     * based on a point and the direction. 
     * 
     * TODO
     * ungetestet, mir scheint das ist auch gar nicht korrekt
     * 
     * @param p euclidean point on the line
     * @param d euclidean direction of the line
     */
    public CGALineOPNS(Point3d p, Vector3d d){
        this((new CGARoundPointIPNS(p)).op(new CGAAttitudeVectorOPNS(d)));
    }
    
    
    // etc
    
    @Override
    public CGALineIPNS dual(){
        return new CGALineIPNS(new CGALineIPNS(impl.dual()).compress());
    }
    @Override
    public CGALineOPNS normalize(){
        return new CGALineOPNS(super.normalize());
    }
    
    
    // decomposition
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(super.attitudeIntern());
    }
    
    public CGAEuclideanVector attitudeIntern2(){
        // nach Spencer
        CGAEuclideanVector result = this.dual().attitudeIntern2();
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