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
    
    
    // composition
    
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
    // (P,r)=>!(P-r**2*.5*ni),
    private static CGAMultivector create(CGARoundPoint location, double r){
        if (!location.isNormalized()) throw new IllegalArgumentException("The given location is not normalized!");
        CGARoundPoint result = new CGARoundPoint(location.sub(createInf(0.5*r*r)));
        result.isNormalized = true;
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
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param point
     * @param radius
     * @param sign 
     * @param weight 
     */
    public CGASphere(Point3d point, double radius, boolean sign, double weight){
       this(createCGASphere(point, radius, sign, weight));
    }
    private static CGAMultivector createCGASphere(Point3d point, double radius, boolean sign, double weight){
        // local blade = weight * ( no + center + 0.5 * ( ( center .. center ) - sign * radius * radius ) * ni )
        CGAMultivector c = createE3(point);
        CGAMultivector sr2;
        if (sign){
            sr2 = new CGAScalar(-radius*radius);
        } else {
            sr2 = new CGAScalar(radius*radius);
        }
        return createOrigin(1d).add(c).add(c.ip(c).sub(sr2).gp(createInf(0.5d))).gp(weight);
    }
    
    /**
     * Determines a sphere from the center and a point on the sphere.
     * 
     * @param location
     * @param pointOnSphere point on the sphere
     */
    public CGASphere(CGARoundPoint location, CGARoundPoint pointOnSphere){
        this(pointOnSphere.ip(location.op(createInf(1d))));
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
    
    
    // decomposition
    
    // CGARoundPoint extends CGASphere und für beide gelten die gleichen 
    // weight Formeln
    @Override
    public double squaredWeight(){
        return Math.pow(weight(),2);
    }
    /**
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return weight
     */
    private double weight(){
        return this.gp(-1d).ip(createInf(1d)).scalarPart();
    }
    
    /**
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return localisation
     */
    public Point3d localisation(){
        //lade = blade / weight
	//local center = no_ni .. ( blade ^ no_ni )
	//local radius_squared = ( center .. center ) + 2 * ( no .. blade )
	//radius_squared = radius_squared:tonumber()
	//local imaginary = false
	//if radius_squared < 0 then
	//	imaginary = true
	//	radius_squared = -radius_squared
	//end
	//local radius = math.sqrt( radius_squared )
        
        CGAMultivector no_inf = createOrigin(1d).op(createInf(1d));
        return no_inf.ip((this.gp(1d/weight())).op(no_inf)).extractE3ToPoint3d();
    }
    
    
    // others
    
    @Override
    public CGADualSphere dual(){
        return new CGADualSphere(impl.dual());
    }
    
    public boolean isNormalized(){
        return isNormalized;
    }
    @Override
    public boolean isImaginary(){
        throw new RuntimeException("not yet implemented!");
    }
}