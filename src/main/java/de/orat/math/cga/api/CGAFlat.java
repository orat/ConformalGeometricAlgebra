package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * FlatPoints, Lines, Planes, hyperplanes, all in inner product null space 
 * representation.
 * 
 * A flat is a round containing inf in its formula.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAFlat extends CGABlade {
     
    CGAFlat(CGAMultivector m){
        super(m);
    }
    CGAFlat(iCGAMultivector m){
        super(m);
    }
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude_cga="+result.toString());
        //double[] coordinates = impl.extractCoordinates(2);
        //return new Vector3d(coordinates[1], coordinates[2], coordinates[3]);
        return result.extractAttitudeFromEeinfRepresentation();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        // Sign of all coordinates changes according to errato to the book Dorst2007
        // mir scheint hier wird von weight==1 ausgegangen. Das Vorzeichen könnte
        // vermutlich verschwinden, wenn ich die beiden Operanden vertausche
        return createInf(-1d).op(this);
    }   
    CGAMultivector locationIntern(Point3d probe){
        return new CGARoundPoint(probe).op(this).div(this);//.extractGrade(1);
    }
    @Override
    public Point3d location(Point3d probe){
        CGAMultivector m = locationIntern(probe);
        // location_cga=2.0000000000000004*eo + 0.0*eo^e3^ei
        System.out.println("location_cga="+m.toString());
        return m.extractE3ToPoint3d();
    }
    public FlatAndDirectionParameters decompose(Point3d probePoint){
        return new FlatAndDirectionParameters(attitude(), location(probePoint));
    }
    
     
    /**
     * Extract the direction and location of a line/plane.
     * 
     * @param probePoint normalized probe result (e0=1, e1,e2,e3, einfM). If not specified use e0.
     * @return direction of the given flat
     */
    /*protected Decomposition3d.FlatAndDirectionParameters decomposeFlat(CGAMultivector probePoint){
        // Kleppe2016
        //Multivector attitude = flat.ip(Multivector.createBasisVector(0), RIGHT_CONTRACTION)
        //        .ip(Multivector.createBasisVector(4), RIGHT_CONTRACTION);
        
        // use dualFlat in Dorst2007
        // damit bekomme ich die attitude in der Form E.op(einfM)
        // für attitude ist ein Vorzeichen nach Dorst2007 zu erwarten, scheint aber nicht zu stimmen
        CGAMultivector attitude = createInf(1d).op(this).undual();
        // attitude=-5.551115123125783E-17*no^e1^e2 + 0.9999999999999996*e1^e2^ni
        System.out.println("attitude="+String.valueOf(attitude.toString()));
                
        // Dorst2007 - Formel für dual-flat verwenden
        // locations are determined as dual spheres
        CGAMultivector location = probePoint.op(this).gp(inverse());
        //CGAMultivector location = probePoint.ip(this, LEFT_CONTRACTION).gp(inverse());
        
        double[] locationCoord = location.impl.extractCoordinates(1);
        int index = location.impl.getEStartIndex();
        return new Decomposition3d.FlatAndDirectionParameters(attitude.extractAttitudeFromEeinfRepresentation(), 
               new Point3d(locationCoord[index++], locationCoord[index++], locationCoord[index]));
    }*/
}
