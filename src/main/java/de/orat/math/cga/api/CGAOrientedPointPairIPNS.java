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
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * FIXME
     * ist das wirklich ein point-pair?
     * 
     * @param c center
     * @param n normal
     * @param r radius
     * @param weight 
     * @param sign 
     */
    public CGAOrientedPointPairIPNS(Point3d c, Vector3d n, double r, double weight, boolean sign){
        this(createCGAMultivector(c,n,r,weight, sign));
    }
    private static CGAMultivector createCGAMultivector(Point3d point, Vector3d normal, 
            double r, double weight, boolean sign){
        CGAMultivector inf=createInf(1d);
        CGAMultivector o = createOrigin(1d);
        CGAMultivector no_inf = o.op(inf);
        CGAMultivector c = createE3(point);
        CGAMultivector n = createE3(normal);
        CGAMultivector sr2;
        if (sign){
            sr2 = new CGAScalar(-r*r);
        } else {
            sr2 = new CGAScalar(r*r);
        }
        // local blade = weight * ( no ^ normal + center ^ normal ^ no_ni - ( center .. normal ) -
        //( ( center .. normal ) * center - 0.5 * ( ( center .. center ) + sign * radius * radius ) * normal ) ^ ni ) * i
        return o.op(n).add(c.op(n).op(no_inf).sub(c.ip(n))).sub(c.ip(n).gp(c).
                sub(c.ip(c).add(sr2)).gp(n).gp(0.5d)).op(inf).gp(createPseudoscalar()).gp(weight);
    }
    @Override
    public CGAOrientedPointPairOPNS undual(){
        return new CGAOrientedPointPairOPNS(impl.undual());
    }
    
    /**
     * Determine weight without a probe point and without determination of the
     * attitute.
     * 
     * Maybe the sign can not be determined. So leave this method private
     */
    private double weight2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local weight = ( #( ( no_ni .. ( blade ^ ni ) ) * i ) ):tonumber()
        return (createOrigin(1d).op(createInf(1d)).ip(this.op(createInf(1d)))).gp(createE3Pseudoscalar()).scalarPart();
    }
    /*@Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }*/
    
    /**
     * Determine the attitude.
     * 
     * @return attitude
     */
    @Override
    protected CGAAttitudeVectorOPNS attitudeIntern(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight
        // local normal = -( no_ni .. ( blade ^ ni ) ) * i
        return new CGAAttitudeVectorOPNS(createOrigin(1d).op(createInf(1d)).ip(
                this.gp(1d/weight2()).op(createInf(1d))).gp(createE3Pseudoscalar().negate()).compress());
    }
    
    /**
     * Determines the center of the point-pair as the mid-point of the two points.
     *
     * @return location
     */
    /*@Override
    public CGARoundPointIPNS locationIntern(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) ) * i
        CGAMultivector no = createOrigin(1d);
        CGAMultivector ni = createInf(1d);
        CGAMultivector no_ni = no.op(ni);
        CGAMultivector result = attitudeIntern().negate().gp(no_ni.ip(
                this.gp(1d/weight2()).op(no.gp(ni)))).gp(createE3Pseudoscalar());
        System.out.println(result.toString("CGAOrientedPointPairIPNS.localIntern"));
        return new CGARoundPointIPNS(result);
    }*/
    
    public Point3d[] decomposePoints(){
        return this.undual().decomposePoints();
    }
    
    /**
     * Specific implementation, because generic implementation for all rounds
     * does not work.
     * 
     * scheint nicht zu funktionieren
     * 
     * @return squaredSize/squaredRadius
     */
    /*@Override
    public double squaredSize(){
        // It must be non-zero and of grade 3
        // CGAUtil.lua l.293 based on center and normal
        //blade = blade / weight
        CGAMultivector blade = this.gp(1d/weight2());
        CGAMultivector no = CGAMultivector.createOrigin(1d);
        CGAMultivector no_ni = no.op(CGAMultivector.createInf(1d));
        CGAMultivector center = locationIntern();
        CGAMultivector normal = attitudeIntern();
        // local radius_squared = -( center .. center ) + 
        // 2 * ( ( no_ni .. ( no ^ blade ) ) * i + ( center .. normal ) * center ) * normal
        return center.ip(center).negate().add(
                no_ni.ip(no.op(blade)).gp(CGAMultivector.createE3Pseudoscalar()).
                        add(center.ip(normal).gp(center)).gp(2d).gp(normal)).scalarPart();
        //FIXME
        // da kommt fälschlicherweise 0 raus
    }*/
}