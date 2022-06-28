package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import static de.orat.math.cga.api.CGAMultivector.squaredWeight;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * DualFlatPoints, DualPointPairs?, DualLines, DualPlanes, DualCircles? 
 * 
 * all in inner product null space representation.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGADualFlat extends CGAMultivector {
    
    CGADualFlat(CGAMultivector m){
        super(m.impl);
    }
    protected CGADualFlat(iCGAMultivector m){
        super(m);
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude_cga="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return createInf(1d).ip(this);
        // Conversion formula von Dorst nach Hilenbrand
        //return createOrigin(2d).ip(this);
    } 
    @Override
    public Point3d location(Point3d probe){
        // Determine a normalized dual sphere as location
        CGAMultivector probeCGA = new CGARoundPoint(probe);
        CGAMultivector m = probeCGA.ip(this).div(this);
        System.out.println("location="+m.toString());
        // the euclidian part is the location in euclidian space
        return new Point3d(m.extractEuclidianVector());
    }
    
    public Decomposition3d.FlatAndDirectionParameters decompose(Point3d probePoint){
        return new Decomposition3d.FlatAndDirectionParameters(attitude(), location(probePoint));
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
}
