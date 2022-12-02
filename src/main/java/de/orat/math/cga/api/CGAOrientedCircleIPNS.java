package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Circle in inner product null space represenation (grade 2 multivector)
 * corresponding to dual circle in Dorst2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedCircleIPNS extends CGAOrientedFiniteRoundIPNS implements iCGABivector {
    
    public CGAOrientedCircleIPNS(CGAMultivector m){
        super(m);
    }
    protected CGAOrientedCircleIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
    /**
     * Composition by the euclidian parameters of a sphere and a plane.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * or
     * CONFORMAL GEOMETRIC OBJECTS WITH FOCUS ON ORIENTED POINTS
     * Dietmar Hildenbrand∗, Patrick Charrier, 2011
     * 
     * Determination by intersection of a sphere and a plane.
     * 
     * @param center
     * @param normal
     * @param radius imaginary circle if radius<0
     * @param weight
     */
    public CGAOrientedCircleIPNS(Point3d center, Vector3d normal, double radius, double weight){
        this((new CGASphereIPNS(center, radius, 1d)).op(new CGAPlaneIPNS(center, normal, 1d)).gp(weight));
    }
    
    /**
     * Create a circle by intersection of two spheres.
     * 
     * @param sphere1 first sphere
     * @param sphere2 second sphere
     */
    public CGAOrientedCircleIPNS(CGASphereIPNS sphere1, CGASphereIPNS sphere2){
        this(sphere1.op(sphere2));
    }
   
    
    // decomposition
    
    /**
     * Determination the absolute of the weight without usage of a probepoint 
     * and without determination of the attitude.
     * 
     * test ok
     * 
     * @return absolute value of the weight
     */
    public double weight2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local weight2Intern = ( #( no_ni .. ( blade ^ ni ) ) ):tonumber()
        // # bedeutet magnitude
        //FIXME warum Math.abs()? Warum bekomme ich hier das Vorzeichen nicht?
        CGAMultivector result =  createOrigin(1d).op(inf.ip(this.op(inf)));
        System.out.println(result.toString("weight2 (CGAOrientedCircleIPNS)"));
        // weight2Intern (CGAOrientedCircleIPNS) = (1.9999999999999991*eo^e1^ei)
        return Math.abs(result.norm());
    }
    
    /**
     * Determine the attitude (normal vector of the carrier plane).
     * 
     * test ok
     * 
     * @return attitude as (E3) 1-vector
     */
    public CGAE3Vector attitudeIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // CGAUtil.lua l.366
        // blade = blade / weight2Intern
	// local normal = -no_ni .. ( blade ^ ni )
        CGAMultivector result = 
                createOrigin(-1d).op(inf).ip(this.gp(1d/weight2()).op(inf)).compress();
        System.out.println(result.toString("attitudeIntern2 (CGAorientedCircleIPNS)"));
        return new CGAE3Vector(result);
    }
    
    /**
     * Determine location as E3 vector.
     * 
     * @return location
     */
    public CGAE3Vector locationIntern2(){
        CGAMultivector no_ni = createOrigin(1d).op(inf);
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) )
	CGAMultivector result = attitudeIntern2().negate().gp(no_ni.ip(
                this.gp(1d/weight2()).op(createOrigin(1d).gp(inf))));
        System.out.println(result.toString("locationIntern2 (CGAOrientedCircleIPNS)"));
        return new CGAE3Vector(result);
    }
    
    /**
     * Determination of the squared size/radius. This is the radiusSquared for a sphere.
     * 
     * scheint nicht zu funktionieren
     * 
     * @return squared size/radius squared
     */
    public CGAScalar squaredSizeIntern5(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight2Intern
        // local radius_squared = ( center .. center ) - 2 * ( ( no_ni .. ( no ^ blade ) ) + ( center .. normal ) * center ) * normal
	CGAMultivector normal = attitudeIntern2();
        CGAMultivector center = locationIntern2();
        CGAMultivector o = createOrigin(1d);
        CGAMultivector no_ni = o.op(inf);
        CGAMultivector result = center.ip(center).sub((no_ni.ip(o.op(this.gp(1d/weight2()))).add((o.ip(normal).gp(center)))).gp(2d).gp(normal));
        return new CGAScalar(result);
    }
    
    @Override
    public CGAScalar squaredSizeIntern3(){
        // change sign following Hitzer2005
        return new CGAScalar(super.squaredSizeIntern3().negate());
    }
    
    /**
     * Determine attitude by extraction from bivector^inf representation. 
     * 
     * @return attutude as euclidean vector
     */
    @Override
    public Vector3d attitude(){
        CGAAttitude result = attitudeIntern();
        //System.out.println("attitude="+result.toString());
        return result.extractAttitudeFromBivectorEinfRepresentation(); //extractE3ToVector3d();
    }
    
    @Override
    public CGAOrientedCircleOPNS undual(){
        return new CGAOrientedCircleOPNS(impl.dual().gp(-1));
    }
}
