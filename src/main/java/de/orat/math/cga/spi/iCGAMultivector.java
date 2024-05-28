package de.orat.math.cga.spi;

import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import java.util.ArrayList;
import java.util.List;
import org.jogamp.vecmath.Tuple3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGAMultivector {
    
    // base methods to create specific cga multivectors
    
    /**
     * Creates a multivector based on the values of all 32 blades corresponding
     * to the implementation. 
     * 
     * @param values of 32 blades
     * @return multivector
     */
    public iCGAMultivector create(double[] values);
    public iCGAMultivector createOrigin(double scale);
    public iCGAMultivector createEx(double scale);
    public iCGAMultivector createEy(double scale);
    public iCGAMultivector createEz(double scale);
    public iCGAMultivector createInf(double scale);
    
    default iCGAMultivector createE(Tuple3d p){
        return createEx(p.x).add(createEy(p.y)).add(createEz(p.z));
    }
    /**
     * Creates the pseudo scalar.
     * 
     * @return pseudo scalar
     */
    default iCGAMultivector createI(){
        double[] values = new double[32];
        // the pseudo scalar should de always the last blade
        values[31] = 1d;
        return create(values);
    }
    
    // the scalar is always the first blade
    default iCGAMultivector createScalar(double d){
        double[] values = new double[32];
        values[0] = d;
        return create(values);
    }
    
    /**
     * Compressed version of this multivector if the multivector data structure
     * does support this. E.g. ganja does not support this, because always a
     * vector of length 32 is saved.
     * 
     * @return a compressed multivector or this.
     */
    default iCGAMultivector getCompressed(){
        return this;
    }
    
    public boolean isScalar();
    
    // unklar wie sich das implementieren lässt
    //TODO
    //public boolean isVersor();
    
    // dual operators
    
    public iCGAMultivector add (iCGAMultivector b);
    default iCGAMultivector add (double b){
        return add(createScalar(b));
    }
    public iCGAMultivector sub (iCGAMultivector b);
    default iCGAMultivector sub (double b){
        return sub(createScalar(b));
    }
    
    
    // products
   
    //TODO
    // testen ob diese default impl wirklich das gleiche liefert als die impl
    // in CGA1Multivector1a 
    default iCGAMultivector gp(double a){
        return gp(createScalar(a));
    }
    
    public iCGAMultivector gp(iCGAMultivector a);
    
    //TODO
    // implement default implementations
    /**
     * Inner product.
     * 
     * The signs of the inner product are defined by the grades of the arguments. 
     * So if the values of the arguments can be degenerated better use left-contraction
     * to have allways correct signs.
     * 
     * @param b
     * @param type
     * @return inner product
     */
    public iCGAMultivector ip(iCGAMultivector b, int type);
    public iCGAMultivector op(iCGAMultivector b);
    
    
    /**
     * Scalar product.
     * 
     * @param x
     * @return scalar product
     */
    default double scp(iCGAMultivector x) {
        if (this.grade() != x.grade()) 
            throw new IllegalArgumentException("The scalar product is defined only for two blades with the same grade!");
        
        //FIXME 
        // das gilt doch nur für R_pq also für Vektoren?
        // ist aber in der Dorst Referenzimplementierung so zu finden
	return ip(x, LEFT_CONTRACTION).scalarPart();
        // das ist vermutlich richtig, aber umständlich?
        //return gp(x).scalarPart();
    }
             
    /**
     * Vee- or regressive-product.
     * 
     * Used to intersect two objects (needs to be in an appropriate subspace)
     * in outer product null space representation.
     * 
     * Overwrites this vee product with an optimized method if possible. This
     * default impl calculates the dual of the wedge of the duals.
     * 
     * @param x second (right side) argument of the vee product
     * @return vee product (intersection object in outer product null space representation)
     */
    default iCGAMultivector vee(iCGAMultivector x){
        // a&b = !(!a^!b)
        //FIXME muss hier nicht dual() statt undual() stehen?
        // es scheint aber so zu funktionieren
        return dual().op(x.dual()).dual().gp(-1);
    }
    
    /**
     * Computes the meet with the specified element in a common subspace.
     * 
     * Da joint and meet vermutlich nur auf Blades definiert sind sollte ich 
     * die Methoden vielleicht nach CGABlade verschieben.
     * 
     * @param mv the second element of the meet.
     * @return a new element from the meet with the specified element.
     */
    
    /**
     * Computes the meet with the specified element.
     * 
     * TODO das ist doch nicht das richtige meet()?
     * @param mv the second element of the meet.
     * @return a new element from the meet with the specified element.
     */
    /*default */iCGAMultivector meet(iCGAMultivector mv);//{
     //   return (gp(createI()).ip(this, LEFT_CONTRACTION));
    //}
    
    /**
     * Computes the meet with the specified element in a common subspace.
     * 
     * @param mv1 the second element of the meet.
     * @param mv2 the element representing a common subspace.
     * @return a new element from the meet with the specified element.
     */
    default iCGAMultivector meet(iCGAMultivector mv1, iCGAMultivector mv2){
       return gp(mv2).ip(mv1, LEFT_CONTRACTION);
    }
        
    public iCGAMultivector join(iCGAMultivector mv);
    
    // monadic/unary operators
    
    
    public iCGAMultivector generalInverse();
    
    /**
     * Inverse for versors and invertable blades only.
     * 
     * Hint: It can be used only for versors or invertable blades. <br>
     * So squaredLength != 0 is needed.<p>
     * 
     * Typically this implementation is more performant than an geneneral inverse
     * implementation.<p>
     * 
     * @return inverse
     */
    default iCGAMultivector versorInverse(){
        return reverse().gp(1d/lengthSquared());
    }
    
    /**
     * Computes the inner product null space representation, if this element 
     * is of type outer product null space representation.
     * 
     * It is defined for any k-vector x of an n-dimensional subspace as the n-k 
     * vector y containing all the basis vectors that are not in x. For 
     * non-degenerate metrics, you can use multiplication with the pseudoscalar 
     * if so desired (although it will be less efficient). This is not possible 
     * for CGA because of its degenerate metric.<p>
     * 
     * The dual operation is an automorphism and almost (up to a sign) an involution.
     * 
     * @return a new element that is the dual of this element (up to a sign).
     */
    public default iCGAMultivector dual() {
        return gp(createI());
    }
    // return gp(createI().versorInverse());
    //   return gp(createI().reverse()); //???
    //   return ip(createI().versorInverse(),LEFT_CONTRACTION);
    //}
    public default iCGAMultivector undual(){
         //return ip(createI(),LEFT_CONTRACTION);
         // scheint auch falsches Vorzeichen zu haben
         // -1 ist fix für CGA
         return gp(createI()).gp(-1); // -1 wird gebraucht
    }
    
    public double scalarPart();
    
    /**
     * Test all coordinates.
     * 
     * Hint: Before the tests are done all coordinates are transforemed into the
     *       "default"-coordinate-system as defined by extractCoordinates() 
     *       implementation. In result this methods is not coordinate-independant.<p>
     * 
     * @param precision
     * @return true if all coordinates are 0 (in the range of the given precision)
     */
    default boolean isNull(double precision){
        //FIXME extractCoordinates() wechselt vorher das Koordinatensystem
        double[] coords = this.extractCoordinates();
        for (int i=0;i<coords.length;i++){
            if (Math.abs(coords[i]) > precision) return false;
        }
        return true;
    }
    
    //default boolean isBlade(){
      /*
    def isBlade(self) -> bool:
        """Returns true if multivector is a blade.
        """
        if len(self.grades()) != 1:
            return False

        return self.isVersor()
    */  
    //}
    //default boolean isVersor(){
        /*
         def isVersor(self) -> bool:
        """Returns true if multivector is a versor.
        From :cite:`ga4cs` section 21.5, definition from 7.6.4
        """
        Vhat = self.gradeInvol() // grade involution/inversion (a sign change operation)
        Vrev = ~self             // reversion
        Vinv = Vrev/(self*Vrev)[()]

        # Test if the versor inverse (~V)/(V * ~V) is truly the inverse of the
        # multivector V
        if (Vhat*Vinv).grades(eps=0.000001) != {0}:
            return False
        if not np.sum(np.abs((Vhat*Vinv).value - (Vinv*Vhat).value)) < 0.0001:
            return False

        # applying a versor (and hence an invertible blade) to a vector should
        # not change the grade
        if not all(
            (Vhat*e*Vrev).grades(eps=0.000001) == {1}
            for e in cf.basis_vectors(self.layout).values()
        ):
            return False

        return True*/
    //}
    
    
    // three involution functions:
    
    /**
     * Reverse - the most important involution.
     * 
     * The reverse is distributive: (A + B)∼ =  ̃A +  ̃B.<p<
     * 
     * (A B)∼ =  ̃B  ̃A
     * (A ∧ B)∼ =  ̃B ∧  ̃A
     * (A · B)∼ =  ̃B ·  ̃A<p>
     *
     * Note that the conjugate and reverse operation commute as their result 
     * ultimately differs by a sign.<p>
     * 
     * @return reverse
     */
    public iCGAMultivector reverse();
    
    /**
     * Grade inversion, also called main grade involution.
     * 
     * @return grade inversion
     */
    public iCGAMultivector gradeInversion();
    
    /**
     * Conjugate is reversion and additional grade involution.
     * 
     * This allows to define a magnitude for general multivectors.
     * 
     * Similar to complex conjugation, the conjugate negates those basis blades 
     * of a multivector that would square to minus one. Like the reverse, the 
     * conjugate is an involution.
     * 
     * Note that the conjugate and reverse operation commute as their result 
     * ultimately differs by a sign.
     * 
     * 
     * For Quaternionen:
     * The conjugate is useful because it has the following properties:
     *
     * qa' * qb' = (qb*qa)' In this way we can change the order of the multiplicands.
     * q * q' = a2 + b2 + c2 + d2 = real number. Multiplying a quaternion by its 
     * conjugate gives a real number. This makes the conjugate useful for finding 
     * the multiplicative inverse. For instance, if we are using a quaternion q 
     * to represent a rotation then conj(q) represents the same rotation in the reverse direction.
     * Pout = q * Pin * q' We use this to calculate a rotation transform.
     *
     * For CGA:
     * 
     * This reverses all directions in space
     *
     * A~ denotes the conjugate of A
     *
     * conjugate, reverse and dual are related as follows.
     *
     * A~= (A†)* = (A*)†
     *
     * identities
     *
     * (A * B)~ = B~* A~
     * 
     * @return clifford conjugate
     */
    default iCGAMultivector conjugate(){
        return this.reverse().gradeInversion();
    }
    
    /**
     * Unit on conjuage operation.
     * 
     * Implementation based on conjugate() instead of reverse() corresponding to
     * ganja.js.
     * 
     * @throws java.lang.ArithmeticException if multivector is null-vector
     * @return unit under 'reverse' norm (this / sqrt(length(this.reverse(this))))
     */
    default iCGAMultivector normalize2(){
        double s = length2Squared();
        if (s == 0.0) throw new java.lang.ArithmeticException("null multivector");
        else return this.gp(1d / Math.sqrt(Math.abs(s)));
    }
    /**
     * Calculate the squared euclidean norm, also called magnitude 
     * (modulus) squared or LengthSquared.
     *
     * Note: This may be negative
     * 
     * TODO
     * Warum wird das 0 für die folgende line aber nicht, wenn ich lengthSquare()
     * verwende? Darf ich überhaupt eine line normalisieren?
     * 
     * @return squared euclidean norm
     */
    default double length2Squared(){
        return gp(conjugate()).scalarPart();
    }
    /** 
     * Magnitude (modulus), absolute value or length. 
     * 
     * Implementation corresponding to ganja.js:
     * get Length (){ 
     *    return Math.sqrt(Math.abs(this.Mul(this.Conjugate).s)); 
     * };
     * 
     * @return length
     */
    default double length2(){
        double result = length2Squared();
        if (result != 0d){
            result = Math.sqrt(Math.abs(result));
        }
        return result;
    }
    
    /**
     * Unit under reverse norm.
     * 
     * Implementation based on reverse() instead of conjugate()! This is different
     * to ganja.js?<p>
     * 
     * This follows https://github.com/pygae/clifford and also Kleppe:<p>
     * 
     * normalize = {
     *   _P(1)/(sqrt(abs(_P(1)*_P(1)~)))
     * }<p>
     * 
     * Multivectors can have a negative squared-magnitude. So, without 
     * introducing formally imaginary numbers, we can only fix the normalized 
     * multivector's magnitude to +-1.<p>
     * 
     * TODO
     * kann ich das Vorzeichen nicht enfach bestimmen und gegebenenfalls hinterher
     * negate() aufrufen?
     * 
     * @throws java.lang.ArithmeticException if the multivector is a null-vector
     * @return unit under 'reverse' norm (this / sqrt(length(this.reverse(this))))
     */
    default iCGAMultivector normalize(){
        double s = lengthSquared();
        if (s == 0.0) throw new java.lang.ArithmeticException("null multivector normalization not allowed");
        // following [Kleppe2016]
        else return this.gp(1d / Math.sqrt(Math.abs(s)));
    }
    /**
     * Magnitude (modulus), absolute value or length, reverse norm
     *
     * Implementation based on reverse() instead of conjugate(). This is different
     * to ganja.js.
     *
     * following
     * https://github.com/pygae/clifford/blob/master/clifford/_multivector.py
     * 
     * The abs() inside the sqrt is need for spaces of mixed signature.
     * 
     * TODO Have we mixed signature here in CGA? If so abs() can be substituted
     * with "-", if not abs() is obsolete.
     * 
     * @return sqrt(abs(~M*M))
     */
    default double length(){
        double result = scp(reverse());
        if (result != 0d){
            result = Math.sqrt(Math.abs(result));
        }
        return result;
    }
    /**
     * Squared magnitude, squared absolute value, squared norm,
     * reverse norm or squared length.
     * 
     * Geometric Algebra: A powerful tool for solving geometric problems in visual computing
     * Leandro A. F. Fernandes, and Manuel M. Oliveira<br>
     * DOI: 10.1109/SIBGRAPI-Tutorials.2009.10<br>
     * 2009<p>
     * 
     * @return squared length
     */
    default double lengthSquared(){
        return scp(reverse());
    }
    
    //public iCGAMultivector extractGrade(int[] G);
    
    public iCGAMultivector extractGrade(int g);
    
    
    /**
     * Get the grade of the multivector if it is homogenious, else -1
     * 
     * @return grade of the multivector or -1 if the multivector contains components
     * of different grades.
     */
    public int grade();
    
    // ungetestet, not very efficient implementation
    default int[] grades(){
        List<Integer> result = new ArrayList<>();
        for (int grade=0;grade<=5;grade++){
            double[] coords = extractCoordinates(grade);
            for (int i=0;i<coords.length;i++){
                if (coords[i] != 0) {
                    result.add(grade);
                    break;
                }
            }
        }
        return result.stream().mapToInt(i->i).toArray();
    }
    default boolean isEven(){
        for (int grade: grades()){
            if (grade % 2 != 0) return false;
        }
        return true;
    }
    default boolean isOdd(){
        return !isEven();
    }
    
    
    //TODO
    // die folgenden Indize-Methoden taugen so nicht, besser statt dessen create/get-
    // Methoden für origin, euclid3d und inf einführen, aber ich brauche ja noch 
    // weitere für Teile von Bivektoren oder um z.B. Quaternionen zu extrahieren etc.
    // Wie bekomme ich das allgemeingültig hin?
    // irgendwie sollten alle diese Methode in die Metric-Klasse ausgelagert werden
    
    /**
     * Get the index of the basevector in the grade 1 part2 of the multivector 
     * which represents the euclid x base vector.
     * 
     * @return index in the conformal vector representing the euclid x base vector
     */
    public int getEStartIndex();
    
    /**
     * Extract all of the 32 coordinates in a specific CGA representation array. 
     * 
     * This includes also the components with numerical or structural 0 values.<p>
     *  
     * @return s, e0, e1, e2, e3, einf, e01, e02, e03, e0inf, e12, e13, e1inf, e23, e2inf, e3inf
     * e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, e0123, 
     * e012inf, e013inf, e023inf, e123inf, e0123inf (coordinates array of length 32)
     */
    public double[] extractCoordinates();
    /**
     * Set coordinates corresponding to extractCoordinates() - e0-einf degenerate metric.
     * 
     * @param values 
     */
    public void setCoordinates(double[] values);
    
    public double[] extractCoordinates(int grade);
    public void setCoordinates(int grade, double[] values);
    
    /**
     * Ordered list of the 32 basis blade names of the underlaying implementation.
     * 
     * The first has to correspond with the scalar and the last with the pseudoscalar.<p>
     * 
     * @return array of 32 basis blade names
     */
    public String[] basisBladeNames();
    
    public iCGAMultivector exp();
   
    //public String toString();
}
