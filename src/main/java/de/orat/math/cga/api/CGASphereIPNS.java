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
        CGARoundPointIPNS result = new CGARoundPointIPNS(location.sub(createInf(0.5*r*r)));
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
     * @param center
     * @param radius
     * @param weight 
     * 
     * The given multivector is not grade 1! grade()==0
     * Failed wenn p1=0,0,0, soll dann aber einfach e0 sein
     * Warum funktioniert diese implementierung nicht?
     * FIXME
     */
    public CGASphereIPNS(Point3d center, double radius, double weight){
       this(createCGASphere(center, radius, weight));
    }
    private static CGAMultivector createCGASphere(Point3d center, double radius, double weight){
        // following Spence2013 lua implementation
        // local blade = weight * ( no + center + 0.5 * ( ( center .. center ) - sign * radius * radius ) * ni )
        CGAMultivector c = createE3(center);
        CGAMultivector sr2;
        if (radius >0){
            sr2 = new CGAScalar(radius*radius);
        } else {
            sr2 = new CGAScalar(-radius*radius);
        }
        /*CGAMultivector test1 = createOrigin(1d).add(c);
        CGAMultivector test2 = c.ip(c);
        System.out.println(test1.toString("CGASphereIPNS test1"));
        System.out.println(test2.toString("CGASphereIPNS test2"));*/
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
    
    /**
     * Determination of weight not based on the attitude.
     * 
     * The weight of a dual sphere is the weight of its center.
     * @return weight
     */
    public double weight2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // also corresponding to Dorst2007 drills 14.9.2, nr. 3
        return negate().ip(createInf(1d)).scalarPart();
    }
    
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * scheint zu funktionieren, siehe testSphereIPNS()
     * Fehler bei Ganja.js examle: creation of dual sphere and plane
     * 
     * @return squared size/radius imaginary sphere, if squared radius < 0
     */
    public double squaredSize2(){
        // implementation follows
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        //local radius_squared = ( center .. center ) + 2 * ( no .. blade )
	//radius_squared = radius_squared:tonumber()
        CGAMultivector center = locationIntern2();
        CGAMultivector result =  center.ip(center).add((createOrigin(1d).ip(this.gp(1d/weight2())).gp(2d)));
        System.out.println(result.toString("squaredSize2 (CGASphereIPNS)"));
        return result.scalarPart();
    }
       
    /**
     * Detmerination of the midpoint of the sphere.
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
     * @return location midpoint/center
     */
    public CGAE3Vector locationIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        //blade = blade / weight2
	//local center = no_ni .. ( blade ^ no_ni )
        CGAMultivector no_inf = createOrigin(1d).op(createInf(1d));
        CGAMultivector result = no_inf.ip((gp(1d/weight2())).op(no_inf));
        System.out.println(result.toString("locationIntern2 (CGASphereIPNS)"));
        return new CGAE3Vector(result);
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