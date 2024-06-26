package de.orat.math.cga.api;


import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * (Oriented finite) flats are rounds containing the the infinity inf, eg
 * points, lines, planes, hyperplanes (k-dimensional flats).
 * 
 * All in inner product null space representation, corresponding to dual flat 
 * in [Dorst2007].<p>
 * 
 * A dual flat point has "hairs" extending to infinity.<p>
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
abstract class CGAFlatIPNS extends CGAKVector implements iCGAFlat {
     
    CGAFlatIPNS(CGAMultivector m){
        super(m);
    }
    CGAFlatIPNS(iCGAMultivector m){
        super(m);
    }
    CGAFlatIPNS(double[] values){
        super(values);
    }
    
    /**
     * Tests if the given multivector is a ipns flat.
     * 
     * @param m
     * @return 
     */
    public static boolean is(CGAMultivector m){
        if (inf.op(m).isNull()) return false;
        return inf.lc(m).isNull();
    }
    
    // decompose
    
    public iCGAFlat.EuclideanParameters decomposeFlat(){
        return new iCGAFlat.EuclideanParameters(attitude(), location(), 
                                      squaredWeight());
    }
    public iCGAFlat.EuclideanParameters decomposeFlat(Point3d probePoint){
        return new iCGAFlat.EuclideanParameters(attitude(), location(probePoint), squaredWeight());
    }
    
     
    /**
     * Determine the carrier flat (euclidean carrier) of a (dual=IPNS) flat.
     * 
     * The carrier flat is the OPNS subspace representation of the minimal (lowest
     * possible dimension) Euclidean subspace to include the whole geometric object
     * when it is placed at the origin.<p>
     * 
     * The carrier flat of an element is the smallest grade flat that contains
     * the element (Dorst2007 p. 445).<p>
     * 
     * The carrier flat is fully position independent.<p>
     * 
     * Carrier method for the general evaluation and control of pose, molecular 
     * conformation, track-ing, and the like.
     * Eckhard Hitzer, Kanta Tachibana, Sven Buchholz and Isseki Yu
     * (Theorem 2.3)
     * 
     * plane IPNS: Bivector e.g. (0.9999999999999991*e1^e2)
     * 
     * FIXME
     * Bei Hitzer2005 ist carrierFlat nur für OPNS definiert!!!
     * 
     * @return carrier flat (not normalized)
     */
    public CGAKVector carrierFlat(){
        // do not normalize before, so that it is possible to determine the weight
        // as norm of the carrier flat.
        //FIXME eventuell mutt ich I0.negate() verwenden, damit das Vorzeichen stimmt
        return new CGAKVector(this.undual().negate().rc(I0));
    }
    
    
    // attitude
    
    public Vector3d attitude(){
        return attitudeIntern().direction();
    }
    
    /**
     * Formula corresponding to [Rettig2023] and [Dorst2009].
     * 
     * für ipns line stimmt das vorzeichen nicht -->bivector
     * für ipns plane --> trivector, aber da stimmen die Vorzeichen der Komponenten nicht!
     * 
     * @return attitude as "E inf"
     */
    @Override
    public CGAAttitudeOPNS attitudeIntern(){
        CGAMultivector result = inf.negate().lc(this.undual()).compress();
        System.out.println(result.toString("attitudeIntern (CGAFlatIPNS, Dorst)"));
        return new CGAAttitudeOPNS(result);
    }   
    
    
    // location
    
    /**
     * Location point of the flat nearest to to the given probe point as 
     * normalized dual (ipns) sphere.
     * 
     * corresponding to
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
     * 2009
     * 
     * @param probe point
     * @return location as normalized dual sphere
     * @throws java.lang.ArithmeticException if the flat is not invertible.
     * 
     * can fail if the flat contains the given probe?
     */
    public CGARoundPointIPNS locationIntern(Point3d probe){
        return new CGARoundPointIPNS(locationInt(probe));
    }
    private CGAMultivector locationInt(Point3d probe){
        return (new CGARoundPointIPNS(probe)).op(this).div(this).compress();
    }
    @Override
    public Point3d location(Point3d probe){
        CGARoundPointIPNS res = locationIntern(probe);
        
        // extract E3 from normalized dual sphere
        // [Dorst2007] p.409
        CGAMultivector oinf = o.op(inf);
        CGAEuclideanVector result = new CGAEuclideanVector(oinf.lc(oinf.op(res)));
        System.out.println(result.toString("location E3 (from CGAFlatIPNS, Dorst)"));
        return result.location();
        // oder direct? ohne vorher oinf rausprojezieren?  return m.extractE3ToPoint3d();
        //FIXME
    }
    
    @Override
    public CGAKVector dual(){
        throw new RuntimeException("The given multivector is ipns-type - dual() is not allowed! Use undual() instead!");
    }
}
