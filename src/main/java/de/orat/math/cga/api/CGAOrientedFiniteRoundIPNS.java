package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Oriented and weighted finite rounds are round points, point-pairs, circles and spheres, 
 * here given in inner product null space representation corresponding to dual 
 * round in Drost2007.
 * 
 * Finite rounds are objects with finite areas/volumes/hyperolumes.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAOrientedFiniteRoundIPNS extends CGAKVector {
    
    CGAOrientedFiniteRoundIPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedFiniteRoundIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    private boolean test(){
        boolean result = false;
        if ((inf.op(this).scalarPart() != 0) && (inf.ip(this).scalarPart() != 0) && (this.sqr().scalarPart() != 0d)){
            result = true;
        }
        return result;
    }
    /**
     * The attitude is the normalized direction of the translation of this object 
     * from the origin to its location.
     * 
     * The Euclidian parts of this direction may not be of unit weight or positive
     * orientation relative to the pseudoscalar of the Euclidean subspace they
     * belong to.
     * 
     * In this case the magnitude of the attitude is the weight and its sign is 
     * the orientation.
     * 
     * @return attitude extraction from the E3 representation inclusive normalization
     */
    public Vector3d attitude(){
        CGAAttitude result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        Vector3d res = result.extractE3ToVector3d();
        res.normalize();
        return res;
    }
    
    /**
     * Determination of the attitude.
     * 
     * The attitude is not normalized. Its length is the weight of the corresponding
     * geometric object and the sign of the weight is its orientation relativ to
     * the choosen pseudoscalar for the directional subspace.
     * 
     * @return attitude
     */
    @Override
    protected CGAAttitude attitudeIntern(){
        // FIXME geht so nur wenn der round im Ursprung ist?
        return attitudeFromTangentAndRound2((CGAKVector) this.undual());
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
    /**
     * Determine location of the correspondig geometric object based on a
     * sandwitch-product following Hildenbrand1998.
     * 
     * @return location of the corresponding geometric object.
     */
    public CGARoundPointIPNS locationIntern3(){
        CGARoundPointIPNS result = new CGARoundPointIPNS(this.gp(inf).gp(this));
        System.out.println(result.toString("locationIntern3 (CGAOrientedFiniteRoundIPNS, Hildenbrand)"));
        return result;
    }
    /**
     * Implementation following Hildenbrand1998.
     * 
     * @return location of the round
     */
    /*public Point3d location2(){
        CGAMultivector result = this.gp(CGAMultivector.createInf(1d)).gp(this);
        return extractE3ToPoint3d();
        //double[] vector = result.impl.extractCoordinates(1);
        //int index = result.impl.getEStartIndex();
        //return new Point3d(vector[index++], vector[index++], vector[index]);
    }*/
    
    /**
     * Determination of the weight without a probe point and without determination
     * of the attitude.
     * 
     * @return weight, can be 0?
     */
    private double weight2(){
        // Implementation following the formulae from CGAUtil Math, Spencer T
        // Parkin 2013. 
        return ip(inf).gp(-1d).scalarPart();
    }
    /**
     * Determine location of the round.
     * 
     * Implementation following the formulae from CGAUtil Math, Spencer T
     * Parkin 2013.
     * 
     * scheint für CGAPoint zu stimmen
     * 
     * @return location of the round
     */
    /*private CGAMultivector location3(double weight){
        CGAMultivector normalizedRound = new CGAMultivector(this.impl);
        normalizedRound = normalizedRound.gp(1d/weight);
        System.out.println("weight="+String.valueOf(weight));
        System.out.println("normalizedRound(location)="+normalizedRound.toString());
        CGAMultivector e0_einf = createOrigin(1d).op(createInf(1d));
	return e0_einf.ip(normalizedRound.op(e0_einf));
    }*/
    
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * Hint: Squared size is - radius squared. The sign is different to 
     * CGAOrientedFiniteRoundOPNS.
     * 
     * precondition:
     * - normalized sphere located at the origin
     * 
     * test failed für circle_ipns
     * test failed for sphere_ipns
     * 
     * @return squared size/-radius squared
     */
    public double squaredSize(){
        return squaredSizeIntern1().scalarPart();
    }
    public CGAScalar squaredSizeIntern1(){
        // sign corresponding to errata in Dorst2007
        CGAScalar result = CGAOrientedFiniteRoundOPNS.squaredSizeIntern1(this.undual());
        System.out.println(result.toString("squaredSizeIntern1 (CGAOrientedFiniteRoundIPNS)"));
        return result;
    }
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * The sign is correct defined only for sphere.
     * 
     * @return squared size/-radius squared
     */
    public CGAScalar squaredSizeIntern3(){
        // following Hitzer, sollte das bis auf Vorzeichen für circle und sphere funktionieren,
        // möglicherweise auch für pointpair
        //FIXME oder muss ich this.dual() übergeben?
        return CGAOrientedFiniteRoundOPNS.squaredSizeIntern3(this);
    }
    /**
     * ok for sphereIPNS
     * failed for circleIPNS Multivector is not invertable: this.negate().ip(CGAMultivector.createInf(1d))
     * 
     * @return 
     */
    public CGAScalar squaredSizeIntern2(){
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // dual_sphere = self.dual
        // dual_sphere /= (-dual_sphere | self.cga.einf)
        // return math.sqrt(abs(dual_sphere * dual_sphere))
        CGAMultivector result = this.div(this.negate().ip(inf));
        result = result.sqr().compress();
        System.out.println(result.toString("squaredSizeIntern2 (CGAOrientedFiniteRoundIPNS)"));
        return new CGAScalar(result);
    }
    /**
     * Determination of the squared size. This is the radiusSquared for a sphere.
     * 
     * ok für dualSphere, hint: CGADualRound ruft diese Methode auf und switched nur das Vorzeichen
     * falsch für CGARoundPoint
     * 
     * @param m round or dual round object represented by a multivector
     * @return squared size/radius squared
     */
    /*static double squaredSizeIntern1(CGAKVector m){
        //gp(2) only in the Hildebrand2004 paper (seems to be wrong) but not in 
        // Dorst2007 p.407 - Formel für Round in Dorst also DualRound in meine Notation
        CGAMultivector result = m.gp(m.gradeInversion()).div((createInf(1d).ip(m)).sqr()).gp(-1d);
        //System.out.println("squaredSizeIntern1/radiusSquared="+result.toString());
        
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // hier findet sich eine leicht andere Formel, mit der direkt die size/radius
        // also nicht squaredSizeIntern1 bestimmt werden kann
        
        return result.scalarPart();
    }*/
    
    public RoundAndTangentParameters decompose(){
       return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
    
    //public abstract boolean isImaginary();
    
    /**
     * Project point on round.
     * 
     * @param point
     * @return projected point
     */
    //(point,sphere)=>-point^ni<<sphere<<sphere
    public CGARoundPointIPNS project(CGARoundPointIPNS point){
        return new CGARoundPointIPNS(point.negate().op(inf).
                lc(this).lc(this));
    }
}
