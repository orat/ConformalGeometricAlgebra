package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Oriented and weighted rounds are points, point-pairs, circles and spheres/hyper-spheres,
 * here given in inner product null space representation corresponding to direct round in Dorst2007.
 * 
 * Rounds are objects with finite areas/volumes/hyperolumes.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAOrientedFiniteRoundOPNS extends CGAKVector {
    
    CGAOrientedFiniteRoundOPNS(CGAMultivector m){
        super(m.impl);
    }
    protected CGAOrientedFiniteRoundOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // decompose
    
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
    protected CGAAttitude attitudeIntern(){
        // z.B. -1.9999999999999982*e1^e2^e3^ei also grade 4 und nicht grade 2
        // wenn das von einem CGASphereOPNS aufgerufen wird
        return attitudeFromTangentAndRound2(this);
        //return attitudeFromDualTangentAndDualRound();
    }
    
    /**
     * Determination of the squared size. This is the radiusSquared for a sphere.
     * 
     * ok for dualSphere?
     * false for pointPair
     * false für OPNS_Sphere
     * 
     * @param m round object represented by a multivector
     * @return squared size/radius squared
     */
    public double squaredSize(){
        double result =  squaredSizeIntern(this.toOrigin()).scalarPart();
        // testweise
        squaredSize3();
        return result;
    }
    /**
     * Determination of the squared size of the corresponding geometric object.
     * 
     * Implementation following Hitzer2005.
     * 
     * @return squared size
     */
    public double squaredSize3(){
        return squaredSize3Intern(this);
    }
    static double squaredSize3Intern(CGAKVector m){
        // andere impl
        // basierend auf Hitzer2005, sollte auch für pointpair funktionieren
        // scheint zu funktionieren für sphereopns
        // für circle sollte eigentlich 1.0-->-1.0 geändert werden
        // (L.ScProd(L))*(1.0/(n.OutProd(L).ScProd(n.OutProd(L))));
        double result = m.scp(m) / (createInf(1d).op(m)).sqr().scalarPart();
        System.out.println("squaredSize (Hitzer2004, for circle change sign)="+String.valueOf(result));
        return result;
    }
    
    /**
     * Determination of the squared size of a round centered in the origin. 
     * 
     * precondition:
     * - location at the origin
     * 
     * @param m round or dual round object represented by a multivector
     * @return squared size/radius (maybe negative)
     */
    static CGAMultivector squaredSizeIntern(CGAKVector m){
        // following Dorst2008 p.407/08 (+errata: ohne Vorzeichen), corresponds to drills 14.9.2
        // CGAMultivector m_normalized = m.normalize();
        // testweise vorher normalisieren: produziert nur ein negatives Vorzeichen
        // Das funktioniert nur, wenn das Objekt im Ursprung liegt!!!!
        // Vergleich mit Hitzer: gleiche Formbel bis auf op statt lc im Nenner FIXME
        //FIXME funktioniert nicht
        CGAMultivector m_n = m.normalize(); // hat im circletest keinen Unterschied gemacht
        // gradInversion() ist elegant, da ich damit die Formel für pp, circle und sphere
        // verwenden kann
        CGAMultivector result = m_n.gp(m_n.gradeInversion()).div((createInf(1d).lc(m_n)).sqr()); 
        System.out.println(result.toString("squaredSize (Dorst2007)"));
        
        // Alternative implementation
        // following Vince2008 for Sphere, Circle and maybe point-pair
        // failed!!!
        //result = m.gp(m).negate().div((m.op(CGAMultivector.createInf(1d))).sqr());
        //System.out.println(result.toString("squaredSizeIntern/radiusSquared2"));
        
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // hier findet sich eine leicht andere Formel, mit der direkt die size/radius
        // also nicht squaredSizeIntern bestimmt werden kann
        //dual_sphere = self.dual
        //dual_sphere /= (-dual_sphere | self.cga.einf)
        //return math.sqrt(abs(dual_sphere * dual_sphere))
        //result = m.dual().div(m.dual().negate().ip(CGAMultivector.createInf(1d)));
        //result = result.gp(result);
        
        return result;
    }
    
    private CGAKVector toOrigin(){
        Vector3d d = new Vector3d(location());
        d.negate();
        CGATranslator t = new CGATranslator(d);
        return new CGAKVector(t.transform(this));
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
        return locationFromTangentAndRoundAsNormalizedSphere(); 
    }
    
    public Decomposition3d.RoundAndTangentParameters decompose(){
       return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
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
    public CGAOrientedPointPairOPNS project(CGARoundPointIPNS point){
        return new CGAOrientedPointPairOPNS(point.op(CGAMultivector.createInf(1d)).lc(this).lc(this).negate());
    }
}