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
abstract class CGAOrientedFiniteFlatIPNS extends CGAKVector {
     
    CGAOrientedFiniteFlatIPNS(CGAMultivector m){
        super(m);
    }
    CGAOrientedFiniteFlatIPNS(iCGAMultivector m){
        super(m);
    }
    
    public abstract Vector3d attitude();
    
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
    public CGAAttitude attitudeIntern(){
        // Sign of all coordinates change according to errato to the book Dorst2007
        // mir scheint hier wird von weight==1 ausgegangen. Das Vorzeichen könnte
        // vermutlich verschwinden, wenn ich die beiden Operanden vertausche
        // testweise normalisieren
        CGAMultivector result = createInf(1d).op(this.normalize()).undual().negate().compress();
        System.out.println(result.toString("attitudeIntern (CGAOrientedFiniteFlatIPNS, Dorst)"));
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
     * 
     * @param probe point
     * @return result as E3
     */
    public CGARoundPointIPNS locationIntern(Point3d probe){
        // input probe as normalized point
        // determination of result as normalized dual sphere 
        CGAMultivector m_n = this.normalize(); // scheint keinen Effekt gehabt zu haben
        CGARoundPointIPNS result =  new CGARoundPointIPNS((new CGARoundPointIPNS(probe)).op(m_n).div(m_n).compress());
        // (5*eo + 25*e1 + 25*e2 - 47*e3 + 23.5*ei)
        // warum enthält das eine ei component, damit wäre doch r != 0?
        System.out.println(result.toString("location as normalized dual sphere (CGAOrientedFiniteFlatIPNS, Dorst)"));
        //result.normalize(); // hat nichts gebracht
        //System.out.println(result.toString("location normalized dual sphere 2 (CGAOrientedFiniteFlatIPNS)"));
        return result;
    }
    @Override
    public Point3d location(Point3d probe){
        CGARoundPointIPNS res = locationIntern(probe);
        // extract E3 from normalized dual sphere
        // Dorst2007 p.409
        CGAMultivector oinf = CGAMultivector.createOrigin(1d).op(CGAMultivector.createInf(1d));
        CGAE3Vector result = new CGAE3Vector(oinf.lc(oinf.op(res)));
        System.out.println(result.toString("location E3 (from CGAOrientedFiniteFlatIPNS, Dorst)"));
        return result.location();
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
        CGAMultivector result = probePoint.op(this).gp(inverse());
        //CGAMultivector result = probePoint.ip(this, LEFT_CONTRACTION).gp(inverse());
        
        double[] locationCoord = result.impl.extractCoordinates(1);
        int index = result.impl.getEStartIndex();
        return new Decomposition3d.FlatAndDirectionParameters(attitude.extractAttitudeFromEeinfRepresentation(), 
               new Point3d(locationCoord[index++], locationCoord[index++], locationCoord[index]));
    }*/
    
}
