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
    
    
    // decompose
    
    @Override
    public iCGAFlat.EuclideanParameters decompose(){
        return new iCGAFlat.EuclideanParameters(attitude(), location(), 
                                      squaredWeight());
    }
    
    public iCGAFlat.EuclideanParameters decompose(Point3d probePoint){
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
    
    public Vector3d attitude(){
        return attitudeIntern().direction();
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
    public CGAAttitudeOPNS attitudeIntern(){
        // Sign of all coordinates change according to errato to the book Dorst2007
        // mir scheint hier wird von weight==1 ausgegangen. Das Vorzeichen könnte
        // vermutlich verschwinden, wenn ich die beiden Operanden vertausche
        // testweise normalisieren
        CGAMultivector result = inf.op(this).undual().negate().compress();
        System.out.println(result.toString("attitudeIntern (CGAOrientedFiniteFlatIPNS, Dorst)"));
        return new CGAAttitudeOPNS(result);
        
        /*corresponds to
          Geometric Algebra: A powerful tool for solving geometric problems in visual computing
          Leandro A. F. Fernandes, and Manuel M. Oliveira
          DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
          2009
         */
        //return new CGAAttitudeVectorOPNS(createInf(-1d).lc(this.undual()).compress());
    }   
    
    /**
     * Location point of the flat nearest to to the given probe point as 
     * normalized dual (ipns) sphere.
     * 
     * corresponds to
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10
     * 2009
     * 
     * @param probe point
     * @return location as normalized dual sphere
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
        //System.out.println(result.toString("location normalized dual sphere 2 (CGAFlatIPNS)"));
        return result;
    }
    @Override
    public Point3d location(Point3d probe){
        CGARoundPointIPNS res = locationIntern(probe);
        // extract E3 from normalized dual sphere
        // Dorst2007 p.409
        CGAMultivector oinf = o.op(inf);
        CGAEuclideanVector result = new CGAEuclideanVector(oinf.lc(oinf.op(res)));
        System.out.println(result.toString("location E3 (from CGAOrientedFiniteFlatIPNS, Dorst)"));
        return result.location();
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
        // damit bekomme ich die attitude in der Form I0.op(einfM)
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
