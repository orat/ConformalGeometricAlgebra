package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A point-pair (0-sphere) in outer product null space representation (grade 2), 
 * corresponding to direct point-pair in [Dorst2007].
 * 
 * no^e1,    no^e2,    no^e3,    e1^e2,    e2^e3,    e3^e1,    e1^ni,    e2^ni,    
 * e3^ni,    no^ni<p>
 * 
 * This corresponds to a sphere in a line, the set of points with an equal distance
 * to the center of the point-pair.<p>
 * 
 * Point pairs are the only rounds for which one can retrieve the points that 
 * constitutes them.<p>
 * 
 * A point-pair can be imaginary. In this case it does not contain points.
 * An imaginary point-pair is equal a ipns circle, it is equal the wedge/outer product
 * of a ipns sphere with an orthogonal ipns plane. Orthogonality of plane and sphere
 * is given if the inner product of both is 0.<p>
 * 
 * A point pair with with its corresponding squared sphere = 0, is tangent vector.<p>
 * 
 * A point-pair be used as a line-segment.<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAPointPairOPNS extends CGARoundOPNS implements iCGABivector, iCGAPointPair {
    
    public CGAPointPairOPNS(CGAMultivector m){
        super(m);
    }
    CGAPointPairOPNS(iCGAMultivector impl){
        super(impl);
    }
    public CGAPointPairOPNS(double[] values){
        super(values);
    }
    
    // ungetested
    CGAPointPairOPNS(Point3d c, Vector3d v, double r){
        super(create(c, new CGAEuclideanVector(v), r).impl);
    }
    
    // composition
    
    /**
     * Create a (non-normalized) point pair in outer product null space representation 
     * (grade 2).
     * 
     * The point pair has a direction from point2 to point1, corresponding to
     * [Hitzer2005]. This corresponds to the line defined by two points in outer
     * product null space representation.
     * 
     * @param point1
     * @param point2
     */
    public CGAPointPairOPNS(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        this(create(point1, point2));
    }
    
    /**
     * Create (non-normalized) point pair in outer product null space 
     * representation (grade 2).
     * 
     * The point pair has a direction from point2 to point1, corresponding to
     * [Hitzer2005]. This corresponds to the line defined by two points in outer
     * product null space representation.
     *
     * @param point1
     * @param weight1
     * @param point2
     * @param weight2
     * 
     */
    public CGAPointPairOPNS(Point3d point1, double weight1, Point3d point2, double weight2){
        this(create(new CGARoundPointIPNS(point1, weight1), new CGARoundPointIPNS(point2, weight2)));
    }
    /**
     * Create a normalized point-pair in outer product null space representation
     * (grade 2).
     * 
     * The direction of the point-pair is defined from point-2 to point-1.
     * 
     * @param point1
     * @param point2 
     */
    public CGAPointPairOPNS(Point3d point1, Point3d point2){
        this(create(new CGARoundPointIPNS(point1), new CGARoundPointIPNS(point2)).normalize());
    }
    
    /**
     * Composition of a (non-normalized) point-pair based on two rounds-points.
     * 
     * The direction of the point-pair is defined from point-2 to point-1.
     * 
     * @param point1
     * @param point2
     * @return non-normalized point-pair
     */
    private static CGAMultivector create(CGARoundPointIPNS point1, CGARoundPointIPNS point2){
        //FIXME normalize() führt zu einem Fehler im Test "testDorst2007DrillsPointPairs
        return point1.op(point2);//.normalize();
    }
    
    
    // etc
    
    @Override
    public CGAPointPairIPNS dual(){
        return new CGAPointPairIPNS(impl.dual());
    }
    
    
    // decomposition
    
    /*@Override
    public Vector3d attitude(){
        CGAAttitudeOPNS result = attitudeIntern();
        // e.g. 0.24011910999709996*e1^e3^ei - 0.3999998699932229*e2^e3^ei
        System.out.println("attitude (CGAOrientedPointPairOPNS)="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }*/
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(attitudeFromTangentAndRound2(false));
    }
    // funktioniert das auch mit imaginären Punktepaaren?
    //TODO
    // vermutlich nein!
    public PointPair decomposePoints(){
        CGAScalarOPNS sqrt = new CGAScalarOPNS(this.ip(this).compress()).sqrt();
        // following Fernandes (Formelsammlung, attachement)
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(this.sub(sqrt).div(inf.negate().ip(this)).compress());
        System.out.println(p2.toString("p2"));
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(this.add(sqrt).div(inf.negate().ip(this)).compress());
        System.out.println(p1.toString("p1"));
        return new PointPair(p1.location(), p2.location());
    }
}
