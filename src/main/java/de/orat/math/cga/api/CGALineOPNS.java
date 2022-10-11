package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Line in outer product null space representation (grade 3 multivector),
 * corresponding to direct line in Dorst2007.
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
        super(m);
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
     * Create line in outer product null space representation 
     * (grade 3 multivector).
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 seconds point in inner product null space representation
     * 
     * Be careful: The representation is called dual in Hildenbrand2013 but not
     * in Dorst2007.
     */
    public CGALineOPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        this(p1.op(p2).op(createInf(1d)));
    }
    
    @Override
    public CGALineIPNS undual(){
        return new CGALineIPNS(impl.undual());
    }
}
