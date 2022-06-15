package de.orat.math.cga.api;

import de.orat.math.cga.impl1.CGA1Multivector1a;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import static de.orat.math.ga.basis.InnerProductTypes.RIGHT_CONTRACTION;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Quat4d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMultivector {
    
    private static CGAMultivector defaultInstance = new CGAMultivector();
    
    static int default_ip_type = LEFT_CONTRACTION;
    iCGAMultivector impl;
    
    CGAMultivector(){
        impl = new CGA1Multivector1a();
    }
    public CGAMultivector(Tuple3d p){
        this.impl = defaultInstance.impl.createE(p);
    }
    public CGAMultivector(double d){
        this.impl = defaultInstance.impl.createScalar(d);
    }
    
    CGAMultivector(iCGAMultivector impl){
        this.impl = impl;
    }
    
    
    public static CGAMultivector createOrigin(double scale){
        return new CGAMultivector(defaultInstance.impl.createOrigin(scale));
    }
    public static CGAMultivector createEinf(double scale){
        return new CGAMultivector(defaultInstance.impl.createInf(scale));
    }
    public static CGAMultivector createEx(double scale){
        return new CGAMultivector(defaultInstance.impl.createEx(scale));
    }
    public static CGAMultivector createEy(double scale){
        return new CGAMultivector(defaultInstance.impl.createEy(scale));
    }
    public static CGAMultivector createEz(double scale){
        return new CGAMultivector(defaultInstance.impl.createEz(scale));
    }
    public static CGAVector createE3(){
        return new CGAVector(createEx(1d).add(createEy(1d)).add(createEz(1d)));
    }
    public static CGAVector createE3(Vector3d v){
        return new CGAVector(createEx(v.x).add(createEy(v.y)).add(createEz(v.z)));
    }
    
      
    // Create conformal algebra primitives
    
    
    /**
     * Create sphere in inner product null space representation (grade 1 multivector).
     * 
     * Be careful: This is a dual real sphere corresponding to Dorst2007 but a 
     * real sphere in Hildenbrand2013.
     * 
     * @param o origin of the shphere
     * @param r radius of the sphere (A negative radius does not CGAMultivector an imagninary 
        sphere. Use the method createImaginarySphere() instead.)
     * @return multivector representation of a sphere (e0, e1, e2, e3, einfM)
     */
    public static CGAVector createSphere(Point3d o, double r){
        return createSphere(createPoint(o), r);
    }
    /**
     * Create sphere in inner product null space representation (grade 1 multivector).
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * Dorst2007 page 363 == Hildenbrand1998 page 29
     * 
     * @param location multivector representing a result
     * @param r radius of the sphere to CGAMultivector
     * @return multivector representing a sphere
     */
    public static CGAVector createSphere(CGAMultivector location, double r){
        return new CGAVector(location.sub(createEinf(0.5*r*r)));
    }
    public static CGAVector createImaginarySphere(CGAMultivector o, double r){
        return new CGAVector(o.add(createEinf(0.5*r*r)));
    }
    /**
     * Create dual sphere.
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * @param o origin of the sphere
     * @param p result on the sphere
     * @return dual sphere (inner product null space representation) as a multivector of grade 4.
     */
    public static CGAQuadvector createDualSphere(Point3d o, Point3d p){
        return new CGAQuadvector(createPoint(p).ip(createEinf(1d).op(createPoint(o))));
    }
    /**
     * Create dual sphere in outer product null space representation (grade 4 multivector).
     * 
     * @param p1 multivector representing a result on the sphere
     * @param p2 multivector representing a result on the sphere
     * @param p3 multivector representing a result on the sphere
     * @param p4 multivector representing a result on the sphere
     * @return multivector representing a dual sphere.
     */
    public static CGAMultivector createDualSphere(CGAMultivector p1, CGAMultivector p2, 
                                                  CGAMultivector p3, CGAMultivector p4){
        return p1.op(p2).op(p3).op(p4);
    }
    /**
     * Create dual sphere in outer product null space represenation (grade 4 multivector).
     * 
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return dual sphere in outer product null space representation
     */
    public static CGAMultivector createDualSphere(Point3d p1, Point3d p2, Point3d p3, Point3d p4){
        return createPoint(p1).op(createPoint(p2)).op(createPoint(p3)).op(createPoint(p4));
    }
    /**
     * Create a conformal point (grade 1 multivector) by up-projecting an euclidian vector
     * into a conformal vector.
     * 
     * Inner and outer product null space representation is identical.<p>
     * 
     * Multiplication of the multivector by double alpha possible.<p>
     * 
     * @param p result
     * @return conformal result
     */
    public static CGAMultivector createPoint(Tuple3d p){
        // old version
        //return createOrigin(1d)
        //        .add(createEx(p.x))
        //        .add(createEy(p.y))
        //        .add(createEz(p.z))
        //        .add(createEinf(0.5*(p.x*p.x+p.y*p.y+p.z*p.z)));
        CGAMultivector x = new CGAMultivector(p);
        return x.add((new CGAMultivector(0.5)).gp(x.gp(x)).gp(createEinf(1d))).add(createOrigin(1d));
    }
    
    /**
     * Create a conformal result in inner product null space representation (grade 4 multivector).
     * 
     * @param sphere1 first sphere in inner product null space representation
     * @param sphere2 seconds sphere in inner product null space represenation
     * @param sphere3 third sphere in inner product null space representation
     * @param sphere4 forth sphere in inner product null space represenation
     * @return conformal result in inner product null space representation
     */
    public static CGAMultivector createDualPoint(CGAMultivector sphere1, CGAMultivector sphere2, 
                                                 CGAMultivector sphere3, CGAMultivector sphere4){
        return sphere1.op(sphere2).op(sphere3).op(sphere4);
    }
    
    /**
     * Create plane in inner product null space representation (grade 1 multivector).
     * 
     * Be careful: This corresponds to dual plane in Dorst2007.
     * 
     * @param n normal vector of the plane
     * @param d distance of the plane to the origin
     * @return conformal plane in inner product null space representation (e1, e2, e3, einfM).
     */
    public static CGAMultivector createPlane(Vector3d n, double d){
        return createEx(n.x)
            .add(createEy(n.y))
            .add(createEz(n.z))
            .add(createEinf(d));
    }
    /**
     * Create plane in outer product null space representation (grade 4 multivector).
     * 
     * @param p1 first result in inner product null space representation
     * @param p2 second result in inner product null space representation
     * @param p3 third result in inner product null space representation
     * @return conformal plane in outer product null space representation.
     */
    public static CGAMultivector createDualPlane(CGAMultivector p1, CGAMultivector p2, CGAMultivector p3){
        return p1.op(p2).op(p3).op(createEinf(1d));
    }
    /**
     * Create a dual plane as a mid plane between two given result (in outer product
     * null space representation).
     * 
     * @param p1
     * @param p2
     * @return multivector representing a dual plane.
     */
    public static CGAMultivector createDualPlane(CGAMultivector p1, CGAMultivector p2){
        return createEinf(1d).op((p1.op(p2)).dual());
    }
    /**
     * Create dual plane from a result on the plane an its normal vector (in outer product
     * null space representation).
     * 
     * @param p result on the plane.
     * @param n normal vector.
     * @return multivector representing a dual plane
     */
    public static CGAMultivector createDualPlane(Point3d p, Vector3d n){
        CGAMultivector cp = createPoint(p);
        CGAMultivector cn = createPoint(n);
        return cp.ip(cn.op(createEinf(1d)));
    }
    /**
     * Create line in inner product null space representation (grade 2 multivector).
     * 
     * Be careful: This corresponds to a dual line in Dorst2007.
     * 
     * @param plane1 plane1 in inner product null space representation
     * @param plane2 plane2 in inner product null space representation
     * @return conformal line in inner product null space representation 
     */
    public static CGAMultivector createLine(CGAMultivector plane1, CGAMultivector plane2){
        return plane1.op(plane2);
    }
    /**
     * Create line in outer product null space representation (grade 3 multivector).
     * 
     * Be careful: This corresponds to a line in Dorst2007 but to a dual line in
     * Hildenbrand2013.
     * 
     * @param p1 first point on the line
     * @param p2 second point on the line or direction of the line
     * @return conformal line in outer product null space representation (tri-vector: 
     * (e12inf, e13inf, e23inf, e10inf, e20inf, e30inf = tri-vector))
     */
    public static CGATrivector createDualLine(Point3d p1, Tuple3d p2){
        return createDualLine(createPoint(p1), createPoint(p2));
    }
    /**
     * Create line in outer product null space representation (grade 3 multivector).
     * 
     * @param p1 first result in inner product null space representation
     * @param p2 seconds result in inner product null space representation
     * @return conformal line in outer product null space representation (tree-vector)
     * 
     * Be careful: The representation is called dual in Hildenbrand213 but not
     * in Dorst2007.
     */
    public static CGATrivector createDualLine(CGAMultivector p1, CGAMultivector p2){
        return new CGATrivector(p1.op(p2).op(createEinf(1d)));
    }
    
    /**
     * Create circle in inner product null space represenation (grade 2 multivector).
     * 
     * @param sphere1
     * @param sphere2
     * @return conformal circle
     */
    public static CGAMultivector createCircle(CGAMultivector sphere1, CGAMultivector sphere2){
        return sphere1.op(sphere2);
    }
    /**
     * Create dual circle in outer product null space representation (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     * @return 
     */
    public static CGAMultivector createDualCircle(CGAMultivector point1, CGAMultivector point2, CGAMultivector point3){
        return point1.op(point2).op(point3);
    }
    /**
     * Create dual circle in outer product null space representation (grade 3 multivector).
     * 
     * @param point1
     * @param point2
     * @param point3
     * @return multivector representing a dual circle.
     */
    public static CGAMultivector createDualCircle(Point3d point1, Point3d point2, Point3d point3){
        return createPoint(point1).op(createPoint(point2)).op(createPoint(point3));
    }
    
    /**
     * Create result pair in inner product null space representation (grade 3 multivector).
     * 
     * @param sphere1
     * @param sphere2
     * @param sphere3
     * @return multivector representing a point pair.
     */
    public static CGAMultivector createPointPair(CGAMultivector sphere1, CGAMultivector sphere2, CGAMultivector sphere3){
        return sphere1.op(sphere2).op(sphere3);
    }
    /**
     * Create dual result pair in outer product null space representation (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     * @return multivector representing a dual point pair.
     */
    public static CGAMultivector createDualPointPair(CGAMultivector point1, CGAMultivector point2){
        return point1.op(point2);
    }
    /**
     * Create dual result pair in outer product null space representation (grade 2 multivector).
     * 
     * @param point1
     * @param point2
     * @return multivector representing a dual point pair.
     */
    public static CGAMultivector createDualPointPair(Point3d point1, Point3d point2){
        return createPoint(point1).op(createPoint(point2));
    }
    
    /**
     * Create the pseudoscalar - The canonical rotor for the R41 of the conformal 
     * space vector base.
     * 
     * @return the multivector representing the pseudoscalar
     */
    public static CGAMultivector createPseudoscalar(){
        return createOrigin(1d).op(createEx(1d))
                .op(createEy(1d)).op(createEz(1d))
                .op(createEinf(1d));
    }
    
    /**
     * Create tangent vector which includes a result and a direction in inner product null space 
     * representation.
     * 
     * @param p result 
     * @param u direction of the tangent
     * @return bivector representing a tangend vector
     */
    public static CGAMultivector createTangentVector(Point3d p, Vector3d u){
        CGAMultivector cp = createPoint(p);
        return cp.ip(cp.op(createPoint(u)).op(createEinf(1d)));
    }
    
    /**
     * Create a parallelogram (area formed by two anchored vectors).
     * 
     * FIXME 
     * Dual?
     * 
     * @param v1
     * @param v2
     * @return multivector representing a parallelogram
     */
    public static CGAMultivector createDualParallelogram(Vector3d v1, Vector3d v2){
        return createE3(v1).op(createE3(v2));
    }
    /**
     * Create parallelepiped (volumen formed by three anchored vectors).
     * 
     * @param v1
     * @param v2
     * @param v3
     * @return multivector representing a parallelepiped
     */
    public static CGAMultivector createDualParallelepiped(Vector3d v1, Vector3d v2, Vector3d v3){
        return createE3(v1).op(createE3(v2)).op(createE3(v3));
    }
    
    
    
    // decompose
    
    /**
     * Extract the direction and location of a line/plane.
     * 
     * @param probePoint normalized probe result (e0=1, e1,e2,e3, einfM). If not specified use e0.
     * @return direction of the given flat
     */
    public Decomposition3d.FlatAndDirectionParameters decomposeFlat(CGAMultivector probePoint){
        // Kleppe2016
        //Multivector attitude = flat.ip(Multivector.createBasisVector(0), RIGHT_CONTRACTION)
        //        .ip(Multivector.createBasisVector(4), RIGHT_CONTRACTION);
        
        // use dualFlat in Dorst2007
        // damit bekomme ich die attitude in der Form E.op(einfM)
        // für attitude ist ein Vorzeichen nach Dorst2007 zu erwarten, scheint aber nicht zu stimmen
        CGAMultivector attitude = createEinf(1d).op(this).undual();
        // attitude=-5.551115123125783E-17*no^e1^e2 + 0.9999999999999996*e1^e2^ni
        System.out.println("attitude="+String.valueOf(attitude.toString()));
                
        // Dorst2007 - Formel für dual-flat verwenden
        // locations are determined as dual spheres
        CGAMultivector location = probePoint.op(this).gp(generalInverse());
        //CGAMultivector location = probePoint.ip(this, LEFT_CONTRACTION).gp(generalInverse());
        
        double[] locationCoord = location.impl.extractCoordinates(1);
        int index = location.impl.getEStartIndex();
        return new Decomposition3d.FlatAndDirectionParameters(attitude.extractDirectionFromEeinfRepresentation(), 
               new Point3d(locationCoord[index++], locationCoord[index++], locationCoord[index]));
    }
    
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
    public Decomposition3d.FlatAndDirectionParameters decomposeDualFlat(CGAMultivector probePoint){
        
        // Dorst2007
        //TODO funktioniert nicht - alle components sind 0
        // Ich brauchen undualize into the full space, macht das dual()?
        //CGAMultivector vector = new CGA1Multivector(Multivector.createBasisVector(4).op(this).dual(CGA1Metric.CGA_METRIC));
        //System.out.println("dirvec="+vector.toString(CGA1Metric.baseVectorNames)); // ==0
        
        // Bestimmung von E einf M
        // stimmt nicht
        //CGA1Multivector dir = CGA1Multivector.createBasisVector(4,-1d).ip(this, LEFT_CONTRACTION);
        // Vector3d attitude = dir.extractDirectionFromEeinfRepresentation();
        
        // Nach Kleppe2016
        CGAMultivector dir = rc(createOrigin(1d)).rc(createEinf(1d));
        // attitude=-0.9799999999999993*e1 statt (0.98,0.0,0.0) mit right contraction
        //FIXME Warum stimmt das Vorzeichen nicht?
        System.out.println("attitude Kleppe="+dir.toString()); 
        Vector3d attitude = dir.extractEuclidVector();
        System.out.println("attitude extraction=("+String.valueOf(attitude.x)+","+String.valueOf(attitude.y)+","+String.valueOf(attitude.z)+")");
        // Kleppe2016 adaptiert
        // oder left contraction?
        // left contraction ist null wenn k > l
        //dir = dualFlat.op(Multivector.createBasisVector(4)).ip(Multivector.createBasisVector(0), HESTENES_INNER_PRODUCT);
        //System.out.println("dirvec2="+vector.toString(CGA1Metric.baseVectorNames)); // ==0
        
        // Dorst2007
        // das sieht richtig aus! ist aber die Formel von dualflat statt flat
        CGAMultivector location = probePoint.op(this).gp(generalInverse());
        // Formel von flat - funktioniert nicht
        //CGAMultivector location = probePoint.ip(this, LEFT_CONTRACTION).gp(generalInverse());
         
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
    }
    
    public CGAMultivector decomposeTangentAndRoundDirectionAsMultivector(){
        // ungetestet
        CGAMultivector einfM = CGAMultivector.createEinf(-1d);
        CGAMultivector einf = CGAMultivector.createEinf(1d);
        return einfM.ip(undual()).op(einf);
    }
    /**
     * Decompose direction.
     * 
     * Dorst2007
     * 
     * @return direction
     */
    private Vector3d decomposeTangentAndRoundDirection(){
        CGAMultivector attitude = decomposeTangentAndRoundDirectionAsMultivector();
        //System.out.println("tangent(Eeinf)= "+attitude.toString(CGA1Metric.baseVectorNames));
        return attitude.extractDirectionFromEeinfRepresentation();
    }
    /**
     * Decompose dual tangent and round direction.
     * 
     * ungetestet
     * 
     * @return direction
     */
    private Vector3d decomposeDualTangentAndRoundDirection(){
        CGAMultivector einf = CGAMultivector.createEinf(1d);
        CGAMultivector attitude = einf.ip(this).op(einf);
        Vector3d result = attitude.extractDirectionFromEeinfRepresentation();
        //FIXME
        // unklar ob das negate überhaupt an den schluss verschoben werden darf
        result.negate();
        return result;
    }
    /**
     * Decompose tangent.
     * 
     * Dorst2007
     * 
     * Keep in mind: Corresponding to Dorst2007 dual and not-dual ist switched.
     * 
     * @return direction and location, radius=0
     */
    public Decomposition3d.RoundAndTangentParameters decomposeTangent(){
        return new Decomposition3d.RoundAndTangentParameters(decomposeTangentAndRoundDirection(), decomposeTangentAndRoundLocation(), 0d);
    }
    
    /**
     * Decompose dual tangend.
     * 
     * ungetestet
     * 
     * @return direction and location, radius=0
     */
    public Decomposition3d.RoundAndTangentParameters decomposeDualTangent(){
        return new Decomposition3d.RoundAndTangentParameters(decomposeDualTangentAndRoundDirection(), 
                decomposeTangentAndRoundLocation(), 0d);
    }
    
    /**
     * Determines the location from rounds, dual-round, tangent and dual-tangent.
     * 
     * Dorst2007
     * 
     * @return location
     */
    private Point3d decomposeTangentAndRoundLocation(){
        
        // vermutlich stimmt das so nicht?
        
        // decompose location as a sphere (dual sphere in Dorst2007)
        // Vorzeichen wird unten gedreht
        // das sollte aber ein normalized dual sphere ergeben
        CGAMultivector location = 
                gp(createEinf(1d).ip(this).generalInverse());
        // das ergibt einen reinen vector (1-blade)
        System.out.println("location1="+location.toString());
        /*double[] vector = location.extractCoordinates(1);
        Point3d result = new Point3d(vector[1], vector[2], vector[3]);
        result.negate();*/
        
        // Hildenbrand2004 (Tutorial)
        location = gp(CGAMultivector.createEinf(1d)).gp(this).div((createEinf(1d).ip(this)).sqr()).gp(-0.5);
        System.out.println("location="+location.toString());
        double[] vector = location.impl.extractCoordinates(1);
        int index = location.impl.getEStartIndex();
        Point3d result = new Point3d(vector[index++], vector[index++], vector[index]);
        return result;
    }
    
    /**
     * Decompose round object.
     * 
     * Dorst2007
     * 
     * @return attitude, location and squared size for multivectors corresponding to rounds in
     * inner product null space representaton
     */
    public Decomposition3d.RoundAndTangentParameters decomposeRound(){
        // (-) because the radius for dual round corresponding to Dorst2007 is needed to
        // get the value corresponding to inner product null space representation
        return new Decomposition3d.RoundAndTangentParameters(decomposeTangentAndRoundDirection(), 
                decomposeTangentAndRoundLocation(), -roundSquaredSize());
    }
    
    /**
     * Decompose a sphere. 
     * 
     * Only for testing. 
     * 
     * @Deprecated use decomposeRound instead.
     * @return location and squared-radius, direction=(0,0,0)
     */
    public Decomposition3d.RoundAndTangentParameters decomposeSphere(){
        double[] result = impl.extractCoordinates(1);
        int index = impl.getEStartIndex();
        int einfIndex = impl.getEinfIndex();
        return new Decomposition3d.RoundAndTangentParameters(new Vector3d(), 
                new Point3d(result[index++], result[index++], result[index]), -2d*result[einfIndex]);
    }
    
    /**
     * Decompose rotation around origin.
     * 
     * @return quaternion representing a rotation around the origin
     */
    public Quat4d decomposeRotation(){
        Quat4d result = new Quat4d();
        result.w = impl.extractCoordinates(0)[impl.getOriginIndex()];
        double[] vector = impl.extractCoordinates(2);
        //TODO weitere Indizes definieren
        result.x = -vector[4]; // i
        result.y = vector[5];  // j
        result.z = -vector[8]; // k
        return result;
    }
    
    /**
     * Determine squared radius for a round.
     * 
     * Dorst2007
     * 
     * FIXME
     * Da das Ergebnis nicht stimmt befürchte ich, dass die Formel nur für Kugeln
     * im Ursprung gilt.
     * 
     * @return squared size/radius for a round corresponding of Dorst2007 and (-) 
     * squared size/radius for dual round
     */
    private double roundSquaredSize(){
        CGAMultivector mvNumerator = gp(gradeInversion());
        CGAMultivector mvDenominator = createEinf(1d).ip(this);
        
        // (-) d.h. das ist die Formel für dual-round nach Dorst2007. Das sieht also
        // richtig aus.
        // aber der Radius im Test stimmt nur ungefährt mit dem ursprünglichen überein
        // vermutlich Probleme mit der norm-Berechnung? radius = 2.061455835083547 statt 2.0
        //FIXME
        //double squaredSize = mvNumerator.gp(-1d/mvDenominator.norm_e2()).scalarPart();
        return mvNumerator.gp(-1d/mvDenominator.squaredNorm()).scalarPart(); 
        //return mvNumerator.gp(-1d/(2d*mvDenominator.squaredNorm())).scalarPart(); // *2.0 aus Hildenbrand, wird noch falscher
    }
    
    /**
     * Decompose dual round.
     * 
     * Dorst2007
     * 
     * @return attitude, location and radius
     */
    public Decomposition3d.RoundAndTangentParameters decomposeDualRound(){
        return new Decomposition3d.RoundAndTangentParameters(decomposeDualTangentAndRoundDirection(), 
                decomposeTangentAndRoundLocation(), -roundSquaredSize());
    }
    
    /**
     * Decompose the geometric product of two lines.
     *
     * @return dij and P if l1 and l2 are not coincident and not parallel else an empty array
     */
    public Decomposition3d.LinePairParameters decomposeLinePair(){
        
        // X soll eine sum aus 1- und 2-blade sein
        // falsch da sind auch zwei 4-blades mit drin
        CGAMultivector n0 = CGAMultivector.createOrigin(1d);
        CGAMultivector ni = CGAMultivector.createEinf(1d);
        CGAMultivector X = sub(n0.ip(this).op(createEinf(1d)));
        System.out.println("X="+X.toString());
        
        // scheint korrekt sum aus 3- und 1-blade
        CGAMultivector Y = n0.ip(this);
        System.out.println("Y="+Y.toString());
        CGAMultivector Y3 = Y.extractGrade(3);
        System.out.println("Y3="+Y3.toString());
        
        CGAMultivector X2 = X.extractGrade(2);
        System.out.println("X2="+X2.toString());
         // quatrieren und test auf !=0
        CGAMultivector X22 = X2.gp(X2);
        
        
        // identisch
        if (this.isScalar()){
            return new Decomposition3d.LinePairParameters(0d, null, null);
        // coplanar
        } else if (X2.isNull()){
            // parallel
            if (Y3.op(ni).isNull()){
                Vector3d dir = new Vector3d();
                //TODO
                return new Decomposition3d.LinePairParameters(0d, null, dir);
            // coplanar mit Schnittpunkt    
            } else {
                //TODO
                double alpha=0;
                Point3d p = new Point3d();
                //TODO
                return new Decomposition3d.LinePairParameters(alpha, p, null);
            }
        // skewed
        } else {
            CGAMultivector d = Y3.gp(X2.reverse().gp(1d/X2.squaredNorm()));
            System.out.println("d="+d.toString());

            double[] dValues = d.impl.extractCoordinates(2);
         
            double alpha = 0d;
            Vector3d p = new Vector3d(); 
            return new Decomposition3d.LinePairParameters(alpha, new Point3d(p.x,p.y,p.z), 
                    //TODO indizes?
                    new Vector3d(dValues[0], dValues[1], dValues[2]));
        }
    }
    
    // Decompose weight
    // eventuell in die vector records einbauen
    // Problem: Die Methoden zur Bestimmung der attitude lieferen direkt Vector3d und nicht result
    // das könnte ich aber ändern
    //TODO
    /**
     * Determine the squared weight of an CGA object.
     * 
     * @param attitude direction specific for the object form the multivector is representing
     * @param probePoint If not specified use e0.
     * @return squared weight
     */
    public static double decomposeSquaredWeight(CGAMultivector attitude, CGAMultivector probePoint){
        CGAMultivector A = probePoint.ip(attitude);
        return A.reverse().gp(A).scalarPart();
    }
    /**
     * Determine the weight of an CGA object.
     * 
     * @param attitude direction specific for the object form the multivector is representing
     * @param probePoint If not specified use e0.
     * @return 
     */
    public static double decomposeWeight(CGAMultivector attitude, CGAMultivector probePoint){
        return Math.sqrt(Math.abs(decomposeSquaredWeight(attitude, probePoint)));
    }
    
    /**
     * Intersection of lines, planes and spheres.
     * 
     * FIXME
     * ist das überhaupt so sinnvoll - besser meet oder vee verweden?
     * 
     * @param mv2 Line, Plane, Sphere
     * @return 
     */
    public CGAMultivector intersect(CGAMultivector mv2){
        return createPseudoscalar().gp(this).ip(mv2);
    }
    
    /**
     * Computes the meet with the specified element in a common subspace.
     * 
     * @param mv the second element of the meet.
     * @return a new element from the meet with the specified element.
     */
    public final CGAMultivector meet(final CGAMultivector mv){
       return new CGAMultivector(impl.meet(mv.impl));
    }

    /**
     * Computes the meet with the specified element in a common subspace.
     * 
     * @param mv1 the second element of the meet.
     * @param mv2 the element representing a common subspace.
     * @return a new element from the meet with the specified element.
     */
    public final CGAMultivector meet(CGAMultivector mv1, CGAMultivector mv2){
       return new CGAMultivector(impl.meet(mv1.impl,mv2.impl));
    }
    
    
    /**
     * Computes the commutation with the specified element.
     * 
     * @param mv the second element of the commutation.
     * @return a new element from the commutation with the specified element.
     */
    public final CGAMultivector commutation(CGAMultivector mv){
        return ((gp(mv)).sub(mv.gp(this))).gp(0.5);
    }
    
    
    // monadic operators
    
    public CGAMultivector reverse(){
        return new CGAMultivector(impl.reverse());
    }
    public CGAMultivector sqr(){
        return gp(this);
    }
    /**
     * The Duality operator implements Poincare duality, a definition and 
     * implementation that works even if the pseudoscalar of the subspace in 
     * consideration is degenerate. 
     * 
     * It is defined for any k-vector x of an 
     * n-dimensional subspace as the n-k vector y containing all the basis
     * vectors that are not in x. For non-degenerate metrics, you can use 
     * multiplication with the pseudoscalar if so desired (although it will be 
     * less efficient). This is not possible for CGA because of its degenerate
     * metric.
     * 
     * @return the dual of this multivector
     */
    public CGAMultivector dual(){
        return new CGAMultivector(impl.dual());
    }
    public CGAMultivector undual(){
        return new CGAMultivector(impl.undual());
    }
    public CGAMultivector generalInverse(){
        return new CGAMultivector(impl.generalInverse());
    }
    public double squaredNorm(){
        return impl.squaredNorm();
    }
    /**
     * Calculate the Euclidean norm. (strict positive).
     * 
     * @return euclidean norm
     */
    public double norm(){
        return Math.sqrt(Math.abs(squaredNorm()));
    }
    /**
     * Calculate the Ideal norm. (signed)
     * 
     * FIXME das ist doch mit dieser Implementierung immer positiv. Der Code
     * stammt aus ganja.js. Da stimmt irgendwas nicht und mir ist unklar wozu
     * ich das überhaupt brauche.
     * 
     * inline float CGA::inorm() { return (!(*this)).norm(); }
     * Was bedeutet das ! in ganja.js : dual
     * 
     * @return ideal norm
     */
    public double idealNorm(){
        return dual().norm();
    }
    
    public boolean isNull(){
        return impl.isNull();
    }
    public boolean isScalar(){
        return impl.isScalar();
    }
    
    /**
     * Verifies if this element is a vector.
     * 
     * @return true if this element is a vector, false otherwise.
     */
    /*public boolean isVector(){
       // getMaxGrade() ins spi einfügen, impl bisher nur für JClifford und CGAMultivector1a/Multivector vorhanden
       // für CGAMultivector2 auf Basis von CGA sollte das aber leicht zu implementieren sein
       // und was ist mit get(0)?
       return ((get(0) == 0.0) && (getMaxGrade() == 1));
    }*/
    
    public double scalarPart(){
        return impl.scalarPart();
    }
    
    
    // dual operators
    
    /**
     * Computes the commutation with the specified element.
     * 
     * @param cl the second element of the commutation.
     * @return a new element from the commutation with the specified element.
     */
    public final CGAMultivector commutaton(final CGAMultivector cl){
        return ((gp(cl)).sub(cl.gp(this))).gp(0.5);
    }
   
    /**
     * @Deprectated brauche ich vermutlich gar nicht
     * 
     * @param x
     * @return 
     */
    public double scp(CGAMultivector x) {
        return impl.scp(x.impl);
    }
    
    /**
     * Inner or dot product.
     * 
     * The dot product implemented is per default the left contraction - without 
     * any extensions or modifications. The geometric meaning is usually 
     * formulated as the dot product between x and y gives the orthogonal
     * complement in y of the projection of x onto y.<p>
     * 
     * @param y right side argument of the inner product
     * @return inner product of this with a 'y'
     */
    public CGAMultivector ip(CGAMultivector y){
        return new CGAMultivector(impl.ip(y.impl, default_ip_type));
    }
    public CGAMultivector rc(CGAMultivector x){
         return new CGAMultivector(impl.ip(x.impl, RIGHT_CONTRACTION));
    }
    public CGAMultivector lc(CGAMultivector x){
         return new CGAMultivector(impl.ip(x.impl, LEFT_CONTRACTION));
    }
    public CGAMultivector gp(CGAMultivector x){
        return new CGAMultivector(impl.gp(x.impl));
    }
    public CGAMultivector gp(double x){
        return new CGAMultivector(impl.gp(x));
    }
    public CGAMultivector div(CGAMultivector x){
        return gp(x.generalInverse());
    }
    public CGAMultivector op(CGAMultivector x){
         return new CGAMultivector(impl.op(x.impl));
    }
    public CGAMultivector vee(CGAMultivector x){
        return dual().op(x.dual()).undual();
    }
    
    public CGAMultivector add(CGAMultivector b){
        return new CGAMultivector(impl.add(b.impl));
    }
    public CGAMultivector sub(CGAMultivector b){
        return new CGAMultivector(impl.sub(b.impl));
    }
    
    public CGAMultivector abs(){
        return new CGAMultivector(impl.abs());
    }
    
    public CGAMultivector extractGrade(int grade){
        return new CGAMultivector(impl.extractGrade(grade));
    }
    public CGAMultivector normalize(){
        return new CGAMultivector(impl.normalize());
    }
    public CGAMultivector gradeInversion(){
        return new CGAMultivector(impl.gradeInversion());
    }
    @Override
    public String toString(){
        return impl.toString();
    }
    
    
    // coordinates extraction
    
    /**
     * Extract direction from E einf multivector representation.
     * 
     * @return direction
     */
    private Vector3d extractDirectionFromEeinfRepresentation(){
        double[] coordinates = impl.extractCoordinates(3);
        //FIXME indizes hängen von der impl ab
        return new Vector3d(coordinates[9], coordinates[8], coordinates[7]);
    }
    private Vector3d extractEuclidVector(){
        double[] coordinates = impl.extractCoordinates(1);
        int index = impl.getEStartIndex();
        return new Vector3d(coordinates[index++], coordinates[index++], coordinates[index]);
    }
    
    
    
    /**
      * Decompose l2l1 into angle, distance, direction.
      * 
      * A Covariant approach to Geometry using Geometric Algebra.
      * Technical report. Universit of Cambridge Departement of Engineering, 
      * Cambridge, UK (2004). 
      * A. Lasenby, J. Lasenby, R. Wareham
      * Formula 5.22, page 46
      * 
      * @param l2l1
      * @return parameters describing the pose of two lines to each other
      */
    public static Decomposition3d.LinePairParameters decomposeLinePair(CGAMultivector l2l1){
        
        System.out.println("l2l1:"+l2l1.toString());
        
        // Skalar
        double cosalpha = l2l1.impl.extractCoordinates(0)[0];
        System.out.println("cosalpha="+String.valueOf(cosalpha));
        System.out.println("alpha="+String.valueOf(Math.acos(cosalpha)*180/Math.PI));
       
        // Bivektoren 
        double[] bivectors = l2l1.impl.extractCoordinates(2);
        double[] quadvectors = l2l1.impl.extractCoordinates(4);
        
        // attitude zeigt von l1 nach l2?
        
        double dist = 0d;
        double sinalpha = 0;
        
        org.jogamp.vecmath.Vector3d attitude = null;
        
        // Geraden nicht senkrecht zueinander
        if (cosalpha != 0){
            System.out.println("attitude aus e01, e02 und e03 bestimmen!");
            attitude = new org.jogamp.vecmath.Vector3d(
                -bivectors[0]/cosalpha, -bivectors[1]/cosalpha, -bivectors[2]/cosalpha);
            System.out.println("d(vectors)= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            dist = attitude.length();
            System.out.println("dist = "+dist);
            attitude.normalize();
            System.out.println("d(vectors) normiert= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            
        } 
            
        // Geraden sind nicht parallel
        if (cosalpha*cosalpha != 1){
            System.out.println("attitude aus e23, e13, e12 und e0123 bestimmen!");
            double cos2alpha = 1d-cosalpha*cosalpha;
            attitude = new org.jogamp.vecmath.Vector3d(
                -bivectors[7]*quadvectors[0]/cos2alpha, 
                 bivectors[5]*quadvectors[0]/cos2alpha, 
                -bivectors[4]*quadvectors[0]/cos2alpha);
            System.out.println("d(vectors)= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            dist = attitude.length();
            System.out.println("dist = "+dist);
            attitude.normalize();
            System.out.println("d(vectors) normiert= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            
        } 
         
        // Geraden haben keinen Schnittpunkt
        if (dist != 0d){
            sinalpha = -quadvectors[0]/dist;
        } else {
            System.out.println("Geraden schneiden sich!");
            //FIXME
            // ist das so richtig?
            sinalpha = 0d;
        }
        //TODO
        Point3d location = null;
        
        return new Decomposition3d.LinePairParameters(Math.atan2(cosalpha, sinalpha), location, attitude);
    }
}
