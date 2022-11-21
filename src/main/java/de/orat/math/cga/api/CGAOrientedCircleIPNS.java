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
     * Determination the weight without usage of a probepoint and without 
     * determination of the attitude.
     * 
     * Vermutlich bekommen ich hier das Vorzeichen nicht eindeutig. Damit ist
     * unklar wie das Normalisieren korrekt durchgeführt werden soll.
     * FIXME
     */
    private double weight2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local weight2 = ( #( no_ni .. ( blade ^ ni ) ) ):tonumber()
        //FIXME warum Math.abs()?
        return Math.abs(createOrigin(1d).op(createInf(1d).ip(this.op(createInf(1d)))).scalarPart());
    }
    /**
     * Determination of the squared weight2.
     *
     * @return squared weight2
     */
    /*@Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }*/
    /**
     * Determine the attitude (normal vector of the carrier plane).
     * 
     * @return attitude
     */
    /*@Override
    protected CGAAttitudeBivectorOPNS attitudeIntern(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // CGAUtil.lua l.366
        // blade = blade / weight2
	// local normal = -no_ni .. ( blade ^ ni )
        return new CGAAttitudeBivectorOPNS(
                createOrigin(-1d).op(createInf(1d)).ip(this.gp(1d/weight2()).op(createInf(1d))).compress());
    }*/
    
   
    /**
     * Determine location as round point in ipns representation.
     * 
     * @return location
     */
    /*@Override
    public CGARoundPointIPNS locationIntern(){
        CGAMultivector no_ni = createOrigin(1d).op(createInf(1d));
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) )
	return new CGARoundPointIPNS(attitudeIntern().negate().gp(no_ni.ip(
                this.gp(1d/weight2()).op(createOrigin(1d).gp(createInf(1d))))));
    }*/
    
    /**
     * Determination of the squared size/radius. This is the radiusSquared for a sphere.
     * 
     * scheint nicht zu funktionieren
     * 
     * @return squared size/radius squared
     */
    public double squaredSize2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight2
        // local radius_squared = ( center .. center ) - 2 * ( ( no_ni .. ( no ^ blade ) ) + ( center .. normal ) * center ) * normal
	CGAMultivector normal = attitudeIntern();
        CGAMultivector center = locationIntern();
        CGAMultivector o = createOrigin(1d);
        CGAMultivector inf = createInf(1d);
        CGAMultivector no_ni = o.op(inf);
        CGAMultivector result = center.ip(center).sub((no_ni.ip(o.op(this.gp(1d/weight2()))).add((o.ip(normal).gp(center)))).gp(2d).gp(normal));
        return Math.abs(result.scalarPart());
    }
    
    
    @Override
    public CGAOrientedCircleOPNS dual(){
        return new CGAOrientedCircleOPNS(impl.dual());
    }
}
