package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Oriented finite flats e.g. FlatPoints, PointPairs?, Lines, Planes, DualCircles? 
 * 
 * All in outer product null space representation, corresponding to direct flat
 * in Dorst2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAOrientedFiniteFlatOPNS extends CGABlade {
    
    CGAOrientedFiniteFlatOPNS(CGAMultivector m){
        super(m.impl);
    }
    protected CGAOrientedFiniteFlatOPNS(iCGAMultivector m){
        super(m);
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude_cga="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }
    
    /**
     * corresponds to
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
     * 2009
     */
    // scheint bei dualline so zu stimmen
    @Override
    protected CGAMultivector attitudeIntern(){
        return createInf(-1d).lc(this);
    } 
    
    /**
     * corresponds to
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
     * 2009
     * if probe set to origin.
     * 
     * @param probe
     * @return location (represented as finite point)
     */
    CGAMultivector locationIntern(Point3d probe){
        return new CGARoundPointOPNS(probe).ip(this).div(this);
    }
    @Override
    public Point3d location(Point3d probe){
        // Determine a normalized dual sphere as location
        CGAMultivector m = locationIntern(probe);
        System.out.println("location="+m.toString());
        // the euclidian part is the location in euclidian space
        return m.extractE3ToPoint3d();
    }
    
    public FlatAndDirectionParameters decompose(Point3d probePoint){
        return new FlatAndDirectionParameters(attitude(), location(probePoint));
    }
    
    /*public CGAFlat undual(){
        return new CGAFlat(impl.undual());
    }*/
    
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
        
        // Bestimmung von E einf M
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
        CGAMultivector nino = CGAMultivector.createInf(1d).op(CGAMultivector.createOrigin(1d));
        return new CGARoundPointIPNS(point.lc(this).lc(this).op(nino).gp(nino).negate().extractE3ToPoint3d());
        // kommt aufs gleiche raus
        //return new CGARoundPointIPNS(point.negate().lc(this).lc(this).op(nino).gp(nino).extractE3ToPoint3d());
    }
}
