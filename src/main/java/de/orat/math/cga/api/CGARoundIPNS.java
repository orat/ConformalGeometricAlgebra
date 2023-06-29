package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Weighted finite rounds are: round points, point-pairs, circles and spheres, 
 * here given in inner product null space representation corresponding to dual 
 * round in [Drost2007].
 * 
 * Finite rounds are objects with finite areas/volumes/hyperolumes.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
abstract class CGARoundIPNS extends CGAKVector implements iCGATangentOrRound {
    
    boolean isNormalized = false;
    
    CGARoundIPNS(CGAMultivector m){
        super(m);
    }
    CGARoundIPNS(iCGAMultivector impl){
        super(impl);
    }
    public CGARoundIPNS(double[] values){
        super(values);
    }
    
    // untested
    /**
     * Creates real or imaginary round in outer product null space representation.
     * 
     * corresponding to [Dorst2009] 14.9
     * 
     * @param c
     * @param Ak euclidean k-Vector with k=1,2,3 to create point-pair, circle and sphere
     * @return round in ipns representation
     */
    static CGAMultivector create(Point3d c, AbstractEuclideanKVector Ak, double r){
        CGARoundPointIPNS cp = new CGARoundPointIPNS(c);
        CGAMultivector result = new CGAMultivector(cp.impl);
        result.sub(inf.gp(0.5*r*r));
        //result.sub((new CGAScalarOPNS(0.5*r*r)).gp(inf));
        return result.op(cp.negate().lc(Ak.gradeInversion().euclideanDual().gp(inf)));
    }
    
    
    // etc
    
    public boolean isNormalized(){
        return isNormalized;
    }
     
    public static boolean typeof(CGAMultivector m){
       if (inf.op(m).isNull()) return false;
       if (inf.ip(m).isNull()) return false;
        // square(m) = 0 then round
        //return !m.sqr().isNull();
       return m.isScalar() && m.decomposeScalar() == 0;
    } 
    private boolean test(){
        boolean result = false;
        if ((inf.op(this).decomposeScalar() != 0) && 
            (inf.ip(this).decomposeScalar() != 0) && 
                (this.sqr().decomposeScalar() != 0d)){
            result = true;
        }
        return result;
    }
    
    
    // decompose
    
    @Override
    public EuclideanParameters decompose(){
        System.out.println(toString("CGARoundIPNS.decompose"));
        return new EuclideanParameters(attitude(), location(), 
                                      squaredSize(), squaredWeight());
    }
    
    /**
     * Determine the carrier flat (euclidean carrier) of a (dual=IPNS) round.
     * 
     * The carrier flat of an element is the smallest grade flat that contains
     * the element (Dorst2007 p. 445).
     *
     * The carrier flat is the OPNS subspace representation of the minimal (lowest
     * possible dimension) Euclidean subspace to include the whole geometric object
     * when it is placed at the origin.
     * 
     * The carrier flat is fully position independent.
     * 
     * @return carrier flat (not normalized)
     */
    public CGAKVector carrierFlat(){
        // do not normalize before, so that it is possible to determine the weight
        // as norm of the carrier flat.
        //FIXME eventuell muss ich IO.normalize() verwenden damit das Vorzeichen stimmt
        // ich brauche tests dazu, Unklarheit wie das Vorzeichen definiert sein soll
        return new CGAKVector(this.undual().op(inf).negate().rc(I0));
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
     * FIXME bei PPIPNS ergibt sich E^inf statt E
     * extract entsprechend ersetzen...
     * 
     * @return attitude extraction from the E3 representation inclusive normalization
     */
    /*public Vector3d attitude(){
        CGAAttitudeOPNS result = attitudeIntern();
        System.out.println("attitude (CGARoundIPNS)="+result.toString());
        Vector3d res = result.extractE3ToVector3d();
        res.normalize();
        return res;
    }*/
    
    /**
     * 
     * @return as euclidean vector
     */
    public Vector3d attitude(){
        return attitudeIntern().direction();
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
    protected CGAAttitudeOPNS attitudeIntern(){
        return attitudeFromTangentAndRoundIPNS();
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
     * sandwitch-product following [Hildenbrand1998].
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
        return ip(inf).gp(-1d).decomposeScalar();
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
     * Determination of squared size.
     * 
     * Hint: Squared size is - radius squared for round (k-sphere). The sign has to be changed 
     * in the formulas for CGARoundOPNS.<p>
     * 
     * @return squared size/-radius squared, negative values are possible and describe imaginary rounds
     */
    public double squaredSize(){
        return squaredSizeIntern1().decomposeScalar();
    }
    public CGAScalarOPNS squaredSizeIntern1(){
        // sign corresponding to Dorst2009 changed for ipns representation
        // FIXME damit bekomme ich aber negatives Vorzeichen in der Demo-IK für C5k
        CGAScalarOPNS result = CGARoundOPNS.squaredSizeIntern1(this).negate();
        return result;
    }
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * The sign is correct defined only for sphere.
     * 
     * @return squared size/-radius squared
     */
    public CGAScalarOPNS squaredSizeIntern3(){
        // following Hitzer, sollte das bis auf Vorzeichen für circle und sphere funktionieren,
        // möglicherweise auch für pointpair
        //FIXME oder muss ich this.dual() übergeben?
        return CGARoundOPNS.squaredSizeIntern3(this);
    }
    /**
     * ok for sphereIPNS
     * failed for circleIPNS, pointpairipns Multivector is not invertable: this.negate().ip(CGAMultivector.createInf(1d))
     * 
     * @return 
     */
    public CGAScalarOPNS squaredSizeIntern2(){
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // dual_sphere = self.dual
        // dual_sphere /= (-dual_sphere | self.cga.einf)
        // return math.sqrt(abs(dual_sphere * dual_sphere))
        CGAMultivector result = this.div(this.negate().ip(inf));
        result = result.sqr().compress();
        System.out.println(result.toString("squaredSizeIntern2 (CGAOrientedFiniteRoundIPNS)"));
        return new CGAScalarOPNS(result);
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
        
        return result.decomposeScalar();
    }*/
    
    /*public RoundAndTangentParameters decomposeMotor(){
       return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }*/
    
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
    
    @Override
    public CGAKVector dual(){
        throw new RuntimeException("The given multivector is ipns-type - dual() is not allowed! Use undual() instead!");
    }
}
