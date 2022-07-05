package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * Circle in inner product null space represenation (grade 2 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircle extends CGARound implements iCGABivector {
    
    public CGACircle(CGAMultivector m){
        super(m);
    }
    protected CGACircle(iCGAMultivector impl){
        super(impl);
    }
    
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param center
     * @param normal
     * @param radius
     * @param weight
     * @param sign 
     */
    public CGACircle(Point3d center, Vector3d normal, double radius, double weight, boolean sign){
        this((new CGASphere(center, radius, sign, 1d)).op(new CGAPlane(center, normal, 1d)).gp(weight));
    }
    
    /**
     * Create a circle by intersection of two spheres.
     * 
     * @param sphere1 first sphere
     * @param sphere2 second sphere
     */
    public CGACircle(CGASphere sphere1, CGASphere sphere2){
        this(sphere1.op(sphere2));
    }
    @Override
    public CGADualCircle dual(){
        return new CGADualCircle(impl.dual());
    }
    /**
     * Determination the weight from this flat point.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     */
    private double weight(){
        // local weight = ( #( no_ni .. ( blade ^ ni ) ) ):tonumber()
        return (createOrigin(1d).op(createInf(1d).ip(this.op(createInf(1d))))).scalarPart();
    }
    /**
     * Determination the squared weight from this flat point.Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return squared weight
     */
    @Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return attitude
     */
    @Override
    protected CGAMultivector attitudeIntern(){
        // blade = blade / weight
	// local normal = -no_ni .. ( blade ^ ni )
        return createOrigin(-1d).op(createInf(1d)).ip(this.gp(1d/weight()).op(createInf(1d)));
    }
    /**
     * Determine a point on the line which has the closest distance to the origin.Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return location
     */
    @Override
    public Point3d location(){
        // local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) )
        return new Point3d(locationIntern().extractEuclidianVector());
    }
    /**
     * Determine a point on the line which has the closest distance to the origin.Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return location
     */
    private CGAMultivector locationIntern(){
        CGAMultivector no_ni = createOrigin(1d).op(createInf(1d));
        return attitudeIntern().gp(-1d).gp(no_ni.ip(this.op(no_ni)));
    }
    
    /**
     * Determination of the squared size. This is the radiusSquared for a sphere.
     * 
     * Determine a point on the line which has the closest distance to the origin.Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * 
     * @return squared size/radius squared
     */
    @Override
    public double squaredSize(){
        // local radius_squared = ( center .. center ) - 2 * ( ( no_ni .. ( no ^ blade ) ) + ( center .. normal ) * center ) * normal
	CGAMultivector normal = attitudeIntern();
        CGAMultivector center = locationIntern();
        CGAMultivector o = createOrigin(1d);
        CGAMultivector inf = createInf(1d);
        CGAMultivector no_ni = o.op(inf);
        CGAMultivector result = center.sqr().sub((no_ni.ip(o.op(this)).add((o.ip(normal).gp(center))))).gp(2d).gp(normal);
        return result.scalarPart();
    }
    
    @Override
    public boolean isImaginary(){
        throw new RuntimeException("not yet implemented!");
    }
}
