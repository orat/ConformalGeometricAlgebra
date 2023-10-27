package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * (Oriented finite) flats are rounds containing the infinity inf, eg
 * points, lines, planes, hyperplanes (k-dimensional flats).
 * 
 * All in outer product null space representation, corresponding to direct flat
 * in [Dorst2007].<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
abstract class CGAFlatOPNS extends CGAKVector implements iCGAFlat {
    
    CGAFlatOPNS(CGAMultivector m){
        super(m.impl);
    }
    protected CGAFlatOPNS(iCGAMultivector m){
        super(m);
    }
    CGAFlatOPNS(double[] values){
        super(values);
    }
    
    public static boolean is(CGAMultivector m){
        if (!inf.op(m).isNull()) return false;
        return !inf.lc(m).isNull();
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
     * Determine the carrier flat (euclidean carrier) of a (direkt) flat.
     * 
     * The carrier flat is the OPNS subspace representation of the minimal (lowest
     * possible dimension) Euclidean subspace to include the whole geometric object
     * when it is placed at the origin.
     * 
     * The carrier flat is fully position independent.
     * 
     * Carrier method for the general evaluation and control of pose, molecular 
     * conformation, track-ing, and the like.
     * Eckhard Hitzer, Kanta Tachibana, Sven Buchholz and Isseki Yu
     * (Theorem 2.3)
     * 
     * @return carrier flat (not normalized, to be able to determine the weight of the carrier)
     */
    public CGAKVector carrierFlat(){
        // do not normalize before, so that it is possible to determine the weight
        // as norm of the carrier flat.
        //FIXME
        // eventuell I0.negate() Vorzeichen kontrollieren
        return new CGAKVector(this.negate().rc(I0));
    }
    
   
    /**
     * Determine the localisation as the a point of the geometric object nearest
     * to the given one.
     * 
     * correspondsing to:
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira, 2009
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
     * if probe set to origin.
     * 
     * @param probe
     * @return location
     */
    public CGARoundPointIPNS locationIntern(Point3d probe){
        return new CGARoundPointIPNS(new CGARoundPointIPNS(probe).ip(this).div(this));
    }
    @Override
    public Point3d location(Point3d probe){
        // Determine a normalized dual sphere as location
        CGARoundPointIPNS m = locationIntern(probe);
        System.out.println(m.toString("location (CGAFlatOPNS, Dorst)"));
        // the euclidian part is the location in euclidian space
        return m.extractE3ToPoint3d();
    }
    
    
    // attitude
    
    public Vector3d attitude(){
        return attitudeIntern().direction();
    }
    
    /**
     * Determine the attitude (inclusive weight == not normalized attitude).
     * 
     * tested for line-opns by comparison with [Dorst2007]: drills (chapter 13.9.1)
     * 
     * @return attitude 
     */
    @Override
    public CGAAttitudeOPNS attitudeIntern(){
        // corresponding to
        // Geometric Algebra: A powerful tool for solving geometric problems in visual computing
        // Leandro A. F. Fernandes, and Manuel M. Oliveira, 2009
        // DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
        // also corresponding to [Dorst2009] p.407
        // tested for line
        //CGAMultivector result =  inf.lc(this).negate().compress();
        CGAMultivector result =  inf.negate().lc(this).compress();
        System.out.println(result.toString("attitudeIntern (CGAFlatOPNS, Dorst2009)"));
        return new CGAAttitudeOPNS(result);
    } 
    
    
    // etc
    
    /**
     * Determine the euclid decomposition parameters corresponding to the given dual Flat.
     * 
     * A Dual flat is a tri-vector.
     * 
     * Be careful: This corresponds to non-dual in Dorst2007.
     * 
     * @param probePoint normalized probe result (e0=1, e1,e2,e3, einfM) to define the location dualFlat parameter.. If not specified use e0.
     * @return euclid parameters. The location is determined as a result of the dualFlat
     * with the smallest distance to the given probe result.
     */
    /*protected Decomposition3d.FlatAndDirectionParameters decomposeDualFlat(CGAMultivector probePoint){
        
        // Dorst2007
        //TODO funktioniert nicht - alle components sind 0
        // Ich brauchen undualize into the full space, macht das dual()?
        //CGAMultivector vector = new CGA1Multivector(Multivector.createBasisVector(4).op(this).dual(CGA1Metric.CGA_METRIC));
        //System.out.println("dirvec="+vector.toString(CGA1Metric.baseVectorNames)); // ==0
        
        // Bestimmung von I0 einf M
        // stimmt nicht
        //CGA1Multivector dir = CGA1Multivector.createBasisVector(4,-1d).ip(this, LEFT_CONTRACTION);
        // Vector3d attitude = dir.extractAttitudeFromEeinfRepresentation();
        
        // Nach Kleppe2016
        CGAMultivector dir = rc(createOrigin(1d)).rc(createInf(1d));
        // attitude=-0.9799999999999993*e1 statt (0.98,0.0,0.0) mit right contraction
        //FIXME Warum stimmt das Vorzeichen nicht?
        System.out.println("attitude Kleppe="+dir.toString()); 
        Vector3d attitude = dir.extractEuclidianVector();
        System.out.println("attitude extraction=("+String.valueOf(attitude.x)+","+String.valueOf(attitude.y)+","+String.valueOf(attitude.z)+")");
        // Kleppe2016 adaptiert
        // oder left contraction?
        // left contraction ist null wenn k > l
        //dir = dualFlat.op(Multivector.createBasisVector(4)).ip(Multivector.createBasisVector(0), HESTENES_INNER_PRODUCT);
        //System.out.println("dirvec2="+vector.toString(CGA1Metric.baseVectorNames)); // ==0
        
        // Dorst2007
        // das sieht richtig aus! ist aber die Formel von dualflat statt flat
        CGAMultivector location = probePoint.op(this).gp(inverse());
        // Formel von flat - funktioniert nicht
        //CGAMultivector location = probePoint.ip(this, LEFT_CONTRACTION).gp(inverse());
         
        // grade 1 ist drin und sieht sinnvoll aus, grade-3 ist mit sehr kleinen Werten aber auch dabei
        // und zusätzlich auch e1einf und e0e1
        System.out.println("location="+location.toString());
        
        // locations are determined as duals-spheres (e0, e1, e2, e3, einfM)
        double[] locationCoord = location.impl.extractCoordinates(1);
        //System.out.println("locationCoord=("+String.valueOf(locationCoord[1])+", "+String.valueOf(locationCoord[2])+" ,"+
        //        String.valueOf(locationCoord[3])+")");
        
        int index = location.impl.getEStartIndex();
        return new Decomposition3d.FlatAndDirectionParameters(attitude, 
               new Point3d(locationCoord[index++], locationCoord[index++], locationCoord[index]));
    }*/
    
    /**
     * Since the geometric product between two vectors contains all the information 
     * regarding their relative directions, it can be used to define projections.
     * 
     * By multiplying the vector a with the square of a unit vector n, it can be 
     * decomposed into parts parallel and perpendicular to n.
     *  
     * A projection is defined as the perpendicular part.
     * 
     * Ported from ganja.js:
     * (point,plane)=>up(-point<<plane<<plane^nino*nino)
     * Precedences:
     * - has 1
     * * has 2
     * << and ^ have 3
     * 
     * @param point
     * @return the project of the given point onto the flat.
     */
    public CGARoundPointIPNS project(CGARoundPointIPNS point){
        CGAMultivector nino = inf.op(CGAMultivector.createOrigin(1d));
        return new CGARoundPointIPNS(point.lc(this).lc(this).op(nino).gp(nino).negate().extractE3ToPoint3d());
        // kommt aufs gleiche raus
        //return new CGARoundPointIPNS(point.negate().lc(this).lc(this).op(nino).gp(nino).extractE3ToPoint3d());
    }
    
    @Override
    public CGAKVector undual(){
        throw new RuntimeException("The given multivector is opns-type - undual() is not allowed! Use dual() instead!");
    }
}
