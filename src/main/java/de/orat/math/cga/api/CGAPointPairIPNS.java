package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createE3;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Point-pair in inner product null space representation 
 * (grade 3 multivector), corresponding to dual point-pair in Dorst2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPointPairIPNS extends CGARoundIPNS implements iCGATrivector  {
    
    public CGAPointPairIPNS(CGAMultivector m){
        super(m);
    }
    CGAPointPairIPNS(iCGAMultivector impl){
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
    public CGAPointPairIPNS(CGASphereIPNS sphere1, CGASphereIPNS sphere2, CGASphereIPNS sphere3){
        this(sphere1.op(sphere2).op(sphere3));
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
    public CGAPointPairIPNS(Point3d c, Vector3d n, double r, double weight, boolean sign){
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
    public CGAPointPairOPNS dual(){
        return new CGAPointPairOPNS(impl.dual());
    }
    
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     */
    private double weight(){
        // local weight = ( #( ( no_ni .. ( blade ^ ni ) ) * i ) ):tonumber()
        return (createOrigin(1d).op(createInf(1d)).ip(this.op(createInf(1d)))).gp(createE3Pseudoscalar()).scalarPart();
    }
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
        // local normal = -( no_ni .. ( blade ^ ni ) ) * i
        return createOrigin(1d).op(createInf(1d)).ip(this.gp(1d/weight()).op(createInf(1d))).gp(createE3Pseudoscalar().gp(-1d));
    }
    
    /**
     * Determines the center of the point-pair as the mid-point of the two points.
     * 
     * Determine a point on the line which has the closest distance to the origin.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return location
     */
    @Override
    public Point3d location(){
        // local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) ) * i
        CGAMultivector no_ni = createOrigin(1d).op(createInf(1d));
        CGAMultivector result = attitudeIntern().gp(no_ni.ip(this.op(no_ni))).gp(createE3Pseudoscalar());
        return result.extractE3ToPoint3d();
    }
}
