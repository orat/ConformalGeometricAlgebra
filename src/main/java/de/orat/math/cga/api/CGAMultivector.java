package de.orat.math.cga.api;

import de.orat.math.cga.api.iCGAPointPair.PointPair;
import de.orat.math.cga.impl1.CGA1Multivector;
import de.orat.math.cga.spi.iCGAMultivector;
import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import static de.orat.math.ga.basis.InnerProductTypes.RIGHT_CONTRACTION;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Quat4d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * https://github.com/pygae/clifford/blob/master/clifford/_multivector.py
 */
public class CGAMultivector {
    
    /**
     * Algebra's Precision.
     */
    public static double eps = 1e-12;

    static CGAMultivector defaultInstance = new CGAMultivector();
    
    static int default_ip_type = LEFT_CONTRACTION;
    iCGAMultivector impl;
    
    // do not change the scale of the following static constants
    public static final CGAMultivector o = createOrigin(1d);
    public static final CGAMultivector inf = createInf(1d);
    public static final CGAMultivector I3 = createI3();
    public static final CGAMultivector I3i = I3.inverse();
    public static final CGAMultivector I = createI();
    public static final CGAMultivector Ii = o.op(I3i).op(inf);
    public static final CGAMultivector I0 = o.op(inf); //inf.op(o);
    
    CGAMultivector(){
        impl = new CGA1Multivector();
    }
    CGAMultivector(double[] values){
        this.impl = defaultInstance.impl.create(values).getCompressed();
    }
    
    public static CGAMultivector create(double[] values, boolean isIPNS){
        if (values.length != 32) throw new IllegalArgumentException("double[] has not the length 32 but \""+
                String.valueOf(values.length+"\"!"));
        CGAMultivector m = new CGAMultivector(values);
        if (isIPNS){
            if (CGARoundIPNS.typeof(m)){
                //TODO
                // test ob circle, point, ...
                return new CGAOrientedPointIPNS(m);
            } 
        } else {
             if (CGARoundIPNS.typeof(m)){
                //TODO
                // test ob circle, point, ...
                return new CGAOrientedPointOPNS(m);
            } 
        }
        System.out.println("Subtype of \""+m.toString("")+"\" not detected!");
        return m;
    }
    
    /*public CGAMultivector(Tuple3d p){
        this.impl = defaultInstance.impl.createE(p);
    }*/
    /*public CGAMultivector(double d){
        this.impl = defaultInstance.impl.createScalar(d);
    }*/
    
    CGAMultivector(iCGAMultivector impl){
        this.impl = impl;
    }
    
    CGAMultivector compress(){
        return new CGAMultivector(impl.getCompressed());
    }
    
    // The origin and the Inf extends the Euclidian space to the Minkovski space.
    
