package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Weighted rounds are points, point-pairs, circles and spheres/hyper-spheres,
 * here given in outer product null space representation corresponding to direct 
 * round in [Dorst2007].
 * 
 * Rounds are objects with finite areas/volumes/hyperolumes.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARoundOPNS extends CGAKVector implements iCGATangentOrRound {
    
    CGARoundOPNS(CGAMultivector m){
        super(m.impl);
    }
    protected CGARoundOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    // untested
    /**
     * Creates real or imaginary round in outer product null space representation.
     * 
     * corresponding to [Dorst2009] 14.9
     * 
     * @param c
     * @param Ak
     * @return round in opns representation
     */
    static CGAMultivector create(Point3d c, AbstractEuclideanKVector Ak, double r){
        CGARoundPointIPNS cp = new CGARoundPointIPNS(c);
        CGAMultivector result = new CGAMultivector(cp.impl);
        result.add(inf.gp(0.5*r*r));
        return result.op(cp.negate().lc(Ak.gradeInversion().gp(inf)));
    }
    
    // decompose
    
    @Override
    public iCGATangentOrRound.EuclideanParameters decompose(){
        return new iCGATangentOrRound.EuclideanParameters(attitude(), location(), 
                                      squaredSize(), squaredWeight());
    }
    
    /**
     * Determine the carrier flat (euclidean carrier) of a (direkt) round.
     * 
     * The carrier flat is the OPNS subspace representation of the minimal (lowest
     * possible dimension) Euclidean subspace to include the whole geometric object
     * when it is placed at the origin.
     * 
     * The carrier flat is fully position independent.
     * 
     * point pair e.g.  carrierFlat = (-1.9999999999999987*e1 + 1.9999999999999987*e2) mit w=-8
     * carrierFlat ist der Richtungsvektor von p2 nach p1
     * attitude (round/tangent) = (1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei)
     * attitude (CGAOrientedPointPairOPNS)=1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei
     * attitude (pp OPNS) = (1.9999999999999996,-1.9999999999999996,0.0)
     * attitude (round/tangent) = (1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei)
     * r wird zu -1.75 bestimmt statt 0.5
     * 
     * bivector
     * circle e.g. carrierFlat = (0.9999999999999993*e1^e2 - 0.9999999999999993*e1^e3 + 0.9999999999999993*e2^e3)
     * 
     * sphere e.g carrierFlat = (-1.9999999999999987*e1^e2^e3)
     *
     * @return carrier flat (not normalized)
     */
    public CGAKVector carrierFlat(){
        // do not normalize before, so that it is possible to determine the weight
        // as norm of the carrier flat.
        //FIXME eventuell I0.negate() Vorzeichen kontrollieren
        return new CGAKVector(this.op(inf).negate().rc(I0));
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude (dualRound/dualTangent)="+result.toString());
        Vector3d res = result.extractE3ToVector3d();
        res.normalize();
        return res;
    }
    /**
     * @return attitude
     */
    @Override
    protected CGAAttitudeOPNS attitudeIntern(){
        // z.B. -1.9999999999999982*e1^e2^e3^ei also grade 4 und nicht grade 2
        // wenn das von einem CGASphereOPNS aufgerufen wird
        return attitudeFromTangentAndRound2(false);
        //return attitudeFromDualTangentAndDualRound();
    }
    
    @Override
    public CGARoundOPNS normalize(){
        return new CGARoundOPNS(super.normalize());
    }
    
    /**
     * Determination of the squared size. 
     * 
     * This is the radiusSquared for a k-sphere.
     * 
     * @return squared size/radius squared
     */
    public double squaredSize(){
        CGARoundOPNS m = this.normalize();
        //FIXME muss ich wirklich normalisieren?
        CGAScalarOPNS result = new CGAScalarOPNS(m.gp(m.gradeInversion()).div(m.carrierFlat().sqr()).compress()); 
        return result.value();
    }
   
    /**
     * Determination of squared size following [Dorst2007].
     * 
     * @return squared size
     * @Deprecated
    **/
    public CGAScalarOPNS squaredSizeIntern1(){
        return squaredSizeIntern1(this);
    }
    /**
     * Determination of the squared size of a round. 
     * 
     * precondition:
     * - location at the origin?
     * 
     * @param m round or dual round object represented by a multivector
     * @return squared size/radius (maybe negative)
     * @Deprecated
     */
    static CGAScalarOPNS squaredSizeIntern1(CGAKVector m){
        // following Dorst2008 p.407/08 (+errata: ohne Vorzeichen), corresponds to drills 14.9.2
        // CGAMultivector m_normalized = m.normalize();
        // testweise vorher normalisieren: produziert nur ein negatives Vorzeichen
        // Das funktioniert nur, wenn das Objekt im Ursprung liegt!!!!
        // Vergleich mit Hitzer: gleiche Formbel bis auf op statt lc im Nenner FIXME
        //FIXME funktioniert nicht
        CGAMultivector m_n = m.normalize(); // hat im circletest keinen Unterschied gemacht
        // gradeInversion() ist elegant, da ich damit die Formel für pp, circle und sphere
        // verwenden kann
        // The given multivector is not not grade 0! -0.5016342651363553 - 
        // 0.6891020957909896*eo^e1^e2^e3 + 0.31092585211142815*eo^e1^e2^ei
        // Der scalar-Teil scheint korrekt zu sein und wird hier mit extractGrade() rausgeschnitten
        // FIXME warum ist das nötig? Vielleicht ist sqr als einfaches ip falsch implementiert
        // vielleicht muss ip vom reverse gerechnet werden für sqr
        CGAScalarOPNS result = new CGAScalarOPNS(m_n.gp(m_n.gradeInversion()).div((inf.lc(m_n)).sqr()).extractGrade(0)/*compress()*/); 
        System.out.println(result.toString("squaredSize (Dorst2007)"));
        
        // Alternative implementation
        // following Vince2008 for Sphere, Circle and maybe point-pair
        // failed!!!
        //result = m.gp(m).negate().div((m.op(CGAMultivector.createInf(1d))).sqr());
        //System.out.println(result.toString("squaredSizeIntern1/radiusSquared2"));
        
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // hier findet sich eine leicht andere Formel, mit der direkt die size/radius
        // also nicht squaredSizeIntern1 bestimmt werden kann
        //dual_sphere = self.dual
        //dual_sphere /= (-dual_sphere | self.cga.einf)
        //return math.sqrt(abs(dual_sphere * dual_sphere))
        //result = m.dual().div(m.dual().negate().ip(CGAMultivector.createInf(1d)));
        //result = result.gp(result);
        
        return result;
    }
    
     /**
     * Determination of the squared size of the corresponding geometric object.
     * 
     * Implementation following Hitzer2005.
     * 
     * @return squared size
     */
    public CGAScalarOPNS squaredSizeIntern3(){
        return squaredSizeIntern3(this);
    }
    static CGAScalarOPNS squaredSizeIntern3(CGAKVector m){
        // andere impl
        // basierend auf Hitzer2005, sollte auch für pointpair funktionieren
        // scheint zu funktionieren für sphereopns
        // für circle sollte eigentlich 1.0-->-1.0 geändert werden
        // (L.ScProd(L))*(1.0/(n.OutProd(L).ScProd(n.OutProd(L))));
        double result = m.scp(m) / (inf.op(m)).sqr().compress().decomposeScalar();
        System.out.println("squaredSize (Hitzer2004, change sign for circle)="+String.valueOf(result));
        return new CGAScalarOPNS(result);
    }
    
    private CGARoundOPNS toOrigin(){
        Vector3d d = new Vector3d(location());
        d.negate();
        CGATranslator t = new CGATranslator(d);
        return new CGARoundOPNS(t.transform(this));
    }
    
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        CGARoundPointIPNS result = locationIntern();
        return result.extractE3ToPoint3d();
    }
    @Override
    public CGARoundPointIPNS locationIntern(){
        // Dorst
        return locationFromTangentAndRoundAsNormalizedSphere(); 
    }
    
    // var project_point_on_round = (point,sphere)=>-point^ni<<sphere<<sphere
    // - hat 1
    // << und ^ hat 3
    /**
     * Projection of a point onto a round (sphere or circle).
     * 
     * @param point if the round is a sphere or the conjugate of the point if the round is a circle
     * @return the projected point = -point^ni<<sphere<<sphere
     */
    public CGAPointPairOPNS project(CGARoundPointIPNS point){
        return new CGAPointPairOPNS(point.op(inf).lc(this).lc(this).negate());
    }
}