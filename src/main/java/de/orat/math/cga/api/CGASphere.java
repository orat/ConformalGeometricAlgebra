package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Sphere in inner product null space representation (grade 1 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGASphere extends CGARound implements iCGAVector {
    
    public CGASphere(CGAMultivector m){
        super(m);
    }
    CGASphere(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create sphere in inner product null space representation 
     * (grade 1 multivector).
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * Dorst2007 page 363 == Hildenbrand1998 page 29
     * 
     * @param location multivector representing a location
     * @param r radius of the sphere to CGAMultivector
     */
    public CGASphere(CGAPoint location, double r){
        this(location.sub(createInf(0.5*r*r)));
    }
    
    /**
     * Create (dual, real) sphere in inner product null space representation 
     * (grade 1 multivector).
     * 
     * Be careful: This is a dual real sphere corresponding to Dorst2007 but a 
     * real sphere in Hildenbrand2013.
     * 
     * @param o origin of the shphere
     * @param r radius of the sphere (A negative radius does not create an imagninary 
     *          sphere. Use the method createImaginarySphere() instead.)
     */
    public CGASphere(Point3d o, double r){
        this(new CGAPoint(o), r);
        if (r < 0) throw new IllegalArgumentException("Negative radius is not allowed!");
    }
    @Override
    public CGADualSphere dual(){
        return new CGADualSphere(impl.dual());
    }
}