    /**
     * This corresponds to the last base vector in homogeneous vector algebra. 
     * 
     * It enables us to work projectively. It represents the origin of the 
     * subspace and therefore removes the singuarlity of the represented 
     * Euclidean space.
     * 
     * @param scale
     * @return 
     */
    public static CGAMultivector createOrigin(double scale){
        return new CGAMultivector(defaultInstance.impl.createOrigin(scale));
    }
    /**
     * Inf encodes the metric of an Euclidean space (projectively represented space). 
     * 
     * For a geometrical CGA-round point this factor represents the distance from 
     * that point to the origin.
     * 
     * This basis blade is also called "reciprocal null vector" together with the
     * basis vector representing the origin (createOrigin()).
     * 
     * @param scale
     * @return base vector representing the point at infinity
     */
    public static CGAMultivector createInf(double scale){
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
    /*public static CGAEuclideanVector createE3(){
        return new CGAEuclideanVector(createEx(1d).add(createEy(1d)).add(createEz(1d)));
    }*/
    /**
     * Create an E3 Vector or 0-Vector if the given argument is a null-vector.
     * 
     * @param v
     * @return E3 vector
    */
    public static CGAEuclideanVector createE3(Tuple3d v){
            return new CGAEuclideanVector(createEx(v.x).add(createEy(v.y)).add(createEz(v.z)));
    }
    // sollte identisch zur createE3 sein? Nein!
    public static CGAMultivector createI3(){
        // da könnte ich doch gleich den richtigen Blade in einem Schritt erzeugen
        //FIXME
        return createEx(1d).op(createEy(1d)).op(createEz(1d));
    }
    
    public Vector3d extractE3ToVector3d(){
        double[] vector = impl.extractCoordinates(1);
        int index = impl.getEStartIndex();
        return new Vector3d(vector[index++], vector[index++], vector[index]);
    }
    public Point3d extractE3ToPoint3d(){
        double[] vector = impl.extractCoordinates(1);
        int index = impl.getEStartIndex();
        return new Point3d(vector[index++], vector[index++], vector[index]);
    }
    
    // s, e0, e1, e2, e3, einf, e01, e02, e03, e0inf, e12, e13, e1inf, e23, e2inf, e3inf
    // e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, e0123, e012inf, e013inf, e023inf, e123inf, e0123inf
    public double[] extractCoordinates(){
        return impl.extractCoordinates();
    }
    public String[] basisBladeNames(){
        return impl.basisBladeNames();
    }
    
    /**
     * Comparison of two multivectors.
     * 
     * @param M
     * @return false in at minimum one of the 32 compontents differs more than the 
     * precision (defined as eps) between the two multivectors
     */
    public boolean equals(CGAMultivector M){
        double[] A = this.extractCoordinates();
        double[] B = M.extractCoordinates();
        double precision = CGAMultivector.eps;
        for (int i=0;i<32;i++){
            if (Math.abs(A[i]-B[i]) > precision){
                return false;
            }
        }
        return true;
    }
    
    // Create conformal algebra primitives
    
    //TODO
    // in eigene Klasse auslagern
    /*public static CGAE3Vector createImaginarySphere(CGAMultivector o, double r){
        return new CGAE3Vector(o.add(createInf(0.5*r*r)));
    }*/
   
    /**
     * Create the pseudoscalar - The canonical rotor for the R41 of the conformal 
     * space vector base.
     * 
     * @return the multivector representing the pseudoscalar
     */
    private static CGAMultivector createI(){
        // da könnte ich doch gleich den richtigen Blade in einem Schritt erzeugen
        //FIXME
        // neu 28.2.23 Methode der Implementierung verwenden
        return new CGAMultivector(defaultInstance.impl.createI());
        /*return createOrigin(1d).op(createEx(1d))
                .op(createEy(1d)).op(createEz(1d))
                .op(inf);*/
    }
   
    public static CGAMultivector createE(double value){
        return createI().gp(value);
    }
    
    /**
     * Create a parallelogram (area formed by two anchored vectors).
     * 
     * FIXME 
     * Dual?
     * 
     * TODO
     * in eigene Class auslagern?
     * @param v1
     * @param v2
     * @return multivector representing a parallelogram
     * @throws IllegalArgumentException if v1 = (0,0,0) or v2=(0,0,0)
     */
    public static CGAMultivector createDualParallelogram(Vector3d v1, Vector3d v2){
        return createE3(v1).op(createE3(v2));
    }
    /**
     * Create parallelepiped (volumen formed by three anchored vectors).
     * 
     * TODO
     * in eigene class auslagern?
     * 
     * @param v1
     * @param v2
     * @param v3
     * @return multivector representing a parallelepiped
     * @throws IllegalArgumentException if v1 = (0,0,0) or v2=(0,0,0) or v3=(0,0,0)
     */
    public static CGAMultivector createDualParallelepiped(Vector3d v1, Vector3d v2, Vector3d v3){
        return createE3(v1).op(createE3(v2)).op(createE3(v3));
    }
    
    
   
    /**
     * Determines location from tangent (direct/dual) and round (direct/dual) 
     * objects.
     * 
     * scheint für CGAOrientedFiniteRoundOPNS um einen faktor 2 falsch zu sein in allen Koordinaten
     * scheint für CGARound zu stimmen
     * scheint mit CGATangent nicht zu stimmen??? mittlerweile korrigiert?
     * scheint mit CGAOrientedPointPair zu stimmen
     * vielleicht muss das object vorher normalisiert werden
     * TODO
     * 
     * @return location represented by a normalized sphere/finite point (dual sphere corresponding to Dorst2007)
     */
    protected CGARoundPointIPNS locationFromTangentAndRoundAsNormalizedSphere(){
        // corresponds to the errata of the book [Dorst2007]
        // and also Fernandes2009 supplementary material B
        // location as finite point/dual sphere corresponding to [Dorst2007]
        // createInf(-1d).ip(this) ist die Wichtung, es wird also durch die Wichtung geteilt,
        // d.h. der Punkt wird normiert?
        System.out.println(this.toString("tangentOrRound"));
        // circleipns:
        // tangentOrRound = (-0.24011911*eo^e1 + 0.39999987*eo^e2 + 0.3904*eo^e3 - 
        // 0.35230035*eo^ei - 0.04229702*e1^ei + 0.07046005*e2^ei + 0.06876903*e3^ei)

        // CGARoundPointIPNS schlägt fehl mit:
        // The given multivector is no blade: 1.0000000000000002*eo + 0.2285897790690278*e1 
        // - 0.3807938564778948*e2 - 0.371654924710276*e3 + 0.159234453488245*ei - 5.391337279392339E-9*e1^e2^ei 
        // - 4.515812467020819E-9*e1^e3^ei - 1.24294008418957E-9*e2^e3^ei

        // FIXME unklar, ob Normierung notwendig ist
        CGAMultivector mn = this.normalize();
        
        // The given multivector is no k-vector: 1.0000000000000002*eo + 0.220516482273777*e1 + 
        // 0.36734504072736274*e2 + 0.1835667072335959*e3 - 
        // 0.3445510478954946*e1^e2^e3 + 0.18137731261233903*ei - 0.28195912872783596*e1^e2^ei + 0.253138237443652*e1^e3^ei - 0.15195837009131624*e2^e3^ei
        // schlägt fehlt bei imaginary point-pair == ipns circle. da rountpointipns keine 3-er-Komponenten enthalten darf
        CGARoundPointIPNS result = new CGARoundPointIPNS((mn.negate().div(inf.lc(mn))).compress());
        //CGARoundPointIPNS result = new CGARoundPointIPNS(this.div(createInf(1d).ip(this)).negate().compress());
        // z.B. locationFromTangentAndRound=eo + 0.02*e1 + 0.02*e2 + e3 + 0.5*ei
        // bei input von p=(0.02,0.02,1.0), funktioniert, aber vermutlich nur,
        // da hier die Wichtung bei e0==1 ist.
        // locationFromTangentAndRound=2.0*eo + 0.5000000000000001*e2 - 0.5*ei
        // hiermit funktioniert es nicht mehr
        System.out.println("locationFromTangentAndRound="+result.toString());
        
        // center of this round, as a null vector
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py Zeile 284:
        // self.mv * self.cga.einf * self.mv // * bedeutet geometrisches Produkt
        //TODO ausprobieren?
        
        // euclidean part rausziehen, scheint zu funktionieren
        CGAMultivector o = CGAMultivector.createOrigin(1d);
        CGAMultivector resultEuclidean = o.op(inf).ip(o.op(inf).op(result));
        // location (decomposed) euclidean only = (0.4999999999999998*e2)
        // FIXME nur halb so gross wie ursprünglich
        // bei circleipns ==0 FIXME
        System.out.println(resultEuclidean.toString("location (decomposed) euclidean only"));
        return result;
    }
    /**
     * Determines location from tangend and round objects and also from its dual.
     * 
     * @return location in the euclidian part directly.
     */
    protected CGAMultivector locationFromTangendAndRound(){
        // corresponds to the errata of the book Dorst2007
        CGAMultivector result = (this.gp(inf).gp(this)).div((inf.ip(this)).sqr()).gp(-0.5d);
        System.out.println("locationFromTangentAndRound2="+result.toString());
        return result;
    }
    /**
     * Determines the squared weight (without sign) based on the attitude and 
     * the origin as probe point.
     * 
     * @return squared weight >0
     */
    public double squaredWeight(){
        return squaredWeight(new Point3d(0d,0d,0d));
    }
    /**
     * Determines the squared weight (without sign) based on the attitude.
     * 
     * @param probePoint
     * @return squared weight > 0
     */
    public double squaredWeight(Point3d probePoint){
        // probePoint(0,0,0)=1.0*eo
        // System.out.println("probePoint(0,0,0)="+probePointCGA.toString());
        return squaredWeight(attitudeIntern(), new CGARoundPointIPNS(probePoint));
    } 
    // bekomme ich da nicht immer einen AttitudeVector zurück?
    //FIXME
    /**
     * Determine the attitude/direction as (I0 inf). 
     * 
     * @return 
     */
    protected CGAMultivector attitudeIntern(){
        throw new RuntimeException("Not implemented. Available for derivative classes only!");
    }
    /**
     * Determination of the location of the corresponding geometric object, if 
     * available.
     * 
     * @param probe
     * @return location
     * @throws RuntimeException if location is not available
     */
    public Point3d location(Point3d probe){
        throw new RuntimeException("Available for most of the derivative classes only!");
    }
    /**
     * Determination of location of the corresponding geometric object, based 
     * on default euclidiean probe point at (0,0,0).
     * 
     * @return location based on probe point = (0,0,0)
     * @throws RuntimeException if location is not available
     */
    public Point3d location(){
        return location(new Point3d(0d,0d,0d));
    }
    public CGARoundPointIPNS locationIntern(){
        return new CGARoundPointIPNS(location());
    }
   
    /**
     * Determine the squared weight (without sign) of any CGA object.
     * 
     * @param attitude direction specific for the object form the multivector is representing
     * @param probePoint If not specified use e0.
     * @return squared weight >0
     */
    protected static double squaredWeight(CGAMultivector attitude, CGARoundPointIPNS probePoint){
        return Math.abs(probePoint.lc(attitude).sqr().decomposeScalar());
        // liefert gleiches Ergebnis
        // CGAMultivector A = probePoint.ip(attitude);
        //return A.reverse().gp(A).decomposeScalar();
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
     * Defined only on blades.
     * 
     * @param mv1 the second element (blade) of the meet.
     * @param mv2 the element (blade) representing a common subspace.
     * @return a new element from the meet with the specified element.
     */
    public final CGAMultivector meet(CGAMultivector mv1, CGAMultivector mv2){
       return new CGAMultivector(impl.meet(mv1.impl,mv2.impl));
    }
    
    public final CGAMultivector join(final CGAMultivector mv){
        return new CGAMultivector(impl.join(mv.impl));
    }
    
    /**
     * Computes the commutation with the specified element.
     * 
     * linear differential
     * commutator durch X darstellen als eigenes Symbol
     * a × B = 0.5 (aB − B a)
     * 
     * @param mv the second element of the commutation.
     * @return a new element from the commutation with the specified element.
     */
    public final CGAMultivector commutation(CGAMultivector mv){
        return ((gp(mv)).sub(mv.gp(this))).gp(0.5);
    }
    
    
    // monadic operators
    
    // An Involution is an operation which maps an operand to itself, when applied
    // twice. There exist three types: inversion, reverse and a combination of both
    // called conjugation.
    
    /**
     * Reverse.
     * 
     * The reverse operation is the most needed involution operation.
     * 
     * @return the reverse of the object. 
     */
    public CGAMultivector reverse(){
        return new CGAMultivector(impl.reverse());
    }
    
    /**
     * Inversion is a reflection in e+, this swaps e0 and ei.
     * 
     * @return space inversion (reflection on a sphere?)
     *
     * cluscript: The inversion is obtained with the ! operator. If a multivector has no
     * inverse then zero is returned.
     */
    public CGAMultivector inverse(){
        return new CGAMultivector(impl.generalInverse());
    }
    
    /**
     * Clifford conjugation.
     * 
     * The conjugation operation is the second most needed involution operation in 
     * geometric algebra.
     * 
     * @return clifford conjugate of the object.
     */
    public CGAMultivector conjugate(){
        return new CGAMultivector(impl.conjugate());
    }
    
    
    /**
     * The Duality operator implements Poincare duality, a definition and 
     * implementation that works even if the pseudoscalar of the subspace in 
     * consideration is degenerate. 
     * 
     * Keep in mind: Different to PGA direct and dual objects transform the same 
     * way!
     * 
     * It is defined for any k-vector x of an n-dimensional subspace as the n-k 
     * vector y containing all the basis vectors that are not in x. For 
     * non-degenerate metrics, you can use multiplication with the pseudoscalar 
     * if so desired (although it will be less efficient). This is not possible 
     * for CGA because of its degenerate metric.
     * 
     * @return the dual of this multivector (representing a direct/non-dual object)
     * with the correct sign
     */
    public CGAMultivector dual(){
        //FIXME
        // oder muss ich hier o und inf und I3 mit dem scale des multivectors den
        // ich dualisieren will nehmen?
        // Dorst 375
        //return lc(Ii);
        
        //  Multivector I = new Multivector(new ScaledBasisBlade((1 << M.getEigenMetric().length)-1, 1.0));
        // return ip(I._versorInverse(), M, LEFT_CONTRACTION);
        // unklar, ob die Implementation in der Version 1 für cga so 
        // auch richtig ist.
        //FIXME
        return new CGAMultivector(impl.dual());
    }
    /**
     * This method is needed, because twice application of the dual operation can 
     * produce a sign, depending on the dimensionality. This is the case for CGA.
     * 
     * @return the undual of the multivector (representing a dual object) with 
     * the correct sign.
     */
    public CGAMultivector undual(){
        return new CGAMultivector(impl.undual());//impl.dual().gp(-1));
    }
    
    public CGAMultivector euclideanDual(){
        return new CGAMultivector(this.lc(I3i).impl);
    }
    
    /**
     * Determine the square.
     * 
     * a a = a^a + a.a = a.a
     * 
     * FIXME
     * Kann es nicht Multivektoren geben die quadriert kein Skalar ergeben?
     * @return square, equals the ip results in a scalar.
     */
    public CGAMultivector /*CGAScalarOPNS*/ sqr(){
        CGAMultivector result = ip(this).compress();
        return /*new CGAScalarOPNS(*/result.compress()/*)*/;
    } 
    public double squaredNorm(){
        return impl.lengthSquared();
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
    public boolean isVector(){
       return (this.grade() == 1);
    }
    public boolean isBivector(){
        return (this.grade() == 2);
    }
    public boolean isTrivector(){
        return (this.grade() == 3);
    }
    public boolean isQuadvector(){
        return (this.grade() == 4);
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
     * Scalar product.
     * 
     * @param x
     * @return scalar product
     */
    public double scp(CGAMultivector x) {
        return impl.scp(x.impl);
    }
    
    /**
     * Inner- or dot-product.
     * 
     * The dot product implemented is per default the left contraction - without 
     * any extensions or modifications. The geometric meaning is usually 
     * formulated as the dot product between x and y gives the orthogonal
     * complement in y of the projection of x onto y.<p>
     * 
     * It can be used for determination of:
     * - the Euclidian distance bewtween two points
     * - the distance between a point an plane
     * - the decision whether a point is inside or outside of a sphere
     * 
     * The inner product is identical to the scalar product, if the arguments
     * are Euclid vectors.
     * 
     * @param x right side argument of the inner product
     * @return inner product of this with a 'x'
     */
    public CGAMultivector ip(CGAMultivector x){
        return new CGAMultivector(impl.ip(x.impl, default_ip_type));
    }
    public CGAMultivector rc(CGAMultivector x){
         return new CGAMultivector(impl.ip(x.impl, RIGHT_CONTRACTION));
    }
    /**
     * Left contraction.
     * 
     * @param x right side argument
     * @return left contraction
     */
    public CGAMultivector lc(CGAMultivector x){
         return new CGAMultivector(impl.ip(x.impl, LEFT_CONTRACTION));
    }
    public CGAMultivector gp(CGAMultivector x){
        return new CGAMultivector(impl.gp(x.impl).getCompressed());
    }
    public CGAMultivector gp(double x){
        return new CGAMultivector(impl.gp(x));
    }
    public CGAMultivector div(CGAMultivector x){
        return gp(x.inverse());
    }
    // expansion/wedge
    public CGAMultivector op(CGAMultivector x){
         return new CGAMultivector(impl.op(x.impl));
    }
    /**
     * Vee/meet or regressive product.
     * 
     * Unsed for intersection.
     * 
     * Overwrites this vee product with an optimized method if possible. The
     * default impl calculates the dual of the wedge of the duals.
     * 
     * @param x second (right side) argument of the vee product
     * @return vee product
     */
    public CGAMultivector vee(CGAMultivector x){
        //FIXME muss hier nicht dual() statt undual() stehen?
        // es scheint aber so zu funktionieren
        // a&b = !(!a^!b)
        return dual().op(x.dual()).dual();
        //TODO besser die default impl im interface aufrufen?
        //return new CGAMultivector(impl.vee(x.impl));
    }
    
    public CGAMultivector add(CGAMultivector b){
        return new CGAMultivector(impl.add(b.impl));
    }
    public CGAMultivector sub(CGAMultivector b){
        return new CGAMultivector(impl.sub(b.impl));
    }
    
    public CGAScalarOPNS abs(){
        return new CGAScalarOPNS(impl.length());
    }
    public CGAMultivector exp() {
        return new CGAMultivector(impl.exp());
    }
    /**
     * Extract a multivector which contains only components of the given grade.
     * 
     * Also called grade projection.
     * 
     * @param grade
     * @return 
     */
    public CGAMultivector extractGrade(int grade){
        return new CGAMultivector(impl.extractGrade(grade));
    }
    
    /**
     * Get the grade (subspace dimension) of the multivector, if it is homogenious, else -1.
     * 
     * The grade is the number of base vectors in a blade. A blade is a multivector
     * with includes only base vectors of the same grade.
     * 
     * @return grade of the blade or -1 if the multivector contains components
     * of different grades and therefor is not a blade. The result ranges from 0 (scalars)
     * to n = p+q (pseudoscalars, n-volumens).
     */
    public int grade(){
        return impl.grade();
    }
    /**
     * Create a normalized multivector.
     * 
     * Multivectors can have a negative squared-magnitude.  So, without 
     * introducing formally imaginary numbers, we can only fix the normalized 
     * multivector's magnitude to +-1.
     * 
     * @return the normalised multivector so that X*~X is +- 1
     */
    public CGAMultivector normalize(){
        // TODO
        // or should I use normalize2() corresponding to ganja.js?
        // mit normalize2() schlägt die Normalisierung einer line fehl, d.h. die
        // Bestimmung der length() liefert dann 0.
        return new CGAMultivector(impl.normalize().getCompressed());
        // alternativ
        //return div(createInf(-1d).ip(this));
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // return div(this.negate().ip(createInf(1d)
    }
    
    public CGAMultivector negate(){
        return this.gp(-1);
    }
    /**
     * Swapping the parity of the grade.
     * 
     * Also called main involution.
     * 
     * @return 
     */
    public CGAMultivector gradeInversion(){
        return new CGAMultivector(impl.gradeInversion());
    }
    
    @Override
    public String toString(){
        return impl.toString();
    }
    public String toString(String name){
        return name+" = ("+toString()+")";
    }
    
    
    // coordinates extraction
    
    /**
     * Extract attitude/direction from I0^einf multivector representation.
     * 
     * @return direction/attitude
     */
    protected Vector3d extractAttitudeFromEeinfRepresentation(){
        double[] coordinates = impl.extractCoordinates(2);
        //FIXME indizes hängen von der impl ab
        return new Vector3d(coordinates[12-6], coordinates[14-6], coordinates[15-6]);
    }
    /**
     * Extract attitude/direction from Bivector^einf multivector representation.
     * 
     * example: -1.9999999999999991*e1^e2^ei + 1.9999999999999991*e1^e3^ei + 1.9999999999999991*e2^e3^ei
     *
     * @return direction/attitude
     */
    protected Vector3d extractAttitudeFromBivectorEinfRepresentation(){
         double[] coordinates = impl.extractCoordinates(3);
         return new Vector3d(coordinates[9], coordinates[8], coordinates[7]);
    }
    
    
    // decompose methods
    
    public double decomposeScalar(){
        return impl.scalarPart();
    }
    
    public iCGATangentOrRound.EuclideanParameters decomposeTangentOrRound(){
        if (this instanceof iCGATangentOrRound tangentOrRound){
            return tangentOrRound.decompose();
        }
        throw new RuntimeException("CGA Multivector is not of type iCGATangentOrRound");
    }
    
    public iCGAFlat.EuclideanParameters decomposeFlat(){
        if (this instanceof iCGAFlat flat){
            return flat.decompose();
        }
        throw new RuntimeException("CGA Multivector is not of type iCGAFlat");
    }
    
    public iCGAFlat.EuclideanParameters decomposeFlat(Point3d probePoint){
        if (this instanceof iCGAFlat flat){
            return flat.decompose(probePoint);
        }
        throw new RuntimeException("CGA Multivector is not of type iCGAFlat");
    }
     
    public Vector3d decomposeAttitude(){
        if (this instanceof iCGAAttitude attitude){
            return attitude.direction();
        }
        throw new RuntimeException("CGA Multivector is not of type iCGAAttitude");
    }
    
    public Quat4d decomposeRotor(){
        if (this instanceof CGARotor rotor){
            return rotor.decompose();
        }
        throw new RuntimeException("CGA Multivector is not of type CGARotor");
    }
    
    public PointPair decomposePointPair(){
        if (this instanceof iCGAPointPair pointPair){
            return pointPair.decomposePoints();
        }        
        throw new RuntimeException("CGA Multivector is not of type iCGAPointPair");
    }
    
      /**
    Gaalop blades sequence:
    [0] Skalar
    [1] e1
    [2] e2
    [3] e3
    [4] einf
    [5] e0
    [6] e1 ^ e2
    [7] e1 ^ e3
    [8] e1 ^ einf
    [9] e1 ^ e0
    [10] e2 ^ e3
    [11] e2 ^ einf
    [12] e2 ^ e0
    [13] e3 ^ einf
    [14] e3 ^ e0
    [15] einf ^ e0
    [16] e1 ^ (e2 ^ e3)
    [17] e1 ^ (e2 ^ einf)
    [18] e1 ^ (e2 ^ e0)
    [19] e1 ^ (e3 ^ einf)
    [20] e1 ^ (e3 ^ e0)
    [21] e1 ^ (einf ^ e0)
    [22] e2 ^ (e3 ^ einf)
    [23] e2 ^ (e3 ^ e0)
    [24] e2 ^ (einf ^ e0)
    [25] e3 ^ (einf ^ e0)
    [26] e1 ^ (e2 ^ (e3 ^ einf))
    [27] e1 ^ (e2 ^ (e3 ^ e0))
    [28] e1 ^ (e2 ^ (einf ^ e0))
    [29] e1 ^ (e3 ^ (einf ^ e0))
    [30] e2 ^ (e3 ^ (einf ^ e0))
    [31] e1 ^ (e2 ^ (e3 ^ (einf ^ e0))) (Pseudoskalar)
    * 
    *  m = (1.0*eo + 2.0*e1 + 3.0*eo^e1 + 4.0*e2 + 5.0*eo^e2 + 6.0*e1^e2 + 
    *    7.0*eo^e1^e2 + 8.0*e3 + 9.0*eo^e3 + 10.0*e1^e3 + 11.0*eo^e1^e3 + 
    *    12.0*e2^e3 + 13.0*eo^e2^e3 + 14.0*e1^e2^e3 + 15.0*eo^e1^e2^e3 + 
    *    16.0*ei + 17.0*eo^ei + 18.0*e1^ei + 19.0*eo^e1^ei + 20.0*e2^ei + 
    *    21.0*eo^e2^ei + 22.0*e1^e2^ei + 23.0*eo^e1^e2^ei + 24.0*e3^ei + 
    *    25.0*eo^e3^ei + 26.0*e1^e3^ei + 27.0*eo^e1^e3^ei + 28.0*e2^e3^ei + 
    *    29.0*eo^e2^e3^ei + 30.0*e1^e2^e3^ei + 31.0*eo^e1^e2^e3^ei)
    * 
    *  [6] e1 ^ e2
    [7] e1 ^ e3
    [8] e1 ^ einf
    [9] e1 ^ e0
    *  [10] e2 ^ e3
    [11] e2 ^ einf
    [12] e2 ^ e0
    [13] e3 ^ einf
    [14] e3 ^ e0
    [15] einf ^ e0
    * 
    *  [0] Skalar
    [1] e1
    [2] e2
    [3] e3
    [4] einf
    [5] e0
         
        *  // m = (1.0*eo + 2.0*e1 + 3.0*eo^e1 + 4.0*e2 + 5.0*eo^e2 + 6.0*e1^e2 + 
         // 7.0*eo^e1^e2 + 8.0*e3 + 9.0*eo^e3 + 10.0*e1^e3 + 11.0*eo^e1^e3 + 
         // 12.0*e2^e3 + 13.0*eo^e2^e3 + 14.0*e1^e2^e3 + 15.0*eo^e1^e2^e3 + 
         // 16.0*ei + 17.0*eo^ei + 18.0*e1^ei + 19.0*eo^e1^ei + 20.0*e2^ei + 
         // 21.0*eo^e2^ei + 22.0*e1^e2^ei + 23.0*eo^e1^e2^ei + 24.0*e3^ei +
         // 25.0*eo^e3^ei + 26.0*e1^e3^ei + 27.0*eo^e1^e3^ei + 28.0*e2^e3^ei + 
         // 29.0*eo^e2^e3^ei + 30.0*e1^e2^e3^ei + 31.0*eo^e1^e2^e3^ei)
    * @param values in gaalop blades sequence
    * @return values in the apis sequence
    */
    public static double[] fromGaalop(double[] values){
        double[] result = new double[]{values[0], values[5], values[1], -values[9], values[2], -values[12], values[6], 
            values[18], values[3], -values[14], values[7],  values[20],  
            values[10],  values[23], values[16], -values[27],
            values[4], -values[15],  values[8],  values[21], values[11], 
            values[24], values[17], -values[28], values[13], 
            values[25], values[19], -values[29], values[22], 
            -values[30], values[26], values[31]};
        return result;
    }
}