package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createE3;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A point-pair (0-sphere) in inner product null space representation 
 * (grade 3), corresponding to dual point-pair in [Dorst2007].
 * 
 * This corresponds to a sphere in a line, the set of point with an equal distance
 * to the center of the point-pair.<p>
 * 
 * Point pairs are the only rounds, for which one can retrieve the points that 
 * constitutes them.<p>
 * 
 * A point-pair be used as a line-segment.<p>
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
    public CGAPointPairIPNS(double[] values){
        super(values);
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
     * Composition of a point pair by its center, radius and direction in ipns
     * representation.
     * 
     * @param c center of the point pair
     * @param n direction of the line defined by the point pair from point-2 to point-1
     * @param r radius, r<0: imaginary point pair
     * @param weight 
     */
    public CGAPointPairIPNS(Point3d c, Vector3d n, double r, double weight){
        this(CGAPointPairIPNS.create(c,n,r,weight));
        // das könnte ich hier auch auf create2 umstellen. Dazu muss ich create2()
        // nur um einen weight-Parameter ergänzen
        //TODO
    }
    public CGAPointPairIPNS(Point3d c, Vector3d n, double r){
        // following Lua code
        //this(CGAPointPairIPNS.create(c,n,r,1d));
        // following generic dual round composition from [Dorst2009].
        // scheint zu gleichem Ergebnis wie die Lua-based implementation zu führen
        this(CGAPointPairIPNS.create2(c,n,r));
    }
    
    /**
     * Implementation follows:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * To determine the formula the formula of a sphere on the left and the formula
     * of a line one the right side of an outer product is used. This makes an
     * implicit choice which affects the sign of the weight.
     */
    // The given multivector m is not of grade 3! 4.5*e1^e2^ei - 3.0*eo^e1^e2^ei
    // TODO Es wäre auch zu versuchen die Implementierung nach den Formeln von
    // Hitzer vorzunehmen.
    @Deprecated
    private static CGAMultivector create(Point3d center, Vector3d normal, 
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
     * Create point-pair in ipns representation based on euclidean objects.
     * 
     * Implementation follows:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf<p>
     * 
     * The implementation is based on the outer product of a sphere with a line
     * through the origin of the sphere.<p>
     * 
     * TODO
     * Die Multiplikation des Ausdrucks mit "weight" ist nur korrekt, wenn der Term
     * vorher normalisiert ist. Es ist aber noch nicht überprüft, ob er implizit bereits normal
     * ist. Vermutlich stimmt es aber mit der impliziten Normalisierung, denn n und
     * c sollten normalisiert sein. <p>
     * 
     * @param center
     * @param normal vector (normalization not needed, from point-2 to point-1)
     * @param r >0 for real point-pair, <0 for imaginary point-pair
     * @param weight
     * @return point pair in ipns representation
     */
    private static CGAMultivector create(Point3d center, Vector3d normal, 
            double r, double weight){
        CGAMultivector c = createE3(center);
        Vector3d normalizedNormal = new Vector3d(normal);
        normalizedNormal.normalize();
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
        // FIXME component e123 scheint falches Vorzeichen zu haben --> das "+" im pdf ist also richtig
        //CGAMultivector a =  o.op(n).add(c.op(n).op(I0)).sub(c.ip(n));
        CGAMultivector a =  o.op(n).add(c.op(n).op(I0)).add(c.ip(n));
        CGAMultivector b = c.ip(n).gp(c);
        CGAMultivector d = c.sqr().add(sr2).gp(0.5).gp(n);
        //FIXME braucht es das normalize() überhaupt? nein!
        //CGAMultivector result = a.sub(b.sub(d).op(inf)).gp(I3).normalize().gp(weight);
        CGAMultivector result = a.sub(b.sub(d).op(inf)).gp(I3).gp(weight);
        return result;
    }
    
    /**
     * Composition of an ipns point-pair based on [Dorst2009], formula 14_10.
     * 
     * @param center
     * @param normal
     * @param r
     * @return point-pair 
     */
    private static CGAMultivector create2(Point3d center, Vector3d normal, double r){
         return CGARoundIPNS.create(center, new CGAEuclideanVector(normal), r);
    }
    
    
    // etc
    
    @Override
    public CGAPointPairOPNS undual(){
        return new CGAPointPairOPNS(super.undual().compress());//impl.dual().gp(-1));
    }
    
    public CGAPointPairIPNS normalize(){
        return new CGAPointPairIPNS(super.normalize());
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
    /**
     * WORKAROUND
     * by implementation following Spencer because maybe generic version implemented
     * in CGARoundIPNS does not work.
     * 
     * scheint keinen Unterschied zu machen zumindest in der CGAView3dDemo app.
     * 
     * @return 
     */
    /*public double squaredSize(){
        return squaredSizeIntern5().decomposeScalar();
    }*/
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(attitudeFromTangentAndRoundIPNS());
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
        CGAMultivector blade = this.gp(1d/weight);
        CGAMultivector no_ni = o.op(inf);
        CGAMultivector result = attitudeIntern2().negate().gp(no_ni.ip(blade.op(o.gp(inf)))).gp(I3);
        // locationIntern2 (CGAOrientedPointPairIPNS) = (0.22051648227377704*e1 + 
        // 0.3673450407273629*e2 + 0.18356670723359603*e3 + 0.3445510478954946*e1^e2^e3)
        //FIXME der letzte Termin ist falsch
        System.out.println(result.toString("locationIntern2 (CGAOrientedPointPairIPNS)"));
        return new CGAEuclideanVector(result);
    }
    
    public iCGAPointPair.PointPair decomposePoints(){
        return this.undual().decomposePoints();
    }
    
    /**
     * Squared size.
     * 
     * Vielversprechende Implementierung following CGALua.
     * 
     * @return squaredSize/squaredRadius <0 for imaginary point paires, else > 0
     */
    public CGAScalarOPNS squaredSizeIntern5(){
        // It must be non-zero and of grade 3
        // CGAUtil.lua l.293 based on center and normal
        //blade = blade / weight
        double weight = weightIntern2();
        System.out.println("weightIntern2 (squaredSizeIntern2, CGAOrientedPointPairIPNS)="+
                String.valueOf(weight));
        CGAMultivector blade = this.gp(1d/weight);
        CGAMultivector no_ni = o.op(inf);
        CGAMultivector center = locationIntern2();
        CGAMultivector normal = attitudeIntern2();
        // local radius_squared = -( center .. center ) + 
        // 2 * ( ( no_ni .. ( no ^ blade ) ) * i + ( center .. normal ) * center ) * normal
        CGAMultivector result =  center.ip(center).negate().add(
                no_ni.ip(o.op(blade)).gp(CGAMultivector.createI3()).
                        add(center.ip(normal).gp(center)).gp(2d).gp(normal));
        System.out.println(result.toString("squaredSizeIntern (CGAOrientedPointPairIPNS, Spencer)"));
        return new CGAScalarOPNS(result);
    }
}