package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Sphere in inner product null space representation (grade 1 multivector).
 * 
 * A sphere with radius=0 is a point.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGASphere extends CGARound implements iCGAVector {
    
    boolean isNormalized = false;
    
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
     * @param location multivector representing a location as normalized point
     * @param r radius of the sphere to CGAMultivector
     */
    public CGASphere(CGARoundPoint location, double r){
        this(create(location, r));
    }
    private static CGAMultivector create(CGARoundPoint location, double r){
        if (!location.isNormalized()) throw new IllegalArgumentException("The given location is not normalized!");
        CGARoundPoint result = new CGARoundPoint(location);
        result.isNormalized = true;
        result.sub(createInf(0.5*r*r));
        return result;
    }
    /**
     * Create sphere in inner product null space representation 
     * (grade 1 multivector).
     * 
     * @param location
     * @param r
     * @param weight 
     */
    public CGASphere(CGARoundPoint location, double r, double weight){
        this(location.sub(createInf(0.5*r*r)));
        if (weight != 1) {
            isNormalized = false;
            gp(weight);
        } else isNormalized = true;
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
        this(new CGARoundPoint(o), r);
        //if (r < 0) throw new IllegalArgumentException("Negative radius is not allowed!");
        isNormalized = true;
    }
    
    @Override
    public CGADualSphere dual(){
        return new CGADualSphere(impl.dual());
    }
    
    public boolean isNormalized(){
        return isNormalized;
    }
}