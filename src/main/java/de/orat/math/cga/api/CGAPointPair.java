package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createE3;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * P-pair in inner product null space representation 
 * (grade 3 multivector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPointPair extends CGARound implements iCGATrivector  {
    
    public CGAPointPair(CGAMultivector m){
        super(m);
    }
    CGAPointPair(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create point-pair in inner product null space representation 
     * (grade 3 multivector).
     * 
     * @param sphere1
     * @param sphere2
     * @param sphere3
     */
    public CGAPointPair(CGASphere sphere1, CGASphere sphere2, CGASphere sphere3){
        this(sphere1.op(sphere2).op(sphere3));
    }
    
    @Override
    public CGADualPointPair dual(){
        return new CGADualPointPair(impl.dual());
    }
    
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     */
    private double weight(){
        // local weight = ( #( ( no_ni .. ( blade ^ ni ) ) * i ) ):tonumber()
        return (createOrigin(1d).op(createInf(1d)).ip(this.op(createInf(1d)))).gp(createE3()).scalarPart();
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
        return createOrigin(1d).op(createInf(1d)).ip(this.gp(1d/weight()).op(createInf(1d))).gp(createE3().gp(-1d));
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
        CGAMultivector result = attitudeIntern().gp(no_ni.ip(this.op(no_ni))).gp(createE3());
        return new Point3d(result.extractEuclidianVector());
    }
    
    @Override
    public boolean isImaginary(){
        throw new RuntimeException("not yet implemented!");
    }
}
