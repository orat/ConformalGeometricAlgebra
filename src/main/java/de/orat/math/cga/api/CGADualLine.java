package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createEinf;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;

/**
 * Dual line.
 * 
 * Generates the motor algebra. There are many ways of finding a dual line: e.g. 
 * the central axis l of a circle σcan be found by contraction with infinity: ∞⌋σ = l.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualLine extends CGATreevector {
    
    public CGADualLine(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create line in outer product null space representation (grade 3 multivector).
     * 
     * Be careful: This corresponds to a line in Dorst2007 but to a dual line in
     * Hildenbrand2013.
     * 
     * @param p1 first point on the line
     * @param p2 second point on the line or direction of the line
     * (tri-vector: (e12inf, e13inf, e23inf, e10inf, e20inf, e30inf = tri-vector))
     */
    public CGADualLine(Point3d p1, Tuple3d p2){
        this((new CGAPoint(p1)), (new CGAPoint(p2)));
    }
    
    /**
     * Create line in outer product null space representation 
     * (grade 3 multivector).
     * 
     * @param p1 first point in inner product null space representation
     * @param p2 seconds point in inner product null space representation
     * 
     * Be careful: The representation is called dual in Hildenbrand213 but not
     * in Dorst2007.
     */
    public CGADualLine(CGAPoint p1, CGAPoint p2){
        this(p1.op(p2).op(createEinf(1d)));
    }
    
    public FlatAndDirectionParameters decompose(CGAPoint probePoint){
        return decomposeDualFlat(probePoint);
    }
}
