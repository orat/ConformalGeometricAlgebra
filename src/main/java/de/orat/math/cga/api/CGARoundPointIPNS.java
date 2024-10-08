package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * A round point in inner product null space representation (grade 1), 
 * corresponding to dual round point in [Dorst2007]. 
 * 
 * no, e1, e2, e3, ni<p>
 * 
 * Normalized homogeneous points, or null-vectors, in the conformal model typically
 * have a weight of 1.<p>
 * 
 * In CGA a point can be represented as a round or a flat. The round point is a 
 * sphere with radius 0, a blade with grade 1.<p>
 *
 * The round point is used more often in geometric expressions than the flat 
 * point, since it has nice perpendicularity properties. For example, given 
 * the round points P, Q and R, it can be used for calculating the CGA-object 
 * that is perpendicular to those three points: P ∧ Q ∧ R. This is the
 * circle passing through P, Q and R.<p>
 *
 * They can also be considered as spheres with zero radius. By
 * adding to or subtracting from the weight of the ∞ basis, we can create imaginary or
 * real dual spheres of the from σ = p ± δ ∞ where p is the homogenous center point
 * and δ is the radius of the sphere: by adding δ we create imaginary spheres with a
 * negative squared radius. Finding this squared radius is as simple as squaring the
 * dual sphere: σ 2 = r 2 . What exactly an imaginary sphere is varies from application to
 * application.<p>
 * 
 * Null vectors, or points, in the conformal model have the unique property of 
 * having a zero dot product with themselves: p · p = 0. This interesting result 
 * is part of a more general useful trait: the dot product between any two 
 * normalized points represents the squared Euclidean distance between them: 
 * p · q = δ 2 .<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARoundPointIPNS extends CGARoundIPNS {
    
    public CGARoundPointIPNS(CGAMultivector m) throws IllegalArgumentException {
        super(m.compress());
    }
    public CGARoundPointIPNS(double[] values){
        super(values);
    }
    CGARoundPointIPNS(iCGAMultivector m){
        super(m);
    }
    
    // composition
    
    /**
     * A normalized conformal point (grade 1 multivector) by up-projecting an 
     * euclidian vector into its conformal one.
     * 
     * Inner and outer product null space representation is identical?.<p>
     * 
     * @param p euclidian normalized point
     */
    public CGARoundPointIPNS(Tuple3d p){
        this(create(p, 1d));
        isNormalized = true;
    }
    
    /**
     * A conformal point (grade 1 multivector) by up-projecting an 
     * euclidian vector into a conformal vector.
     * 
     * Inner and outer product null space representation is identical?.<p>
     * 
     * @param p euclidian point
     * @param weight
     */
    public CGARoundPointIPNS(Tuple3d p, double weight){
        this(create(p, weight));
        if (weight == 1d) isNormalized=true;
    }
    
    public CGARoundPointIPNS(CGAEuclideanVector v){
        this(create(v.extractE3ToVector3d(),1d));
    }
    
    /**
     * Create point with given weight.
     * 
     * implementation looks identical with
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @param p euclidian point/vector
     * @param weight
     * @return (weighted) point
     */
    private static CGAMultivector create(Tuple3d p, double weight){
        // old version
        CGAMultivector result = (o
                .add(createEx(p.x))
                .add(createEy(p.y))
                .add(createEz(p.z))
                .add(createInf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)))).gp(weight);
        return result;
        //CGAVectorE3 x = new CGAVectorE3(p);
        //return x.add((new CGAMultivector(0.5)).gp(x.gp(x)).gp(createInf(1d))).add(createOrigin(1d));
    }
   
   
    
    // decomposition
    
    /*@Override
    public EuclideanParameters decompose(){
        return new EuclidianParameters(attitude(), location(), 
                                      0d, squaredWeight());
    }*/
    
    @Override
    public EuclideanParameters decompose(){
        // round point hat kein euclidean direction daher muss ich die gleichnamige
        // methode von roundpoint hiermit überschreiben
        return new EuclideanParameters(null, location(), 
                                      squaredSize(), squaredWeight());
    }
    public Vector3d attitude(){
        throw new RuntimeException("CGARoundPointIPNS does not implement Vector attitude()! Use attitudeRoundPoint instead!");
    }
    // returns the value of e1^e2^e3^einf aber das ist "E dir" representation
    // d.h. inf kommt weg und dann bleibt ein trivector, der irgendwie ein Volumen
    // repräsentiert, das könnte ich mit 4/3pir3 vergleichen, aber gibts da noch
    // ein Vorzeichen?
    public double attitudeRoundPoint(){
        CGAAttitudeTrivectorOPNS att = attitudeIntern();
        System.out.println("CGARoundPointIPNS.att()="+att.toString());
        // für ein Sphere funktioniert direction() nicht, da ich dann nur ein double bekomme
        //o.op(att) damit habe ich vermutlich e1^e2^e3 und jetzt brauche ich noch den
        // Wert dieses Blades
        return att.extractCoordinates()[30];
    }
    /**
     * @return e1^e2^e3^ei
     */
    @Override
    public CGAAttitudeTrivectorOPNS attitudeIntern(){
        return new CGAAttitudeTrivectorOPNS(super.attitudeIntern());
    }
    
    @Override
    public CGARoundPointIPNS locationIntern(){
        return this.normalize();
    }
    
    /*public double squaredWeight(){
        CGAMultivector attitude = determineDirectionFromTangentAndRoundObjectsAsMultivector();
        CGARoundPointIPNS probePoint = new CGARoundPointIPNS(new Point3d(0d,0d,0d));
        return CGAMultivector.squaredWeight(attitude, probePoint);
    }*/
    
    /**
     * Decompose location.
     * 
     * implementation follows
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * 
     * blade = blade / weight
     * local center = no_ni .. ( blade ^ no_ni )
     * 
     * FIXME
     * kann das nicht einfach wieder abschaffen?
     * Die super-Methode sollte doch auch funktionieren
     * 
     * @return location
     */
    @Override
    /*public Point3d location(){
        // weight corresponding to CGAUtil.lua, line 223ff
        // local weight2 = -blade .. ni
        double weight = this.negate().ip(CGAMultivector.createInf(1d)).decomposeScalar();
        //System.out.println("CGARoundPointIPNS.location weight = "+String.valueOf(weight));
        // blade = blade / weight2
        CGAMultivector result = this.gp(1d/weight);
        
        // location corresponding to CGAUtil.lua, line 223ff
        
        //local center = no_ni .. ( blade ^ no_ni )
        CGAMultivector no_inf = createOrigin(1d).op(createInf(1d));
        CGAMultivector resultTest = no_inf.ip(result.op(no_inf));
        System.out.println(resultTest.toString("CGARoundPointIPNS.location decomposeMotor test"));
        
        System.out.println(this.toString("CGARoundPointIPNS.location decomposeMotor"));
        System.out.println(result.toString("CGARoundPointIPNS.location decomposeMotor+normalized"));
        return result.extractE3ToPoint3d();
    }*/
    
    /*public CGAE3Vector locationIntern1(){
        // extract E3 from normalized dual sphere
        // Dorst2007 p.409
        CGAMultivector oinf = CGAMultivector.createOrigin(1d).op(CGAMultivector.createInf(1d));
        CGAMultivector result = oinf.lc(oinf.op(this));
        System.out.println(result.toString("location as E3 (CGAOrientedFiniteFlatIPNS, Dorst)"));
        return new CGAE3Vector(result);
    }*/
    /**
     * Detmerination of the midpoint of the sphere.
     * 
     * @return location origin/midpoint
     */
    /*
    public Point3d location(){
        return locationIntern1().extractE3ToPoint3d();
    }*/
    
    /**
     * Squared size.
     * 
     * @return squared size
     * @Override
     */
    public double squaredSize(){
        return 0d;
    }
    
    
    // etc
    
    /**
     * Normalized round points can be multiplied by scalar factor and 
     * then represent the same point. 
     * 
     * Sometimes a ’unique’ or default representation is required for calculations.<p>
     * 
     * Therefore the point is normalized by the formula P0 :=P/(-∞·P). 
     * This sets the e0 -factor (weight) of the point to 1.<p>
     * 
     * @return normalized point
     */
    @Override
    public CGARoundPointIPNS normalize(){
        CGARoundPointIPNS result = new CGARoundPointIPNS(this.div(inf.negate().ip(this)));
        result.isNormalized = true;
        return result;
    }
    
    /**
     * Determine squared distance.
     * 
     * ganja.js example dual planes/spheres:
     * The distance between two points.
     * var d = (a,b)=>((a<<b).Length*2)**.5;
     * TODO Wie passt das mit der aktuellen Implementierung zusammen?
     * - Was bedeutet .Length? vermutlich decomposeScalar()?
     * - Warum muss hier keine Vorzeichen korrigiert werden. Das wird vermutlich
     * irgendwie mit der method Length() gemacht. 
     * - Vielleicht eine eigene length() method einführen
     *
     * get Length (){ 
     *    return options.over?Math.sqrt(Math.abs(this.Mul(this.Conjugate).s.s)):Math.sqrt(Math.abs(this.Mul(this.Conjugate).s)); 
     * };
     * 
     * @param p second point to determine the distance to
     * @return squared distance to the given point
     */
    public double distSquare(CGARoundPointIPNS p){
        // Implementation following Dorst2008 drills p. 39
        return -2*(this.normalize()).ip(p.normalize()).decomposeScalar();
    }
    
    @Override
    public CGARoundPointOPNS undual(){
        return new CGARoundPointOPNS(super.undual().compress());//impl.dual().gp(-1));
    }
    @Override
    public CGAKVector dual(){
        throw new RuntimeException("The given multivector is ipns-type - dual() is not allowed! Use undual() instead!");
    }
    
    public CGARoundPointIPNS midPoint(CGARoundPointIPNS p2){
        return midPoint(this, p2);
    }
    public static CGARoundPointIPNS midPoint(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
        CGAMultivector m = p1.add(p2).gp(0.5);
        return new CGARoundPointIPNS(m.gp(inf).gp(m).negate().div(m.ip(inf).sqr().gp(2)));
    }
    
    public static boolean is(CGAMultivector m){
        boolean result = CGARoundIPNS.is(m);
        if (!result) return false;
        return m.sqr().isNull();
    }
}
