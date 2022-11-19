package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Sphere in inner product null space representation (grade 1 multivector)
 * corresponding to dual real or imaginary sphere in Dorst2007.
 * 
 * no, e1, e2, e3, ni
 * 
 * A sphere with radius=0 is a round point.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGASphereIPNS extends CGAOrientedFiniteRoundIPNS implements iCGAVector {
    
    boolean isNormalized = false;
    
    public CGASphereIPNS(CGAMultivector m){
        super(m);
    }
    CGASphereIPNS(iCGAMultivector impl){
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
     * @param r radius of the sphere to CGAMultivector, imaginary sphere if r<0
     */
    public CGASphereIPNS(CGARoundPointIPNS location, double r){
        this(create(location, r));
    }
    // (P,r)=>!(P-r**2*.5*ni)
    private static CGAMultivector create(CGARoundPointIPNS location, double r){
        if (!location.isNormalized()) throw new IllegalArgumentException("The given location is not normalized!");
        CGARoundPointIPNS result;
        if (r >0){
            result = new CGARoundPointIPNS(location.sub(createInf(0.5*r*r)));
        } else {
            result = new CGARoundPointIPNS(location.add(createInf(0.5*r*r)));
        }
        result.isNormalized = true;
        return result;
    }
    
    /**
     * Create sphere in inner product null space representation 
     * (grade 1 multivector).
     * 
     * @param location multivector representation of a point
     * @param r radius of the sphere, r<0 if imaginary sphere
     * @param weight weight2
     */
    public CGASphereIPNS(CGARoundPointIPNS location, double r, double weight){
        //this(location.sub(createInf(0.5*r*r)).gp(weight2));
        this(create(location, r).gp(weight));
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
     * @param weight 
     * 
     */
    public CGASphereIPNS(Point3d point, double radius, double weight){
       this(createCGASphere(point, radius, weight));
    }
    private static CGAMultivector createCGASphere(Point3d point, double radius, double weight){
        // local blade = weight2 * ( no + center + 0.5 * ( ( center .. center ) - sign * radius * radius ) * ni )
        CGAMultivector c = createE3(point);
        CGAMultivector sr2;
        if (radius >0){
            sr2 = new CGAScalar(radius*radius);
        } else {
            sr2 = new CGAScalar(-radius*radius);
        }
        return createOrigin(1d).add(c).add(c.ip(c).sub(sr2).gp(createInf(0.5d))).gp(weight);
    }
    
    /**
     * Determines a sphere from the center and a point on the sphere.
     * 
     * @param location
     * @param pointOnSphere point on the sphere
     */
    public CGASphereIPNS(CGARoundPointIPNS location, CGARoundPointIPNS pointOnSphere){
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
    public CGASphereIPNS(Point3d o, double r){
        this(new CGARoundPointIPNS(o), r);
        //if (r < 0) throw new IllegalArgumentException("Negative radius is not allowed!");
        isNormalized = true;
    }
    
    
    // decomposition
    
    // CGARoundPointIPNS extends CGASphereIPNS und für beide gelten die gleichen 
    // weight2 Formeln
    /*@Override
    public double squaredWeight(){
        return Math.pow(weight2(),2);
    }*/
    /**
     * Determination of weight2 without probe point and not based on the attitude.
     * 
     * @return weight2
     */
    private double weight2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        return gp(-1d).ip(createInf(1d)).scalarPart();
    }
    
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * scheint zu funktionieren, siehe testSphereIPNS()
     * 
     * @return squared size/radius imaginary sphere if radius < 0
     */
    public double squaredSize2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        //local radius_squared = ( center .. center ) + 2 * ( no .. blade )
	//radius_squared = radius_squared:tonumber()
        CGAMultivector center = locationIntern2();
        return center.ip(center).add((createOrigin(1d).ip(this.gp(1d/weight2())).gp(2d))).scalarPart();
    }
       
    /**
     *
     * @return location origin/midpoint
     */
    @Override
    public Point3d location(){
        return locationIntern().extractE3ToPoint3d();
    }
    /**
     * Decompose location.
     * 
     * @return 
     */
    public CGARoundPointIPNS locationIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        //blade = blade / weight2
	//local center = no_ni .. ( blade ^ no_ni )
        CGAMultivector no_inf = createOrigin(1d).op(createInf(1d));
        return new CGARoundPointIPNS(no_inf.ip((gp(1d/weight2())).op(no_inf)));
    }
    
    
    // others
    
    @Override
    public CGASphereOPNS undual(){
        return new CGASphereOPNS(impl.undual());
    }
    
    public boolean isNormalized(){
        return isNormalized;
    }
    public boolean isImaginary(){
        throw new RuntimeException("not yet implemented!");
    }
}