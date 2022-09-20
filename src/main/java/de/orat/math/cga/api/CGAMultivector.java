package de.orat.math.cga.api;

import de.orat.math.cga.impl1.CGA1Multivector;
import de.orat.math.cga.spi.iCGAMultivector;
import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import static de.orat.math.ga.basis.InnerProductTypes.RIGHT_CONTRACTION;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Containment:
 * x element of A, grade>=1 => x^A=0=x.A*
 * 
 * Perpendicularity:
 * grade(A)<=grade(B),A upright B => A.B=0=A^B*
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * https://github.com/pygae/clifford/blob/master/clifford/_multivector.py
 */
public class CGAMultivector {
    
    /**
     * Algebra's Precision.
     */
    //protected static double eps = 1e-12;

    private static CGAMultivector defaultInstance = new CGAMultivector();
    
    static int default_ip_type = LEFT_CONTRACTION;
    iCGAMultivector impl;
    
    CGAMultivector(){
        impl = new CGA1Multivector();
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
    public static CGAVectorE3 createE3(){
        return new CGAVectorE3(createEx(1d).add(createEy(1d)).add(createEz(1d)));
    }
    public static CGAVectorE3 createE3(Tuple3d v){
        return new CGAVectorE3(createEx(v.x).add(createEy(v.y)).add(createEz(v.z)));
    }
    public static CGAMultivector createE3Pseudoscalar(){
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
    
    // Create conformal algebra primitives
    
    //TODO
    // in eigene Klasse auslagern
    /*public static CGAVectorE3 createImaginarySphere(CGAMultivector o, double r){
        return new CGAVectorE3(o.add(createInf(0.5*r*r)));
    }*/
   
    /**
     * Create the pseudoscalar - The canonical rotor for the R41 of the conformal 
     * space vector base.
     * 
     * @return the multivector representing the pseudoscalar
     */
    public static CGAMultivector createPseudoscalar(){
        return createOrigin(1d).op(createEx(1d))
                .op(createEy(1d)).op(createEz(1d))
                .op(createInf(1d));
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
     */
    public static CGAMultivector createDualParallelepiped(Vector3d v1, Vector3d v2, Vector3d v3){
        return createE3(v1).op(createE3(v2)).op(createE3(v3));
    }
    
    
    // decompose
   
    /**
     * Determine direction/attitude from tangent or round objects.
     * 
     * Hildenbrand2004
     * 
     * @return direction/attitude
     */
    protected CGAMultivector attitudeFromTangentAndRound(){
        // see errata, dual tangend/round formula Dorst2007
        CGAMultivector einf = CGAMultivector.createInf(1d);
        CGAMultivector einfM = CGAMultivector.createInf(-1d);
        CGAMultivector result = einfM.ip(dual()).op(einf);
        System.out.println("attitude(round/attitude)="+result.toString());
        return result;
    }
    protected CGAMultivector attitudeFromDualTangentAndDualRound(){
        // see errata, dual tangend/round formula Dorst2007
        CGAMultivector einf = CGAMultivector.createInf(1d);
        CGAMultivector result = (einf.ip(this)).gp(-1d).op(einf);
        System.out.println("attitude(round/attitude)="+result.toString());
        return result;
    }
    /**
     * Determines location from tangent and round objects and also from its dual.
     * 
     * scheint für CGARound zu stimmen
     * @return location represented by a normalized sphere
     */
    protected CGAMultivector locationFromTangendAndRoundAsNormalizedSphere(){
        // corresponds to the errata of the book Dorst2007
        // mit inf = createOrigin(1d).gp(2d); funktioniert es gar nicht
        CGAMultivector inf = createInf(1d);
        
        CGAMultivector result = (this.div(inf.ip(this))).gp(-1d);
        System.out.println("locationFromTangentAndRound="+result.toString());
        return result;
    }
    /**
     * Determines location from tangend and round objects and also from its dual.
     * 
     * @return location in the euclidian part directly.
     */
    protected CGAMultivector locationFromTangendAndRound(){
        // corresponds to the errata of the book Dorst2007
        CGAMultivector inf = createInf(1d);
        CGAMultivector result = (this.gp(inf).gp(this)).div((inf.ip(this)).sqr()).gp(-0.5d);
        System.out.println("locationFromTangentAndRound2="+result.toString());
        return result;
    }
    public double squaredWeight(){
        return squaredWeight(new Point3d(0d,0d,0d));
    }
    public double squaredWeight(Point3d probePoint){
        CGARoundPoint probePointCGA = new CGARoundPoint(probePoint);
        System.out.println("probePoint(0,0,0)="+probePointCGA.toString());
        return squaredWeight(attitudeIntern(), probePointCGA);
    } 
    protected CGAMultivector attitudeIntern(){
        throw new RuntimeException("Not implemented. Available for derivative classes only!");
    }
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not implemented. Available for derivative classes only!");
    }
    /**
     * Determination of location based on default probe point at (0,0,0).
     * 
     * @return location based on probe point = (0,0,0)
     */
    public Point3d location(){
        return location(new Point3d(0d,0d,0d));
    }
   
    /**
     * Determine the squared weight of any CGA object.
     * 
     * @param attitude direction specific for the object form the multivector is representing
     * @param probePoint If not specified use e0.
     * @return squared weight
     */
    protected static double squaredWeight(CGAMultivector attitude, CGAMultivector probePoint){
        return probePoint.ip(attitude).sqr().scalarPart();
        // liefert gleiches Ergebnis
        // CGAMultivector A = probePoint.ip(attitude);
        //return A.reverse().gp(A).scalarPart();
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
    /*public CGAMultivector intersect(CGAMultivector mv2){
        return createPseudoscalar().gp(this).ip(mv2);
    }*/
    
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
    
    public CGAMultivector reverse(){
        return new CGAMultivector(impl.reverse());
    }
    //FIXME ist das korrekt
    public CGAMultivector sqr(){
        //return gp(this);
        return gp(this.reverse());
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
        throw new RuntimeException("dual() is implemented only for derivative objects!");
        //return new CGAMultivector(impl.dual());
    }
    /**
     * This method is needed, because twice application of the dual operation can 
     * produce a sign, depending on the dimensionality. This is the case for CGA.
     * 
     * @return the undual of the multivector (representing a dual object) with 
     * the correct sign.
     */
    public CGAMultivector undual(){
        throw new RuntimeException("undual() is implemented only for derivative objects!");
        //return new CGAMultivector(impl.undual());
    }
    
    
    public CGAMultivector inverse(){
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
     * It can be used for determination of:
     * - the Euclidian distance bewtween two points
     * - the distance between a point an plane
     * - the decision whether a point is inside or outside of a sphere
     * 
     * The inner product is identical to the scalar product if the arguments
     * are Euclid vectors.
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
        return gp(x.inverse());
    }
    // expansion/wedge
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
    public CGAMultivector exp() {
        return new CGAMultivector(impl.exp());
    }
    /**
     * Extract a multivector which contains only components of the given grade.
     * 
     * @param grade
     * @return 
     */
    public CGAMultivector extractGrade(int grade){
        return new CGAMultivector(impl.extractGrade(grade));
    }
    
    /**
     * Get the grade of the multivector if it is homogenious, else -1
     * 
     * The grade is the number of base vectors in a blade. A blade is a multivector
     * with includes only base vectors of the same grade.
     * 
     * @return grade of the blade or -1 if the multivector contains components
     * of different grades and therefor is not a blade.
     */
    public int grade(){
        return impl.grade();
    }
    /**
     * Multivectors can have a negative squared-magnitude.  So, without 
     * introducing formally imaginary numbers, we can only fix the normalized 
     * multivector's magnitude to +-1.
     * 
     * @return the normalised multivector so that X*~X is +- 1
     */
    public CGAMultivector normalize(){
        return new CGAMultivector(impl.normalize());
        // alternativ
        //return div(createInf(-1d).ip(this));
        // or
        // this.div(abs(this)
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // return div(this.negate().ip(createInf(1d)
    }
    public CGAMultivector negate(){
        return this.gp(-1);
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
     * Extract attitude/direction from E^einf multivector representation.
     * 
     * @return direction/attitude
     */
    protected Vector3d extractAttitudeFromEeinfRepresentation(){
        double[] coordinates = impl.extractCoordinates(2);
        //FIXME indizes hängen von der impl ab
        return new Vector3d(coordinates[12-6], coordinates[14-6], coordinates[15-6]);
    }
}