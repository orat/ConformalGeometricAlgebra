package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * (Oriented finite) flats are rounds containing the the infinity inf., e.g. flat
 * points, lines, planes, hyperplanes (k-dimensional flats), all in inner product 
 * null space representation, corresponding to dual flat in Dorst2007.
 * 
 * A dual flat point has "hairs" extending to infinity.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAOrientedFiniteFlatIPNS extends CGAKVector {
     
    CGAOrientedFiniteFlatIPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedFiniteFlatIPNS(iCGAMultivector m){
        super(m);
    }
    // unklar, ob das hier so gut aufgehoben ist, vermutlich klappt nämlich extract
    // nicht bei Ebenen und Kugeln da ich dann vermutlich einen Bivector bekomme
    //  nach Abspaltung von einf
    // dann kann ich den spezifischen extract-Methode in die spezifischen Attitude classen verschieben?
    //TODO
    public Vector3d attitude(){
        CGAAttitude result = attitudeIntern();
        System.out.println("attitude_cga="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }
    
    /**
     * TODO
     * vermutlich die Methode hier ganz beseitigen, da line, plane etc. Vector
     * Bivector ... erzeugen ...
     * 
     * für ipns line stimmt das vorzeichen nicht -->bivector
     * für ipns plane --> trivector
     * 
     * @return attitude
     */
    @Override
    protected CGAAttitude attitudeIntern(){
        // Sign of all coordinates change according to errato to the book Dorst2007
        // mir scheint hier wird von weight==1 ausgegangen. Das Vorzeichen könnte
        // vermutlich verschwinden, wenn ich die beiden Operanden vertausche
        CGAMultivector result = createInf(1d).op(this).undual().negate().compress();
        System.out.println(result.toString("attitudeIntern(CGAOrientedFiniteFlatIPNS)"));
        // IPNS plane = (1.0*e3 + 2.0*ei)
        // attitudeIntern(CGAOrientedFiniteFlatIPNS) = (5.551115123125783E-17*eo^e1^e2 - 0.9999999999999996*e1^e2^ei)
        // die bestimmte attitude ist hier grade 3
        //TODO
        // vielleicht ist das aber richtig und ich muss hier einen AttitudeBivector
        // statt einen AttitudeVector erzeugen
        return new CGAAttitude(result);
        
        /*corresponds to
          Geometric Algebra: A powerful tool for solving geometric problems in visual computing
          Leandro A. F. Fernandes, and Manuel M. Oliveira
          DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
          2009
         */
        //return new CGAAttitudeVectorOPNS(createInf(-1d).lc(this.undual()).compress());
    }   
    
    /**
     * Location point of the flat nearest to to the given probe point.
     * 
     * corresponds to
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
     * 2009
     * if probe set to origin.
     * 
     * @param probe point
     * @return location as normalized dual sphere (grade 1)
     */
    CGAMultivector locationIntern(Point3d probe){
        return (new CGARoundPointIPNS(probe)).op(this).div(this).compress();
    }
    @Override
    public Point3d location(Point3d probe){
        CGAMultivector m = locationIntern(probe);
        // location_cga=2.0000000000000004*eo 
        // should be a normalized dual sphere (grade 1)
        //FIXME
        // Dorst2007 hat weitere Formeln um E3 komplett abzuspalten
        System.out.println(m.toString("location_cga (Finite flat)"));
        return m.extractE3ToPoint3d(); 
        // sollte funktionieren, da normalized dual sphere (grade 1) x,y,z 
        // als e1,e2,e3 direkt enthalten sollte
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
