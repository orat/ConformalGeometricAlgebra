package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Circle in inner product null space represenation (grade 2)
 * corresponding to dual circle in [Dorst2007].
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGACircleIPNS extends CGARoundIPNS implements iCGABivector {
    
    public CGACircleIPNS(CGAMultivector m){
        super(m);
    }
    protected CGACircleIPNS(iCGAMultivector impl){
        super(impl);
    }
    public CGACircleIPNS(double[] values){
        super(values);
    }
    
    
    public static boolean typeof(CGAMultivector m){
       boolean result = CGARoundIPNS.typeof(m);
       // TODO weitere Tests
       return result;
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
     * @param center center of the circle
     * @param normal normal vector of the carrier plane of the circle
     * @param radius imaginary circle if radius<0
     * @param weight weight
     */
    public CGACircleIPNS(Point3d center, Vector3d normal, double radius, double weight){
        this(create(center, normal, radius, weight));
        //this((new CGASphereIPNS(center, radius, 1d)).op(new CGAPlaneIPNS(center, normal, 1d)).gp(weight));
    }
    /**
     * Create a circle in inps respresentation.
     *
     * @param center
     * @param normal
     * @param radius imaginary circle if radius<0
     * @param weight
     * @return multivector representing a circle ipns object
     */
    private static CGAMultivector create(Point3d center, Vector3d normal, 
                                         double radius, double weight){
        // Formula corresponding to cgaLua pdf documentation
        // ε₀∧nn+(x⋅nn)E₀+x∧nn+((x⋅nn)x-0.5(x²-r²)nn)∧εᵢ
        
        double r2 = radius*radius;
        if (radius<0){
            r2 = -r2;
        }
        // CGA lua code
        // local blade = weight * ( no ^ normal + ( center .. normal ) * no_ni + center ^ normal +
        // ( ( center .. normal ) * center - 0.5 * ( ( center .. center ) - sign * radius * radius ) * normal ) ^ ni )
        CGAEuclideanVector x = new CGAEuclideanVector(center);
        CGAEuclideanVector n = new CGAEuclideanVector(normal);
        CGAScalarOPNS sr2= new CGAScalarOPNS(r2);
        
        CGAMultivector a = o.op(n).add(x.ip(n).gp(I0)).add(x.op(n));
        CGAMultivector b = x.ip(n).gp(x);
        CGAMultivector c = x.sqr().sub(sr2).gp(n).gp(0.5);
	return a.add((b.sub(c)).op(inf)).gp(weight);
    }
    
    public CGACircleIPNS(Point3d center, Vector3d normal, double radius){
         this(center, normal, radius, 1d);
    }
    /**
     * Create a circle by intersection of two spheres.
     * 
     * @param sphere1 first sphere
     * @param sphere2 second sphere
     */
    public CGACircleIPNS(CGASphereIPNS sphere1, CGASphereIPNS sphere2){
        this(sphere1.op(sphere2));
    }
   
    
    // etc
    
    @Override
    public CGACircleOPNS undual(){
        return new CGACircleOPNS(super.undual().compress()); //impl.dual().gp(-1));
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
        // weight2Intern (CGACircleIPNS) = (1.9999999999999991*eo^e1^ei)
        return Math.abs(result.norm());
    }
    
    /**
     * Determine location as E3 vector.
     * 
     * @return location as euclidean-vector
     */
    public CGAEuclideanVector locationIntern2(){
        CGAMultivector no_ni = createOrigin(1d).op(inf);
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) )
	CGAMultivector result = attitudeIntern2().negate().gp(no_ni.ip(
                this.gp(1d/weight2()).op(createOrigin(1d).gp(inf))));
        System.out.println(result.toString("locationIntern2 (CGAOrientedCircleIPNS)"));
        return new CGAEuclideanVector(result);
    }
    
    /**
     * Determination of the squared size/radius. 
     * 
     * scheint nicht zu funktionieren
     * 
     * @return squared size/radius squared
     */
    public CGAScalarOPNS squaredSizeIntern5(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight2Intern
        // local radius_squared = ( center .. center ) - 2 * ( ( no_ni .. ( no ^ blade ) ) + ( center .. normal ) * center ) * normal
	CGAMultivector normal = attitudeIntern2();
        CGAMultivector center = locationIntern2();
        CGAMultivector o = createOrigin(1d);
        CGAMultivector no_ni = o.op(inf);
        CGAMultivector result = center.ip(center).sub((no_ni.ip(o.op(this.gp(1d/weight2()))).add((o.ip(normal).gp(center)))).gp(2d).gp(normal));
        return new CGAScalarOPNS(result);
    }
    
    @Override
    public CGAScalarOPNS squaredSizeIntern3(){
        // change sign following Hitzer2005
        return new CGAScalarOPNS(super.squaredSizeIntern3().negate());
    }
    
    /**
     * WORKAROUND 
     * by implementation following Spencer because generic version implemented
     * in CGARoundIPNS does not work.
     * 
     * @return attitude as euclidean vector
     */
    @Override
    public Vector3d attitude(){
        return attitudeIntern2().direction();
        //System.out.println("attitude="+result.toString());
    }
    @Override
    public CGAAttitudeBivectorOPNS attitudeIntern(){
        //FIXME
        // scheint das falsche Vorzeichen zu liefern, um 90 grad gekippten Normalenvektor
        return new CGAAttitudeBivectorOPNS(super.attitudeIntern());
    }
    /**
     * Determine the attitude (normal vector of the carrier plane).
     * 
     * TODO
     * Vorzeichen passt zu carrierFlat aber value schein normalisiert zu sein
     * 
     * @Deprecated 
     * @return normalized attitude as (E3) 1-vector
     */
    public CGAEuclideanVector attitudeIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // CGAUtil.lua l.366
        // blade = blade / weight2
	// local normal = -no_ni .. ( blade ^ ni )
        CGAMultivector result = 
                createOrigin(-1d).op(inf).ip(this.gp(1d/weight2()).op(inf)).compress();
        System.out.println(result.toString("attitudeIntern2 (CGACircleIPNS)"));
        return new CGAEuclideanVector(result);
    }
}
