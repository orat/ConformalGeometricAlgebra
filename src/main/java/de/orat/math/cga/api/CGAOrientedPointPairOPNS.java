package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

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
public class CGAOrientedPointPairOPNS extends CGAOrientedFiniteRoundOPNS implements iCGABivector, iCGAPointPair {
    
    public CGAOrientedPointPairOPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedPointPairOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
    /**
     * Create point pair in outer product null space representation 
     * (grade 2 multivector).
     * 
     * The point pair has a direction from point2 to point1, corresponding to
     * [Hitzer2005]. This corresponds to the line defined by two points in outer
     * product null space representation.
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
     * The point pair has a direction from point2 to point1, corresponding to
     * [Hitzer2005]. This corresponds to the line defined by two points in outer
     * product null space representation.
    
     * @param point1
     * @param weight1
     * @param point2
     * @param weight2
     */
    public CGAOrientedPointPairOPNS(Point3d point1, double weight1, Point3d point2, double weight2){
        this(create(new CGARoundPointIPNS(point1, weight1), new CGARoundPointIPNS(point2, weight2)));
    }
    
    private static CGAMultivector create(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        return point1.op(point2);
    }
    
    
    // etc
    
    @Override
    public CGAOrientedPointPairIPNS dual(){
        return new CGAOrientedPointPairIPNS(impl.dual());
    }
    
    
    // decomposition
    
    @Override
    public Vector3d attitude(){
        CGAAttitudeOPNS result = attitudeIntern();
        System.out.println("attitude (CGAOrientedPointPairOPNS)="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }
    
    public PointPair decomposePoints(){
        //Point3d[] result = new Point3d[2];
        CGAScalarOPNS sqrt = new CGAScalarOPNS(this.ip(this).compress()).sqrt();
        // following Fernandes (Formelsammlung, attachement)
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(this.sub(sqrt).div(CGAMultivector.createInf(-1d).ip(this)).compress());
        System.out.println(p2.toString("p2"));
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(this.add(sqrt).div(CGAMultivector.createInf(-1d).ip(this)).compress());
        System.out.println(p1.toString("p1"));
        
        //result[0] = p1.location();
        //result[1] = p2.location();
        return new PointPair(p1.location(), p2.location());
    }
}
