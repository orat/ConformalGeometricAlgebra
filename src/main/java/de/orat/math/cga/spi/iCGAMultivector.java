package de.orat.math.cga.spi;

import de.orat.math.cga.api.CGAMultivector;
import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import de.orat.math.ga.basis.Multivector;
import org.jogamp.vecmath.Tuple3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGAMultivector {
    
    // base methods to create specific cga multivectors
    
    /**
     * Creates a multivector based on the values of all 32 blades.
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
    // the pseudo scalar is always the last blade
    default iCGAMultivector createPseudoScalar(){
        double[] values = new double[32];
        values[31] = 1d;
        return create(values);
    }
    // the scalar is always the first blade
    //TODO
    // default-impl
    public iCGAMultivector createScalar(double d);
    
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
    public iCGAMultivector ip(iCGAMultivector b, int type);
    public iCGAMultivector op(iCGAMultivector b);
    
    
    // Scalarproduct
    default double scp(iCGAMultivector x) {
	return ip(x, LEFT_CONTRACTION).scalarPart();
    }
             
    /**
     * Vee or regressive product.
     * 
     * Overwrites this vee product with an optimized method if possible. The
     * default impl caluclates the dual of the wedge of the duals.
     * 
     * @param x second argument of the vee product
     * @return vee product
     */
    default iCGAMultivector vee(iCGAMultivector x){
        return dual().op(x.dual()).undual();
    }
    
    /**
     * Computes the meet with the specified element in a common subspace.
     * 
     * @param mv the second element of the meet.
     * @return a new element from the meet with the specified element.
     */
    
    /**
     * Computes the meet with the specified element.
     * 
     * @param mv the second element of the meet.
     * @return a new element from the meet with the specified element.
     */
    default iCGAMultivector meet(iCGAMultivector mv){
        return (gp(createPseudoScalar()).ip(this, LEFT_CONTRACTION));
    }
    
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
        
    // monadic/unary operators
    
    /**
     * The inverse of the multivector even if it is not a versor (returns 0 if 
     * inverse does not exist).
     * 
     * @return the inverse of an arbitray multivector or 0 if no inverse exist.
     * 
     * TODO
     * test if it really returns 0 if no inverse exists!
     */
    default iCGAMultivector generalInverse(){
        iCGAMultivector conjugate = conjugate();
        iCGAMultivector gradeInversion = gradeInversion();
        iCGAMultivector reversion = reverse();
        iCGAMultivector negate14 = negate14();
        iCGAMultivector part = gp(conjugate).gp(gradeInversion).gp(reversion);
        double scalar = part.gp(negate14).gp(part).scalarPart();
        return conjugate.gp(gradeInversion).gp(reversion).gp(negate14).gp(part).gp(1d/scalar);
    }
        
    /**
     * An more efficient implementation can use the information that the multivector 
     * a versor.
     * 
     * @return the default implementation is identical to generalInverse()
     */
    default iCGAMultivector versorInverse(){
        return generalInverse();
    }
    
    /**
     * Negates only the signs of the vector and 4-vector parts of an multivector. 
     * 
     * Used only to implement general inverse functionality.
     * 
     * @return multivector with changed signs for vector and 4-vector parts
     */
    default iCGAMultivector negate14(){
        double[] coordinates = extractCoordinates();
        for (int i=1;i<6;i++){
            coordinates[i] = -coordinates[i];
        }
        for (int i=26;i<31;i++){
            coordinates[i] = -coordinates[i];
        }
        return create(coordinates);
    }
   
    /**
     * Computes the dual of this element.
     * 
     * @return a new element that is the dual of this element.
     */
    default iCGAMultivector dual(){
       return gp(createPseudoScalar().reverse());
    }
    
    //TODO
    // implement default implementation
    public iCGAMultivector undual();

    public double scalarPart();
    
    public boolean isNull();
    
    public iCGAMultivector conjugate();
    public iCGAMultivector reverse();
    
    // main grade involution
    public iCGAMultivector gradeInversion();
    
    public iCGAMultivector exp();
   
    /**
     * Unit under reverse norm.
     * 
     * nach Kleppe
     * normalize = {
     *   _P(1)/(sqrt(abs(_P(1)*_P(1)~)))
     * }
     *
     * @throws java.lang.ArithmeticException if multivector is null-vector
     * @return unit under 'reverse' norm (this / sqrt(abs(this.reverse(this))))
     */
    default iCGAMultivector normalize(){
        double s = scp(reverse());
        if (s == 0.0) throw new java.lang.ArithmeticException("null multivector");
        else return this.gp(1d / Math.sqrt(Math.abs(s)));
    }
    
    /** 
     * Magnitude (modulus).This is sqrt(abs(~M*M))``.
     *
     * following
     * https://github.com/pygae/clifford/blob/master/clifford/_multivector.py
     * 
     * The abs inside the sqrt is need for spaces of mixed signature.
     * 
     * @return absolute value
     */
    default double abs2(){
        return Math.sqrt(Math.abs(squaredNorm()));
    }
    
    /**
     * @return 
     */
    default double abs(){
        double result = scp(reverse());
        if (result != 0d){
            result = Math.sqrt(Math.abs(result));
        }
        return result;
    }
    /**
     * Calculate the squared euclidean norm, also called magnitude 
     * (modulus) squared.
     *
     * Note: this may be negative
     *
     * @return squared euclidean norm
     */
    default double squaredNorm(){
        //TODO warum nicht reverse statt conjugate?
        return gp(conjugate()).scalarPart();
    }
    
    //public iCGAMultivector extractGrade(int grade);
    public iCGAMultivector extractGrade(int[] G);
    
    default iCGAMultivector extractGrade(int g) {
        return extractGrade(new int[]{g});
    }
    
    /**
     * Get the grade of the multivector if it is homogenious, else -1
     * 
     * @return grade of the multivector or -1 if the multivector contains components
     * of different grades.
     */
    public int grade();
    
    //TODO
    // die folgenden Indize-Methoden taugen so nicht, besser statt dessen create/get-
    // Methoden für origin, euclid3d und inf einführen, aber ich brauche ja noch 
    // weitere für Teile von Bivektoren oder um z.B. Quaternionen zu extrahieren etc.
    // Wie bekomme ich das allgemeingültig hin?
    // irgendwie sollten alle diese Methode in die Metric-Klasse ausgelagert werden
    
    /**
     * Get the index of the basevector in the conformal vector which represents
     * the euclid x base vector.
     * 
     * @return index in the conformal vector representing the euclid x base vector
     */
    public int getEStartIndex();
    public int getEinfIndex();
    public int getOriginIndex();
    
    public double[] extractCoordinates();
    public double[] extractCoordinates(int grade);
    public void setCoordinates(int grade, double[] values);
}
