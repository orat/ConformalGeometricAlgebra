package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createEinf;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGASphere extends CGAMultivector {
    
    public CGASphere(CGAMultivector m){
        super(m.impl);
    }
    
    /**
     * Create sphere in inner product null space representation (grade 1 multivector).
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * Dorst2007 page 363 == Hildenbrand1998 page 29
     * 
     * @param location multivector representing a location
     * @param r radius of the sphere to CGAMultivector
     */
    public CGASphere(CGAMultivector location, double r){
        this(location.sub(createEinf(0.5*r*r)));
    }
    
    /**
     * Create sphere in inner product null space representation (grade 1 multivector).
     * 
     * Be careful: This is a dual real sphere corresponding to Dorst2007 but a 
     * real sphere in Hildenbrand2013.
     * 
     * @param o origin of the shphere
     * @param r radius of the sphere (A negative radius does not CGAMultivector an imagninary 
     *          sphere. Use the method createImaginarySphere() instead.)
     */
    public CGASphere(Point3d o, double r){
        this(new CGAPoint(o), r);
    }
    
    public RoundAndTangentParameters decompose(){
        return decomposeRound();
    }
}