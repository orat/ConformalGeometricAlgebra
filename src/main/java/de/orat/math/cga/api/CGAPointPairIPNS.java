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
public class CGAPointPairIPNS extends CGARoundIPNS implements iCGATrivector, iCGAPointPair  {
    
    public CGAPointPairIPNS(CGAMultivector m){
        super(m);
    }
    CGAPointPairIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
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
    public CGAPointPairIPNS(CGASphereIPNS sphere1, CGASphereIPNS sphere2, CGASphereIPNS sphere3){
        this(sphere1.op(sphere2).op(sphere3));
    }
    public CGAPointPairIPNS(CGARoundPointIPNS p1, CGARoundPointIPNS p2){
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
     * @param r radius > 0
     * @param weight 
     * @param real true for a real point-pair else for a imaginary point pair
     */
    /*public CGAPointPairIPNS(Point3d c, Vector3d n, double r, double weight, boolean real){
        this(createCGAMultivector(c,n,r,weight, real));
    }
    public CGAPointPairIPNS(Point3d c, Vector3d n, double r, boolean real){
        this(createCGAMultivector(c,n,r,1d, real));
    }*/
    /**
     * Composition of a point pair by its center, radius and direction in ipns
     * representation.
     * 
     * @param c center of the point pair
     * @param n direction of the line defined by the point pair
     * @param r radius, r<=imaginary point pair
     * @param weight 
     */
    public CGAPointPairIPNS(Point3d c, Vector3d n, double r, double weight){
        this(createCGAMultivector(c,n,r,weight));
    }
    public CGAPointPairIPNS(Point3d c, Vector3d n, double r){
        this(createCGAMultivector(c,n,r,1d));
    }
    
    // The given multivector m is not of grade 3! 4.5*e1^e2^ei - 3.0*eo^e1^e2^ei
    // TODO Es wäre auch zu versuchen die Implementierung nach den Formeln von
    // Hitzer vorzunehmen.
    private static CGAMultivector createCGAMultivector(Point3d center, Vector3d normal, 
            double r, double weight, boolean sign){
        CGAMultivector no_inf = o.op(inf);
        CGAMultivector c = createE3(center);
        CGAMultivector n = createE3(normal);
        CGAMultivector sr2;
        if (sign){
            sr2 = new CGAScalarOPNS(-r*r);
        } else {
            sr2 = new CGAScalarOPNS(r*r);
        }
        // code scheint nicht mit der Formel im pdf übereinzustimmen
        // (das erste "-" ist im pdf ein "+"
        // local blade = weight * ( no ^ normal + center ^ normal ^ no_ni - ( center .. normal ) -
        //( ( center .. normal ) * center - 0.5 * ( ( center .. center ) + sign * radius * radius ) * normal ) ^ ni ) * i
        

        //return oo.op(n).add(c.op(n).op(no_inf).sub(c.ip(n))).sub(c.ip(n).gp(c).
        //        sub(c.ip(c).add(sr2)).gp(n).gp(0.5d)).op(inf)
        //        .gp(createI3()).gp(weight);
        
        CGAMultivector a =  o.op(n).add(c.op(n).op(no_inf)).sub(c.ip(n));
        CGAMultivector b = c.ip(n).gp(c);
        CGAMultivector d = c.ip(c).add(sr2).gp(0.5).gp(n);
        CGAMultivector result = a.sub(b.sub(d).op(inf)).gp(weight).gp(createI3());
        return result;
    }
    /**
     * Create point pair in ipns representation based on euclidean objects.
     * 
     * @param center
     * @param normal
     * @param r >0 for real pointpair, <0 for imaginary pointpait
     * @param weight
     * @return 
     */
    private static CGAMultivector createCGAMultivector(Point3d center, Vector3d normal, 
            double r, double weight){
        CGAMultivector c = createE3(center);
        CGAMultivector n = createE3(normal);
        CGAMultivector sr2;
        
        if (r<0){
            sr2 = new CGAScalarOPNS(-r*r);
        } else {
            sr2 = new CGAScalarOPNS(r*r);
        }
        // code scheint nicht mit der Formel im pdf übereinzustimmen
        // (das erste "-" ist im pdf ein "+"
        // local blade = weight * ( no ^ normal + center ^ normal ^ no_ni - ( center .. normal ) -
        //( ( center .. normal ) * center - 0.5 * ( ( center .. center ) + sign * radius * radius ) * normal ) ^ ni ) * i
        

        //return oo.op(n).add(c.op(n).op(no_inf).sub(c.ip(n))).sub(c.ip(n).gp(c).
        //        sub(c.ip(c).add(sr2)).gp(n).gp(0.5d)).op(inf)
        //        .gp(createI3()).gp(weight);
        
        CGAMultivector a =  o.op(n).add(c.op(n).op(I0)).sub(c.ip(n));
        CGAMultivector b = c.ip(n).gp(c);
        CGAMultivector d = c.sqr().add(sr2).gp(0.5).gp(n);
        CGAMultivector result = a.sub(b.sub(d).op(inf)).gp(weight).gp(createI3());
        return result;
    }
    
    // etc
    
    @Override
    public CGAPointPairOPNS undual(){
        return new CGAPointPairOPNS(super.undual().compress());//impl.dual().gp(-1));
    }
    
    
    // decomposition
    
    /**
     * Determine weight without a probe point and without determination of the
     * attitute.
     * 
     * The sign can not be determined.
     * 
     * Implementation is different to circle in IPNS representation and also 
     * different to sphere in IPNS representation.
     * 
     * @Deprecated
     * @return weight of the corresponding geometric object
     */
    public double weightIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // local weight = ( #( ( no_ni .. ( blade ^ ni ) ) * i ) ):tonumber()
        return (o.op(inf).ip(this.op(inf))).gp(I3).norm();
    }
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(attitudeFromTangentAndRound2(true));
    }
    
    /**
     * Determine the attitude.
     * 
     * @Deprecated
     * @return normalized attitude as (E3) 1-vector
     */
    public CGAEuclideanVector attitudeIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        // blade = blade / weight
        // local normal = -( no_ni .. ( blade ^ ni ) ) * i
        return new CGAEuclideanVector(o.op(inf).ip(this.gp(1d/weightIntern2()).op(inf)).gp(createI3().negate()).compress());
    }
    
    /**
     * Determines the center of the point-pair as the mid-point of the two points.
     *
     * @return location
     */
    public CGAEuclideanVector locationIntern2(){
        // Implementation following:
        // https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
        //blade = blade / weight
	//local center = -normal * ( no_ni .. ( blade ^ ( no * ni ) ) ) * i
        double weight = weightIntern2();
        CGAMultivector no = createOrigin(1d);
        CGAMultivector no_ni = no.op(inf);
        CGAMultivector result = attitudeIntern2().negate().gp(no_ni.ip(this.gp(1d/weight).op(no.gp(inf)))).gp(createI3());
        System.out.println(result.toString("locationIntern2 (CGAOrientedPointPairIPNS)"));
        return new CGAEuclideanVector(result);
    }
    
    public iCGAPointPair.PointPair decomposePoints(){
        return this.undual().decomposePoints();
    }
    
    /**
     * Specific implementation, because generic implementation for all rounds
     * does not work.
     * 
     * @return squaredSize/squaredRadius
     */
    public CGAScalarOPNS squaredSizeIntern5(){
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
        return new CGAScalarOPNS(result);
    }
}