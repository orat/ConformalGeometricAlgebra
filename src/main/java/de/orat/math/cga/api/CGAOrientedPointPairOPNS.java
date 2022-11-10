package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * A point-pair (0-sphere) in outer product null space representation (grade 2 
 * multivector), corresponding to direct point-pair in Dorst2007.
 * 
 * no^e1,    no^e2,    no^e3,    e1^e2,    e2^e3,    e3^e1,    e1^ni,    e2^ni,    
 * e3^ni,    no^ni
 * 
 * This corresponds to a sphere in a line, the set of points with an equal distance
 * to the center of the point-pair.
 * 
 * Point pairs are the only rounds for which one can retrieve the points that 
 * constitutes them.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointPairOPNS extends CGAOrientedFiniteRoundOPNS implements iCGABivector {
    
    public CGAOrientedPointPairOPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedPointPairOPNS(iCGAMultivector impl){
        super(impl);
    }
    /**
     * Create point pair in outer product null space representation 
     * (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     */
    public CGAOrientedPointPairOPNS(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        this(create(point1, point2));
    }
    
    /**
     * Create point pair in outer product null space representation (grade 2 multivector).
     * 
     * @param point1
     * @param weight1
     * @param point2
     * @param weight2
     */
    public CGAOrientedPointPairOPNS(Point3d point1, double weight1, Point3d point2, double weight2){
        //this((new CGARoundPointIPNS(point1)).op(new CGARoundPointIPNS(point2)).gp(weight));
        this(create(new CGARoundPointIPNS(point1, weight1), new CGARoundPointIPNS(point2, weight2)));
    }
    
    private static CGAMultivector create(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        return point1.op(point2);
    }
    
    @Override
    public CGAOrientedPointPairIPNS dual(){
        return new CGAOrientedPointPairIPNS(impl.dual());
    }
    
    
    // decomposition
    
    /**
     * Specific implementation because generic implementation for all rounds
     * does not work.
     * 
     * @return squared size/radius
     */
    @Override
    public double squaredSize(){
        //return this.dual().squaredSize();
        return super.squaredSize();
    }
    
    public Point3d[] decomposePoints(){
        Point3d[] result = new Point3d[2];
        CGAScalar sqrt = new CGAScalar(this.ip(this)).sqrt();
        
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(this.add(new CGAScalar(sqrt)).div(CGAMultivector.createInf(1d).ip(this)).compress());
        System.out.println(p1.toString("p1"));
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(this.sub(new CGAScalar(sqrt)).div(CGAMultivector.createInf(1d).ip(this)).compress());
        System.out.println(p2.toString("p2"));
        
        result[0] = p1.location();
        result[1] = p2.location();
        return result;
    }
}
