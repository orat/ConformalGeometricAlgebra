package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Rounds are RoundPoints, point-pairs, circles and spheres, here given in inner product
 * null space representation corresponding to dual round in Drost2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGARoundIPNS extends CGABlade {
    
    CGARoundIPNS(CGAMultivector m){
        super(m);
    }
    CGARoundIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    private boolean test(){
        boolean result = false;
        CGAMultivector inf = createInf(1d);
        if ((inf.op(this).scalarPart() != 0) && (inf.ip(this).scalarPart() != 0) && (this.sqr().scalarPart() != 0d)){
            result = true;
        }
        return result;
    }
    /**
     * The attitude is the direction of the translation of this object from the origin
     * to its location.
     * 
     * The Euclidian parts of this direction may not be of unit weight or positive
     * orientation relative to the pseudoscalar of the Euclidean subspace they
     * belong to.
     * 
     * In this case the magnitude of the attitude is the weight and its sign is 
     * the orientation.
     * 
     * @return attitude in the form E^inf
     */
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractE3ToVector3d();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return attitudeFromTangentAndRound();
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    // für eine CGARoundIPNS scheint das jetzt zu stimmen
    @Override
    public Point3d location(){
        //CGAMultivector location = location3(weight());
        //System.out.println("location lua="+location.toString()); // scheint für CGAPoint zu stimmen
        
        CGAMultivector result = locationFromTangendAndRoundAsNormalizedSphere();
        return result.extractE3ToPoint3d();
        //double[] vector = result.impl.extractCoordinates(1);
        //int index = result.impl.getEStartIndex();
        //return new Point3d(vector[index++], vector[index++], vector[index]);
    }
        
    /**
     * Implementation following Hildenbrand1998.
     * 
     * @return location of the round
     */
    public Point3d location2(){
        CGAMultivector result = this.gp(CGAMultivector.createInf(1d)).gp(this);
        return extractE3ToPoint3d();
        //double[] vector = result.impl.extractCoordinates(1);
        //int index = result.impl.getEStartIndex();
        //return new Point3d(vector[index++], vector[index++], vector[index]);
    }
    
    /**
     * Implementation following the formulae from CGAUtil Math, Spencer T
     * Parkin 2013. 
     * 
     * @return squaredWeight
     */
    @Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }
    
    /**
     * Implementation following the formulae from CGAUtil Math, Spencer T
     * Parkin 2013. 
     * 
     * @return weight, can be 0
     */
    private double weight(){
        return ip(createInf(1d)).gp(-1d).scalarPart();
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
    private CGAMultivector location3(double weight){
        CGAMultivector normalizedRound = new CGAMultivector(this.impl);
        normalizedRound = normalizedRound.gp(1d/weight);
        System.out.println("weight="+String.valueOf(weight));
        System.out.println("normalizedRound(location)="+normalizedRound.toString());
        CGAMultivector e0_einf = createOrigin(1d).op(createInf(1d));
	return e0_einf.ip(normalizedRound.op(e0_einf));
    }
    
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * @return squared size
     */
    public double squaredSize(){
        return squaredSize(this);
    }
    /**
     * Determination of the squared size. This is the radiusSquared for a sphere.
     * 
     * ok für dualSphere, hint: CGADualRound ruft diese Methode auf uns switched nur das Vorzeichen
     * falsch für CGARoundPoint
     * 
     * @param m round or dual round object represented by a multivector
     * @return squared size/radius squared
     */
    static double squaredSize(CGABlade m){
        //gp(2) only in the Hildebrand2004 paper (seems to be wrong) but not in 
        // Dorst2007 p.407 - Formel für Round in Dorst also DualRound in meine Notation
        CGAMultivector result = m.gp(m.gradeInversion()).div((createInf(1d).ip(m)).sqr()).gp(-1d);
        //System.out.println("squaredSize/radiusSquared="+result.toString());
        
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // hier findet sich eine leicht andere Formel, mit der direkt die size/radius
        // also nicht squaredSize bestimmt werden kann
        
        return result.scalarPart();
    }
    
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
        return new CGARoundPointIPNS(point.negate().op(CGAMultivector.createInf(1d)).
                lc(this).lc(this));
    }
}
