package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createE3;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A point-pair (0-sphere) in inner product null space representation 
 * (grade 3 multivector), corresponding to dual point-pair in Dorst2007.
 * 
 * This corresponds to a sphere in a line, the set of point with an equal distance
 * to the center of the point-pair.
 * 
 * Point pairs are the only rounds for which one can retrieve the points that 
 * constitutes them.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointPairIPNS extends CGAOrientedFiniteRoundIPNS implements iCGATrivector  {
    
    public CGAOrientedPointPairIPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedPointPairIPNS(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create point-pair in inner product null space representation 
     * (grade 3 multivector).
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * or Dorst2007
     *
     * @param sphere1
     * @param sphere2
     * @param sphere3
     */
    public CGAOrientedPointPairIPNS(CGASphereIPNS sphere1, CGASphereIPNS sphere2, CGASphereIPNS sphere3){
        this(sphere1.op(sphere2).op(sphere3));
    }
    public CGAOrientedPointPairIPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        this(p1.op(p2).dual());
    }
    
    /**
     * Composition of a point pair in IPNS representation from euclidean parameters.
     * 
     * Implementation follows:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * To determine the formula the formula of a sphere on the left and the formula
     * of a line one the right side of an outer product is used. This makes an
     * implicit choice which affects the sign of the weight.
     * 
     * @param c center
     * @param n normal
     * @param r radius
     * @param weight 
     * @param real true for a real point-pair else for a imaginary point pair
     */
    public CGAOrientedPointPairIPNS(Point3d c, Vector3d n, double r, double weight, boolean real){
        this(createCGAMultivector(c,n,r,weight, real));
    }
    
    // The given multivector m is not of grade 3! 4.5*e1^e2^ei - 3.0*eo^e1^e2^ei
    // TODO Es wäre auch zu versuchen die Implementierung nach den Formeln von
    // Hitzer vorzunehmen.
    private static CGAMultivector createCGAMultivector(Point3d center, Vector3d normal, 
            double r, double weight, boolean sign){
        CGAMultivector o = createOrigin(1d);
        CGAMultivector no_inf = o.op(inf);
        CGAMultivector c = createE3(center);
        CGAMultivector n = createE3(normal);
        CGAMultivector sr2;
        if (sign){
            sr2 = new CGAScalar(-r*r);
        } else {
            sr2 = new CGAScalar(r*r);
        }
        // code scheint nicht mit der Formel im pdf übereinzustimmen
        // (das erste "-" ist im pdf ein "+"
        // local blade = weight * ( no ^ normal + center ^ normal ^ no_ni - ( center .. normal ) -
        //( ( center .. normal ) * center - 0.5 * ( ( center .. center ) + sign * radius * radius ) * normal ) ^ ni ) * i
        

        //return o.op(n).add(c.op(n).op(no_inf).sub(c.ip(n))).sub(c.ip(n).gp(c).
        //        sub(c.ip(c).add(sr2)).gp(n).gp(0.5d)).op(inf)
        //        .gp(createI3()).gp(weight);
        
        CGAMultivector a =  o.op(n).add(c.op(n).op(no_inf)).sub(c.ip(n));
        CGAMultivector b = c.ip(n).gp(c);
        CGAMultivector d = c.ip(c).add(sr2).gp(0.5).gp(n);
        CGAMultivector result = a.sub(b.sub(d).op(inf)).gp(weight).gp(createI3());
        return result;
    }
    @Override
    public CGAOrientedPointPairOPNS undual(){
        return new CGAOrientedPointPairOPNS(impl.dual().gp(-1));
    }
    
    
    // decomposition
    
    /**
     * Determine weight without a probe point and without determination of the
     * attitute.The sign can not be determined.
     * 
     * Implementation is different to circle in IPNS representation and also 
     * different to sphere in IPNS representation.
     * 
     * @return weight of the corresponding geometric object
     */
    public double weightIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local weight = ( #( ( no_ni .. ( blade ^ ni ) ) * i ) ):tonumber()
        return (createOrigin(1d).op(inf).ip(this.op(inf))).gp(createI3()).norm();
    }
    
    
    /**
     * Determine the attitude.
     * 
     * @Deprecated
     * @return attitude
     */
    public CGAE3Vector attitudeIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight
        // local normal = -( no_ni .. ( blade ^ ni ) ) * i
        return new CGAE3Vector(createOrigin(1d).op(inf).ip(this.gp(1d/weightIntern2()).op(inf)).gp(createI3().negate()).compress());
    }
    
    /**
     * Determines the center of the point-pair as the mid-point of the two points.
     *
     * @return location
     */
    public CGAE3Vector locationIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        //blade = blade / weight
	//local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) ) * i
        double weight = weightIntern2();
        CGAMultivector no = createOrigin(1d);
        CGAMultivector no_ni = no.op(inf);
        CGAMultivector result = attitudeIntern2().negate().gp(no_ni.ip(this.gp(1d/weight).op(no.gp(inf)))).gp(createI3());
        System.out.println(result.toString("locationIntern2 (CGAOrientedPointPairIPNS)"));
        return new CGAE3Vector(result);
    }
    
    public Point3d[] decomposePoints(){
        return this.undual().decomposePoints();
    }
    
    /**
     * Specific implementation, because generic implementation for all rounds
     * does not work.
     * 
     * @return squaredSize/squaredRadius
     */
    public CGAScalar squaredSizeIntern5(){
        // It must be non-zero and of grade 3
        // CGAUtil.lua l.293 based on center and normal
        //blade = blade / weight
        double weight = weightIntern2();
        System.out.println("weightIntern2 (squaredSizeIntern2, CGAOrientedPointPairIPNS)="+
                String.valueOf(weight));
        CGAMultivector blade = this.gp(1d/weight);
        System.out.println(blade.toString("CGAOrientedPointPairIPNS input to determine squaredRadius"));
        CGAMultivector no = CGAMultivector.createOrigin(1d);
        CGAMultivector no_ni = no.op(inf);
        CGAMultivector center = locationIntern2();
        CGAMultivector normal = attitudeIntern2();
        // local radius_squared = -( center .. center ) + 
        // 2 * ( ( no_ni .. ( no ^ blade ) ) * i + ( center .. normal ) * center ) * normal
        CGAMultivector result =  center.ip(center).negate().add(
                no_ni.ip(no.op(blade)).gp(CGAMultivector.createI3()).
                        add(center.ip(normal).gp(center)).gp(2d).gp(normal));
        System.out.println(result.toString("squaredSizeIntern (CGAOrientedPointPairIPNS, Spencer)"));
        return new CGAScalar(result);
    }
}